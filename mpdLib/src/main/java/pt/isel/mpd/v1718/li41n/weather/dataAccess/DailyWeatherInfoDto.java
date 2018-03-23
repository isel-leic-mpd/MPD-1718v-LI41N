package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import java.time.LocalDate;

public class DailyWeatherInfoDto {
    private int maxTemp;
    private int minTemp;
    private LocalDate date;

    public DailyWeatherInfoDto(int maxTemp, int minTemp, LocalDate date) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }
}
