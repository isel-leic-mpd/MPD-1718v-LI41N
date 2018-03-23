package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WorldWeatherOnlineWeatherCsvFileTest extends WeatherDataSourceTest {

    @Override
    protected WeatherDataSource getDataSource() {
        return new WorldWeatherOnlineCsvFileWeatherInfoDataSource("weatherInfo.csv");
    }
}
