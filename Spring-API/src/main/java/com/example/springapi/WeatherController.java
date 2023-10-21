package com.example.springapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    @GetMapping("/weather")
    public String weather() {
        return "The weather is nice today!";
    }

}
