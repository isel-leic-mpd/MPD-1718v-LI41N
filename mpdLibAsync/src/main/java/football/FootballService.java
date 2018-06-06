package football;

import football.dto.LeagueDto;
import football.dto.LeagueTableDto;
import football.dto.StandingDto;
import football.model.Standing;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static util.Logging.log;

public class FootballService {
    private FootballWebApi api;

    public FootballService(FootballWebApi api) {
        this.api = api;
    }

    public CompletableFuture<Stream<Standing>> getFirstPlaceOnALlLeagues() {
        return api.getLeagues()
                .thenCompose(this::processLeagues);
    }

    public CompletableFuture<Stream<Standing>> getFirstPlaceOnALlLeagues1() {
        final CompletableFuture<Stream<LeagueDto>> leagues = api.getLeagues();
        return leagues     // CompletableFuture<Stream<LeagueDto>>
                .thenApply(leagueDtoStream ->
                            leagueDtoStream                             // Stream<LeagueDto>
                                    .map(l -> api.getLeagueTable(l.id))   // Stream<CompletableFuture<LeagueTableDto>>
                                    .collect(toList()).stream()
                                    .peek(__ -> log("before join"))
                                    .map(CompletableFuture::join)       // Stream<LeagueTableDto>
                                    .peek(__ -> log("after join"))
                                    .map(this::convertLeagueTableDtoToStanding) // // Stream<Standing>
                );
    }

    public CompletableFuture<Stream<Standing>> getFirstPlaceOnALlLeagues2() {
        final CompletableFuture<Stream<LeagueDto>> leagues = api.getLeagues();
        return leagues     // CompletableFuture<Stream<LeagueDto>>
                // CompletableFuture<Stream<CompletableFuture<LeagueTableDto>>>
            .thenApply(leagueDtoStream -> leagueDtoStream.map(l -> api.getLeagueTable(l.id))
                    .collect(toList()).stream())
            // CompletableFuture<Stream<LeagueTableDto>>
            .thenApply(completableFutureStream -> completableFutureStream.map(CompletableFuture::join))
             // CompletableFuture<Stream<Standing>>
             .thenApply(leagueTableDtoStream -> leagueTableDtoStream.map(this::convertLeagueTableDtoToStanding))

        ;


//        // Stream<LeagueDto>
//        // Stream<CompletableFuture<LeagueTableDto>>
//                                .collect(toList()).stream()
//                .map(CompletableFuture::join)       // Stream<LeagueTableDto>
//                .map(this::convertLeagueTableDtoToStanding) // // Stream<Standing>
    }

    private CompletableFuture<Stream<Standing>> processLeagues(Stream<LeagueDto> leagueDtoStream) {

        final Stream<CompletableFuture<LeagueTableDto>> completableFutureStream =
                leagueDtoStream.map(l -> api.getLeagueTable(l.id));

        final CompletableFuture<LeagueTableDto>[] leagueTableFutures =
                completableFutureStream
                    .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(leagueTableFutures)
                .thenApply(v -> convertLeagueTablesToStandings(leagueTableFutures));
    }

    private Stream<Standing>  convertLeagueTablesToStandings(CompletableFuture<LeagueTableDto>[] leagueTableFutures) {
        return Arrays.stream(leagueTableFutures)       // Stream<CompletableFuture<LeagueTableDto>>
                .map(CompletableFuture::join)   // Stream<LeagueTableDto>
                .map(this::convertLeagueTableDtoToStanding);    // Stream<Standing>
    }

    private Standing convertLeagueTableDtoToStanding(LeagueTableDto leagueTableDto) {
        String teamName = "Dummy";
        int points = 99;
        String leagueName = leagueTableDto.leagueCaption;

        if(leagueTableDto.standing != null) {
            StandingDto standingDto = leagueTableDto.standing[0];
            teamName = standingDto.teamName;
            points = standingDto.points;
        }
        return new Standing(teamName, points, leagueName);
    }
}
