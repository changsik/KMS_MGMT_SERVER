/**
 * ApplicationSpecificInformation.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Application Specific Information attribute is a structure 
 * used to store data specific to the application(s) using the 
 * Managed Object. It consists of the following fields: an 
 * Application Namespace and Application Data specific to that 
 * application namespace.
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

public class ApplicationSpecificInformation extends Attribute {

	
	public ApplicationSpecificInformation(){
		super(new KMIPTextString("Application Specific Information"), new EnumTag(EnumTag.ApplicationSpecificInformation), new EnumType(EnumType.Structure));
		this.values = new KMIPAttributeValue[2];
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.TextString), new EnumTag(EnumTag.ApplicationNamespace), new KMIPTextString());
		this.values[0].setName("Application Namespace");
		
		this.values[1] = new KMIPAttributeValue(new EnumType(EnumType.TextString), new EnumTag(EnumTag.ApplicationData), new KMIPTextString());
		this.values[1].setName("Application Data");
	}
		
}
