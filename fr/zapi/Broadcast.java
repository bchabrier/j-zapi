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

package fr.zapi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import fr.zapi.utils.NetUtils;

public class Broadcast {

	/**
	 * nop
	 * sur le réseau local
	 * 
	 * @return
	 */
	public static ZbNopResponse nop() throws SocketException {
	
		return nop(NetUtils.getLocaleBroadcastIpAddress());
	}
	
	/**
	 * nop
	 * 
	 * @param ipBroadcast
	 * @return
	 */
	public static ZbNopResponse nop(String ipBroadcast) {
		ZbRequest zbRequest = new ZbRequest((short)8, null, null, null, null);

		ZbNopResponse zbNopResponse = null;
	
		byte[] buffer = zbRequest.toBytes();
		DatagramSocket clientSocket = null;
		
		try {
			clientSocket = new DatagramSocket();
			InetAddress ipAddress = InetAddress.getByName(ipBroadcast);
			clientSocket.setSoTimeout(20*1000);

			DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, ipAddress, Zibase.port);
			clientSocket.send(sendPacket);

			byte[] ack = new byte[70];  // 70 l'entete
			DatagramPacket receivePacket = new DatagramPacket(ack, ack.length);
			clientSocket.receive(receivePacket);
			
			zbNopResponse = new ZbNopResponse(new ZbHeader(receivePacket.getData()), receivePacket.getAddress());
			
		} catch (java.net.SocketTimeoutException exc) {
			System.out.println("timeout !");

		} catch (UnknownHostException exc) {
			exc.printStackTrace();
			
		} catch (IOException exc) {
			exc.printStackTrace();
			
		} finally {
			if (clientSocket != null)
				clientSocket.close();
		}

		return zbNopResponse;		
	}
}
