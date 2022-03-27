/**
 * Digest.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Digest attribute is a structure that contains the digest 
 * value of the key or secret data, certificate, or opaque object.
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

import com.tmonet.kmip.kmipenum.EnumHashingAlgorithm;
import com.tmonet.kmip.kmipenum.EnumTag;
import com.tmonet.kmip.kmipenum.EnumType;
import com.tmonet.kmip.objects.base.Attribute;
import com.tmonet.kmip.types.KMIPByteString;
import com.tmonet.kmip.types.KMIPTextString;

public class Digest extends Attribute {

	
	public Digest(){
		super(new KMIPTextString("Digest"), new EnumTag(EnumTag.Digest), new EnumType(EnumType.Structure));
		this.values = new KMIPAttributeValue[2];
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.Enumeration), new EnumTag(EnumTag.HashingAlgorithm),
				new EnumHashingAlgorithm());
		this.values[0].setName("Hashing Algorithm");
		
		this.values[1] = new KMIPAttributeValue(new EnumType(EnumType.ByteString), new EnumTag(EnumTag.DigestValue), new KMIPByteString());
		this.values[1].setName("Digest Value");
	}
}
