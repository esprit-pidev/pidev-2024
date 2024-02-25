package Controllers.EventControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import test.Client.ClientThread;

import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
        private static final DatagramSocket socket;

        static {
            try {
                socket = new DatagramSocket(); // init to any available port
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }

        private static final InetAddress address;

        static {
            try {
                address = InetAddress.getByName("localhost");
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }

        private static final String identifier = "samir";

        private static final int SERVER_PORT = 8000; // send to server

    @FXML
    private TextField inputBox;

    @FXML
    private TextArea messageArea;
    @FXML
    private AnchorPane container;

    public AnchorPane getContainer() {
        return container;
    }

    @Override
        public void initialize(URL location, ResourceBundle resources) {



            // thread for receiving messages
            ClientThread clientThread = new ClientThread(socket, messageArea);
            clientThread.start();

            // send initialization message to the server
            byte[] uuid = ("init;" + identifier).getBytes();
            DatagramPacket initialize = new DatagramPacket(uuid, uuid.length, address, SERVER_PORT);
            try {
                socket.send(initialize);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            messageArea.setMaxWidth(500);
            messageArea.setEditable(false);


            inputBox.setMaxWidth(500);
            inputBox.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    String temp = identifier + ";" + inputBox.getText(); // message to send
                    messageArea.setText(messageArea.getText() + inputBox.getText() + "\n"); // update messages on screen
                    byte[] msg = temp.getBytes(); // convert to bytes
                    inputBox.setText(""); // remove text from input box

                    // create a packet & send
                    DatagramPacket send = new DatagramPacket(msg, msg.length, address, SERVER_PORT);
                    try {
                        socket.send(send);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });


        }




    }


