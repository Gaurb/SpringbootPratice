package com.example.CRUD.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherResponse {

    private Current current;

    public class AirQuality{
        private String co;
        private String no2;
        private String o3;
        private String so2;
        private String pm2_5;
        private String pm10;
        @JsonProperty("us-epa-index")
        private String usEpaIndex;
        @JsonProperty("gb-defra-index")
        private String gbDefraIndex;
    }

    public class Astro{
        private String sunrise;
        private String sunset;
        private String moonrise;
        private String moonset;
        private String moon_phase;
        private int moon_illumination;
    }

    public class Current{
        private String observation_time;
        private int temperature;
        private int weather_code;
        private List<String> weather_icons;
        private List<String> weather_descriptions;
        private Astro astro;
        private AirQuality air_quality;
        private int wind_speed;
        private int wind_degree;
        private String wind_dir;
        private int pressure;
        private int precip;
        private int humidity;
        private int cloudcover;
        private int feelslike;
        private int uv_index;
        private int visibility;
        private String is_day;
    }




}
