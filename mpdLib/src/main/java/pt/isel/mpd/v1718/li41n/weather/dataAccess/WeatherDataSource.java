package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import pt.isel.mpd.v1718.li41n.weather.domain.DailyWeatherInfo;

import java.time.LocalDate;
import java.util.Collection;

public interface WeatherDataSource {
        Collection<DailyWeatherInfoDto> getDailyWeatherInfoBetween(String location, LocalDate start, LocalDate end);
}
