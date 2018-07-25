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

import java.io.IOException;
import java.net.UnknownHostException;

import fr.zapi.Zibase;

/**
 * RegisterSample 
 * 
 * 
 * 
 * @author Luc Doré
 */
public class RegisterSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			System.out.println("connexion à la Zibase...");
			Zibase zibase = new Zibase("192.168.0.44");

			System.out.println("register sur la Zibase...");

			String host = "localhost";
			int localport = 9876;				// choisir un port libre

			System.out.println("zibase registering...");
			zibase.hostRegistering(host, localport);

			// maintenant le système enregistré va recevoir tous les messages émis par la Zibase
			// cf. ListenZibase.java


		} catch (UnknownHostException exc) {
			exc.printStackTrace();

		} catch (IOException exc) {
			exc.printStackTrace();


			// System.out.println("unregister sur la Zibase...");
			// zibase.hostUnregistering(host, port);

			System.out.println("terminé avec succès!");

		} catch (Throwable th) {
			System.out.println("EN ERREUR:");
			th.printStackTrace();
		}
	}
}
