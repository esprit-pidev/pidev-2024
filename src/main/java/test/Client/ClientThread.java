package test.Client;


import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientThread extends Thread {

    private DatagramSocket socket;
    private byte[] incoming = new byte[256];

    private TextArea textArea;
    private String roomId;

    public ClientThread(DatagramSocket socket, TextArea textArea,String roomId) {
        this.socket = socket;
        this.textArea = textArea;
        this.roomId  = roomId;
    }

    @Override
    public void run() {
        System.out.println("starting thread");
        while (true) {
            DatagramPacket packet = new DatagramPacket(incoming, incoming.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String message = new String(packet.getData(), 0, packet.getLength()) + "\n";
            String current = textArea.getText();
            String firstWord = message.split(";")[0];

            if (textArea.getUserData().equals(firstWord)) {
                // Remove the first word from the incoming message
                message = removeFirstWord(message);

                // Append the modified message to the textArea
                textArea.setText(current + message);
            }
        }
    }

    public static String removeFirstWord(String phrase) {
        int index = phrase.indexOf(';');
        if (index != -1) { // If there is at least one semicolon
            return phrase.substring(index + 1);
        } else { // If there is no semicolon (only one word)
            return "";
        }
    }

}

