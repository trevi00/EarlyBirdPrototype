package weather.cache;

import weather.model.Weather;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * [InMemoryWeatherCacheManager]
 * - 메모리(HashMap) 기반 캐시 저장소
 * - 매일 새벽 캐시를 초기화할 수 있도록 설계
 */
public class InMemoryWeatherCacheManager implements WeatherCacheManager {

    private Map<String, Weather> cache = new HashMap<>();
    private LocalDate cachedDate = LocalDate.now(); // 마지막으로 저장한 날짜

    @Override
    public Weather getTodayWeather(String cityName) {
        if (!isToday()) {
            clearCache();
        }
        return cache.get(cityName);
    }

    @Override
    public void saveTodayWeather(String cityName, Weather weather) {
        if (!isToday()) {
            clearCache();
        }
        cache.put(cityName, weather);
    }

    /**
     * 오늘 날짜인지 확인
     */
    private boolean isToday() {
        return LocalDate.now().equals(cachedDate);
    }

    /**
     * 캐시를 초기화하고 날짜를 갱신
     */
    private void clearCache() {
        cache.clear();
        cachedDate = LocalDate.now();
    }
}