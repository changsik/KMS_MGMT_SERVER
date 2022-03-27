/**
 * CryptographicAlgorithm.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Cryptographic Algorithm used by the object 
 * (e.g., RSA, DSA, DES, 3DES, AES, etc).
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

import com.tmonet.kmip.kmipenum.EnumCryptographicAlgorithm;
import com.tmonet.kmip.kmipenum.EnumTag;
import com.tmonet.kmip.kmipenum.EnumType;
import com.tmonet.kmip.objects.base.Attribute;
import com.tmonet.kmip.objects.base.SingleAttribute;
import com.tmonet.kmip.types.KMIPTextString;
import com.tmonet.kmip.types.KMIPType;

public class CryptographicAlgorithm extends SingleAttribute<EnumCryptographicAlgorithm> {

	public CryptographicAlgorithm(EnumCryptographicAlgorithm value){
		super("Cryptographic Algorithm",
				new EnumTag(EnumTag.CryptographicAlgorithm),
				new EnumType(EnumType.Enumeration),
				value);
	}

	public CryptographicAlgorithm(){
		this(new EnumCryptographicAlgorithm());
	}

	public CryptographicAlgorithm(int value){
		this(new EnumCryptographicAlgorithm(value));
	}

	public CryptographicAlgorithm(KMIPType type) {
		this((EnumCryptographicAlgorithm) type);
	}
}
