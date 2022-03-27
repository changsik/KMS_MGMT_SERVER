/**
 * KMIPStub.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Stub encapsulates the whole KMIP functionality of the
 * client side. To process a request, it encodes the request, 
 * sends it to the server over the transport layer, and finally 
 * decodes and returns the response.  
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

package com.tmonet.kmip.stub;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmonet.kmip.config.ContextProperties;
import com.tmonet.kmip.container.KMIPContainer;
import com.tmonet.kmip.process.decoder.KMIPDecoderInterface;
import com.tmonet.kmip.process.encoder.KMIPEncoderInterface;
import com.tmonet.kmip.stub.transport.KMIPStubTransportLayerInterface;
import com.tmonet.kmip.test.UCStringCompare;
import com.tmonet.kmip.utils.KMIPUtils;

/**
 * The KMIPStubInterface is the interface for all stubs. It 
 * provides the needful flexibility for the interchangeability of 
 * the stub.
 * The Stub encapsulates the whole KMIP functionality of the
 * server side. To process a request, it offers two superimposed 
 * methods:
 * <ul>
 * 	<li><code>processRequest(KMIPContainer c)</code> for common use</li>
 * 	<li><code>processRequest(KMIPContainer c, String expectedTTLVRequest, String expectedTTLVResponse)</code> for test cases</li>
 * </ul>
 */
public class KMIPStub implements KMIPStubInterface {

	private static final Logger logger = LoggerFactory.getLogger(KMIPStub.class);
	
	private KMIPEncoderInterface encoder;
	private KMIPDecoderInterface decoder;
	private KMIPStubTransportLayerInterface transportLayer;
	
	
	public KMIPStub(File configFile) {
		super();
		
		try {
		    ContextProperties props = new ContextProperties(configFile);

		    this.encoder = (KMIPEncoderInterface) getClass(props.getProperty("Encoder"), "com.tmonet.kmip.process.encoder.KMIPEncoder").newInstance();
		    this.decoder = (KMIPDecoderInterface) getClass(props.getProperty("Decoder"), "com.tmonet.kmip.process.decoder.KMIPDecoder").newInstance();
		    this.transportLayer = (KMIPStubTransportLayerInterface) getClass(props.getProperty("TransportLayer"), "com.tmonet.kmip.stub.transport.KMIPStubTransportLayerHTTP").newInstance();
		    this.transportLayer.setTargetHostname(props.getProperty("TargetHostname"));
			String keyStorePW = props.getProperty("KeyStorePW");
			this.transportLayer.setKeyStorePW(keyStorePW);
			String keyStoreLocation = props.getProperty("KeyStoreLocation");
			File keyStoreFile = new File(keyStoreLocation);
			if (!keyStoreFile.isFile()) {
				// Doesn't exist yet, create a empty keystore
				keyStoreFile.getParentFile().mkdirs();
				KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
				ks.load(null, keyStorePW.toCharArray());
				FileOutputStream fos = new FileOutputStream(keyStoreFile);
				ks.store(fos, keyStorePW.toCharArray());
				fos.close();
			}
	    	this.transportLayer.setKeyStoreLocation(keyStoreFile.getAbsolutePath());

	    	UCStringCompare.testingOption = props.getIntProperty("Testing");
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private Class<?> getClass(String path, String defaultPath) throws ClassNotFoundException {
		return Class.forName(KMIPUtils.getClassPath(path, defaultPath));
	}
	
	/**
	 * Processes a KMIP-Request-Message stored in a <code>KMIPContainer</code> and returns a corresponding KMIP-Response-Message.
	 * 
	 * @param c :      	the <code>KMIPContainer</code> to be encoded and sent. 
	 * @return			<code>KMIPContainer</code> with the response objects.
	 */
	public KMIPContainer processRequest(KMIPContainer c){
		ArrayList<Byte> ttlv = encoder.encodeRequest(c);
		ArrayList<Byte> responseFromServer = transportLayer.send(ttlv);
		return decodeResponse(responseFromServer);
	}
	
	/**
	 * Processes a KMIP-Request-Message stored in a <code>KMIPContainer</code> and returns a corresponding KMIP-Response-Message.
	 * For test cases, there are two additional parameters that may be set by the caller. The idea is, that the generated TTLV-Strings 
	 * can be compared to the expected TTLV-Strings. 
	 * 
	 * @param c :      	the <code>KMIPContainer</code> to be encoded and sent. 
	 * @param expectedTTLVRequest :      	the <code>String</code> to be compared to the encoded request message. 
	 * @param expectedTTLVResponse :      	the <code>String</code> to be compared to the decoded response message. 
	 * @return			<code>KMIPContainer</code> with the response objects.
	 */
	public KMIPContainer processRequest(KMIPContainer c, String expectedTTLVRequest, String expectedTTLVResponse){
		// encode Request
		ArrayList<Byte> ttlv = encoder.encodeRequest(c);
		logger.info("Encoded Request from Client: (actual/expected)");
		KMIPUtils.printArrayListAsHexString(ttlv);
		logger.debug(expectedTTLVRequest);
		UCStringCompare.checkRequest(ttlv, expectedTTLVRequest);
		
		// send Request and check Response
		ArrayList<Byte> responseFromServer = transportLayer.send(ttlv);
		logger.info("Encoded Response from Server: (actual/expected)");
		KMIPUtils.printArrayListAsHexString(responseFromServer);
		logger.debug(expectedTTLVResponse);
		UCStringCompare.checkResponse(responseFromServer,expectedTTLVResponse);
		return decodeResponse(responseFromServer);
	}
	
	private KMIPContainer decodeResponse(ArrayList<Byte> responseFromServer){
		try {
			return decoder.decodeResponse(responseFromServer);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

		
}
