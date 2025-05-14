package weather.model;

/**
 * [Weather 데이터 모델]
 * - 도시명, 기온, 날씨 설명을 저장
 */
public class Weather {

    private String cityName;
    private double temperature;
    private String description;

    public Weather(String cityName, double temperature, String description) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.description = description;
    }

    public String getCityName() {
        return cityName;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }
}