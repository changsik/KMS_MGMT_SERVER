/**
 * CertificateIssuer.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Certificate Issuer attribute is a structure used to identify 
 * the issuer of a certificate, containing the Issuer Distinguished 
 * Name.
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

public class CertificateIssuer extends Attribute {
	
	public CertificateIssuer(){
		super(new KMIPTextString("Certificate Issuer"), new EnumTag(EnumTag.CertificateIssuer), new EnumType(EnumType.Structure));
		this.values = new KMIPAttributeValue[2];
		
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.TextString), new EnumTag(EnumTag.CertificateIssuerDistinguishedName), new KMIPTextString());
		this.values[0].setName("Certificate Issuer Distinguished Name");
		
		this.values[1] = new KMIPAttributeValue(new EnumType(EnumType.TextString), new EnumTag(EnumTag.CertificateIssuerAlternativeName), new KMIPTextString());
		this.values[1].setName("Certificate Issuer Alternative Name");
	}
}
