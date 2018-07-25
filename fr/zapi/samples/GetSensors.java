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

import java.util.List;

import fr.zapi.SensorEvent;
import fr.zapi.SensorNumVal;
import fr.zapi.Sensors;

/**
 * GetSensors
 * Lecture des informations /sensors.xml
 * 
 * 
 * @author Luc Doré
 *
 */
public class GetSensors {


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Sensors sensors = new Sensors("192.168.0.44");

		System.out.println("la valeur brute telle que fournie dans le xml:");
		System.out.println("x10Tab...=" + sensors.getX10Tab());
		System.out.println("zwTab....=" + sensors.getZwTab());
		System.out.println("zwPre....=" + sensors.getZwPre());
		System.out.println("zwErr....=" + sensors.getZwErr());
		System.out.println("zwLowBatt=" + sensors.getZwLowBatt());

		System.out.println("256 caractères de A1 à P16 représentant les états (1 = ON, 0 = OFF) des périphériques:"); 
		System.out.println("x10Tab...="+Sensors.getBitSetAsString(sensors.getBitSetX10Tab()));
		System.out.println("zwTab....="+Sensors.getBitSetAsString(sensors.getBitSetZwTab()));
		System.out.println("zwPre....="+Sensors.getBitSetAsString(sensors.getBitSetZwPre()));
		System.out.println("zwErr....="+Sensors.getBitSetAsString(sensors.getBitSetZwErr()));
		System.out.println("zwLowBatt="+Sensors.getBitSetAsString(sensors.getBitSetZwLowBatt()));

		System.out.println("boolean repésentant l'état du périphérique demandé:");
		System.out.println("Zwave A1:"+sensors.getZwTab("A1"));
		System.out.println("Zwave A1:"+sensors.getZwTab("A1"));
		System.out.println("Zwave A10:"+sensors.getZwTab("A10"));
		System.out.println("Zwave A12:"+sensors.getZwTab("A12"));
		System.out.println("Zwave pre A1:"+sensors.getZwPre("A1"));
		System.out.println("Zwave pre A2:"+sensors.getZwPre("A2"));
		System.out.println("Zwave pre A3:"+sensors.getZwPre("A3"));
		System.out.println("**********************");
		System.out.println("======Zwave pre A7:"+sensors.getZwPre("A7"));

		System.out.println("Zwave err A1:"+sensors.getZwErr("A1"));
		System.out.println("Zwave err A2:"+sensors.getZwErr("A2"));
		System.out.println("Zwave err A3:"+sensors.getZwErr("A3"));

		System.out.println("Liste des variables:");
		List<SensorNumVal> listVars = sensors.getVars();
		for (SensorNumVal sensorNumVal : listVars) {
			System.out.println(sensorNumVal.getNum() + " = " + sensorNumVal.getVal());
		}

		System.out.println("Liste des calendriers:");
		List<SensorNumVal> listCals = sensors.getCals();
		for (SensorNumVal sensorNumVal : listCals) {
			System.out.println(sensorNumVal.getNum() + " = " + sensorNumVal.getVal());
		}

		System.out.println("Liste événements:");		
		List<SensorEvent> list = sensors.getEvents();
		for (SensorEvent sensorEvent : list) {
			System.out.println(sensorEvent.getType() + " / " + sensorEvent.getPro() + " / " + sensorEvent.getId() + " / " + sensorEvent.getDate());
		}
	}
}
