package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WorldWeatherOnlineWeatherApiTest extends WeatherDataSourceTest  {

    @Override
    protected WeatherDataSource getDataSource() {
        return new WorldWeatherOnlineWeatherInfoApi();
    }
}
