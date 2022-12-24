package org.mediator.project.client;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.DataInputStream;

@RequiredArgsConstructor
class ClientListener extends Thread {
    private static final String CLIENT_EXIT_STRING = "/exit";
    private final DataInputStream inputStream;
    private final Client client;

    @SneakyThrows
    @Override
    public void run() {
        String message;

        while (!(message = inputStream.readUTF()).equalsIgnoreCase(CLIENT_EXIT_STRING)) {
            client.send(message);
        }

        client.logout();
    }
}
