package football;

import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import football.dto.LeagueDto;
import football.exceptions.FootballWebApiException;
import util.IRequest;
import util.MyImmediateFuture;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static util.Logging.log;


/**
 * Instances of this class are responsible to make requests to  www.football-data.org API and transforms
 * its responses in DTOs, defined in {@link football.dto} package.
 */
public class FootballWebApi {
    private final static String BASE_URL = "https://www.football-data.org/v1/";
    private final static String COMPETITIONS_URL = BASE_URL + "competitions";
    private final static Gson gson = new Gson();
    private IRequest req;


    public FootballWebApi(IRequest req) {
        this.req = req;
    }

    public CompletableFuture<Stream<LeagueDto>> getLeagues() throws FootballWebApiException {
        log("getLeagues");
        // Get the url response String
        return req.getBody(COMPETITIONS_URL)
                .thenApply(this::getLeagueDtos);
    }

    private Stream<LeagueDto> getLeagueDtos(String resp) {
        log("getLeagueDtos");
        return Arrays.stream(gson.fromJson(resp, LeagueDto[].class));
    }
}
