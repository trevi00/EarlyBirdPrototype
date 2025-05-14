package weather.fetcher;

import weather.model.Weather;

/**
 * [WeatherFetcher 인터페이스]
 * - 외부(OpenWeatherMap 등)로부터 오늘 날씨를 가져오는 역할
 */
public interface WeatherFetcher {
    /**
     * 오늘 날짜 기준으로 주어진 도시의 날씨를 가져온다.
     *
     * @param cityName 도시명
     * @return Weather 객체
     */
    Weather fetchTodayWeather(String cityName);
}