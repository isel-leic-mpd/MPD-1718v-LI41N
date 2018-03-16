package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WorldWeatherOnlineWeatherApiDataAccessTest {

    public static final String LOCATION = "Lisboa";
    public static final LocalDate START = LocalDate.of(2018, 1, 1);
    public static final LocalDate END = LocalDate.of(2018, 2, 28);

    @Test
    public void shouldGetDailyWeatherInfoDtosForALocationBetweenTwoDates() {
        // Arrange
        WeatherDataSource dataSource = new WorldWeatherOnlineWeatherApiDataAccess();

        // Act
        final Collection<DailyWeatherInfoDto> dwiDtos = dataSource.getDailyWeatherInfoBetween(LOCATION, START, END);

        // Assert
        assertNotNull(dwiDtos);
        assertTrue(dwiDtos.size()>0);
    }
}
