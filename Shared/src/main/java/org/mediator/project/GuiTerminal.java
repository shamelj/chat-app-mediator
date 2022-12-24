package org.mediator.project;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Semaphore;

public class GuiTerminal extends JFrame implements Terminal {

    public static final String EXIT_STRING = "/exit";
    private final Semaphore isButtonClickedSemaphore;
    private JTextField messageField; // text field for entering messages
    private JTextArea messageArea; // text area for displaying messages

    public GuiTerminal() {
        // set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // handle exception
        }

        // create a frame and set its properties
        initializeFrame();

        // create a panel for the message field and send button
        JPanel messagePanel = initializeMessagePanel();
        JButton sendButton = initializeSendButton();
        messagePanel.add(messageField);
        messagePanel.add(sendButton);

        // create a text area for displaying messages
        initializeMessageTextArea();
        JScrollPane scrollPane = initializeScrollPane();

        // use a BorderLayout to arrange the message panel and text area in the frame
        add(messagePanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        // send button isn't clicked yet
        isButtonClickedSemaphore = new Semaphore(0);

        // add an action listener to the send button
        sendButton.addActionListener(new ActionListener() {
            @SneakyThrows
            public void actionPerformed(ActionEvent e) {
                // set clicked flag to true
                isButtonClickedSemaphore.release();
                // give enough time for read() to read the text field before clearing it
                    Thread.sleep(300);
                // clear the message field
                messageField.setText("");
            }
        });

        // add a window listener to the frame
        addWindowListener(new WindowAdapter() {
            // override the windowClosing method
            public void windowClosing(WindowEvent e) {
                // show a confirmation dialog
                messageField.setText(EXIT_STRING);
                isButtonClickedSemaphore.release();
            }
        });

        this.setVisible(true);
    }

    private JScrollPane initializeScrollPane() {
        return new JScrollPane(messageArea);
    }

    private void initializeMessageTextArea() {
        messageArea = new JTextArea();
        messageArea.setEditable(false); // set text area to be read-only

        // set the font for the message area
        messageArea.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    private static JButton initializeSendButton() {
        JButton sendButton = new JButton("Send");

        // set the font for the send button
        sendButton.setFont(new Font("Arial", Font.BOLD, 16));

        // set the background color to green
        sendButton.setBackground(new Color(0, 150, 0));

        // set the foreground color (text color) to black
        sendButton.setForeground(Color.BLACK);

        return sendButton;
    }

    private JPanel initializeMessagePanel() {
        JPanel messagePanel = new JPanel();
        messageField = new JTextField(30);

        // set the font for the message field
        messageField.setFont(new Font("Arial", Font.PLAIN, 16));

        return messagePanel;
    }

    private void initializeFrame() {
        setTitle("Chat Room");
        setSize(500, 500);
        setLocationRelativeTo(null); // center the frame on the screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set the icon for the frame
        ImageIcon icon = new ImageIcon("chat-icon.png");
        setIconImage(icon.getImage());
    }

    @Override
    public String read(String displayMessage) {
        write(displayMessage);
        return read();
    }

    @Override
    public String read() {
        isButtonClickedSemaphore.acquireUninterruptibly();
        isButtonClickedSemaphore.drainPermits();
        var message = messageField.getText();
        messageField.setText("");
        return message;
    }

    @Override
    public void write(Object message) {
        messageArea.append(message.toString() + "\n");
    }
}
