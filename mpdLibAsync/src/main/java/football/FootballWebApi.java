package football;

import com.google.gson.Gson;
import football.dto.LeagueTableDto;
import football.dto.LeagueDto;
import util.IRequest;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

    private static Map<String,String> headarsMap = new HashMap<>();

    private final static Gson gson = new Gson();
    private IRequest req;


    private static final String API_KEY = "f8bc89b8024f459e8a2855df9b934469";

    static {
        headarsMap.put("X-Auth-Token", API_KEY);
    }

    public FootballWebApi(IRequest req) {
        this.req = req;
    }

    public CompletableFuture<Stream<LeagueDto>> getLeagues() {
        log("getLeagues");
        // Get the url response String
        return req.getBody(COMPETITIONS_URL, headarsMap)
                .thenApply(this::toLeagueDtoStream);
    }

    public CompletableFuture<LeagueTableDto> getStandings(int leagueId) {
        return req.getBody(MessageFormat.format(LEAGUE_TABLE_URL, leagueId), headarsMap)
                .thenApply(this::toLeagueTableDto);

    }

    private LeagueTableDto toLeagueTableDto(String jsonStr) {
        log("toLeagueDtoStream");
        final LeagueTableDto leagueTableDto = gson.fromJson(jsonStr, LeagueTableDto.class);
        return leagueTableDto;
    }

    private Stream<LeagueDto> toLeagueDtoStream(String jsonStr) {
        log("toLeagueDtoStream");
        final LeagueDto[] leagueDtos = gson.fromJson(jsonStr, LeagueDto[].class);
        return Arrays.stream(leagueDtos);
    }
}
