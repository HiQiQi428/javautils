package org.luncert.mullog.appender;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;

public final class UDPAppender extends StandardAppender {

	private DatagramSocket udp;

	private InetAddress addr;

	private int port;

	public UDPAppender(Properties props) throws SocketException, UnknownHostException {
		super(props);
		InetAddress addr = InetAddress.getByName(props.getProperty("host"));
		int port = Integer.valueOf(props.getProperty("port"));
		udp = new DatagramSocket();
		this.addr = addr;
		this.port = port;
	}

	@Override
	public void finalize() { udp.close(); }

	@Override
	protected void output(String data) throws Exception {
		byte[] buf = data.getBytes();
		udp.send(new DatagramPacket(buf, buf.length, addr, port));
	}
	
}