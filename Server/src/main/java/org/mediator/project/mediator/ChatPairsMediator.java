package org.mediator.project.mediator;

import lombok.SneakyThrows;
import org.mediator.project.ConsoleTerminal;
import org.mediator.project.Terminal;
import org.mediator.project.client.Client;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class ChatPairsMediator implements ChatMediator {
    private final Terminal terminal;

    private final Collection<Client> singlesPool;
    private final Collection<Pair> doublesPool;

    public ChatPairsMediator() {
        terminal = ConsoleTerminal.getInstance();
        singlesPool = Collections.synchronizedSet(new HashSet<>());
        doublesPool = Collections.synchronizedSet(new HashSet<>());
    }

    @Override
    public void addClient(Client client) {
        if (singlesPool.isEmpty()) {
            singlesPool.add(client);
        } else {

        }
    }

    @Override
    public void sendMessage(String username, String message) {
    }

    @Override
    @SneakyThrows
    public void logout(Client client) {
    }

    private class Pair {
        Client first;
        Client second;
    }
}
