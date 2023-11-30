package com.example.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupForm {
    private Stage signupStage;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField emailField;

    public SignupForm() {
        initialize();
    }

    private void initialize() {
        signupStage = new Stage();
        signupStage.initModality(Modality.APPLICATION_MODAL);
        signupStage.setTitle("Signup");

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> handleSignUp());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameLabel, usernameField, emailLabel, emailField, passwordLabel, passwordField, signUpButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10; -fx-background-color: #EEE;");

        Scene scene = new Scene(layout, 300, 200);
        signupStage.setScene(scene);
    }

    public void show() {
        signupStage.showAndWait();
    }

    private void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        try {
            // Create a user JSON object
            JSONObject userJson = new JSONObject();
            userJson.put("username", username);
            userJson.put("password", password);
            userJson.put("email", email);

            // Set up the HTTP request
            URL url = new URL("http://localhost:8080/auth/signup"); // Replace with your API URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Send the JSON as body of the request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = userJson.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                // Signup successful
                showSignupSuccessMessage();
            } else if (responseCode == HttpURLConnection.HTTP_CONFLICT) {
                // Email is already registered
                showSignupFailureMessage("Email is already registered.");
            } else {
                // Handle other error cases (show alert or log)
                showSignupFailureMessage("Signup failed. Please try again later.");
                throw new Exception("Signup failed with error code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            // Handle errors (show alert or log)
        }

        // Close the signup window after successful signup
        signupStage.close();
    }

    // Method to display a success message popup
    private void showSignupSuccessMessage() {
        showAlert(Alert.AlertType.INFORMATION, "Signup Successful", "You have successfully signed up!");
    }

    // Method to display a failure message popup
    private void showSignupFailureMessage(String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Signup Failed", errorMessage);
    }

    // Method to display an alert dialog
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
