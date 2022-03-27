/**
 * ObjectType.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Object Type of a Managed Object 
 * (e.g., public key, private key, symmetric key, etc)
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

import com.tmonet.kmip.kmipenum.*;
import com.tmonet.kmip.objects.base.SingleAttribute;
import com.tmonet.kmip.types.KMIPType;

public class ObjectType extends SingleAttribute<EnumObjectType> {

	public ObjectType(EnumObjectType value){
		super("Object Type",
				new EnumTag(EnumTag.ObjectType),
				new EnumType(EnumType.Enumeration),
				value);
	}

	public ObjectType(){
		this(new EnumObjectType());
	}

	public ObjectType(KMIPType value){
		this((EnumObjectType) value);
	}

	public ObjectType(int value){
		this(new EnumObjectType(value));
	}
}
