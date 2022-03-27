/**
 * ProcessStartDate.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * This is the date and time when a Managed Symmetric Key Object 
 * MAY begin to be used to process cryptographically-protected 
 * information (e.g., decryption or unwrapping), depending on the 
 * value of its Cryptographic Usage Mask attribute.
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

public class ProcessStartDate extends Attribute {

	public ProcessStartDate(){
		super(new KMIPTextString("Process Start Date"), new EnumTag(EnumTag.ProcessStartDate), new EnumType(EnumType.DateTime));
		this.values = new KMIPAttributeValue[1];
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.DateTime), new EnumTag(EnumTag.ProcessStartDate), new KMIPDateTime());
		this.values[0].setName(this.getAttributeName());
	}
	
	public ProcessStartDate(KMIPType value){
		this();
		this.values[0].setValue(value);
	}
}