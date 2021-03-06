/**
 * KeyBlock.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * A Key Block object is a structure used to encapsulate all of the 
 * information that is closely associated with a cryptographic key. 
 * It contains a Key Value of the defined Key Format Types. 
 * The Key Block MAY contain the Key Compression Type, which 
 * indicates the format of the elliptic curve public key.
 * The Key Block also has the Cryptographic Algorithm and the 
 * Cryptographic Length of the key contained in the Key Value field.
 * The Key Block SHALL contain a Key Wrapping Data structure if the 
 * key in the Key Value field is wrapped.
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

package com.tmonet.kmip.objects.base;

import java.util.ArrayList;

import com.tmonet.kmip.attributes.CryptographicAlgorithm;
import com.tmonet.kmip.attributes.CryptographicLength;
import com.tmonet.kmip.kmipenum.EnumKeyCompressionType;
import com.tmonet.kmip.kmipenum.EnumKeyFormatType;
import com.tmonet.kmip.kmipenum.EnumTag;

public class KeyBlock extends BaseObject {

	// Attributes
	private CryptographicLength cryptographicLength;
	private CryptographicAlgorithm cryptographicAlgorithm;
	
	// Base Objects
	private KeyValue keyValue;
	private KeyWrappingData keyWrappingData;
	
	// KMIP Types
	private EnumKeyCompressionType keyCompressionType;
	private EnumKeyFormatType keyFormatType;
	
	public KeyBlock(KeyValue keyValue) {
		this();
		this.keyValue = keyValue;
	}

	public KeyBlock() {
		super(new EnumTag(EnumTag.KeyBlock));
	}

	public void addAttribute(Attribute a) {
		if(a instanceof CryptographicLength){
			this.cryptographicLength = (CryptographicLength) a;
		} else if(a instanceof CryptographicAlgorithm){
			this.cryptographicAlgorithm = (CryptographicAlgorithm) a;
		}
	}
	
	// Getters & Setters
	public void setKeyFormatType(String string) {
		this.keyFormatType = new EnumKeyFormatType(string);
	}
	
	public void setKeyFormatType(EnumKeyFormatType keyFormatType) {
		this.keyFormatType = keyFormatType;
	}

	public void setKeyCompressionType(String string) {
		this.keyCompressionType = new EnumKeyCompressionType(Integer.parseInt(string));
	}

	public void setKeyValue(KeyValue keyValue) {
		this.keyValue = keyValue;
	}

	public void setCryptographicAlgorithm(Attribute cryptographicAlgorithm) {
		this.cryptographicAlgorithm = (CryptographicAlgorithm) cryptographicAlgorithm;
	}
	
	public void setCryptographicLength(Attribute cryptographicLength) {
		this.cryptographicLength = (CryptographicLength) cryptographicLength;
	}

	public void setKeyWrappingData(KeyWrappingData keyWrappingData) {
		this.keyWrappingData = keyWrappingData;
	}


	public CryptographicLength getCryptographicLength() {
		return cryptographicLength;
	}

	public CryptographicAlgorithm getCryptographicAlgorithm() {
		return cryptographicAlgorithm;
	}

	public KeyValue getKeyValue() {
		return keyValue;
	}

	public KeyWrappingData getKeyWrappingData() {
		return keyWrappingData;
	}

	public EnumKeyCompressionType getKeyCompressionType() {
		return keyCompressionType;
	}

	public EnumKeyFormatType getKeyFormatType() {
		return keyFormatType;
	}
	
	public ArrayList<Attribute> getAttributes() {
		ArrayList<Attribute> attributes = new ArrayList<>();
		if(hasCryptographicAlgorithm()){
			attributes.add(cryptographicAlgorithm);
		}
		if(hasCryptographicLength()){
			attributes.add(cryptographicLength);
		}
		return attributes;
	}
	
	
	// has Methods
	public boolean hasCryptographicLength(){
		return this.cryptographicLength != null;
	}
	
	public boolean hasCryptographicAlgorithm(){
		return this.cryptographicAlgorithm != null;
	}
	
	public boolean hasKeyValue(){
		return this.keyValue != null;
	}
	
	public boolean hasKeyWrappingData(){
		return this.keyWrappingData != null;
	}
	
	public boolean hasKeyCompressionType(){
		return this.keyCompressionType != null;
	}
	
	public boolean hasKeyFormatType(){
		return this.keyFormatType != null;
	}
	
	// toString Method
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Key Block: ");
		sb.append("\t" + "Key Format Type [" + keyFormatType.getKey() + "]");
		if(keyCompressionType != null){
			sb.append("\n" + "Key Compression Type [" + keyCompressionType.getKey() + "]");
		}
		sb.append(keyValue.toString());
		if(keyWrappingData != null){
			sb.append(keyWrappingData.toString());
		}
		sb.append("\n" + cryptographicLength.toString());
		sb.append("\n" + cryptographicAlgorithm.toString());
		
		return sb.toString();
	}
	
}
