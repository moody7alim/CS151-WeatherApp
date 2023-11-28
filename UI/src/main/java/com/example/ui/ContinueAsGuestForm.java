package com.example.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.json.*;
import org.kordamp.ikonli.javafx.FontIcon;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ContinueAsGuestForm {
    // Instance variables
    private String localURL = "http://localhost:8080/weather";
    private Stage guestStage;
    private TextField locationField;
    private Label cityLabel, dateLabel, temperatureLabel, sunriseLabel, windLabel, humidityLabel;
    private ImageView weatherIcon;
    private String sunriseText = "sunrise";
    private String windText = "wind";
    private String humidityText = "humidity";
    private FontIcon sunriseIconView, windIconView, humidityIconView;
    private boolean isCelsius = true; // Default unit
    private JSONObject currentWeatherData; // Declare this at the class level
    private static String language = "en";
    private HBox detailsLayout;

    // Default constructor
    public ContinueAsGuestForm() {
        initialize();
    }

    /** Initialize */
    private void initialize() {
        guestStage = new Stage();
        guestStage.initModality(Modality.APPLICATION_MODAL);
        guestStage.setTitle("Weather Information");

        VBox mainLayout = new VBox(10);
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

        Button fetchWeatherButton = new Button("", weatherButtonIcon); // Add the icon to the button
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
        detailsLayout = new HBox(30);
        detailsLayout.setAlignment(Pos.CENTER);
        // Create placeholders for icons
        detailsLayout.getChildren().addAll(
                createVBox(sunriseIconView, sunriseText, sunriseLabel),
                createVBox(windIconView, windText, windLabel),
                createVBox(humidityIconView, humidityText, humidityLabel)
        );
        detailsLayout.setVisible(false); // Hide the layout until weather data is fetched

        // Inside your initialize method, after initializing other components
        ComboBox<String> unitComboBox = new ComboBox<>();
        unitComboBox.getItems().addAll("°C", "°F"); // Add Celsius and Fahrenheit options
        unitComboBox.setValue("°C"); // Set default value to Celsius
        unitComboBox.setOnAction(e -> updateTemperatureUnit(unitComboBox.getValue()));

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English", "Russian", "French", "Japanese", "Korean", "Vietnamese"); // Add languages as needed
        languageComboBox.setValue("English"); // Set default language
        languageComboBox.setOnAction(e -> updateLanguage(languageComboBox.getValue()));

        // Add the ComboBox to your layout
        locationInputLayout.getChildren().add(languageComboBox); // Adjust layout as needed

        // Inside your initialize method, add the toggle button to the layout
        locationInputLayout.getChildren().add(unitComboBox); // Add it to the appropriate place in the layout

        // Add all components to the main layout
        mainLayout.getChildren().addAll(locationInputLayout, cityLabel, dateLabel, weatherIcon, temperatureLabel, detailsLayout);

        Scene scene = new Scene(mainLayout, 600, 500); // Adjust the size as needed
        scene.getStylesheets().add(getClass().getResource("/com/example/ui/weather.css").toExternalForm());
        guestStage.setScene(scene);
    }

    // Helper method to create a VBox containing an icon and label
    private VBox createVBox(FontIcon icon, String s, Label label) {
        icon.setIconSize(24); // Set a default icon size (will be used when icon is visible)
        VBox vbox = new VBox(5, icon, new Label(s), label); // 5 is the spacing between icon and label
        vbox.setAlignment(Pos.CENTER); // Center align the content

        return vbox;
    }

    public void show() {
        guestStage.showAndWait();
    }

    private void fetchWeatherData() {
        try {
            String location = locationField.getText().trim().replace(" ", "%20");
            URL url = new URL(localURL + "?location=" + location + "&lang=" + language);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if(responseCode == 404) {
                throw new Exception("wrong city name");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            currentWeatherData = new JSONObject(response.toString());
            displayWeatherInfo(currentWeatherData);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    /** Display weather info */
    private void displayWeatherInfo(JSONObject weatherData) {
        // Assume weatherData is the root JSON object from the API response
        String city = weatherData.getString("name");
        JSONObject main = weatherData.getJSONObject("main");

        // Assuming the temperature in the JSON is always in Celsius
        double temperature = weatherData.getJSONObject("main").getDouble("temp");
        if (!isCelsius) {
            temperature = convertCelsiusToFahrenheit(temperature);
        }

        int humidity = main.getInt("humidity");
        JSONObject wind = weatherData.getJSONObject("wind");
        double windSpeed = wind.getDouble("speed");
        JSONArray weatherArray = weatherData.getJSONArray("weather");
        String iconCode = weatherArray.getJSONObject(0).getString("icon");
        long sunriseTime = weatherData.getJSONObject("sys").getLong("sunrise") * 1000;

        // Fetch timezone offset from the weather data
        long timezoneOffset = weatherData.getLong("timezone"); // Assuming it's in seconds

        // Calculate the local time of the city
        Instant now = Instant.now();
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds((int) timezoneOffset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, zoneOffset);

        // Format the current time
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, hh:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(zoneOffset)); // Set the timezone for the date format
        String formattedDate = dateFormat.format(Date.from(localDateTime.atZone(zoneOffset).toInstant()));

        // Format the sunrise time
        // Assuming sunriseTime is in seconds since epoch, convert to milliseconds
        Date sunriseDate = new Date(sunriseTime * 1000L);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
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
        cityLabel.setText(weatherData.getString("name"));
        dateLabel.setText(formattedDate);

        // Apply translations
        temperatureLabel.setText(String.format("%.1f%s", temperature, isCelsius ? "°C" : "°F"));
        sunriseLabel.setText(formattedSunrise);
        windLabel.setText(String.format("%.1f m/s", windSpeed));
        humidityLabel.setText(String.format("%d%%", humidity));

        // Show the details layout
        detailsLayout.setVisible(true);

        // Call the separate method to display temperature
        displayTemperature(weatherData);

        // Ensure that the date is formatted correctly every time
        formatDateLabel(getLocaleFromLanguage(language)); // Locale based on current language
    }

    /** Convert celcius to fahrenheit */
    private double convertCelsiusToFahrenheit(double temperatureCelsius) {
        return (temperatureCelsius * 9 / 5) + 32;
    }

    private void updateTemperatureUnit(String selectedUnit) {
        isCelsius = "°C".equals(selectedUnit);
        if (currentWeatherData != null) {
            displayTemperature(currentWeatherData); // New method to display only temperature
        }
    }

    private void displayTemperature(JSONObject weatherData) {
        double temperature = weatherData.getJSONObject("main").getDouble("temp");
        if (!isCelsius) {
            temperature = convertCelsiusToFahrenheit(temperature);
        }
        temperatureLabel.setText(String.format("%.1f%s", temperature, isCelsius ? "°C" : "°F"));
    }


    /** Translate language */
    private void updateLanguage(String selectedLanguage) {
        language = selectedLanguage.substring(0, 2).toLowerCase();
        fetchWeatherData(); // Fetch new data with the updated language
        try {
            updateLabelsForLanguage(language);
            updateDateFormatForLanguage(language); // New method call
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbwS9g6V1pz2uggBH3k1EbX2U8xBREIP9NB-LzUxRox2xl_J2inrebzyYF7tPiEHTQGU/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    /** Update labels for language */
    private void updateLabelsForLanguage(String langTo) throws IOException {
        String langFrom = "en"; // Assuming English is the source language

        // Translate labels
        sunriseText = translate(langFrom, langTo, "sunrise");
        windText = translate(langFrom, langTo, "wind");
        humidityText = translate(langFrom, langTo, "humidity");

        // Update label texts immediately
        ((Label) sunriseIconView.getParent().getChildrenUnmodifiable().get(1)).setText(sunriseText);
        ((Label) windIconView.getParent().getChildrenUnmodifiable().get(1)).setText(windText);
        ((Label) humidityIconView.getParent().getChildrenUnmodifiable().get(1)).setText(humidityText);

        // Update weather info if already displayed
        if (currentWeatherData != null) {
            displayWeatherInfo(currentWeatherData);
        }
    }

    /** Update date format for language */
    private void updateDateFormatForLanguage(String language) {
        Locale locale = getLocaleFromLanguage(language);
        // Assuming dateLabel is already initialized
        if (currentWeatherData != null) {
            formatDateLabel(locale); // New method to format the date label
        }
    }

    private Locale getLocaleFromLanguage(String language) {
        return switch (language) {
            case "ru" -> new Locale("ru", "RU");
            case "fr" -> Locale.FRENCH;
            case "ja" -> Locale.JAPANESE;
            case "ko" -> Locale.KOREAN;
            case "vi" -> new Locale("vi", "VN");
            default -> Locale.ENGLISH;
        };
    }

    private void formatDateLabel(Locale locale) {
        long timezoneOffset = currentWeatherData.getLong("timezone"); // Assuming it's in seconds
        Instant now = Instant.now();
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds((int) timezoneOffset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, zoneOffset);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, hh:mm a", locale);
        dateFormat.setTimeZone(TimeZone.getTimeZone(zoneOffset));
        String formattedDate = dateFormat.format(Date.from(localDateTime.atZone(zoneOffset).toInstant()));

        dateLabel.setText(formattedDate);
    }
}