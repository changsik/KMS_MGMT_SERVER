/**
 * CryptographicLength.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * Cryptographic Length is the length in bits of the clear-text 
 * cryptographic key material of the Managed Cryptographic Object.
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

package com.tmonet.kmip.attributes;

import com.tmonet.kmip.kmipenum.EnumTag;
import com.tmonet.kmip.kmipenum.EnumType;
import com.tmonet.kmip.objects.base.SingleAttribute;
import com.tmonet.kmip.types.KMIPInteger;
import com.tmonet.kmip.types.KMIPType;


public class CryptographicLength extends SingleAttribute<KMIPInteger> {

	public CryptographicLength(KMIPInteger value){
		super("Cryptographic Length",
				new EnumTag(EnumTag.CryptographicLength),
				new EnumType(EnumType.Integer),
				value);
	}

	public CryptographicLength(){
		this(new KMIPInteger());
	}

	public CryptographicLength(KMIPType value){
		this((KMIPInteger)value);
	}
	
	public CryptographicLength(int value){
		this(new KMIPInteger(value));
	}
	
}
