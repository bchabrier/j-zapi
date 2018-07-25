/*
	j-zapi - Une implementation Java de l'API de la Zibase
    Copyright (C) 2012 Luc Doré luc.dore@free.fr

	This library is free software; you can redistribute it and/or modify 
	it under the terms of the GNU Lesser General Public License as published by 
	the Free Software Foundation; either version 2.1 of the License, or 
	(at your option) any later version.

	This library is distributed in the hope that it will be useful, 
	but WITHOUT ANY WARRANTY; without even the implied warranty of 
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
	See the GNU Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public License 
	along with this library; if not, write to the 
	Free Software Foundation, Inc., 59 Temple Place, Suite 330, 
	Boston, MA 02111-1307 USA
*/

package fr.zapi.samples;

import fr.zapi.Zibase;

/**
 * GetState
 * Permet de récupérer l'état d'un actionneur 
 * Testé avec un module Chacon DI-O 
 * 
 * 
 * @author Luc Doré
 *
 */
public class GetState {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Zibase zibase = new Zibase("192.168.0.44");
			
			// récupération de l'état de mon module Chacon   
			System.out.println("state A10=" + (zibase.getState("A10")?"ON":"OFF"));
			System.out.println("state A12=" + (zibase.getState("A12")?"ON":"OFF"));
		
			
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

}
