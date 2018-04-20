package pt.isel.mpd.v1718.li41n.weather.dataAccess;

import pt.isel.mpd.v1718.li41n.queries.Queries;
import pt.isel.mpd.v1718.li41n.utils.StreamProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

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
    final protected Collection<DailyWeatherInfoDto> process(InputStream inputStream) throws IOException {
        Stream<String> stringStream = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(Queries.iterator(inputStream), Spliterator.ORDERED), false);

        return stringStream                // Stream<String>
                .skip(8)            // Stream<String>
                .map(this::split)   // Stream<String[]>
                .filter(this::isLineValid)  // Stream<String[]>
                .map(this::createDailyWeatherInfoDto) // Stream<DailyWeatherInfoDto>
                .collect(toList());

    }

    private String[] split(String line) {
        return line.split(",");
    }

    private  DailyWeatherInfoDto createDailyWeatherInfoDto(String[] split) {
        return new DailyWeatherInfoDto(Integer.parseInt(split[1]), Integer.parseInt(split[3]), LocalDate.parse(split[0]));
    }

    private boolean isLineValid(String[] split) {
        if (split[0].startsWith("#"))
            return false;

        if (split.length != 9)
            return false;

        LocalDate date = LocalDate.parse(split[0]);
        return date.isAfter(start) && date.isBefore(end);
    }

}


