package org.mediator.project;

import lombok.SneakyThrows;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.mediator.project.ClientThread.EXIT_STRING;

public class ClientWritingThread extends Thread {
    private final Socket socket;

    private final String clientName;

    private final DataOutputStream outputStream;

    private final Terminal terminal;

    @SneakyThrows
    public ClientWritingThread(Socket socket, String clientName, Terminal terminal) {
        this.terminal = terminal;
        this.socket = socket;
        this.clientName = clientName;
        outputStream = new DataOutputStream((socket.getOutputStream()));
    }

    @SneakyThrows
    @Override
    public void run() {
        String message = clientName;
        do {
            sendToServer(message);
            message = terminal.read();
        } while (!message.trim().equalsIgnoreCase(EXIT_STRING));
        sendToServer(EXIT_STRING);
        socket.shutdownOutput();
        socket.getInputStream().close();
        socket.close();

    }

    private void sendToServer(String message) throws IOException {
        outputStream.writeUTF(message);
    }
}
