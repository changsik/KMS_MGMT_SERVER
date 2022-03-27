/**
 * CryptographicDomainParameters.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Cryptographic Domain Parameters attribute is a structure 
 * that contains a set of 549 OPTIONAL fields that MAY need to be 
 * specified in the Create Key Pair Request Payload.
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

import com.tmonet.kmip.kmipenum.EnumRecommendedCurve;
import com.tmonet.kmip.kmipenum.EnumTag;
import com.tmonet.kmip.kmipenum.EnumType;
import com.tmonet.kmip.objects.base.Attribute;
import com.tmonet.kmip.types.KMIPInteger;
import com.tmonet.kmip.types.KMIPTextString;

public class CryptographicDomainParameters extends Attribute {
	
	public CryptographicDomainParameters(){
		super(new KMIPTextString("Cryptographic Domain Parameters"), new EnumTag(EnumTag.CryptographicDomainParameters), new EnumType(EnumType.Structure));
		this.values = new KMIPAttributeValue[2];
		
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.Integer), new EnumTag(EnumTag.Qlength), new KMIPInteger());
		this.values[0].setName("Qlength");
		
		this.values[1] = new KMIPAttributeValue(new EnumType(EnumType.Enumeration), new EnumTag(EnumTag.RecommendedCurve), new EnumRecommendedCurve());
		this.values[1].setName("Recommended Curve");
	}
	
}
