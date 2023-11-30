package com.example.springapi.controller;

import com.example.springapi.model.User;
import com.example.springapi.security.JwtUtil;
import com.example.springapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WeatherController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;
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
    public WeatherController(RestTemplate restTemplate,
                             UserService userService,
                             PasswordEncoder passwordEncoder,
                             AuthenticationManager authenticationManager,
                             JwtUtil jwtUtil
    )
    {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/auth/weather")
    public ResponseEntity<String> weather(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        System.out.println("params : "+params);
        String location = params.get("location")[0];
        String units = params.get("units") != null ? params.get("units")[0] : "metric";
        String lang = params.get("lang") != null ? params.get("lang")[0] : "en";

        String url = apiUrl + "?q=" + location + "&appid=" + apiKey + "&units=" + units + "&lang=" + lang;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response;
    }



    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // Retrieve the user by email from the database
        User user = userService.findByEmail(email);
        if(user == null) {
            // User with the email not found
            System.out.println("User with the email not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            // Authentication failed
            System.out.println("Authentication failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        // Authentication successful
        // You can generate a JWT token here and send it back to the client
        String token = jwtUtil.createToken(user);
        // Or you can set up Spring Security to handle authentication and authorization

        System.out.println("Authentication successful");
        // create a response object that has both the token and the user
         Map<String, Object> response = new HashMap<>();
         response.put("token", token);
         response.put("user", user.getEmail());
         return ResponseEntity.ok(response.toString());

    }

    @PostMapping("/auth/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {

        // Check if the email already exists in the database
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            // A user already exists with the email provided in the request
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Create a new user record
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

}
