/**
 * CertificateIdentifier.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Certificate Identifier attribute is a structure used to 
 * provide the identification of a 565 certificate. For X.509 
 * certificates, it contains the Issuer Distinguished Name and the 
 * Certificate Serial Number.
 *
 * @author     Stefanie Meile <stefaniemeile@gmail.com>
 * @author     Michael Guster <michael.guster@gmail.com>
 * @org.       NTB - University of Applied Sciences Buchs, (CH)
 * @copyright  Copyright © 2013, Stefanie Meile, Michael Guster
 * @license    Simplified BSD License (see LICENSE.TXT)
 * @version    1.0, 2013/08/09
 * @since      Class available since Release 1.0
 *
 * 
 */

package com.tmonet.kmip.attributes;

import com.tmonet.kmip.kmipenum.EnumTag;
import com.tmonet.kmip.kmipenum.EnumType;
import com.tmonet.kmip.objects.base.Attribute;
import com.tmonet.kmip.types.KMIPTextString;

public class CertificateIdentifier extends Attribute {
	
	public CertificateIdentifier(){
		super(new KMIPTextString("Certificate Identifier"), new EnumTag(EnumTag.CertificateIdentifier), new EnumType(EnumType.Structure));
		this.values = new KMIPAttributeValue[2];
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.TextString), new EnumTag(EnumTag.Issuer), new KMIPTextString());
		this.values[0].setName("Issuer");
		
		this.values[1] = new KMIPAttributeValue(new EnumType(EnumType.TextString), new EnumTag(EnumTag.SerialNumber), new KMIPTextString());
		this.values[1].setName("Serial Number");
	}
}
