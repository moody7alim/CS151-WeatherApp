package com.example.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class HelloApplication extends Application {
    private final String API_URL = "http://localhost:8080/weather";
    private Stage primaryStage;
    private Label weatherLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Weather App - Home");

        Button loginButton = new Button("Login");
        Button signUpButton = new Button("Sign Up");
        Button guestButton = new Button("Continue as Guest");

        loginButton.setOnAction(e -> showLoginForm());
        signUpButton.setOnAction(e -> showSignUpForm());
        guestButton.setOnAction(e -> continueAsGuest());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(loginButton, signUpButton, guestButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        scene.getStylesheets().add(getClass().getResource("homepage.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();


//        // Create UI components
//        Label locationLabel = new Label("Enter City:");
//        TextField locationField = new TextField();
//        Button getWeatherButton = new Button("Get Weather");
//        weatherLabel = new Label();
//
//
//        // Create layout
//        VBox layout = new VBox(10);
//        layout.setPadding(new Insets(10));
//        layout.getChildren().addAll(locationLabel, locationField, getWeatherButton, weatherLabel);
//
//        // Handle button click
//        getWeatherButton.setOnAction(e -> {
//            String location = locationField.getText().trim();
//            if (!location.isEmpty()) {
//                // Fetch weather data on a background thread
//                WeatherService weatherService = new WeatherService(location);
//                weatherService.start();
//            }
//        });
    }

    private void showLoginForm() {
        LoginForm loginForm = new LoginForm();
        loginForm.show();
    }

    private void showSignUpForm() {
        SignupForm signupForm = new SignupForm();
        signupForm.show();
    }

    private void continueAsGuest() {
        ContinueAsGuestForm guestForm = new ContinueAsGuestForm();
        guestForm.show();
    }


    // Service for fetching weather data
    private class WeatherService extends Service<String> {
        private final String location;

        public WeatherService(String location) {
            this.location = location;
        }

        // invoced on the FX Application Thread after the Service has been started. weatherService.start()
        @Override
        protected Task<String> createTask() {
            return new Task<>() {
                @Override
                protected String call() throws Exception {
                    return getWeatherData(location);
                }
            };
        }

        @Override
        protected void succeeded() {
            // which function calls this? weatherService.start()
            super.succeeded();
            // getValue() is a method of Task class. It returns the result of the task if it is in the state SUCCEEDED
            weatherLabel.setText(getValue());
        }
    }

    // Make HTTP request to fetch weather data
    private String getWeatherData(String location) {
        try {

            URL url = new URL(API_URL + "?location=" + location.replace(" ", "%20") + "&units=metric" + "&lang=ar");
            System.out.println("URL: " + url.toString());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONObject main = jsonObject.getJSONObject("main");
            double temperature = main.getDouble("temp");

            return "Temperature in " + location + ": " + temperature + "°C";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching weather data:\n"+ e.getMessage());
            return "Error fetching weather data";
        }
    }
}
