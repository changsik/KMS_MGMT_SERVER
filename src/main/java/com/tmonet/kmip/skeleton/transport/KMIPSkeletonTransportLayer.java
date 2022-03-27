/**
 * KMIPSkeletonTransportLayer.java
 * -------------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -------------------------------------------------------------------
 * Description:
 * The Skeleton Transport Layer provides a server thread for the 
 * client-server-communication via sockets. Each communication is 
 * executed in an own thread, handled in the KLMSServerNetworkService. 
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

package com.tmonet.kmip.skeleton.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmonet.kmip.skeleton.KMIPSkeleton;


public class KMIPSkeletonTransportLayer implements KMIPSkeletonTransportLayerInterface {

	private static final Logger logger = LoggerFactory.getLogger(KMIPSkeletonTransportLayer.class);
	
	private int port = 5555;	// default
		
	public KMIPSkeletonTransportLayer(KMIPSkeleton skeleton) {
		
		logger.info("--KLMS-Server is starting...");
		// open communication ServerSide
		try {
			// Create a ServerSocket. It waits for requests to come in over the network
			final ServerSocket serverSocket = new ServerSocket (port);
			// Creates a thread pool that creates new threads as needed. An Executor is normally used instead of explicitly creating threads.
			final ExecutorService pool = Executors.newCachedThreadPool();
			// Create and start the server thread for the Client-Server-Communication
			Thread ts = new Thread(new KLMSServerNetworkService(pool,serverSocket, skeleton));
		    ts.start();	    
			logger.info("KLMS-Server is ready to receive requests. Listening on port: " + port);	
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void setPort(String value) {
		if(value != null){
			this.port = Integer.parseInt(value);
		}
	}

}
