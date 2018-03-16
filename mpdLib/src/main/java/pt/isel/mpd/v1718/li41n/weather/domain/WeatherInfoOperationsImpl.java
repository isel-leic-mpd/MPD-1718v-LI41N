package pt.isel.mpd.v1718.li41n.weather.domain;

import pt.isel.mpd.v1718.li41n.weather.dataAccess.DailyWeatherInfoDto;
import pt.isel.mpd.v1718.li41n.weather.dataAccess.WeatherDataSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class WeatherInfoOperationsImpl implements WeatherInfoOperations {

    private WeatherDataSource data;

    public WeatherInfoOperationsImpl(WeatherDataSource data) {
        this.data = data;
    }

    @Override
    public Collection<DailyWeatherInfo> getDailyWeatherInfoBetween(String location, LocalDate start, LocalDate end) {
        final Collection<DailyWeatherInfoDto> dtosColl = data.getDailyWeatherInfoBetween(location, start, end);
//        return convertDtoCollectionToDomainCollection(dtosColl, new Converter<DailyWeatherInfoDto, DailyWeatherInfo>() {
//            @Override
//            public DailyWeatherInfo convert(DailyWeatherInfoDto dto) {
//                return new DailyWeatherInfo(dto.getMaxTemp(), dto.getMinTemp());
//            }
//        });

        return convertDtoCollectionToDomainCollection(dtosColl,
            dto -> new DailyWeatherInfo(dto.getMaxTemp(), dto.getMinTemp()));

    }

    private Collection<DailyWeatherInfo> convertDailyWeatherInfoDtoCollectionToDailyWeatherInfoCollection(Collection<DailyWeatherInfoDto> dtos) {
        Collection<DailyWeatherInfo> coll = new ArrayList<>();
        for (DailyWeatherInfoDto dto : dtos) {
            coll.add(new DailyWeatherInfo(dto.getMaxTemp(), dto.getMinTemp()));
        }
        return coll;
    }

    private <DTO, DOMAIN> Collection<DOMAIN> convertDtoCollectionToDomainCollection1(Collection<DTO> dtos, Converter<DTO, DOMAIN> converter  ) {
        Collection<DOMAIN> coll = new ArrayList<>();
        for (DTO dto : dtos) {
            coll.add(converter.convert(dto));
        }
        return coll;
    }
    private <DTO, DOMAIN> Collection<DOMAIN> convertDtoCollectionToDomainCollection(Collection<DTO> dtos, Function<DTO, DOMAIN> converter  ) {
        Collection<DOMAIN> coll = new ArrayList<>();
        for (DTO dto : dtos) {
            coll.add(converter.apply(dto));
        }
        return coll;
    }

    @FunctionalInterface
    private interface Converter<T, U> {
        U convert(T t);
    }
}
