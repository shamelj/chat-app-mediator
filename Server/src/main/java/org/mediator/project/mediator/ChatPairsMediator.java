package org.mediator.project.mediator;

import lombok.SneakyThrows;
import org.mediator.project.ConsoleTerminal;
import org.mediator.project.Terminal;
import org.mediator.project.client.Client;

import java.util.*;

public class ChatPairsMediator implements ChatMediator {
    private final Terminal terminal;

    private final List<Client> singlesPool;
    private final Map<Client, Client> doublesPool;

    public ChatPairsMediator() {
        terminal = ConsoleTerminal.getInstance();
        singlesPool = Collections.synchronizedList(new LinkedList<>());
        doublesPool = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public void addClient(Client client) {
        client.login();
        if (singlesPool.isEmpty()) {
            singlesPool.add(client);
            terminal.write(client + " is waiting");
        } else {
            var second = singlesPool.remove(0);
            doublesPool.putIfAbsent(client, second);
            doublesPool.putIfAbsent(second, client);
            terminal.write(client + " talk with " + second);
        }
        sendMessage(client, "Logged in!");
    }

    @Override
    public void sendMessage(Client sender, String message) {
        var receiver = Optional.ofNullable(doublesPool.get(sender));
        receiver.ifPresent(client -> client.receive(sender.getUsername(), message));
        sender.receive(sender.getUsername(), message);
    }

    @Override
    @SneakyThrows
    public void removeClient(Client client) {
        final var loggedOutMessage = "Logged Out :'^(";
        sendMessage(client, loggedOutMessage);
        var other = Optional.ofNullable(doublesPool.remove(client));
        if (other.isEmpty()) singlesPool.remove(client);
        other.ifPresent(otherClient -> {
            doublesPool.remove(otherClient);
            singlesPool.add(otherClient);
        });
        terminal.write(client + " left, " + other.orElse(null) + " is waiting");
    }
}
