package weather.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import weather.cache.WeatherCacheManager;
import weather.fetcher.WeatherFetcher;
import weather.model.Weather;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    private WeatherCacheManager cacheManager;
    private WeatherFetcher fetcher;
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        cacheManager = mock(WeatherCacheManager.class);
        fetcher = mock(WeatherFetcher.class);
        weatherService = new DefaultWeatherService(cacheManager, fetcher);
    }

    @Test
    void testReturnsCachedWeatherIfExists() {
        Weather cached = new Weather("Seoul", 20.0, "맑음");
        when(cacheManager.getTodayWeather("Seoul")).thenReturn(cached);

        Weather result = weatherService.getTodayWeather("Seoul");

        assertEquals(cached, result);
        verify(fetcher, never()).fetchTodayWeather(anyString());
    }

    @Test
    void testFetchesAndCachesIfNotInCache() {
        when(cacheManager.getTodayWeather("Seoul")).thenReturn(null);
        Weather fetched = new Weather("Seoul", 22.5, "흐림");
        when(fetcher.fetchTodayWeather("Seoul")).thenReturn(fetched);

        Weather result = weatherService.getTodayWeather("Seoul");

        assertEquals(fetched, result);
        verify(fetcher).fetchTodayWeather("Seoul");
        verify(cacheManager).saveTodayWeather("Seoul", fetched);
    }
}
