package com.valeriygulin.ssespringbootjavafx.controllers;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import com.valeriygulin.ssespringbootjavafx.App;
import com.valeriygulin.ssespringbootjavafx.model.Message;
import com.valeriygulin.ssespringbootjavafx.retrofit.MessageRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

public class MainController {
    @FXML
    public TextField textFieldYourMessage;
    @FXML
    public ListView listViewUsersChat;
    @FXML
    public ListView listViewUsersOnline;
    private LinkedList<String> list = new LinkedList<>();

    Preferences prefs = Preferences.userRoot().node(App.class.getName());

    private MessageRepository messageRepository = new MessageRepository();

    private Thread thread = new Thread(() -> {
        try {
            while (true) {
                System.out.println("Initialize event source");
                long id = Long.parseLong(prefs.get("id", ""));
                String url = "http://localhost:8080/sse/chat/" + id;
                EventSource.Builder builder = new EventSource.Builder(new EventHandler() {
                    @Override
                    public void onOpen() {
                        System.out.println("onOpen");
                    }

                    @Override
                    public void onClosed() {
                        System.out.println("onClosed");
                    }

                    @Override
                    public void onMessage(String event, MessageEvent messageEvent) {
                        System.out.println(messageEvent.getData());
                        Platform.runLater(() -> {
                            list.add(messageEvent.getData());
                            listViewUsersChat.setItems(FXCollections.observableList(list));
                            try {
                                List<Long> longs = messageRepository.get();
                                listViewUsersOnline.setItems(FXCollections.observableList(longs));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    @Override
                    public void onComment(String comment) {
                        System.out.println("onComment");
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("onError: " + t);
                    }
                }, URI.create(url));

                try (EventSource eventSource = builder.build()) {
                    eventSource.start();
                    TimeUnit.MINUTES.sleep(1);
                }
            }
        } catch (InterruptedException ignored) {
        }
    });

    @FXML
    public void initialize() {
        this.thread.start();
    }

    @FXML
    public void buttonSendMessage(ActionEvent actionEvent) throws IOException {
        String text = this.textFieldYourMessage.getText();
        if (text == null) {
            App.showAlert("Error!", "You try to send null message!", Alert.AlertType.ERROR);
            return;
        }
        long id = Long.parseLong(prefs.get("id", ""));
        Message message = new Message();
        message.setContent(text);
        this.messageRepository.post(id, message);
        this.textFieldYourMessage.clear();
    }

    public void buttonLogOut(ActionEvent actionEvent) throws IOException {
        Preferences prefs = Preferences.userRoot().node(App.class.getName());
        prefs.put("login", "");
        prefs.put("password", "");
        App.openWindow("authentification.fxml", "Authorization form", null);
        App.closeWindow(actionEvent);
    }
}
