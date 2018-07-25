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

import fr.zapi.Broadcast;
import fr.zapi.ZbNopResponse;

/**
 * Nop
 * Découverte de la Zibase sur le réseau local
 * 
 * 
 * @author Luc Doré
 *
 */
public class Nop {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			// recherche sur le réseau local:
			ZbNopResponse zbNopResponse = Broadcast.nop();
			
 			// recherche sur un réseau spécifique:
			// ZbNopResponse zbNopResponse = Broadcast.nop("192.10.10.255"); 

			if (zbNopResponse == null)
				System.out.println("Zibase non découverte.");
			else {
				System.out.println("ZibaseId       = " + zbNopResponse.getZbHeader().getZibaseId());
				System.out.println("IP de la Zibase= " + zbNopResponse.getInetAddress().getHostAddress());
				
				// Zibase zibase = new Zibase(zbNopResponse.getInetAddress().getHostAddress());
				// ...
			}

		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

}
