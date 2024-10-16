import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient {
    private static String serverAddress = "127.0.0.1"; // Change if server runs on a different machine
    private static int serverPort = 12345;
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws Exception {
        ChatClientGUI clientGUI = new ChatClientGUI();
        clientGUI.setVisible(true);

        socket = new Socket(serverAddress, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String message = in.readLine();
            if (message == null) break;
            clientGUI.displayMessage(message);
        }
    }

    public static void sendMessage(String message) {
        out.println(message);
    }
}

class ChatClientGUI extends JFrame {
    private JTextField inputField;
    private JTextArea chatArea;
    private JButton sendButton;

    public ChatClientGUI() {
        setTitle("Chat Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField(25);
        sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    ChatClient.sendMessage(message);
                    inputField.setText("");
                }
            }
        });

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    public void displayMessage(String message) {
        chatArea.append(message + "\n");
    }
}
