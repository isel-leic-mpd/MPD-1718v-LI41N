package football;

import football.dto.LeagueDto;
import football.dto.LeagueTableDto;
import football.model.Standing;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class FootballService {
    private FootballWebApi api;

    public FootballService(FootballWebApi api) {
        this.api = api;
    }

    public CompletableFuture<Stream<Standing>> getFirstPlaceOnALlLeagues() {
        return api.getLeagues().thenCompose(this::processLeagues);

    }

    private CompletableFuture<Stream<Standing>> processLeagues(Stream<LeagueDto> leagueDtoStream) {

        final CompletableFuture<LeagueTableDto>[] leagueTableFutures =
                leagueDtoStream.map(l -> api.getStandings(l.id))
                .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(leagueTableFutures).thenApply(v -> convertLeagueTablesToStandings(leagueTableFutures));
    }

    private Stream<Standing>  convertLeagueTablesToStandings(CompletableFuture<LeagueTableDto>[] leagueTableFutures) {
        return Arrays.stream(leagueTableFutures)       // Stream<CompletableFuture<LeagueTableDto>>
                .map(CompletableFuture::join)   // Stream<LeagueTableDto>
                .map(this::convertLeagueTableToStanding);    // Stream<Standing>
    }

    private Standing convertLeagueTableToStanding(LeagueTableDto leagueTableDto) {
        return null;
    }
}
