package com.example;

import com.example.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view-fxml/web-app.fxml"));
        primaryStage.setTitle("Simple Web App");
        primaryStage.setScene(new Scene(root, 609, 460));
        primaryStage.show();
    }
}
