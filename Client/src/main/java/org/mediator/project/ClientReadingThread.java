package org.mediator.project;

import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReadingThread extends Thread {

    private final Terminal terminal;


    private final Socket socket;

    private final String clientName;

    private final DataInputStream inputStream;

    @SneakyThrows
    public ClientReadingThread(Socket socket, String clientName, Terminal terminal) {
        this.terminal = terminal;
        this.socket = socket;
        this.clientName = clientName;
        inputStream = new DataInputStream((socket.getInputStream()));
    }

    @Override
    public void run() {
        String message = "";
        while (!socket.isInputShutdown()) {
            try {
                message = readFromServer();
            } catch (IOException e) {
                System.exit(0);
            }
            terminal.write(message);
        }
    }

    private String readFromServer() throws IOException {
        return inputStream.readUTF();
    }
}
