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

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import fr.zapi.ZbResponse;
import fr.zapi.utils.XmlSimpleParse;

/**
 * ListenZibase 
 * 
 * Ecoute des messages reçus par la Zibase 
 * 
 * 
 * @author Luc Doré
 */
public class ListenZibase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			// prérequis: il faut dans un premier temps faire un register
			// cf. RegisterSample
			
			int localport = 9876;				
			DatagramSocket serverSocket = new DatagramSocket(localport);  // bind

			byte[] receiveData = new byte[470];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			while (true) {

				System.out.println("receive...");
				serverSocket.receive(receivePacket);
			

				ZbResponse zbResponse = new ZbResponse(receivePacket.getData());
				System.out.println("id="+zbResponse.getZbHeader().getZibaseId());
				System.out.println("p1="+zbResponse.getZbHeader().getParam1());
				System.out.println("p2="+zbResponse.getZbHeader().getParam2());
				System.out.println("p3="+zbResponse.getZbHeader().getParam3());
				System.out.println("p4="+zbResponse.getZbHeader().getParam4());
				System.out.println("cmd="+zbResponse.getZbHeader().getCommand());
				System.out.println("mycount="+zbResponse.getZbHeader().getMyCount());
				System.out.println("yourcount="+zbResponse.getZbHeader().getYourCount());

				System.out.println("message="+zbResponse.getMessage());

				String id = XmlSimpleParse.getTagValue("id", zbResponse.getMessage());

				System.out.println("id==" + id);
				System.out.println("rf==" + XmlSimpleParse.getTagValue("rf", zbResponse.getMessage()));
				System.out.println("bat==" + XmlSimpleParse.getTagValue("bat", zbResponse.getMessage()));
				System.out.println("lev==" + XmlSimpleParse.getTagValue("lev", zbResponse.getMessage()));
				System.out.println("noise==" + XmlSimpleParse.getTagValue("noise", zbResponse.getMessage()));
				System.out.println("flag3==" + XmlSimpleParse.getTagValue("flag3", zbResponse.getMessage()));

				// reset buffer
				for (int i = 0; i < receiveData.length; i++) 
					receiveData[i] = 0;

			}


		} catch (Throwable th) {
			System.out.println("EN ERREUR:");
			th.printStackTrace();
		}

		System.out.println("terminé!");
	}

}
