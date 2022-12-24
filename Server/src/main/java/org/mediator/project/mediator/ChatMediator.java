package org.mediator.project.mediator;

import org.mediator.project.client.Client;

public interface ChatMediator {

    void addClient(Client clientSocket);

    void sendMessage(String username, String message);

    void logout(Client client);
}
