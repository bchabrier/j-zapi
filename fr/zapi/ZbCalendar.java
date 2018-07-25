/*
	j-zapi - Une implementation Java de l'API de la Zibase
    Copyright (C) 2013 Luc Doré luc.dore@free.fr

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
 * ZbCalendar
 *  
 * 2 modes de calendriers:
 * 
 * - mode heures validées: un tableau booleéns des 24 heures de la journée.
 *   	B0..23 : heures validées 
 *      B31=0 
 * - mode monoplage: heure de début et heure de fin (en minutes depuis minuit)
 * 		B0..11 = START (0..1440mn=24*60) 
 *		B12..B23 = STOP (0..1440mn=24*60)
 *		B32=1
 * 
 * @author lDore
 *
 */
public class ZbCalendar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isUndefined = false;

	private boolean isMonoPlage = false;

	private boolean days[] = new boolean[8];
	private boolean hours[] = new boolean[24];

	private int startTime = 0, endTime = 0;


	public ZbCalendar() {
		for (int i = 0; i < days.length; i++)
			days[i] = false;
		for (int i = 0; i < hours.length; i++)
			hours[i] = false;
	}

	protected ZbCalendar(int value) {
		setValue(value);
	}

	/**
	 * isMonoPlage
	 * @return le mode de gestion de la plage horaire
	 * soit monoplage: avec une heure de début et une heure de fin
	 * soit heurs pointees (de 0h à 0h59: oui/non, de 1h à 1h59: oui/non...)
	 */
	public boolean isMonoPlage() {
		return isMonoPlage;
	}
	public void setMonoPlage(boolean isMonoPlage) {
		this.isMonoPlage = isMonoPlage;
	}

	/**
	 * setStartTime
	 * @param hours 	 
	 * @param minutes	
	 */
	public void setStartTime(int hours, int minutes) {
		startTime = hours*60 + minutes;
	}
			
	/**
	 * getStartTime
	 * @return
	 */
	public int getStartTime() {
		return startTime;
	}

	public int getHoursStartTime() {		
		return startTime/60;
	}

	public int getMinutesStartTime() {		
		return startTime%60;
	}

	
	/**
	 * setEndTime
	 * @param hours 	 
	 * @param minutes	
	 */
	public void setEndTime(int hours, int minutes) {
		endTime = hours*60 + minutes;
	}
			
	/**
	 * getEndTime
	 * @return
	 */
	public int getEndTime() {
		return endTime;
	}

	public int getHoursEndTime() {		
		return endTime/60;
	}

	public int getMinutesEndTime() {		
		return endTime%60;
	}

	/**
	 * setHeure
	 * @param index de 0 à 23
	 * @param b
	 */
	public void setRangeTime(int index, boolean b) {
		hours[index] = b;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public boolean isRangeTime(int index) {
		return hours[index];
	}

	/**
	 * setUndefined
	 * @param undefeined si true, le calendrier sera considéré comme indéfini (valeur = -1)
	 */
	public void setUndefined(boolean isUndefined) {
		this.isUndefined = isUndefined;
	}

	public boolean isUndefined() {
		return isUndefined;
	}

	/**
	 * @param jour
	 * @return valeur entre 0 et 6, pour zapi: lundi=0, mardi=1...samedi=5,dimanche=6 
	 */
	private int getIndex(int jour) {
		int index = (jour-2)%7;
		if (index < 0) index = 6;
		return index;
	}

	/**
	 * setDay
	 * @param jour 	Calendar.SUNDAY..Calendar.SATURDAY
	 * @param b 		
	 */
	public void setDay(int jour, boolean b) {
		days[getIndex(jour)] = b;
	}

	/**
	 * setDay
	 * Valeurs entre 0 et 6, pour zapi: lundi=0, mardi=1...samedi=5,dimanche=6
	 * @param jour 	Calendar.SUNDAY..Calendar.SATURDAY
	 */
	public void setDay(int jour) {
		days[getIndex(jour)] = true;
	}

	/**
	 * isDay
	 * Valeurs entre 0 et 6, pour zapi: lundi=0, mardi=1...samedi=5,dimanche=6
	 * @param jour 	Calendar.SUNDAY..Calendar.SATURDAY
	 */
	public boolean isDay(int jour) {
		return days[getIndex(jour)];
	}

	/**
	 * getValue
	 * @return
	 */
	protected int getValue() {
		int value = -1;

		if (isUndefined == false) {

			value = 0;
			if (isMonoPlage) {
				String s = Integer.toHexString(startTime);
				if (s.length() < 2) s="0"+s;
				s += Integer.toHexString(endTime);
				if (s.length() < 4) s="0"+s;

				value = Integer.parseInt(s, 16);
			} else
				for (int i = 0; i < 24; i++)     
					value |= (int)(hours[i]?1:0) << i;

			for (int i = 0; i < 7; i++)                 
				value |= (int)(days[i]?1:0) << 24+i;

			value |= (int)(isMonoPlage?1:0) << 31;
		}
		return value;
	}

	/**
	 * setValue
	 * @param value
	 */
	protected void setValue(int value) {

		if (value == -1)
			isUndefined = true;
		else {
			isUndefined = false;

			isMonoPlage = ((value & (int)Math.pow(2, 31))==0)?false:true;

			if (isMonoPlage) {			
				String h = Integer.toHexString(value);
				String tmp = h.substring(h.length()-2);
				endTime = Integer.parseInt(tmp, 16);

				tmp = h.substring(h.length()-4, h.length()-2);
				startTime = Integer.parseInt(tmp, 16);

			} else {
				for (int i = 0; i < 24; i++)			
					hours[i] = ((value & (int)Math.pow(2, i))==0)?false:true;
			}
			for (int i = 0; i < 7; i++)			
				days[i] = ((value & (int)Math.pow(2, 24+i))==0)?false:true;
		}
	}
}
