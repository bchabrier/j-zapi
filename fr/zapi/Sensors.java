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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

import fr.zapi.utils.XmlSimpleParse;

/**
 * Parsing de sensors.xml
 * - parsing simple du xml sans utiliser de librairie externe du type jaxb ou jdom  
 * - décodage des champs x10tab, zwtab, zwpre, zwerr et zwlowbatt
 * 
 * @author lDore
 * 
 */
public class Sensors {
	private Zibase zibase; 
	private String xml = null;

	private BitSet bsX10Tab = null;
	private BitSet bsZwTab = null;
	private BitSet bsZwPre = null;
	private BitSet bsZwErr = null;	
	private BitSet bsZwLowBatt = null;

	/**
	 * 
	 * @param zibase
	 */
	public Sensors(Zibase zibase) {
		this.zibase = zibase;
	}

	/**
	 * 
	 * @param ip
	 */
	public Sensors(String ip) {
		zibase = new Zibase(ip);
	}

	/**
	 * read
	 * récupéraation du xml sensors.xml sur la Zibase
	 */
	private void read() {
		if (xml == null) {
			URL url;
			HttpURLConnection conn = null;
			BufferedReader rd = null;
			String line;
			StringBuffer sb = new StringBuffer();
			try {
				url = new URL("http://"+ zibase.getIpAddress().getHostAddress() + "/sensors.xml");

				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");

				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				rd.close();
				conn.disconnect();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
			finally {

				if (conn != null)
					conn.disconnect();
				try {
					if (rd != null)
						rd.close();
				} catch (IOException e) {

				}
			}
			xml = sb.toString();
		}
	}

	/**
	 * refresh
	 * 
	 */
	public void refresh() {
		xml = null;
		bsX10Tab = null;
		bsZwTab = null;
		bsZwPre = null;
		bsZwErr = null;	
		bsZwLowBatt = null;
	}

	/**
	 * getX10Tab
	 * @return le champ x10tab brut tel que fourni dans le xml
	 */
	public String getX10Tab() {
		read();
		return XmlSimpleParse.getTagValue("x10tab", xml);
	}

	/**
	 * getZwTab
	 * @return le champ zwtab brut tel que fourni dans le xml
	 */
	public String getZwTab() {
		read();
		return XmlSimpleParse.getTagValue("zwtab", xml);
	}

	/**
	 * getZwPre
	 * @return le champ zwpre brut tel que fourni dans le xml
	 */
	public String getZwPre() {
		read();
		return XmlSimpleParse.getTagValue("zwpre", xml);
	}

	/**
	 * getZwErr
	 * @return le champ zwerr brut tel que fourni dans le xml
	 */
	public String getZwErr() {
		read();
		return XmlSimpleParse.getTagValue("zwerr", xml);
	}

	/**
	 * getZwLowBatt
	 * @return le champ zwlowbatt brut tel que fourni dans le xml
	 */
	public String getZwLowBatt() {
		read();
		return XmlSimpleParse.getTagValue("zwlowbatt", xml);
	}

	/**
	 * getBitSetX10Tab
	 * 256 caractères de A1 à P16 représentant les états des actionneurs X10/Chacon etc… (1 = ON, 0 =OFF)) 
	 * @return un champ de bit correspondant au champ x10tab du xml
	 */
	public BitSet getBitSetX10Tab() {
		if (bsX10Tab == null)
			bsX10Tab = getBitSet(getX10Tab()); 
		return bsX10Tab; 		
	}

	/**
	 * getX10Tab
	 * @param adresse de A1 à P16
	 * @return l'état du périphérique correspondant
	 */
	public boolean getX10Tab(String address) {
		return getBitSetX10Tab().get(getTabAddress(address)); 		
	}

	/**
	 * getBitSetZwTab
	 * 256 caractères de A1 à P16 représentant les états des périphériques ZWAVE (1 = ON, 0 = OFF))
	 * @return le champ de bit décodé correspondant au champ zwtab du xml
	 */
	public BitSet getBitSetZwTab() {
		if (bsZwTab == null)
			bsZwTab = getBitSet(getZwTab()); 
		return bsZwTab; 		
	}

	/**
	 * getZwTab
	 * @param adresse de A1 à P16
	 * @return l'état du périphérique correspondant
	 */
	public boolean getZwTab(String address) {
		return getBitSetZwTab().get(getTabAddress(address));
	}

	/**
	 * getBitSetZwPre
	 * 256 caractères de A1 à P16 représentant les états de déclaration dans le   
	 * contrôleur ZWAVE des périphériques ZWAVE (1= déclaré, 0 non déclaré)
	 * @return le champ de bit décodé correspondant au champ zwpre du xml
	 */
	public BitSet getBitSetZwPre() {
		if (bsZwPre == null)
			bsZwPre = getBitSet(getZwPre()); 
		return bsZwPre; 		
	}

	/**
	 * getZwPre
	 * @param adresse de A1 à P16
	 * @return l'état du périphérique correspondant
	 */
	public boolean getZwPre(String address) {
		return getBitSetZwPre().get(getTabAddress(address)); 		
	}

	/**
	 * getBitSetZwErr
	 * 256 caractères de A1 à P16 représentant les états de transmission sur les périphériques ZWAVE (1 = error)
	 * @return le champ de bit décodé correspondant au champ zwerr du xml
	 */
	public BitSet getBitSetZwErr() {
		if (bsZwErr == null)
			bsZwErr = getBitSet(getZwErr()); 
		return bsZwErr; 		
	}

	/**
	 * getZwErr
	 * @param adresse de A1 à P16
	 * @return l'état du périphérique correspondant
	 */
	public boolean getZwErr(String address) {
		return getBitSetZwErr().get(getTabAddress(address)); 		
	}

	/**
	 * getBitSetZwLowBatt
	 * 256 caractères de A1 à P16 représentant les états bas de la batterie  sur les périphériques ZWAVE (1 = Lowbatt)
	 * @return le champ de bit décodé correspondant au champ zwlowbatt du xml
	 */
	public BitSet getBitSetZwLowBatt() {
		if (bsZwLowBatt == null)
			bsZwLowBatt = getBitSet(getZwLowBatt());
		return bsZwLowBatt;
	}

	/**
	 * getZwLowBatt
	 * @param adresse de A1 à P16
	 * @return l'état du périphérique correspondant
	 */
	public boolean getZwLowBatt(String address) {
		return getBitSetZwLowBatt().get(getTabAddress(address));
	}

	/**
	 * getBitSetAsString
	 * @param bitSet
	 * @return le champ de bit sous forme d'une chaîne composée de 0 et de 1, d'une longueur de 256 caractères. 
	 */
	public static String getBitSetAsString(BitSet bitSet) {
		StringBuffer s = new StringBuffer(bitSet.length()); 
		for (int bitIndex = 0; bitIndex < 256; bitIndex++)
			s.append(bitSet.get(bitIndex)?"1":"0");
		return s.toString();
	}

	/**
	 * getVars
	 * Récupération de la liste des variables 
	 * @return
	 */
	public List<SensorNumVal> getVars() {
		return getNumValList(SensorElement.var);
	}

	/**
	 * getCals
	 * Récupération de la liste des calendriers
	 * @return
	 */
	public List<SensorNumVal> getCals() {
		return getNumValList(SensorElement.var);
	}


	/**
	 * Récupération d'une liste des variables ou des calendriers
	 * @param sensorElement
	 * @return
	 */
	public List<SensorNumVal> getNumValList(SensorElement sensorElement) {
		read();

		final String tagNum = "num=";
		final String tagVal = "val=";

		List<SensorNumVal> list = new ArrayList<SensorNumVal>();

		String tag = "<" + sensorElement.name() + " ";	
		int pos = 0;

		while (pos != -1) {			
			pos = xml.indexOf(tag, pos);
			if (pos != -1) {
				pos++;

				String num = getTagValue(xml, pos, tagNum);
				String val = getTagValue(xml, pos, tagVal);

				list.add(new SensorNumVal(Integer.parseInt(num), val));
			}		
		}
		return list;	
	}

	/**
	 * getEvents
	 * récupération de la liste des événements
	 * @return
	 */
	public List<SensorEvent> getEvents() {
		read();

		final String tagType = "type=";
		final String tagPro = "pro=";
		final String tagId = "id=";
		final String tagGmt = "gmt=";
		final String tagV1 = "v1=";
		final String tagV2 = "v2=";
		final String tagLowbatt = "lowbatt=";

		List<SensorEvent> list = new ArrayList<SensorEvent>();

		String tag = "<ev ";	
		int pos = 0;

		while (pos != -1) {			
			pos = xml.indexOf(tag, pos);
			if (pos != -1) {
				pos++;

				String type = getTagValue(xml, pos, tagType);
				String pro = getTagValue(xml, pos, tagPro);
				String id = getTagValue(xml, pos, tagId);
				String gmt = getTagValue(xml, pos, tagGmt);
				String v1 = getTagValue(xml, pos, tagV1);
				String v2 = getTagValue(xml, pos, tagV2);
				String lowbatt = getTagValue(xml, pos, tagLowbatt);

				Date date = new Date(Integer.parseInt(gmt)*1000L);

				list.add(new SensorEvent(type, pro, id, date, Integer.parseInt(v1), Integer.parseInt(v2), Integer.parseInt(lowbatt)));
			}		
		}
		return list;	
	}

	/**
	 * getTagValue
	 * @param xml
	 * @param pos
	 * @param tag
	 * @return
	 */
	private String getTagValue(String xml, int pos, String tag) {

		int pos2 = xml.indexOf(tag, pos);
		String value = xml.substring(pos2+tag.length()+1);

		pos2 = value.indexOf("\"");
		value = value.substring(0, pos2);

		return value;
	}

	/**
	 * getBitSet
	 * @param tab tel que fourni dans le xml
	 * @return le champ de bit décodé.
	 */
	public static BitSet getBitSet(String tab) {
		BitSet bs = new BitSet(256);		
		String[] x10Address = tab.split("(?<=\\G.{4})");
		int index = 0;
		for (int i = 0; i < x10Address.length; i++) {
			String[] x10AddressDigits = x10Address[i].split("(?<=\\G.{1})");
			String x10AddressReordered = x10AddressDigits[2] + x10AddressDigits[3] + x10AddressDigits[0] + x10AddressDigits[1];

			String[] deviceAddress = hexToBinary(x10AddressReordered).split("(?<=\\G.{1})");
			for (int j = deviceAddress.length-1; j >= 0; j--) {
				if (deviceAddress[j].equalsIgnoreCase("1")) {
					bs.set(index);
				}
				index++;
			}
		}
		return bs;
	}

	/**
	 * hexToBinary
	 */
	private static String hexToBinary(String hexString) {
		String[] hexDigit = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
		String[] binary = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
		String result = "";

		for (int i = 0; i < hexString.length(); i++) {
			char temp = hexString.charAt(i);
			String temp2 = "" + temp + "";
			for (int j = 0; j < hexDigit.length; j++) {
				if (temp2.equalsIgnoreCase(hexDigit[j])) {
					result = result + binary[j];
				}
			}
		}
		return result;
	}	

	/**
	 * Calcul de l'index de 0 à 255 pour une adresse de A1 à P16
	 * A1, A2 ... A8, B1, B2 ... P1 ... P16
	 * @param address de A1 à P16
	 * @return
	 */
	private static int getTabAddress(String address) {
		int a = (int) (address.charAt(0) - 65);
		a = a*16 + Integer.parseInt(address.substring(1)) -1;
		return a;
	}

}
