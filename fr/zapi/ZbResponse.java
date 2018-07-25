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

import fr.zapi.utils.XmlSimpleParse;

/**
 * Réponse spécifique de la Zibase
 */
public class ZbResponse  {

		private ZbHeader zbHeader = null;
	 	private String message = null;
	 	
	 	/**
	 	 * Construit la réponse à partir des données binaires envoyées par la Zibase 	
	 	 */
	 	public ZbResponse(byte[] buffer) { 		
	    
	 		zbHeader = new ZbHeader(buffer);
	 		
			ByteBuffer bb = ByteBuffer.wrap(buffer, 70, buffer.length-70);
			bb.order(ByteOrder.BIG_ENDIAN);
			byte b[] = new byte[400];
			bb.get(b);
			message = new String(b);
	 	}

	 	public ZbHeader getZbHeader() {
	 		return zbHeader;
	 	}
	 	
		public String getMessage() {
			return message;
		}
		
		public String getTagFromMessage(String tag) {
			if (message == null)
				return null;
			return XmlSimpleParse.getTagValue(tag, message);
		}
		
	 }

