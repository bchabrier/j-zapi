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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 
 * Entête des messages Zibase
 * en emission et en réception
 *
 */
public class ZbHeader {
	public String header = "ZSIG";
	short command; 							// command number
	public String reserved1 = null;
	public String zibaseId = null;
	public String reserved2 = null;

	int param1 = 0; 						// generic parameters for commands
	int param2 = 0; 						// generic parameters for commands
	int param3 = 0; 						// generic parameters for commands
	int param4 = 0; 						// generic parameters for commands
	short my_count = 0; 					// incr.counter at each sent packet (not incr. in burst of same packets)
	short your_count = 0; 					// last counter value received by the other side

	public int getCommand() {
		return command;
	}
	public String getZibaseId() {
		return zibaseId;
	}
	
	public int getParam1() {
		return param1;
	}
	public int getParam2() {
		return param2;
	}
	public int getParam3() {
		return param3;
	}
	public int getParam4() {
		return param4;
	}
	public short getMyCount() {
		return my_count;
	}
	public short getYourCount() {
		return your_count;
	}

	public ZbHeader() {
		
	}
	
 	/**
 	 * Construit la partie header de la réponse à partir des données binaires envoyées par la Zibase 	
 	 */
 	public ZbHeader(byte[] buffer) { 		
    
		ByteBuffer bb = ByteBuffer.wrap(buffer);
		bb.order(ByteOrder.BIG_ENDIAN);
		byte[] h = new byte[4];
		bb.get(h);
		command = bb.getShort();

//		byte[] r1 = new byte[6];
//		bb.get(r1);
//		System.out.println(" r1 = " + new String(r1));
//		short reserved_short = bb.getShort();
//		System.out.println(" reserved_short = " + reserved_short);
//		r1 = new byte[8];
//		bb.get(r1);
//		System.out.println(" r1 = " + new String(r1));

		byte[] r1 = new byte[16];
		bb.get(r1);
		reserved1 = new String(r1);

		byte[] zid = new byte[12];
		bb.get(zid);
		zibaseId = new String(zid);

		byte[] r2 = new byte[16];
		bb.get(r2);
		reserved2 = new String(r2);

		param1 = bb.getInt();
		param2 = bb.getInt();
		param3 = bb.getInt();
		param4 = bb.getInt();
		
		my_count = bb.getShort();
		your_count = bb.getShort();
 	}

}
