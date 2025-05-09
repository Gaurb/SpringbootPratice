// package com.example.CRUD.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpMethod;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;

// @Component
// public class WeatherService {
//     String API_KEY="92da73a31fa8a9fff98f46b2978000da";
//     String BASE_URL="https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

//     @Autowired
//     private RestTemplate restTemplate;

//     public String getWeatherData(String city){
//         String url = BASE_URL.replace("API_KEY", API_KEY).replace("CITY", city);
//         restTemplate.exchange(url, HttpMethod.GET,null, Weather.class)
//     }
// }
