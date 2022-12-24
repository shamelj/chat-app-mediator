package org.mediator.project;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

public class GuiTerminal extends JFrame implements Terminal {

    private final Semaphore semaphore;
    private JTextField messageField; // text field for entering messages
    private JTextArea messageArea; // text area for displaying messages
    private boolean isClicked = false;

    public GuiTerminal() {
        // create a frame and set its properties
        setTitle("Chat Room");
        setSize(500, 500);
        setLocationRelativeTo(null); // center the frame on the screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // create a panel for the message field and send button
        JPanel messagePanel = new JPanel();
        messageField = new JTextField(30);
        JButton sendButton = new JButton("Send");
        messagePanel.add(messageField);
        messagePanel.add(sendButton);

        // create a text area for displaying messages
        messageArea = new JTextArea();
        messageArea.setEditable(false); // set text area to be read-only
        JScrollPane scrollPane = new JScrollPane(messageArea); // add scroll bar to text area

        // add the message panel and text area to the frame
        add(messagePanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        semaphore = new Semaphore(0);
        // add an action listener to the send button
        sendButton.addActionListener(new ActionListener() {
            @SneakyThrows
            public void actionPerformed(ActionEvent e) {
                // get the message from the message field
                String message = messageField.getText();
                // set clicked flag to true
//                isClicked = true;
                semaphore.release();
                // to release the thread
                Thread.sleep(1000);
                // clear the message field
                messageField.setText("");
            }
        });
        this.setVisible(true);
    }

    @Override
    public String read(String displayMassage) {
        write(displayMassage);
        return read();
    }

    @Override
    public String read() {
        semaphore.acquireUninterruptibly();
//        while (!isClicked) {
//            try {
//                Thread.sleep(100); // wait for 100 milliseconds
//            } catch (InterruptedException ex) {
//                // do nothing
//            }
//        }
        var message = messageField.getText();
        messageField.setText("");
        isClicked = false;
        return message;
    }

    @Override
    public void write(Object message) {
        messageArea.append(message.toString() + "\n");
    }
}
