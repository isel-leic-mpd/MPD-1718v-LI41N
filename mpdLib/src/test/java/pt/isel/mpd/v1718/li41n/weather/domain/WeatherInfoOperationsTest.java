package pt.isel.mpd.v1718.li41n.weather.domain;

import org.junit.Test;
import pt.isel.mpd.v1718.li41n.weather.dataAccess.MemoryWeatherDataSource;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WeatherInfoOperationsTest {

    public static final int MIN_ALLOWED_TEMP = -40;
    public static final int MAX_ALLOWED_TEMPERATURE = 50;
    public static final String LOCATION = "Lisbon";

    @Test
    public void shouldGetDailyWeatherInfoBetweenTwoDates() {
        // Arrange
        WeatherInfoOperations wiOper = new WeatherInfoOperationsImpl(new MemoryWeatherDataSource());
        LocalDate start = null;
        LocalDate end = null;

        // Act
        Collection<DailyWeatherInfo> dwiColl = wiOper.getDailyWeatherInfoBetween(LOCATION, start, end);

        // Assert
        assertNotNull(dwiColl);
        assertTrue(dwiColl.size() > 0);
        assertDailyWeatherInfoCollectionContent(dwiColl);

    }

    private void assertDailyWeatherInfoCollectionContent(Collection<DailyWeatherInfo> dwiColl) {
        for (DailyWeatherInfo dailyWeatherInfo : dwiColl) {
            assertNotNull(dailyWeatherInfo);
            final int maxTemp = dailyWeatherInfo.getMaxTemp();
            final int minTemp = dailyWeatherInfo.getMinTemp();
            assertTrue(maxTemp >= minTemp);
            assertTrue(maxTemp > MIN_ALLOWED_TEMP && maxTemp < MAX_ALLOWED_TEMPERATURE);
            assertTrue(minTemp > MIN_ALLOWED_TEMP && minTemp < MAX_ALLOWED_TEMPERATURE);

        }
    }
}
