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

import java.io.Serializable;

/**
 * SensorNumVal
 * Utilisé par Sensors
 * Représente une variable ou un calendrier
 * 
 * @author lDore
 *
 */
public class SensorNumVal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int num;
	private String val;

	public SensorNumVal() {

	}
	
	public SensorNumVal(int num, String val) {
		this.num = num;
		this.val = val;
	}
	
	public int getNum() {
		return num;
	}

	public String getVal() {
		return val;
	}



}
