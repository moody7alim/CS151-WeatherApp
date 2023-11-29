package com.example.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;

import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class LoginForm {
    private Stage loginStage;
    private RestTemplate restTemplate;

    private TextField emailField;
    private PasswordField passwordField;

    public LoginForm() {
        restTemplate = new RestTemplate();
        initialize();
    }

    private void initialize() {
        loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setTitle("Login");


        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, loginButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

        // Apply CSS classes to components
        layout.getStyleClass().add("login-form");
        passwordField.getStyleClass().add("login-form-field");
        loginButton.getStyleClass().add("login-form-button");
        loginStage.setScene(scene);
    }

    public void show() {
        loginStage.showAndWait();
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        // Create a JSON object for the login request
        JSONObject loginRequest = new JSONObject();
        loginRequest.put("email", email);
        loginRequest.put("password", password);

        // Send a login request to the Spring API
        String loginUrl = "http://localhost:8080/login"; // Replace with your API URL

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(loginRequest.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Authentication successful, close the login window or take further actions
                loginStage.close();
            } else {
                // Authentication failed, display an error message to the user
                showError("Login failed. Please check your credentials.");
            }
        } catch (RestClientException ex) {
            // Handle the exception (e.g., connection error)
            showError("An error occurred. Please try again later.");
        }
    }

    private void showError(String message) {
        // Implement a method to display an error message to the user
        // You can use a Label, Alert, or other UI element to show the error message
    }
}
