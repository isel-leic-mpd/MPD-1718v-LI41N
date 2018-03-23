package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import com.sun.javafx.iio.ios.IosDescriptor;
import pt.isel.mpd.v1718.li41n.utils.StreamProcessor;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public abstract class WeatherInfoDataSourceBase extends StreamProcessor<Collection<DailyWeatherInfoDto>> implements WeatherDataSource {

    private InputStream inputStream;
    private String location;
    private LocalDate start;
    private LocalDate end;

    @Override
    public final Collection<DailyWeatherInfoDto> getDailyWeatherInfoBetween(String location, LocalDate start, LocalDate end) {
        this.location = location;
        this.start = start;
        this.end = end;

        return get();
    }

    protected abstract InputStream getStream(String location, LocalDate start, LocalDate end) throws IOException;

    @Override
    final protected InputStream getStream() throws IOException {
        return getStream(location,start, end);
    }

    @Override
    final protected Collection<DailyWeatherInfoDto> process(InputStream stream) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            Collection<DailyWeatherInfoDto> coll = new ArrayList<>();
            String line;

            for (int i = 0; i < 8; i++) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    final String[] split = line.split(",");
                    if (split.length == 9) {
                        LocalDate date = LocalDate.parse(split[0]);
                        if (date.isAfter(start) && date.isBefore(end)) {
                            coll.add(new DailyWeatherInfoDto(Integer.parseInt(split[1]), Integer.parseInt(split[3]), date));
                        }
                    }
                }
            }
            return coll;
        }
    }
}
