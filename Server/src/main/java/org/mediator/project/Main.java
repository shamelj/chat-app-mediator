package org.mediator.project;

import org.mediator.project.client.Client;
import org.mediator.project.mediator.ChatMediator;
import org.mediator.project.mediator.SingleChatroomMediator;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    private static final int PORT = 1234;

    private static ChatMediator mediator = new SingleChatroomMediator();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);

        new ExitThread(serverSocket).start();

        while (true) {
            var client = new Client(serverSocket.accept(), mediator);
            new Thread(() -> mediator.addClient(client)).start();
        }
    }

}