package org.luncert.Mullog.appender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;

import org.luncert.Mullog.formatter.Formatter;

public final class UDPAppender implements Appender {

	private Formatter formatter;

	private DatagramSocket udp;

	private InetAddress addr;

	private int port;

	public UDPAppender(Properties props) throws SocketException, UnknownHostException {
		InetAddress addr = InetAddress.getByName(props.getProperty("host"));
		int port = Integer.valueOf(props.getProperty("port"));
		udp = new DatagramSocket();
		this.addr = addr;
		this.port = port;
	}
	
	private void output(String data) throws IOException {
		byte[] buf = data.getBytes();
		udp.send(new DatagramPacket(buf, buf.length, addr, port));
	}

	@Override
	public Appender setFormatter(Formatter formatter) {
		this.formatter = formatter;
		return this;
	}

	@Override
	public void log(int logLevel, String message) throws Exception {
		if (formatter == null) throw new Exception("no formatter set");
		output(formatter.format(logLevel, message));
	}

	@Override
	public void finalize() {
		udp.close();
	}
	
}