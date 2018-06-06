package http;

import football.FootballService;
import football.FootballWebApi;
import football.model.League;
import football.model.Standing;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;
import util.HttpRequest;

import java.text.MessageFormat;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static util.Logging.log;

public class FootballHttpServer {

    private static final int PORT = 3000;
    private static final FootballService footballService = new FootballService(new FootballWebApi(new HttpRequest()));
    private static final String LEAGUE_ID = "leagueId";
    private static final String TEAM_ID = "teamId";
    private static final String TEMPLATES_DIR = "templates";
    private static final HandlebarsTemplateEngine handlebarsTemplateEngine = HandlebarsTemplateEngine.create();

    public static void main(String[] args) {

        // Create the vertx library object entry point
        Vertx vertx = Vertx.vertx();

        // Create the HttpServer object
        final HttpServer httpServer = vertx.createHttpServer();

        // Create and configure the router
        Router router = Router.router(vertx);


        router.route().handler(FootballHttpServer::setContentType);
        router.route("/leaders").handler(FootballHttpServer::leagueLeaders);
        router.get("/leagues").handler(FootballHttpServer::leaguesGet);
        router.post("/leagues").handler(FootballHttpServer::leaguesPost);
        router.get(route("/leagues/:{0}", LEAGUE_ID)).handler(FootballHttpServer::leagueDetails);

        httpServer.requestHandler(router::accept);

        httpServer.listen(PORT);
        log("Listening on port {0}", PORT);

        System.out.println("Bye");
    }

    private static String route(String str, Object ...args) {
        return MessageFormat.format(str, args);
    }


    private static void setContentType(RoutingContext routingContext) {
        log("setContentType");
        routingContext.response().headers().add("Content-Type", "Content-Type: text/html; charset=utf-8");
        routingContext.next();
    }

    private static void leagueDetails(RoutingContext ctx) {
        final HttpServerResponse response = ctx.response();
        log("leagueDetails");
        ctx.put("title", "League Details");

        final League l = new League(1, "Liga Portuguesa");
        ctx.put("league", l);

        // VERY IMPORTANT: the template name in the next call (/details) must have the leading '/', otherwise it wont work
        renderTemplate(ctx, "/leagueDetails");
    }

    private static void leaguesGet(RoutingContext routingContext) {
        log("leaguesGet");
        final HttpServerResponse response = routingContext.response();
        routingContext.response().setChunked(true);
        response.write("Get - All leagues should appear here... Soon...");

        routingContext.next();

    }

    private static void leaguesPost(RoutingContext routingContext) {
        final HttpServerResponse response = routingContext.response();
        response.end("Post - All leagues should appear here... Soon...");

    }

    public static void leagueLeaders(RoutingContext routingContext) {

        log("request received");
        footballService.getFirstPlaceOnALlLeagues()
                .thenAccept(standingsStream -> FootballHttpServer.generateStandingsPage(routingContext, standingsStream));
    }

    private static void generateStandingsPage(RoutingContext ctx, Stream<Standing> standingStream) {
        ctx.put("title", "Leagues Leaders");
        ctx.put("leaders", standingStream.collect(toList()));
        renderTemplate(ctx, "/leagueLeaders");

    }

    private static void renderTemplate(RoutingContext ctx, String templateName) {
        handlebarsTemplateEngine.render(ctx, TEMPLATES_DIR, templateName, res -> sendTemplateResult(ctx, res));
        log("Requested template {0} to render.", templateName);
    }


    private static void sendTemplateResult(RoutingContext ctx, AsyncResult<Buffer> res) {
        if (res.succeeded()) {
            ctx.response().end(res.result());
        } else {
            log("Error executing template: ", res.cause());
            ctx.fail(res.cause());
        }

    }

}
