package org.luncert.objpool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Objpool implements Runnable {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket socket = new ServerSocket(Configure.port);
        Socket client;
        while (true) {
            client = socket.accept();
            executorService.execute(new Objpool(client));
        }
    }

    private Socket client;

    public Objpool(Socket client) {
        this.client = client;
    }

	@Override
	public void run() {
		// transaction
	}

}