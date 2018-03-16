package pt.isel.mpd.v1718.li41n.weather.domain;

import java.time.LocalDate;
import java.util.Collection;


/**
 * Interface that contains domain specific operations for weather information.
 */
public interface WeatherInfoOperations {
    Collection<DailyWeatherInfo> getDailyWeatherInfoBetween(String location, LocalDate start, LocalDate end);
}
