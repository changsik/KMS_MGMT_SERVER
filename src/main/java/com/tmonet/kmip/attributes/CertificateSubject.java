/**
 * CertificateSubject.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Certificate Subject attribute is a structure used to identify 
 * the subject of a certificate.
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

public class CertificateSubject extends Attribute {
	
	public CertificateSubject(){
		super(new KMIPTextString("Certificate Subject"), new EnumTag(EnumTag.CertificateSubject), new EnumType(EnumType.Structure));
		this.values = new KMIPAttributeValue[2];
		
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.TextString), new EnumTag(EnumTag.CertificateSubjectDistinguishedName), new KMIPTextString());
		this.values[0].setName("Certificate Subject Distinguished Name");
		
		this.values[1] = new KMIPAttributeValue(new EnumType(EnumType.TextString), new EnumTag(EnumTag.CertificateSubjectAlternativeName), new KMIPTextString());
		this.values[1].setName("Certificate Subject Alternative Name");
	}
}
