package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public abstract class WeatherDataSourceTest {
    public static final String LOCATION = "Lisboa";
    public static final LocalDate START = LocalDate.of(2018, 3, 1);
    public static final LocalDate END = LocalDate.of(2018, 3, 10);
    private WeatherDataSource dataSource;

    @Before
    public void setUp() throws Exception {
        dataSource = getDataSource();
    }

    @Test
    public void shouldGetDailyWeatherInfoDtosForALocationBetweenTwoDates() {
        // Arrange

        // Act
        final Collection<DailyWeatherInfoDto> dwiDtos = dataSource.getDailyWeatherInfoBetween(LOCATION, START, END);

        // Assert
        assertNotNull(dwiDtos);
        assertTrue(dwiDtos.size()>0);
    }

    protected abstract WeatherDataSource getDataSource();
}
