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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.StringTokenizer;

import fr.zapi.utils.NetUtils;

public class Zibase {

	private String ip; 	
	private InetAddress ipAddress = null;
	public static final int port = 49999;

	/**
	 * @param ipAddr Adresse IP de la zibase
	 */
	public Zibase(String ipAddr) {
		ip = ipAddr;
	}

	public InetAddress getIpAddress() throws UnknownHostException {
		if (ipAddress == null)
			ipAddress = InetAddress.getByName(ip);
		return ipAddress;
	}

	/**
	 * Envoie la requête à la Zibase 
	 * @param ZbRequest requête au format Zibase
	 * @return ZbResponse réponse de la zibase
	 */
	private ZbResponse sendRequest(final ZbRequest zbRequest, boolean readResponse) {
		byte[] buffer = zbRequest.toBytes();

		return sendRequest(buffer, readResponse);
	}

	/**
	 * sendRequest
	 * Envoi d'une requête à la Zibase 
	 * @param buffer
	 * @param readResponse
	 * @return
	 */
	private ZbResponse sendRequest(byte[] buffer, boolean readResponse) {

		ZbResponse zbResponse = null; 
		DatagramSocket clientSocket = null;
		try {			

			clientSocket = new DatagramSocket();
			clientSocket.setSoTimeout(5000);  // 5sec. la Zibase est censée répondre dans les 3s. selon les specs Zodianet

			DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, getIpAddress(), port);
			clientSocket.send(sendPacket);

			if (readResponse == true) {
				byte[] ack = new byte[512];  // 70 l'entete + 400 au max de message
				DatagramPacket receivePacket = new DatagramPacket(ack, ack.length);
				clientSocket.receive(receivePacket);

				zbResponse = new ZbResponse(receivePacket.getData());
			}

		} catch (java.net.SocketTimeoutException exc) {
			System.out.println("timeout !");

		} catch (UnknownHostException exc) {
			exc.printStackTrace();
		} catch (IOException exc) {
			exc.printStackTrace();
		} finally {
			if (clientSocket != null)
				clientSocket.close();
		}

		return zbResponse;
	}


	/**
	 * hostRegistering
	 * Permet l'enregistrement du système HOST auprès de la ZIBASE
	 * 
	 * exemples de messages reçus:
	 * Zapi linked to host IP=<zip>192.168.0.49</zip> UDP Port=<zudp>9876</zudp>
	 * 
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 */
	public void hostRegistering(String host, int port) throws UnknownHostException, SocketException {
		InetAddress ipAddressHost = InetAddress.getByName(host);

		String ipHost = ipAddressHost.getHostAddress();
		if (ipHost.equals("127.0.0.1") || (ipHost.equals("localhost")))
			ipHost = NetUtils.getLocaleIpAddress();

		System.out.println("ipHost=" + ipHost);

		StringTokenizer st = new StringTokenizer(ipHost, ".");
		int i = 0;
		byte[] b = new byte[4];
		while (st.hasMoreTokens()) 
			b[i++] = (byte)Integer.parseInt(st.nextToken());

		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(ByteOrder.BIG_ENDIAN);

		ZbRequest zbRequest = new ZbRequest((short)13, bb.getInt(), port, 0, 0);
		zbRequest.setReserved1("ZapiInit");

		sendRequest(zbRequest, false);
	}


	/**
	 * hostUnregistering
	 * Permet le dés-enregistrement du système HOST auprès de la ZIBASE
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 */
	public void hostUnregistering(String host, int port) throws UnknownHostException {
		int ipHost = getIpAsInt(host);
		ZbRequest zbRequest = new ZbRequest((short)22, ipHost, port, null, null);
		sendRequest(zbRequest, false);
	}


	/**
	 * getIpAsInt
	 * @param host
	 * @return l'ip du host dans un int
	 * @throws UnknownHostException
	 */
	private int getIpAsInt(String host) throws UnknownHostException {
		InetAddress ipAddressHost = InetAddress.getByName(host);

		String ipHost = ipAddressHost.getHostAddress();
		if (ipHost.equals("127.0.0.1"))
			ipHost = InetAddress.getLocalHost().getHostAddress();

		StringTokenizer st = new StringTokenizer(ipHost, ".");
		int i = 0;
		byte[] b = new byte[4];
		while (st.hasMoreTokens()) 
			b[i++] = (byte)Integer.parseInt(st.nextToken());

		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(ByteOrder.BIG_ENDIAN);

		return bb.getInt();
	}


	/**
	 * Récupérer l'état d'un actionneur.
	 * La zibase ne recoit que les ordres RF et non les ordres CPL X10,
	 * donc l'état d'un actionneur X10 connu par la zibase peut être erroné
	 * @param string adresse au format X10 de l'actionneur
	 * @return true:ON, false:OFF, null
	 */
	public Boolean getState(String address) {
		if (address.length() > 1) {	
			address = address.toUpperCase();
			ZbRequest request = new ZbRequest();
			request.setCommand((short)11);
			request.setParam1(5);
			request.setParam3(4);

			int houseCode = address.charAt(0) - 0x41;
			int device = Integer.parseInt(address.substring(1)) - 1;

			device |= (houseCode & 0x0F) << 0x04;		
			request.setParam4(device);

			ZbResponse response = sendRequest(request, true);
			if (response != null)
				return (response.getZbHeader().getParam1() == 1)?Boolean.TRUE:Boolean.FALSE;
		}
		return null;
	}

	/**
	 *  setEvent
	 * 
	 * 	command = decimal 11
		param1 = 4
		param2 = action
		param3 = ID
		param4 = ev_type
		action :
		- 0 : inactiver une alerte
		- 1 : activer une alerte
		- 2 : simuler l'arrivée d'un ID de detecteur (peut entraîner l'execution de scenarii)
	 */
	private void setEvent(ZbAction action, int id, int evType) {
		ZbRequest request = new ZbRequest();
		request.setCommand((short)11);

		request.setParam1(4);
		request.setParam2(action.getCode());
		request.setParam3(id);					// ID

		// AXX_PXX_ON = 4
		// AXX_PXX_OFF = 9
		// AXX_PXX_ZW_ON = 19
		// AXX_PXX_ZW_OFF = 20
		// => ID 0..255 pour adresses X10 ou ZWave

		// autres valeurs 0-(2**31-1) : detector id ????
		request.setParam4(evType);				

		sendRequest(request, true); 		
	}

	/**
	 * setEventX10
	 * @param id
	 * @param on  true: ON / false: OFF
	 */
	public void setEventX10(int id, boolean on) {
		setEvent(on?ZbAction.ON:ZbAction.OFF, 
				id, 
				on?4:9);   // AXX_PXX_ON  / AXX_PXX_OFF
	}
	
	/**
	 * setEventZWave
	 * @param id
	 * @param on  true: ON / false: OFF
	 */
	public void setEventZWave(int id, boolean on) {
		setEvent(on?ZbAction.ON:ZbAction.OFF, 
				id, 
				on?19:20);   // AXX_PXX_ZW_ON  / AXX_PXX_ZW_OFF
	}


	/**
	 * Lance la commande de l'actionneur spécifié par son adresse et son protocol 
	 * @param address Adresse au format X10 de l'actionneur (ex: B5)
	 * @param action Action à réaliser (Utiliser l'enum ZbAction) 
	 * @param protocol Protocole RF (Utiliser l'enum ZbProtocol)
	 * @param dimLevel Non supporté par la zibase pour l'instant
	 * @param nbBurst Nombre d'émissions RF
	 */
	public void sendCommand(String address, ZbAction action, ZbProtocol protocol, int dimLevel, int nbBurst) { 		  
		address = address.toUpperCase();

		ZbRequest request = new ZbRequest();
		request.setCommand((short)11);

		if (action == ZbAction.DIM_BRIGHT && dimLevel == 0)
			action = ZbAction.OFF;

		int p2 = action.ordinal();
		p2 |= (protocol.ordinal() & 0xFF) << 0x08;

		if (action == ZbAction.DIM_BRIGHT)
			p2 |= (dimLevel & 0xFF) << 0x10;

		if (nbBurst > 1)
			p2 |= (nbBurst & 0xFF) << 0x18;

		request.setParam2(p2);
		
		int p3 = Integer.parseInt(address.substring(1)) -1;
		request.setParam3(p3);
		
		char c4 = (char) (address.charAt(0) - 65);
		request.setParam4(c4);

		sendRequest(request, true);
	}

	/**
	 * Lance la commande de l'actionneur spécifié par son adresse et son protocol
	 * @param address
	 * @param action
	 * @param protocol
	 */
	public void sendCommand(String address, ZbAction action, ZbProtocol protocol) {
		sendCommand(address, action, protocol, 0, 1);
	}

	/**
	 * Lance le scenario repéré par son numéro
	 * @param numScenario Le numéro du scenario (indiqué entre parenthèse dans le suivi d'activité de la console)
	 */
	public void launchScenario(int numScenario) {
		ZbRequest request = new ZbRequest();
		request.setCommand((short)11);
		request.setParam1(1);
		request.setParam2(numScenario);
		sendRequest(request, true);	
	}

	/**
	 * Lance un script de commande
	 * @param script le script à lancer
	 * exemple: cmd: lm [toto]
	 */
	public ZbResponse launchScript(String script) {
		ZbRequest request = new ZbRequest();
		request.setCommand((short)16);

		String cmdScript = "cmd: "+script;
		ByteBuffer bb = ByteBuffer.allocate(70 + 96);
		bb.put(request.toBytes());
		bb.put(cmdScript.getBytes());

		return sendRequest(bb.array(), false);
	}

	/**
	 * Récupère la valeur d'une variable Vx de la Zibase
	 * @param numVar le numéro de la variable 
	 * V0 à V14 sont volatiles et remises à zéro à chaque relancement du moteur (modif d'un scénario par exemple)
	 * V15 à V31 sont persitantes 
	 * @return int la valeur de la variable demandée
	 */
	public synchronized Integer getVariable(int numVar) {		

		ZbRequest request = new ZbRequest((short)11, 5, null, 0, numVar);

		ZbResponse response = sendRequest(request, true);
		if (response != null)
			return response.getZbHeader().getParam1();
		return null;			
	}


	/**
	 * Met à jour une variable Zibase avec la valeur spécifiée
	 * @param numéro de la variable (0 à 31)
	 * V0 à V14 sont volatiles et remises à zéro à chaque relancement du moteur (modif d'un scénario par exemple)
	 * V15 à V31 sont persitantes 
	 * @param valeur à écrire 	 
	 */
	public synchronized ZbResponse setVariable(int numVar, int value) {

		ZbRequest request = new ZbRequest();
		request.setCommand((short)11);

		request.setParam1(5);
		request.setParam2(value & 0xFFFF);
		request.setParam3(1);	// WRITE	
		request.setParam4(numVar);	

		return sendRequest(request, true); 		
	}


	/**
	 * Commande VIRTUAL_PROBE_EVENT. 
	 * Cette commande permet au système HOST d’envoyer dans ZiBASE une information de sonde virtuelle comme si celle-ci était reçue sur la RF. 
	 * Il n’implique pas l’enregistrement préalable du système HOST auprès de ZiBASE. 
	 * command = decimal 11 
	 * param1 = 69/21 
	 * V1.14
	 * param2 = Sensor ID (e.g. 4196984322) 
	 * param3 = 
	 *  B15...0 : analog value 1 
	 *  B23..16 : analog value 2 
	 *  B31..24 : Low batt =1 (B26) 
	 * param4 = 
 	 * 	17: Scientific Oregon Type (OS) 
 	 *  20: OWL Type (WS)
 	 *  
	 */	
	public ZbResponse setVirtualProbeValue(int sensorId, byte value1, byte value2, boolean lowBatt, ZbVirtualProbe zbVirtualProbe) {

		ZbRequest request = new ZbRequest();
		request.setCommand((short)11);

		request.setParam1(6);
		request.setParam2(sensorId);
		
		byte[] b = new byte[4];
		b[0] = (byte)(lowBatt?0x4:0x0); 		
		b[1] = value2; 
		b[2] = 0; 
		b[3] = value1;
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(ByteOrder.BIG_ENDIAN);

		request.setParam3(bb.getInt());   
		request.setParam4(zbVirtualProbe.getCode());	

		return sendRequest(request, true); 		
	}

	/**
	 * Commande READ CALENDAR
	 */
	public ZbCalendar readCalendar(int numCalendar) {
		ZbRequest request = new ZbRequest((short)11, 5, null, 2, numCalendar-1);
		ZbCalendar zbCalendar = null;
		
		ZbResponse response = sendRequest(request, true);
		if (response != null) 			
			zbCalendar = new ZbCalendar(response.getZbHeader().getParam1());
		
		return zbCalendar;			
	}

	/**
	 * Commande WRITE CALENDAR
	 */
	public void writeCalendar(int numCalendar, ZbCalendar zbCalendar) {
		
		ZbRequest request = new ZbRequest((short)11, 5, zbCalendar.getValue(), 3, numCalendar-1);

		ZbResponse response = sendRequest(request, true);
		if (response != null)
			System.out.println(response.getZbHeader().getParam1());
	}

}

