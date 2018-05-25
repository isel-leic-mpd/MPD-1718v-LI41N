package football;

import com.google.gson.Gson;
import football.dto.LeagueTableDto;
import football.dto.LeagueDto;
import util.IRequest;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static util.Logging.log;


/**
 * Instances of this class are responsible to make requests to  www.football-data.org API and transforms
 * its responses in DTOs, defined in {@link football.dto} package.
 */
public class FootballWebApi {
    private final static String BASE_URL = "https://www.football-data.org/v1/";
    private final static String COMPETITIONS_URL = BASE_URL + "competitions";

    private static final String LEAGUE_TABLE_URL = BASE_URL + "competitions/{0}/leagueTable";
    private final static Gson gson = new Gson();
    private IRequest req;


    public FootballWebApi(IRequest req) {
        this.req = req;
    }

    public CompletableFuture<Stream<LeagueDto>> getLeagues() {
        log("getLeagues");
        // Get the url response String
        return req.getBody(COMPETITIONS_URL)
                .thenApply(this::getDtoStream);
    }

    public CompletableFuture<LeagueTableDto> getStandings(int leagueId) {
//        req.getBody(MessageFormat.format(LEAGUE_TABLE_URL, leagueId))
//                .thenApply();

        return null;

    }

    private Stream<LeagueDto> getDtoStream(String resp) {
        log("getDtoStream");
        return Arrays.stream(gson.fromJson(resp, LeagueDto[].class));
    }
}
