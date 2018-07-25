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

package fr.zapi.utils;


public class XmlSimpleParse {

	/**
	 * getTagValue
	 * Récupération de la valeur d'un champ dans un contenu au format xml
	 * @param tagName
	 * @param xml
	 * @return
	 */
	static public String getTagValue(String tagName, String xml) {
		String value = null;
		if (xml != null) {
			String tagdebut = "<"+tagName+">";
			int pos = xml.indexOf(tagdebut);
			if (pos != -1) {
				value = xml.substring(pos+tagdebut.length());
				// on ne teste plus la balise de fin qui est parfois mauvaise: telecommande Atlantic's <flag3> </flags3>
//				value = value.substring(0, value.indexOf("</"+tagName+">"));
				value = value.substring(0, value.indexOf("</"));
			}
		}
		return value;
	}

	/**
	 * 
	 * @param tagName
	 * @param xml
	 * @param index
	 * @return
	 */
	static public String getTagValue(String tagName, String xml, int index) {
		if (xml != null) {
			String tagdebut = "<"+tagName+">";		
			for (int i = 0; i < index; i++) {
				int pos = xml.indexOf(tagdebut);
				if (pos != -1) {
					xml = xml.substring(pos+tagdebut.length());
				}
			}
			return getTagValue(tagName, xml);
		}
		return null;
	}
	
	/**
	 * getDataValue
	 * 
	 * ...<condition data="Risques de pluie"/>....
	 * Risques de pluie
	 * 
	 * @param xml
	 * @param name
	 * @return
	 */
	public static String getDataValue(String xml, String name) {
		
		String value = null;
		String tag = "<" + name + " data=";
		
		int pos = xml.indexOf(tag);
		if (pos != -1) {
			value = xml.substring(pos+tag.length());
			
			pos = value.indexOf("/>");
			value = value.substring(0, pos);
		}		
		return value;	
	}
}
