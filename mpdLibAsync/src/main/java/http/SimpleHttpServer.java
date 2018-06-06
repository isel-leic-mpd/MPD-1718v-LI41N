package http;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import util.Logging;



public class SimpleHttpServer {

    private static final int PORT = 3000;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        final HttpServer httpServer = vertx.createHttpServer();

        httpServer.requestHandler(req -> {
            Logging.log("Request received for url " + req.uri());

            final HttpServerResponse response = req.response();
            response.setStatusCode(201);
            response.headers().add("Content-Type", "text/html");
            response.end("SLB!");
        } );

        httpServer.listen(PORT);
        Logging.log("Listening on port {0}", PORT);

        System.out.println("Bye");
    }
}
