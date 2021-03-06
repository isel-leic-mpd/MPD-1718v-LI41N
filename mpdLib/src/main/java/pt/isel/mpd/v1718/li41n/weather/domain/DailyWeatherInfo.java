package pt.isel.mpd.v1718.li41n.weather.domain;

import pt.isel.mpd.v1718.li41n.weather.dataAccess.DailyWeatherInfoDto;

import java.util.Collection;

public class DailyWeatherInfo {
    private int maxTemp;
    private int minTemp;

    public DailyWeatherInfo(int maxTemp, int minTemp) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }
}
