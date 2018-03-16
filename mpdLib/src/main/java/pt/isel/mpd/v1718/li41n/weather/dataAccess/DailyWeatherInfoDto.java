package pt.isel.mpd.v1718.li41n.weather.dataAccess;

public class DailyWeatherInfoDto {
    private int maxTemp;
    private int minTemp;

    public DailyWeatherInfoDto(int maxTemp, int minTemp) {
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