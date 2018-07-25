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

import fr.zapi.ZbVirtualProbe;
import fr.zapi.Zibase;

/**
 * SetVirtualProbeValue
 * 
 * 
 * 
 * @author Luc Doré
 *
 */
public class SetVirtualProbeValue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Zibase zibase = new Zibase("192.168.0.44");

						
			// valeur1 (temperature): 197 = 19.7°C
			// valeur2 (humidité)	: 55 = 55%			
			
			zibase.setVirtualProbeValue(439157267, (byte)197, (byte)55, false, ZbVirtualProbe.OREGON);
			
			// retour dans le suivi d'activité: 
			// Received radio ID (<rf>INTERNAL</rf> Noise=<noise>0</noise> Level=<lev>0.0</lev>/5 <dev>Oregon  Temp-Hygro/THGR228N</dev> Ch=<ch>19</ch> T=<tem>+19.7</tem>ï¿½C (+67.4ï¿½F) Humidity=<hum>55</hum>%  Batt=<bat>Ok</bat>): <id>OS439157267</id>
			
			
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

}
