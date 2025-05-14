package weather.service;

import weather.cache.WeatherCacheManager;
import weather.fetcher.WeatherFetcher;
import weather.model.Weather;

/**
 * [DefaultWeatherService]
 * - WeatherService 기본 구현체
 * - 캐시 조회 → 없으면 외부 API 호출 → 캐시에 저장
 */
public class DefaultWeatherService implements WeatherService {

    private WeatherCacheManager cacheManager;
    private WeatherFetcher fetcher;

    public DefaultWeatherService(WeatherCacheManager cacheManager, WeatherFetcher fetcher) {
        this.cacheManager = cacheManager;
        this.fetcher = fetcher;
    }

    @Override
    public Weather getTodayWeather(String cityName) {
        Weather weather = cacheManager.getTodayWeather(cityName);
        if (weather != null) {
            return weather;
        }

        weather = fetcher.fetchTodayWeather(cityName);
        if (weather != null) {
            cacheManager.saveTodayWeather(cityName, weather);
        }

        return weather;
    }
}