package weather.fetcher;

import org.junit.jupiter.api.Test;
import weather.model.Weather;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherFetcherTest {

    @Test
    void fetchTodayWeather_returnsValidData() {
        OpenWeatherFetcher fetcher = new OpenWeatherFetcher();
        String city = "Seoul";

        Weather weather = fetcher.fetchTodayWeather(city);

        assertNotNull(weather, "날씨 정보는 null이 아니어야 함");
        assertEquals("Seoul", weather.getCityName(), "도시명이 일치해야 함");
        System.out.println("🌤 " + weather.getTemperature() + "도, " + weather.getDescription());
    }
}
