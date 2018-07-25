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
 * UnregisterSample 
 * 
 * 
 * 
 * @author Luc Doré
 */
public class UnregisterSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			System.out.println("connexion à la Zibase...");
			Zibase zibase = new Zibase("192.168.0.44"); 

			String host = "localhost";
			int port = 9876;				// fournir le même numéro de port qu'à l'enregistrement 

			System.out.println("unregister sur la Zibase...");
			zibase.hostUnregistering(host, port);

			System.out.println("terminé avec succès!");

		} catch (Throwable th) {
			System.out.println("EN ERREUR:");
			th.printStackTrace();
		}
	}

}
