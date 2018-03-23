package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;

public class MemoryWeatherDataSource implements WeatherDataSource {


    private Collection<DailyWeatherInfoDto> data;

    public MemoryWeatherDataSource() {
        data = new LinkedList<>();
        data.add(new DailyWeatherInfoDto(15, 2, LocalDate.of(2016, 03, 12)));
        data.add(new DailyWeatherInfoDto(13, 10, LocalDate.of(2016, 03, 12)));
    }

    @Override
    public Collection<DailyWeatherInfoDto> getDailyWeatherInfoBetween(String location, LocalDate start, LocalDate end) {
        return data;
    }
}
