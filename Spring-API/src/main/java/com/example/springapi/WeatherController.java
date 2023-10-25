package com.example.springapi;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
public class WeatherController {

    private final RestTemplate restTemplate;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    @Autowired
    public WeatherController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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

    @GetMapping("/hourly")
    public String hourly() {
        return "The weather is nice at 2PM!";
    }

}
