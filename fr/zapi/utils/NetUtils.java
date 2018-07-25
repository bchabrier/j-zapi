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

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetUtils {


	/**
	 * getLocaleIpAddress
	 * 
	 * recherche de l'adresse ip locale à communiquer à la Zibase
	 * pas toujours facile de retrouver la bonne ip:
	 * on ignore les ip v6
	 * il faut trouver l'ip de la machine correspondant au réseau local
	 * donc ignore aussi les boucles locales, les VPN, et autres interfaces...
	 * en conclusion prend la premiere adresse ip v4 locale qui ne soit pas une boucle

	 * @return l'ip locale ou null
	 */
	public static String getLocaleIpAddress() throws SocketException {

		String ip = null;
		Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); 
		while (e.hasMoreElements()) { 

			Enumeration<InetAddress> i = e.nextElement().getInetAddresses(); 
			while (i.hasMoreElements()) { 
				InetAddress a = i.nextElement(); 
				if ((a.isLoopbackAddress() == false)
						&& (a.isSiteLocalAddress() == true)
						&& ((a instanceof Inet6Address) == false)
					    && (a.getHostAddress().indexOf("192") == 0))
					ip = a.getHostAddress();
			} 
		}

		return ip;
	}

	/**
	 * getLocaleBroadcastIpAddress()
	 * 
	 * adresse de broadcast sur le réseau local
	 * @return
	 * @throws SocketException
	 */
	public static String getLocaleBroadcastIpAddress() throws SocketException {
		String ipBroadcast = NetUtils.getLocaleIpAddress();
		return ipBroadcast.substring(0, ipBroadcast.lastIndexOf(".") +1) + "255";
	} 

}