package org.mediator.project;

import lombok.SneakyThrows;

import java.net.Socket;

public class ClientThread extends Thread {
    public static final String EXIT_STRING = "/exit";

    private static final String DOMAIN_NAME = "localhost";

    private static final int PORT = 1234;

    private final Terminal terminal;

    private final Socket socket;

    private final String clientName;

    @SneakyThrows
    public ClientThread(String clientName, Terminal terminal) {
        this.terminal = terminal;
        this.clientName = clientName;
        socket = new Socket(DOMAIN_NAME, PORT);
    }


    @Override
    public void run() {
        new ClientWritingThread(socket, clientName, terminal).start();
        new ClientReadingThread(socket, terminal).start();
    }
}

