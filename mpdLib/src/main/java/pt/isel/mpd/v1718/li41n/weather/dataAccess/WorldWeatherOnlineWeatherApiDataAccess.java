package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import java.time.LocalDate;
import java.util.Collection;

public class WorldWeatherOnlineWeatherApiDataAccess implements WeatherDataSource {
    @Override
    public Collection<DailyWeatherInfoDto> getDailyWeatherInfoBetween(String location, LocalDate start, LocalDate end) {
        return null;
    }
}
