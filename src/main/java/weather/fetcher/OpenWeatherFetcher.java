package weather.fetcher;

import weather.model.Weather;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * [OpenWeatherFetcher]
 * - OpenWeatherMap API를 호출해 오늘 날씨를 가져오는 구현체
 */
public class OpenWeatherFetcher implements WeatherFetcher {

    private static final String API_KEY = "8ee01c131076cf0b6d2f00fdd1ff9836"; // TODO: 본인 OpenWeatherMap API 키 입력
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    public Weather fetchTodayWeather(String cityName) {
        try {
            String urlStr = API_URL + "?q=" + cityName + "&appid=" + API_KEY + "&units=metric&lang=kr";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("OpenWeather API 호출 실패: 응답코드 " + responseCode);
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());

            String city = json.getString("name");
            double temp = json.getJSONObject("main").getDouble("temp");
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");

            return new Weather(city, temp, description);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
