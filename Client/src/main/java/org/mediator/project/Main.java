package org.mediator.project;

public class Main {

    public static void main(String[] args) {
        Terminal terminal = new GuiTerminal();
        var username = terminal.read("Enter your username:");
        terminal.write(username);
        new ClientThread(username, terminal).start();
    }
}
