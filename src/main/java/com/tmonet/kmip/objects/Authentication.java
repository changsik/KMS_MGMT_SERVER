/**
 * Authentication.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description:
 * This is used to authenticate the requester. It is an OPTIONAL 
 * information item, depending on the type of request being issued 
 * and on server policies. The Authentication structure SHALL 
 * contain a Credential structure.
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
package com.tmonet.kmip.objects;

import com.tmonet.kmip.kmipenum.EnumCredentialType;
import com.tmonet.kmip.objects.base.Credential;


public class Authentication {

	private Credential credential;

	public Authentication(Credential credential) {
		this.credential = credential;
	}

	
	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Authentication =\t");
		sb.append("\nCredential = ").append(credential);
		return  sb.toString();
	}

	public static Authentication fromCreds(String user, String password) {
		return new Authentication(
			new Credential(
					new EnumCredentialType(EnumCredentialType.UsernameAndPassword),
					new CredentialValue(user, password)));
	}
	
}
