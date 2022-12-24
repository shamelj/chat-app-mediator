package org.mediator.project.mediator;

import org.mediator.project.client.Client;

public interface ChatMediator {

    void addClient(Client client);

    void sendMessage(Client sender, String message);

    void removeClient(Client client);
}
