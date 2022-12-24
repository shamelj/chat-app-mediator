package org.mediator.project;

import org.mediator.project.client.Client;
import org.mediator.project.mediator.ChatMediatorFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    private static final int PORT = 1234;

    public static final Terminal terminal = ConsoleTerminal.getInstance();

    private static final ChatMediatorFactory mediatorFactory = new ChatMediatorFactory();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);

        var type = terminal.read("Enter chatting type: \nsingle-chatroom\npairs");

        var mediator = mediatorFactory.getChatMediator(type);

        if(mediator == null) {
            return;
        }

        new ExitThread(serverSocket).start();

        while (true) {
            mediator.addClient(new Client(serverSocket.accept(), mediator));
        }
    }

}