/**
 * State.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * This attribute is an indication of the State of an object as 
 * known to the key management server.
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

import com.tmonet.kmip.kmipenum.EnumState;
import com.tmonet.kmip.kmipenum.EnumTag;
import com.tmonet.kmip.kmipenum.EnumType;
import com.tmonet.kmip.objects.base.Attribute;
import com.tmonet.kmip.types.KMIPTextString;
import com.tmonet.kmip.types.KMIPType;

public class State extends Attribute {
	

	public State(){
		super(new KMIPTextString("State"), new EnumTag(EnumTag.State), new EnumType(EnumType.Enumeration));
		this.values = new KMIPAttributeValue[1];
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.Enumeration), new EnumTag(EnumTag.State),
				new EnumState());
		this.values[0].setName(this.getAttributeName());
	}
	
	public State(KMIPType value){
		this();
		this.values[0].setValue(value);
	}
	
	public State(int value){
		this();
		this.values[0].setValue(new EnumState(value));
	}

}
