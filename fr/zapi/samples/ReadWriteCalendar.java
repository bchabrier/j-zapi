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

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import fr.zapi.ZbCalendar;
import fr.zapi.Zibase;

/**
 * ReadWriteCalenndar
 * Lecture/Ecriture de calendriers
 * 
 * 
 * @author Luc Doré
 *
 */
public class ReadWriteCalendar {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			Zibase zibase = new Zibase("192.168.0.44");


			// Affichage des calendriers de la Zibase
			for (int index = 1; index < 17; index++) {
				ZbCalendar zbCalendar = zibase.readCalendar(index);
				System.out.println();
				printCalendar("CALENDAR #" + index, zbCalendar);
			}

			
			// Exemple d'un calendrier 
			// lundi et samedi 
			// heures pointées: 2h00 à 2h59, 3h00 à 3h59, 4h00 à 4h59 et 16h00 à 16h59
			ZbCalendar tmp = new ZbCalendar();
			tmp.setDay(Calendar.MONDAY);
			tmp.setDay(Calendar.SATURDAY);
			tmp.setRangeTime(2, true);
			tmp.setRangeTime(3, true);
			tmp.setRangeTime(4, true);
			tmp.setRangeTime(16, true);
			printCalendar("EXEMPLE 1:", tmp);

			// Autre exemple d'un calendrier 
			// vendredi, samedi, dimanche 
			// de 12h23 à 21h58  
			ZbCalendar tmp1 = new ZbCalendar();			
			tmp1.setDay(Calendar.MONDAY);
			tmp1.setDay(Calendar.SATURDAY);
			tmp1.setMonoPlage(true);
			tmp1.setStartTime(12,23);
			tmp1.setEndTime(21,58);
			printCalendar("EXEMPLE 2:", tmp1);
			
		}
		catch (Throwable thr) {
			thr.printStackTrace();
		}
	}

	static void printCalendar(String title, ZbCalendar zbCalendar) {
		System.out.println("===");
		System.out.println(title);
		if (zbCalendar.isUndefined())
			System.out.print("UNDEFINED CALENDAR");
		else {
			
		System.out.println("Lundi="+zbCalendar.isDay(Calendar.MONDAY));
		System.out.println("Mardi="+zbCalendar.isDay(Calendar.TUESDAY));
		System.out.println("Mercredi="+zbCalendar.isDay(Calendar.WEDNESDAY));
		System.out.println("Jeudi="+zbCalendar.isDay(Calendar.THURSDAY));
		System.out.println("Vendredi="+zbCalendar.isDay(Calendar.FRIDAY));
		System.out.println("Samedi="+zbCalendar.isDay(Calendar.SATURDAY));
		System.out.println("Dimanche="+zbCalendar.isDay(Calendar.SUNDAY));

		if (zbCalendar.isMonoPlage()) {
			System.out.print("MonoPlage=" 
					+ zbCalendar.getHoursStartTime() + ":" + zbCalendar.getMinutesStartTime()
					+ " à "
					+ zbCalendar.getHoursEndTime() + ":" + zbCalendar.getMinutesEndTime());
		} else {
			System.out.print("HeuresPointees=");

			for (int i = 0; i < 24; i++)
				System.out.print((zbCalendar.isRangeTime(i)?"X":"-"));
		}
		}
		System.out.println();
		System.out.println("---");
	}
}