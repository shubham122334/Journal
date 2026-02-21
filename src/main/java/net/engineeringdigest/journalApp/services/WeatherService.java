package net.engineeringdigest.journalApp.services;



import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.api.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private RedisService redisService;

    public WeatherResponse getApiResponse(String city) {
        WeatherResponse weatherResponse=redisService.get("weather_"+city,WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }else{

            String finalApi= Objects.requireNonNull(appCache.cache.get("weather_api").replace("<apiKey>", apiKey)).replace("<city>", city);
            ResponseEntity<WeatherResponse> response= restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);
            WeatherResponse body=response.getBody();
            if(body!=null){
                redisService.set("weather_"+city,body,300L);
            }
            return body;

        }
    }

}
