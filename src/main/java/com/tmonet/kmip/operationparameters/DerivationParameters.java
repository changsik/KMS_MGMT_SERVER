/**
 * DerivationParameters.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description for class
 * Derivation Parameters is a Structure object containing the 
 * parameters needed by the specified derivation method. It is only
 * used for the operation Derive Key. 
 *
 * The message payload is determined by the specific operation being 
 * requested or to which is being replied. There are additional 
 * parameters depending on the operation, which are placed in the 
 * package com.tmonet.kmip.operationparameters. These parameters are 
 * not defined as Attributes according to the KMIP 1.0 specification,
 * but they are handled like these. That's why they all extend the
 * superclass Attribute. 
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
package com.tmonet.kmip.operationparameters;

import com.tmonet.kmip.attributes.CryptographicParameters;
import com.tmonet.kmip.types.KMIPByteString;
import com.tmonet.kmip.types.KMIPInteger;


public class DerivationParameters {

	
	private CryptographicParameters cryptographicParameters;
	private KMIPByteString initializationVector;
	private KMIPByteString derivationData;
	
	// only for PBKDF2
	private KMIPByteString salt;
	private KMIPInteger iterationCount;


	public DerivationParameters() {
		super();
	}
	

	public DerivationParameters(CryptographicParameters cryptographicParameters, KMIPByteString initializationVector, KMIPByteString derivationData) {
		super();
		this.cryptographicParameters = cryptographicParameters;
		this.initializationVector = initializationVector;
		this.derivationData = derivationData;
	}


	

	public CryptographicParameters getCryptographicParameters() {
		return cryptographicParameters;
	}

	public void setCryptographicParameters(CryptographicParameters cryptographicParameters) {
		this.cryptographicParameters = cryptographicParameters;
	}

	public KMIPByteString getInitializationVector() {
		return initializationVector;
	}

	public void setInitializationVector(KMIPByteString initializationVector) {
		this.initializationVector = initializationVector;
	}

	public KMIPByteString getDerivationData() {
		return derivationData;
	}

	public void setDerivationData(KMIPByteString derivationData) {
		this.derivationData = derivationData;
	}
	
	public KMIPByteString getSalt() {
		return salt;
	}

	public void setSalt(KMIPByteString salt) {
		this.salt = salt;
	}

	public KMIPInteger getIterationCount() {
		return iterationCount;
	}

	public void setIterationCount(KMIPInteger iterationCount) {
		this.iterationCount = iterationCount;
	}


	
	
	public boolean hasCryptographicParameters(){
		return this.cryptographicParameters != null;
	}
	
	public boolean hasInitializationVector(){
		return this.initializationVector != null;
	}
	
	public boolean hasDerivationData(){
		return this.derivationData != null;
	}
	
	public boolean hasSalt(){
		return this.salt != null;
	}
	
	public boolean hasIterationCount(){
		return this.iterationCount != null;
	}
	
	
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Derivation Parameters");
		
		if(hasCryptographicParameters()){
			sb.append("\n"+ this.cryptographicParameters.toString());
		}
		
		if(hasInitializationVector()){
			sb.append("\nInitializationVector: "+ this.initializationVector.getValueString());
		}
		
		if(hasDerivationData()){
			sb.append("\nDerivationData: "+ this.derivationData.getValueString());
		}
		
		if(hasSalt()){
			sb.append("\nSalt: "+ this.salt.getValueString());
		}
		
		if(hasIterationCount()){
			sb.append("\nIterationCount: "+ this.iterationCount.getValueString());
		}
	
		return sb.toString();
	}
	
	
}
