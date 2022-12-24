package org.mediator.project.mediator;

import lombok.SneakyThrows;
import org.mediator.project.ConsoleTerminal;
import org.mediator.project.Terminal;
import org.mediator.project.client.Client;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class SingleChatroomMediator implements ChatMediator {

    private final Terminal terminal;
    private final Collection<Client> clients;

    public SingleChatroomMediator() {
        clients = Collections.synchronizedSet(new HashSet<>());
        terminal = ConsoleTerminal.getInstance();
    }

    @SneakyThrows
    @Override
    public void addClient(Client client) {

        client.login();
        clients.add(client);

        terminal.write(client + " logged in");

        sendMessage(client.getUsername(), "Logged in!");

    }

    @Override
    public void sendMessage(String senderName, String message) {
        terminal.write(senderName + " is writing");
        clients.forEach(client -> client.receive(senderName, message));
    }

    @Override
    @SneakyThrows
    public void logout(Client client) {
        terminal.write(client + " logged out");
        sendMessage(client.getUsername(), "Logged Out :'^(");
        clients.remove(client);
    }
}
