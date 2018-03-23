package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import java.io.InputStream;
import java.time.LocalDate;

public class WorldWeatherOnlineCsvFileWeatherInfoDataSource extends WeatherInfoDataSourceBase {
    private String path;


    public WorldWeatherOnlineCsvFileWeatherInfoDataSource(String path) {
        this.path = path;
    }


    @Override
    protected InputStream getStream(String location, LocalDate start, LocalDate end) {
        return ClassLoader.getSystemResourceAsStream(path);
    }
}
