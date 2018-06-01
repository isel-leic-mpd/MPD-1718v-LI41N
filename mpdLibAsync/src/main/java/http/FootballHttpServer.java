package http;

import football.FootballService;
import football.FootballWebApi;
import football.model.Standing;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import util.HttpRequest;
import util.IRequest;
import util.Logging;

import static java.util.stream.Collectors.joining;
import static util.Logging.log;

public class FootballHttpServer {

    private static final int PORT = 3000;

    public static void main(String[] args) {
        final FootballService footballService = new FootballService(new FootballWebApi(new HttpRequest()));



        Vertx vertx = Vertx.vertx();

        final HttpServer httpServer = vertx.createHttpServer();

        httpServer.requestHandler(req -> {
            log("request received");
            footballService.getFirstPlaceOnALlLeagues()
                    .thenAcceptAsync(standingStream -> {
                        log("thanAccept called");
                        final HttpServerResponse response = req.response();
                        response.headers().add("Content-Type", "text/plain");
                                final String standingsStr = standingStream
                                        .peek(s -> log(s.toString()))
                                        .map(Standing::toString).collect(joining("\r\n"));
                                response.end(standingsStr);
                    }
                    );
        } );

        httpServer.listen(PORT);
        log("Listening on port {0}", PORT);

        System.out.println("Bye");
    }
}
