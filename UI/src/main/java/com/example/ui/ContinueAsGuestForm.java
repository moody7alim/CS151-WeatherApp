package com.example.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.CollationElementIterator;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

public class ContinueAsGuestForm {
//    private Stage guestStage;
//    private TextField locationField;
//    private Label cityLabel, dateLabel, temperatureLabel, sunriseLabel, windLabel, humidityLabel;
//    private ImageView weatherIcon;
//    private VBox mainLayout;
//    private TextArea weatherInfoArea;

    private Stage guestStage;
    private TextField locationField;
    private Button fetchWeatherButton;
    private Label cityLabel, dateLabel, temperatureLabel, sunriseLabel, windLabel, humidityLabel;
    private ImageView weatherIcon;
    private VBox mainLayout;


    // icons
    private FontIcon sunriseIconView, windIconView, humidityIconView;


    public ContinueAsGuestForm() {
        initialize();
    }

    private void initialize() {
        guestStage = new Stage();
        guestStage.initModality(Modality.APPLICATION_MODAL);
        guestStage.setTitle("Weather Information");

        mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setId("mainLayout");

        // Initialize components
        locationField = new TextField();
        locationField.setPromptText("Enter a city name");
        locationField.getStyleClass().add("location-field"); // Adding a CSS class

        // Icon for the fetch weather button
        FontIcon weatherButtonIcon = new FontIcon(); // Create a new FontIcon instance
        weatherButtonIcon.setIconLiteral("fas-search"); // fas-cloud-sun-rain
        weatherButtonIcon.setIconSize(16); // Set icon size as needed

        fetchWeatherButton = new Button("", weatherButtonIcon); // Add the icon to the button
        fetchWeatherButton.setContentDisplay(ContentDisplay.LEFT); // Set the icon position (left of text)
        fetchWeatherButton.setOnAction(e -> fetchWeatherData()); // Set the action for the button
        fetchWeatherButton.getStyleClass().add("weather-button");

        // New HBox for location input and button
        HBox locationInputLayout = new HBox(5); // 10 is the spacing between the text field and the button
        locationInputLayout.setAlignment(Pos.CENTER); // Center alignment within the HBox
        locationInputLayout.getChildren().addAll(locationField, fetchWeatherButton); // Add the text field and button to the HBox


        cityLabel = new Label();
        cityLabel.setId("cityLabel");

        dateLabel = new Label();
        dateLabel.setId("dateLabel");

        temperatureLabel = new Label();
        temperatureLabel.setId("temperatureLabel");

        weatherIcon = new ImageView();
        weatherIcon.setFitHeight(100); // Set the size as needed
        weatherIcon.setFitWidth(100);
        weatherIcon.setId("weatherIcon");

        // Initialize labels with placeholders for icons, they will be invisible without an icon literal
        sunriseLabel = new Label();
        sunriseLabel.setId("sunriseLabel");

        windLabel = new Label();
        windLabel.setId("windLabel");

        humidityLabel = new Label();
        humidityLabel.setId("humidityLabel");

        // Create the FontIcon objects without setting an icon literal
        sunriseIconView = new FontIcon();
        windIconView = new FontIcon();
        humidityIconView = new FontIcon();

        // Layout for weather details such as sunrise, wind, humidity
        HBox detailsLayout = new HBox(30);
        detailsLayout.setAlignment(Pos.CENTER);
        // Create placeholders for icons
        detailsLayout.getChildren().addAll(
                createVBox(sunriseIconView, sunriseLabel),
                createVBox(windIconView, windLabel),
                createVBox(humidityIconView, humidityLabel)
        );

        // Add all components to the main layout
        mainLayout.getChildren().addAll(locationInputLayout, cityLabel, dateLabel, weatherIcon, temperatureLabel, detailsLayout);

        Scene scene = new Scene(mainLayout, 300, 500); // Adjust the size as needed
        scene.getStylesheets().add(getClass().getResource("/com/example/ui/weather.css").toExternalForm());
        guestStage.setScene(scene);
    }

    // Helper method to create a VBox containing an icon and label
    private VBox createVBox(FontIcon icon, Label label) {
        icon.setIconSize(24); // Set a default icon size (will be used when icon is visible)
        VBox vbox = new VBox(5, icon, label); // 5 is the spacing between icon and label
        vbox.setAlignment(Pos.CENTER); // Center align the content
        return vbox;
    }

    public void show() {
        guestStage.showAndWait();
    }

    private void fetchWeatherData() {
        try {
            String location = locationField.getText().trim().replace(" ", "%20");
            URL url = new URL("http://localhost:8080/weather?location=" + location);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(response.toString());
            displayWeatherInfo(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayWeatherInfo(JSONObject weatherData) {
        // Assume weatherData is the root JSON object from the API response
        String city = weatherData.getString("name");
        JSONObject main = weatherData.getJSONObject("main");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        JSONObject wind = weatherData.getJSONObject("wind");
        double windSpeed = wind.getDouble("speed");
        JSONArray weatherArray = weatherData.getJSONArray("weather");
        String mainWeather = weatherArray.getJSONObject(0).getString("main");
        String iconCode = weatherArray.getJSONObject(0).getString("icon");
        long sunriseTime = weatherData.getJSONObject("sys").getLong("sunrise") * 1000;

        // Fetch timezone offset from the weather data
        long timezoneOffset = weatherData.getLong("timezone"); // Assuming it's in seconds

        // Calculate the local time of the city
        Instant now = Instant.now();
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds((int) timezoneOffset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, zoneOffset);

        // Pass the localDateTime to the updateBackgroundStyle method
        updateBackgroundStyle(mainWeather, localDateTime);

        // Format the current time
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, hh:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(zoneOffset)); // Set the timezone for the date format
        String formattedDate = dateFormat.format(Date.from(localDateTime.atZone(zoneOffset).toInstant()));

        // Format the sunrise time
        // Assuming sunriseTime is in seconds since epoch, convert to milliseconds
        Date sunriseDate = new Date(sunriseTime * 1000L);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String formattedSunrise = timeFormat.format(sunriseDate);

        // Load the weather icon from the URL
        String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
        Image iconImage = new Image(iconUrl, true); // 'true' enables background loading
        weatherIcon.setImage(iconImage);
        // make the weather icon bigger
        weatherIcon.setFitHeight(200);
        weatherIcon.setFitWidth(200);

        // Now that we have the data, we can set the icon literals
        sunriseIconView.setIconLiteral("fas-sun");
        windIconView.setIconLiteral("fas-wind");
        humidityIconView.setIconLiteral("fas-tint");

        // Display the weather data
        cityLabel.setText(city);
        cityLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        dateLabel.setText(formattedDate);
        dateLabel.setStyle("-fx-font-size: 20px;");

        temperatureLabel.setText(String.format("%.1f°C", temperature));
        temperatureLabel.setStyle("-fx-font-size: 50px; -fx-font-weight: bold;");

        sunriseLabel.setText("  Sunrise\n" + formattedSunrise);
        sunriseLabel.setStyle("-fx-font-size: 16px;");

        windLabel.setText("  Wind\n" + String.format("%.1f m/s", windSpeed));
        windLabel.setStyle("-fx-font-size: 16px;");

        humidityLabel.setText("Humidity\n" + "    " + String.format("%d%%", humidity));
        humidityLabel.setStyle("-fx-font-size: 16px;");
    }

    private void updateBackgroundStyle(String weatherCondition, LocalDateTime localDateTime) {
        String backgroundStyle;
        String textColor = "#FFFFFF"; // Default text color

        // Determine the time of day based on localDateTime
        if (localDateTime.getHour() < 12) {
            backgroundStyle = weatherCondition.equals("Sunny") ? "morning-sunny" : "morning-cloudy";
            textColor = weatherCondition.equals("Sunny") ? "#333333" : "#FFFFFF";
        } else if (localDateTime.getHour() < 18) {
            backgroundStyle = weatherCondition.equals("Sunny") ? "afternoon-sunny" : "afternoon-cloudy";
            textColor = weatherCondition.equals("Sunny") ? "#333333" : "#FFFFFF";
        } else {
            backgroundStyle = weatherCondition.equals("Clear") ? "night-clear" : "night-cloudy";
            textColor = "#FFFFFF";
        }

        mainLayout.getStyleClass().clear();
        mainLayout.getStyleClass().add(backgroundStyle);

        // Update text color of all labels
        cityLabel.setStyle("-fx-text-fill: " + textColor + ";");
        dateLabel.setStyle("-fx-text-fill: " + textColor + ";");
        temperatureLabel.setStyle("-fx-text-fill: " + textColor + ";");
        sunriseLabel.setStyle("-fx-text-fill: " + textColor + ";");
        windLabel.setStyle("-fx-text-fill: " + textColor + ";");
        humidityLabel.setStyle("-fx-text-fill: " + textColor + ";");
    }
}