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
import java.util.Date;

public class SensorEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String pro;  // protocol, ex: VS, ZW_ON, ZW_OFF
	private String id;

	private Date date;
	private int v1;
	private int v2;
	private int lowbatt;

	public SensorEvent() {

	}

	public SensorEvent(String type, String pro, String id, Date date, int v1, int v2, int lowbatt) {	
		this.type = type;
		this.pro = pro;
		this.id = id;
		this.date = date;
		this.v1 = v1;
		this.v2 = v2;
		this.lowbatt = lowbatt;
	}

	public String getType() {
		return type;
	}

	public String getPro() {
		return pro;
	}

	public String getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public int getV1() {
		return v1;
	}

	public int getV2() {
		return v2;
	}

	public int getLowbatt() {
		return lowbatt;
	}

}
