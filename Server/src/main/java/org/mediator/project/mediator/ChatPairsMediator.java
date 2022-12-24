package org.mediator.project.mediator;

import lombok.AllArgsConstructor;
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
        if (singlesPool.isEmpty()) {
            singlesPool.add(client);
            terminal.write(client + " is waiting");
        } else {
            var second =  singlesPool.remove(0);
            doublesPool.putIfAbsent(client, second);
            doublesPool.putIfAbsent(second, client);
            terminal.write(client + " talk with " + second);
        }
    }

    @Override
    public void sendMessage(Client sender, String message) {
        doublesPool.get(sender).send(message);
    }

    @Override
    @SneakyThrows
    public void removeClient(Client client) {
        var other = doublesPool.remove(client);
        doublesPool.remove(other);
        singlesPool.add(other);
        terminal.write(client + " left, "+other+" is waiting");
    }
}
