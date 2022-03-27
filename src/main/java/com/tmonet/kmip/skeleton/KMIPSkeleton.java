/**
 * KMIPSkeleton.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Skeleton encapsulates the whole KMIP functionality of the
 * server side. It has an encoder pool, a decoder pool and an 
 * adapter to the KLMS. To process a request, it decodes the 
 * request, processes the separated batches of the request via the
 * adapter to the KLMS, encodes and returns the response.  
 *
 * @author     Stefanie Meile <stefaniemeile@gmail.com>
 * @author     Michael Guster <michael.guster@gmail.com>
 * @org.       NTB - University of Applied Sciences Buchs, (CH)
 * @copyright  Copyright ï¿½ 2013, Stefanie Meile, Michael Guster
 * @license    Simplified BSD License (see LICENSE.TXT)
 * @version    1.0, 2013/08/09
 * @since      Class available since Release 1.0
 *
 * 
 */

package com.tmonet.kmip.skeleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmonet.kmip.config.ContextProperties;
import com.tmonet.kmip.container.KMIPBatch;
import com.tmonet.kmip.container.KMIPContainer;
import com.tmonet.kmip.kmipenum.EnumResultReason;
import com.tmonet.kmip.kmipenum.EnumResultStatus;
import com.tmonet.kmip.process.decoder.*;
import com.tmonet.kmip.process.encoder.*;
import com.tmonet.kmip.skeleton.transport.KMIPSkeletonTransportLayerInterface;
import com.tmonet.kmip.utils.KMIPUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Skeleton encapsulates the whole KMIP functionality of the
 * server side. It has an encoder pool, a decoder pool and an 
 * adapter to the KLMS. To process a request, it decodes the 
 * request, processes the separated batches of the request via the
 * adapter to the KLMS, encodes and returns the response.  
 */
public class KMIPSkeleton implements KMIPSkeletonInterface{

	private static final Logger logger = LoggerFactory.getLogger(KMIPSkeleton.class);
	
	private static final String DEFAULT_LOCATION_ENCODER = "com.tmonet.kmip.process.encoder.KMIPEncoder";
	private static final String DEFAULT_LOCATION_DECODER = "com.tmonet.kmip.process.decoder.KMIPDecoder";
	private static final String DEFAULT_LOCATION_ADAPTER = "com.tmonet.klms.KLMSAdapter";
	private static final String DEFAULT_LOCATION_KLMS = "com.tmonet.klms.KLMS";
	private static final String DEFAULT_LOCATION_TRANSPORTLAYER = "com.tmonet.kmip.skeleton.transport.KMIPSkeletonTransportLayer";

	private KLMSAdapterInterface klmsAdapter;
	private KMIPEncoderPool encoderPool;
	private KMIPDecoderPool decoderPool;

	/**
	 * This constructor is used to instantiate the skeleton by a middle-ware. 
	 * 
	 * @param klmsAdapter : 	the <code>KLMSAdapterInterface</code>
	 * @param encoderPath : 	the fully qualified name of the Encoder as a <code>String</code>, defined in the "web.xml"-file as 
	 * 							context-parameter. (e.g.: <code>com.tmonet.kmip.process.encoder.KMIPEncoder</code>)
	 * @param decoderPath : 	the fully qualified name of the Decoder as a <code>String</code>, defined in the "web.xml"-file as 
	 * 							context-parameter. (e.g.: see encoderPath)
	 */
	public KMIPSkeleton(KLMSAdapterInterface klmsAdapter, String encoderPath, String decoderPath){
		this.klmsAdapter = klmsAdapter;
		this.encoderPool = new KMIPEncoderPool(encoderPath,  DEFAULT_LOCATION_ENCODER);
		this.decoderPool = new KMIPDecoderPool(decoderPath, DEFAULT_LOCATION_DECODER);
	}
	
	/**
	 * This constructor is used to instantiate the skeleton, using a self-made Transport-Layer, which implements the KMIPSkeletonTransportLayerInterface. 
	 */
	public KMIPSkeleton() {
		super();
		
		try {
			String xmlPath = this.getClass().getResource("config/").getPath();
			ContextProperties props = new ContextProperties(xmlPath, "SkeletonConfig.xml");
			
			this.klmsAdapter = (KLMSAdapterInterface) getClass(props.getProperty("Adapter"), DEFAULT_LOCATION_ADAPTER).newInstance();
			this.klmsAdapter.setKLMS(props.getProperty("KLMS"), DEFAULT_LOCATION_KLMS);
			
			this.encoderPool = new KMIPEncoderPool(props.getProperty("Encoder"), DEFAULT_LOCATION_ENCODER);
			this.decoderPool = new KMIPDecoderPool(props.getProperty("Decoder"), DEFAULT_LOCATION_DECODER);
		
			setTransportLayer(props);
			
		} catch (Exception e){
			logger.error("Error while initializing KMIPSkeleton");
			e.printStackTrace();
		}
		
	}
	
	private void setTransportLayer(ContextProperties props) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		KMIPSkeletonTransportLayerInterface transportLayer;
		Class<?> transportLayerClass = getClass(props.getProperty("TransportLayer"), DEFAULT_LOCATION_TRANSPORTLAYER);
		Constructor<?> transportLayerConstructor = transportLayerClass.getConstructor(this.getClass());
		transportLayer = (KMIPSkeletonTransportLayerInterface) transportLayerConstructor.newInstance(this);
		transportLayer.setPort(props.getProperty("Port"));
	}

	private Class<?> getClass(String path, String defaultPath) throws ClassNotFoundException {
		return Class.forName(KMIPUtils.getClassPath(path, defaultPath));
	}
	

	public ArrayList<Byte> processRequest(ArrayList<Byte> request) {
		KMIPEncoderInterface encoder = getEncoder();
		KMIPDecoderInterface decoder = getDecoder();
		
		ArrayList<Byte> response = createResponse(request, decoder);
		
		encoderPool.returnEncoder(encoder);
		decoderPool.returnDecoder(decoder);

		return response;
	}

	private ArrayList<Byte> createResponse(ArrayList<Byte> request, KMIPDecoderInterface decoder){
		KMIPContainer responseContainer = new KMIPContainer();
		KMIPContainer requestContainer = decodeRequest(decoder, request);
		
		for(int i = 0; i < requestContainer.getBatchCount(); i++){
			responseContainer.addBatch(processRequestBatch(requestContainer.getBatch(i), requestContainer));
		}
		
		responseContainer.calculateBatchCount();
		ArrayList<Byte> response = encodeResponse(responseContainer);
		
		if(requestContainer.hasMaximumResponseSize() && response.size() > requestContainer.getMaximumResponseSize().getValue()){
			response = createMaxResponseSizeResponse(response, requestContainer);
		} 
		return response;
	}

	private KMIPEncoderInterface getEncoder(){
		try {
			return encoderPool.getEncoder();
		} catch (KMIPEncoderPoolOverflowException e) {
			logger.error("Encoder Pool Overflow");
			e.printStackTrace();
		}
		return null;
	}
	
	private KMIPDecoderInterface getDecoder(){
		try {
			return decoderPool.getDecoder();
		} catch (KMIPDecoderPoolOverflowException e) {
			logger.error("Decoder Pool Overflow");
			e.printStackTrace();
		}
		return null;
	}
	
	private KMIPContainer decodeRequest(KMIPDecoderInterface decoder, ArrayList<Byte> request){
		try{
			return decoder.decodeRequest(request);
		}catch (Exception e){
			logger.error("Error while decoding Request!");
			e.printStackTrace();
		}
		return null;
	}

	private KMIPBatch processRequestBatch(KMIPBatch batch, KMIPContainer requestContainer) {
		boolean asynchronousIndicator = false;
		if(requestContainer.hasAsynchronousIndicator() && requestContainer.getAsynchronousIndicator().getValue()){
			asynchronousIndicator = true;
		}
		
		if(requestContainer.hasAuthentication()){
			return klmsAdapter.doProcess(batch, requestContainer.getAuthentication().getCredential(), asynchronousIndicator);
		} else{
			return klmsAdapter.doProcess(batch, null, asynchronousIndicator);
		}
	}
	

	private ArrayList<Byte> createMaxResponseSizeResponse(ArrayList<Byte> response, KMIPContainer requestContainer) {
		KMIPContainer responseContainer = new KMIPContainer();
		KMIPBatch batch = new KMIPBatch();
		batch.setResultStatus(new EnumResultStatus(EnumResultStatus.OperationFailed));
		batch.setResultReason(new EnumResultReason(EnumResultReason.ResponseTooLarge));
		batch.setResultMessage("Response size: " + response.size() + ", Maximum Response Size indicated in request: " + requestContainer.getMaximumResponseSize().getValue());
		responseContainer.addBatch(batch);	
		responseContainer.calculateBatchCount();
		return encodeResponse(responseContainer);
	}

	
	
	private ArrayList<Byte> encodeResponse(KMIPContainer container) {
		ArrayList<Byte> response;
		KMIPEncoderInterface encoder;
		try {
			encoder = encoderPool.getEncoder();
			response = encoder.encodeResponse(container);	
			encoderPool.returnEncoder(encoder);
			return response;
		} catch (KMIPEncoderPoolOverflowException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns a <code>HashMap{@literal <}String, String{@literal >}</code>, which contains status information of the KLMS.
	 * Each entry has an information-description and an information-value. (e.g.: <code>status.put("Loaded Encoder", encoderPool.getLoadedEncoder());</code>)
	 * 
	 * @return	<code>HashMap{@literal <}String, String{@literal >}</code>
	 */
	public HashMap<String, String> getStatus(){
		HashMap<String, String> status = klmsAdapter.getStatus();
		status.put("Loaded Encoder", encoderPool.getLoadedEncoder());
		status.put("Loaded Decoder", decoderPool.getLoadedDecoder());
		return status;
	}
	

}
