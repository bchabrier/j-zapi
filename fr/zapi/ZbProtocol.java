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

/**
 * 
 * enum des protocoles
 *
 */
public enum ZbProtocol {
	PRESET(0),
	VISONIC433(1),
	VISONIC868(2),
	CHACON(3),			// (32bits frame) (ChaconV2/DIO series) 
	DOMIA(4),			// (24 bits frame) ( Chacon V1 + low-cost devices)
	X10(5),    			// RFX10
	ZWAVE(6),
	RFS10(7),			// TS10
	X2D433(8),
	X2D433ALRM(8),  	
	X2D868(9),
	X2D868ALRM(9),
	X2D868INSH(10),  	// XDD868INTER   (housecodes: pure XDD868: A/B, RFY: C/D, RFY long burst:E/F, BLY:G/H)	 	
	X2D868PIWI(11),   	// XDD868PILOTWIRE	 	
	X2D868BOAC(12);  	// XDD868BOILER	 	
	
	
	private int code;

	private ZbProtocol(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
