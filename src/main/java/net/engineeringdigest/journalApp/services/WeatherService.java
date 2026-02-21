package net.engineeringdigest.journalApp.services;



import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.api.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;


    private final AppCache appCache;
    //private static final String API="http://api.weatherstack.com/current?access_key=<apiKey>&query=<city>";

    @Value("${app.weather.api.key}")
    private String apiKey;


    public WeatherResponse getApiResponse(String city) {
        String finalApi= Objects.requireNonNull(appCache.cache.get("weather_api").replace("<apiKey>", apiKey)).replace("<city>", city);
        return restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class).getBody();
    }

}
