/**
 * ActivationDate.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * This is the date and time when the Managed Cryptographic Object 
 * MAY begin to be used.
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
import com.tmonet.kmip.types.KMIPDateTime;
import com.tmonet.kmip.types.KMIPTextString;
import com.tmonet.kmip.types.KMIPType;

public class ActivationDate extends Attribute {
	
	public ActivationDate(){
		super(new KMIPTextString("Activation Date"), new EnumTag(EnumTag.ActivationDate), new EnumType(EnumType.DateTime));
		this.values = new KMIPAttributeValue[1];
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.DateTime), new EnumTag(EnumTag.ActivationDate), new KMIPDateTime());
		this.values[0].setName(this.getAttributeName());
	}
	
	public ActivationDate(KMIPType value){
		this();
		this.values[0].setValue(value);
	}

}
