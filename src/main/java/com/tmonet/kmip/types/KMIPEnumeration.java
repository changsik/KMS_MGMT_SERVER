/**
 * KMIPEnumeration.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * This class encapsulates the concrete KMIPType, Enumeration.
 * Enumerations are encoded as four-byte long (32 bit) binary 
 * unsigned numbers transmitted big-endian. Extensions, which are 
 * permitted, but are not defined in this specification, contain the 
 * value 8 hex in the first nibble of the first byte.
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

package com.tmonet.kmip.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.tmonet.kmip.attributes.KMIPAttributeValue;
import com.tmonet.kmip.kmipenum.KMIPEnumUndefinedKeyException;
import com.tmonet.kmip.kmipenum.KMIPEnumUndefinedValueException;


public abstract class KMIPEnumeration extends KMIPType{

	private final int defaultLength = 4;

	protected Entry<String, Integer> value; 

	public abstract void setValue(String value);


	
	public ArrayList<Byte> toArrayList(KMIPAttributeValue attributeValue) {
		int val = value.getValue();
		ArrayList<Byte> value = new ArrayList<>();
		value.add((byte) (val >> 24));
		value.add((byte) (val >> 16));
		value.add((byte) (val >> 8));
		value.add((byte) val);
		value.add((byte) 0x00);
		value.add((byte) 0x00);
		value.add((byte) 0x00);
		value.add((byte) 0x00);
		attributeValue.setLength(defaultLength);
		return value;
	}


	public int getDefaultLength() {
		return defaultLength;
	}

	public String getValueString() {
		return Integer.toString(value.getValue());
	}
	
	protected Entry<String, Integer> getEntry(int value, HashMap<String, Integer> values) throws KMIPEnumUndefinedValueException{
		for (Map.Entry<String, Integer> e : values.entrySet()){
		    if(e.getValue()==value){
		    	return e;
		    }
		}
		throw new KMIPEnumUndefinedValueException(value, this.getClass().getName());
	}
	
	protected Entry<String, Integer> getEntry(String key, HashMap<String, Integer> values) throws KMIPEnumUndefinedKeyException{
		for (Map.Entry<String, Integer> e : values.entrySet()){
			if (e.getKey().toUpperCase().equals(key.toUpperCase())) {
				return e;
			}
		}
		throw new KMIPEnumUndefinedKeyException(key, this.getClass().getName());
	}
	
	public String getKey(){
		return value.getKey();
	}
	
	public int getValue(){
		return value.getValue();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "{" + getKey() + "," + getValue() + "}";
	}
}

