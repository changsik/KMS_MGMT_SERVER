/**
 * RevocationReason.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * The Revocation Reason attribute is a structure used to indicate 
 * why the Managed 854 Cryptographic Object was revoked 
 * (e.g., ï¿½compromisedï¿½, ï¿½expiredï¿½, ï¿½no longer usedï¿½, etc).
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

import com.tmonet.kmip.kmipenum.EnumRevocationReasonCode;
import com.tmonet.kmip.kmipenum.EnumTag;
import com.tmonet.kmip.kmipenum.EnumType;
import com.tmonet.kmip.objects.base.Attribute;
import com.tmonet.kmip.types.KMIPTextString;

public class RevocationReason extends Attribute {
	
	public RevocationReason(){
		super(new KMIPTextString("Revocation Reason"), new EnumTag(EnumTag.RevocationReason), new EnumType(EnumType.Structure));
		this.values = new KMIPAttributeValue[2];
		this.values[0] = new KMIPAttributeValue(new EnumType(EnumType.Enumeration), new EnumTag(EnumTag.RevocationReasonCode),
				new EnumRevocationReasonCode());
		this.values[0].setName("Revocation Reason Code");
		
		this.values[1] = new KMIPAttributeValue(new EnumType(EnumType.TextString), new EnumTag(EnumTag.RevocationMessage), new KMIPTextString());
		this.values[1].setName("Revocation Message");
	}
	}
