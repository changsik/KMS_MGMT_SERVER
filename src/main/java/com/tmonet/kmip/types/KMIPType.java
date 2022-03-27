/**
 * KMIPType.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * This class is the superclass of all KMIP-Types.
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

import com.tmonet.kmip.attributes.KMIPAttributeValue;

public abstract class KMIPType {
	
	public abstract void setValue(String value);

	public abstract ArrayList<Byte> toArrayList(KMIPAttributeValue value);

	public abstract int getDefaultLength();

	public abstract String getValueString();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "={" + getValueString() + "}";
	}
}
