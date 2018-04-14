package zmbz;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.*;
import java.net.*;

public class NetworkServerThread extends Thread
{

	protected DatagramSocket socket = null;
	protected BufferedReader in = null;
	protected boolean playingGame = true;
	private NetworkMessage myInterface;
	public InetAddress addres;

	private byte[] remoteIP = { (byte) 10, (byte) 131, (byte) 5, (byte) 82 };

	public NetworkServerThread(NetworkMessage intf) throws IOException
	{
		this("GameThread");
		myInterface = intf;
	}

	public NetworkServerThread(String name) throws IOException
	{
		super(name);
		socket = new DatagramSocket(4445);
	}

	public void run()
	{

		while (playingGame)
		{
			try
			{
				byte[] buf = new byte[256];

				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				addres = packet.getAddress();
				String received = new String(packet.getData(), 0, packet.getLength());
				// System.out.println(received);
				myInterface.networkMove(received);

			} catch (IOException e)
			{
				e.printStackTrace();

			}
		}
		socket.close();
	}

	public void setRemoteIP(int a1, int a2, int a3, int a4)
	{
		remoteIP[0] = (byte) a1;
		remoteIP[1] = (byte) a2;
		remoteIP[2] = (byte) a3;
		remoteIP[3] = (byte) a4;
	}

	public void sendMessage(String msg)
	{
		try
		{
			DatagramSocket socket = new DatagramSocket();

			// send request
			byte[] buf = new byte[256];

			InetAddress address = InetAddress.getByAddress(remoteIP);

			buf = msg.getBytes();
			// System.out.println(msg);// "sending packet, address:" + address +
			// ", msg: " + msg);

			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
			socket.send(packet);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
