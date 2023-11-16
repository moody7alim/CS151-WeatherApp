package com.example.springapi.controller;

import com.example.springapi.model.User;
import com.example.springapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Map;

@RestController
public class WeatherController {

    private final RestTemplate restTemplate;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WeatherController(RestTemplate restTemplate, UserService userService, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/weather")
    public ResponseEntity<String> weather(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        String location = params.get("location")[0];
        String units = params.get("units") != null ? params.get("units")[0] : "metric";
        String lang = params.get("lang") != null ? params.get("lang")[0] : "en";

        String url = apiUrl + "?q=" + location + "&appid=" + apiKey + "&units=" + units + "&lang=" + lang;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response;
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // Retrieve the user by email from the database
        User user = userService.findByUsername(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        // Authentication successful
        // You can generate a JWT token here and send it back to the client
        // Or you can set up Spring Security to handle authentication and authorization

        return ResponseEntity.ok("Authentication successful");
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {

        // Check if the email already exists in the database
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null) {
            // A user already exists with the email provided in the request
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Create a new user record
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

}
