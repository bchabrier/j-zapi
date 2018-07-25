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
 * Requête spécifique pour la Zibase
 */
public class ZbRequest extends ZbHeader {

	public ZbRequest() {		
	}

	public ZbRequest(short command, Integer param1, Integer param2, Integer param3, Integer param4) {
		super();
		this.command = command;
		if (param1 != null)
			this.param1 = param1;
		if (param2 != null)
			this.param2 = param2;
		if (param3 != null)
			this.param3 = param3;
		if (param4 != null)
			this.param4 = param4;		
	}

	/**
	 * Formate la requête pour le flux à envoyer sur la Zibase
	 * @return 
	 */
	public byte[] toBytes()	{ 		

		ByteBuffer bb = ByteBuffer.allocate(70);
		bb.order(ByteOrder.BIG_ENDIAN);

		bb.put(header.getBytes());
		bb.putShort(command);

		// 44
		bb.put(getPad(reserved1, 16));
		bb.put(getPad(zibaseId, 16));
		bb.put(getPad(reserved2, 12));

//	

//		System.out.println("param2=" + param2);

		bb.putInt(param1);
		bb.putInt(param2);
		bb.putInt(param3);
		bb.putInt(param4);
		bb.putShort(my_count);
		bb.putShort(your_count);

//		System.out.println("command=" + command + " /param1="+param1+" /param2="+param2+" /param3="+param3+" /param4="+param4);
	
		return bb.array();
	}

	static byte[] getPad(String s, int len) {
		
		ByteBuffer bb = ByteBuffer.allocate(len);
		if (s != null) 
			bb.put(s.getBytes());
		
		return bb.array();
	}


	public void setCommand(short command) {
		this.command = command;
	}


	public void setParam1(int param1) {
		this.param1 = param1;
	}

	public void setParam2(int param2) {
		this.param2 = param2;
	}

	public void setParam3(int param3) {
		this.param3 = param3;
	}


	public void setParam4(int param4) {
		this.param4 = param4;
	}

	public void setMyCount(short myCount) {
		this.my_count = myCount;
	}


	public void setYourCount(short yourCount) {
		this.your_count = yourCount;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;		
	}

}
