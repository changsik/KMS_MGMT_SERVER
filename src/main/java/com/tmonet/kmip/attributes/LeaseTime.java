/**
 * LeaseTime.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Lease Time attribute defines a time interval for a Managed 
 * Cryptographic Object beyond which the client SHALL NOT use the 
 * object without obtaining another lease. This attribute always 
 * holds the initial length of time allowed for a lease, and not the 
 * actual remaining time. Once its lease expires, the client is only 
 * able to renew the lease by calling Obtain Lease.
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
import com.tmonet.kmip.types.KMIPInterval;
import com.tmonet.kmip.types.KMIPTextString;

public class LeaseTime extends Attribute {
	
	public LeaseTime(){
		super(new KMIPTextString("Lease Time"), new EnumTag(EnumTag.LeaseTime), new EnumType(EnumType.Interval));
		this.values = new KMIPAttributeValue[1];
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.Interval), new EnumTag(EnumTag.LeaseTime), new KMIPInterval());
		this.values[0].setName(this.getAttributeName());
	}

	public LeaseTime(KMIPInterval value) {
		this();
		this.values[0].setValue(value);
	}
}
