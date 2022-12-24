package org.mediator.project;

import java.util.Scanner;

public class ConsoleTerminal implements Terminal {
    private static volatile ConsoleTerminal terminal;

    private final Scanner scanner = new Scanner(System.in);

    public static ConsoleTerminal getInstance() {
        if (terminal == null) {
            synchronized (ConsoleTerminal.class) {
                if (terminal == null) {
                    terminal = new ConsoleTerminal();
                }
            }
        }
        return terminal;
    }

    public String read(String displayMassage) {
        write(displayMassage);
        return read();
    }

    public String read() {
        return scanner.nextLine().trim();
    }

    public void write(Object massage) {
        System.out.println(massage);
    }
}
