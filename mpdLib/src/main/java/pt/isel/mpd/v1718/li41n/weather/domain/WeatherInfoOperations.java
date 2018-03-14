package pt.isel.mpd.v1718.li41n.weather.domain;

import java.time.LocalDate;
import java.util.Collection;

public interface WeatherInfoOperations {
    Collection<DailyWeatherInfo> getDailyWeatherInfoBetween(LocalDate start, LocalDate end);
}
