package weather.cache;

import weather.model.Weather;

/**
 * [WeatherCacheManager 인터페이스]
 * - 오늘 날짜 기준으로 도시별 날씨 캐시를 관리
 */
public interface WeatherCacheManager {
    /**
     * 오늘 날짜 기준으로 해당 도시의 캐시를 가져온다.
     *
     * @param cityName 도시명
     * @return Weather 객체 (없으면 null)
     */
    Weather getTodayWeather(String cityName);

    /**
     * 오늘 날짜 기준으로 해당 도시의 날씨를 저장한다.
     *
     * @param cityName 도시명
     * @param weather 저장할 날씨 객체
     */
    void saveTodayWeather(String cityName, Weather weather);
}