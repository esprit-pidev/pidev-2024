package Controllers.EventControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import test.Client.ClientThread;
import tn.esprit.entities.User.User;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import java.net.*;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
        private static final DatagramSocket socket;
        AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();
        private User user =userLoggedIn;
        UserService userService = new UserService();

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

        int userId = user.getId();
        User name = userService.getAll().stream().filter(e->e.getId() ==userId ).findFirst().orElse(null);
        private  String identifier = name.getNom();

        private static final int SERVER_PORT = 8000; // send to server

    @FXML
    private TextField inputBox;
    @FXML
    private Label expand;
    @FXML
    private VBox CommentsContainer;
    @FXML
    private TextArea messageArea;
   @FXML
    VBox container;
   private boolean isCollapsed = false;

   private String roomId ;

    public VBox getContainer() {
        return container;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public TextArea getMessageArea() {
        return messageArea;
    }


    @Override
        public void initialize(URL location, ResourceBundle resources) {

        CommentsContainer.setVisible(false);
        CommentsContainer.setMaxHeight(0);

        Platform.runLater(()->{
            ClientThread clientThread = new ClientThread(socket, messageArea,roomId);
            clientThread.start();
            System.out.println(messageArea.getUserData());
            });

            // send initialization message to the server
            byte[] uuid = ("init;" + identifier).getBytes();
            DatagramPacket initialize = new DatagramPacket(uuid, uuid.length, address, SERVER_PORT);
            try {
                socket.send(initialize);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            messageArea.setEditable(false);



            inputBox.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    String temp =roomId +";"+ identifier + ";" + inputBox.getText(); // message to send
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


    public void ExpendOrCollapse(MouseEvent mouseEvent) {
        
        isCollapsed =!isCollapsed;
        if(!isCollapsed){
            CommentsContainer.setVisible(false);
            CommentsContainer.setMaxHeight(0);
            expand.setText("Expand");

        }
        else {CommentsContainer.setVisible(true);
            CommentsContainer.setMaxHeight(300);
            expand.setText("Collapse");

        }
    }
}


