package ro.ubbcluj.map.seminar7.GUI;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.ubbcluj.map.seminar7.domain.Message;
import ro.ubbcluj.map.seminar7.domain.Utilizator;
import ro.ubbcluj.map.seminar7.service.ServiceInvitation;
import ro.ubbcluj.map.seminar7.service.ServiceMessage;
import ro.ubbcluj.map.seminar7.service.ServicePrieten;
import ro.ubbcluj.map.seminar7.service.ServiceUtilizator;
import ro.ubbcluj.map.seminar7.validators.ValidationException;

import java.util.Iterator;

public class ChatController {
    ServiceUtilizator serviceUtilizator;
    ServicePrieten servicePrieten;
    ServiceInvitation serviceInvitation;
    ServiceMessage serviceMessage;
    Stage dialogStage;
    Utilizator currentUser;
    Utilizator fromUser;
    Message reply;

    public ChatController(ServiceUtilizator serviceUtilizator, ServicePrieten servicePrieten, ServiceInvitation serviceInvitation, ServiceMessage serviceMessage, Stage dialogStage, Utilizator currentUser, Utilizator fromUser) {
        this.serviceUtilizator = serviceUtilizator;
        this.servicePrieten = servicePrieten;
        this.serviceInvitation = serviceInvitation;
        this.serviceMessage = serviceMessage;
        this.dialogStage = dialogStage;
        this.currentUser = currentUser;
        this.fromUser = fromUser;
    }

    public void sendMessage(TextField textFieldChat, ChoiceBox<Utilizator> choiceBoxFriends, Message reply){
        String msg = textFieldChat.getText();
//        if(reply != null)
//            this.reply = this.serviceUtilizator.findUser(reply.getId());
//        else
//            this.reply = null;

        if(msg != null){
            this.serviceMessage.addMessage(choiceBoxFriends.getValue(), this.currentUser, msg, reply);
            textFieldChat.clear();
            this.reply = null;
        }
        else{
            throw new ValidationException("Mesajul trimis nu poate fi gol");
        }
        this.reply = null;
    }

//    private void setTextField(Utilizator user,TextField textField, javafx.geometry.Pos alignment){
//        textField.setOnMouseClicked(e -> {
//            this.labelReply.setText("Reply: " + textField.getText());
//            this.reply = user;
//        });
//        textField.setEditable(false);
//        textField.setStyle("-fx-background-color: #FFDBDB");
//        textField.setAlignment(alignment);
//    }
//    private TextField createTextField(Message msg) {
//        TextField t = new TextField();
//        t.setId("message_" + msg.getId().toString());
//        if (msg.getTo().equals(this.currentUser.getId())) {
//            if(msg.getReply().equals(null)) {
//                t.setText(msg.getMessage());
//                setTextField(null, t, Pos.CENTER_LEFT);
//            }
//            else {
//                String m = this.serviceMessage.findMessage(msg.getReply()).getMessage();
//                Utilizator u = this.serviceUtilizator.findUser(msg.getReply());
//                t.setText("Reply: " + m.substring(0,Math.min(m.length(), 10)) + '\n' + msg.getMessage());
//                setTextField(u, t, Pos.CENTER_LEFT);
//            }
//
//        }
//        if (msg.getFrom().equals(this.currentUser.getId())) {
//            if(msg.getReply().equals(null)) {
//                t.setText(msg.getMessage());
//                setTextField(null, t, Pos.CENTER_RIGHT);
//            }
//            else {
//                String m = this.serviceMessage.findMessage(msg.getReply()).getMessage();
//                Utilizator u = this.serviceUtilizator.findUser(msg.getReply());
//                t.setText("Reply: " + m.substring(0,Math.min(m.length(), 10)) + '\n' + msg.getMessage());
//                setTextField(u, t, Pos.CENTER_RIGHT);
//            }
//        }
//
//        return t;
//    }
    public void setChat(ListView<Message> listViewChat){
        listViewChat.getItems().clear();
        Iterable<Message> messages = this.serviceMessage.findAll();
        Iterator<Message> iterator = messages.iterator();
        while(iterator.hasNext()){
            Message msg = iterator.next();
            if(msg.contain(this.currentUser.getId()) && msg.contain(fromUser.getId())) {
                if(msg.getReply() == null)
                    listViewChat.getItems().add(msg);
                else
                    listViewChat.getItems().add(msg);
            }
        }
    }
    private void test(){
//        TextField msg1 = createTextField("Buna", javafx.geometry.Pos.CENTER_RIGHT);
//        TextField msg2 = createTextField("Salut", Pos.CENTER_LEFT);
//        TextField msg3 = createTextField("Ce faci", javafx.geometry.Pos.CENTER_RIGHT);
//        TextField msg4 = createTextField("Bine tu", Pos.CENTER_LEFT);
//        TextField msg5 = createTextField("E fain afara", javafx.geometry.Pos.CENTER_RIGHT);
//        TextField msg6 = createTextField("Stiu", Pos.CENTER_LEFT);
//        TextField msg7 = createTextField("Super", javafx.geometry.Pos.CENTER_RIGHT);
//        TextField msg8 = createTextField("A", Pos.CENTER_LEFT);
//        TextField msg9 = createTextField("B", javafx.geometry.Pos.CENTER_RIGHT);
//        TextField msg10 = createTextField("C", Pos.CENTER_LEFT);
//        TextField msg11 = createTextField("D", javafx.geometry.Pos.CENTER_RIGHT);
//        TextField msg12 = createTextField("E", Pos.CENTER_LEFT);
//        this.vBoxChat.getChildren().addAll(msg1,msg2, msg3, msg4, msg5, msg6, msg7,
//                msg8, msg9, msg10, msg11);

    }
}
