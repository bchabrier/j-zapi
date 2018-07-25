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
 * Enum des actions possibles par la Zibase
 */
public enum ZbAction {

	OFF(0),
	ON(1),
	DIM_BRIGHT(2),     	// (DIM/BRIGHT, CHACON/ZWAVE ONLY)
	ALL_LIGHTS_ON(4),	// X10, not implemented
	ALL_LIGHTS_OFF(5),	// X10, not implemented
	ALL_OFF(6),
	ASSOC(7), 			// association mode with broadcasted packet over default protocols when protocol=0, or 
						// association mode targeted to the specified ‘protocol” when <>0
	UNASSOC(8); 		// unassociation mode (Zwave Only)
		  
	
	private int code;

	private ZbAction(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
