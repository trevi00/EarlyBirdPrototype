package weather.service;

import weather.model.Weather;

/**
 * [WeatherService 인터페이스]
 * - 오늘 날짜 기준으로 날씨를 가져오는 비즈니스 로직
 */
public interface WeatherService {
    /**
     * 주어진 도시의 오늘 날씨를 가져온다.
     *
     * @param cityName 도시명
     * @return Weather 객체
     */
    Weather getTodayWeather(String cityName);
}