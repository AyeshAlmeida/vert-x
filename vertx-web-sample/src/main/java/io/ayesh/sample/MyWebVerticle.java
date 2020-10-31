package io.ayesh.sample;

import io.ayesh.sample.domain.Whisky;
import io.ayesh.sample.service.WhiskeyService;
import io.ayesh.sample.service.impl.WhiskeyServiceImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class MyWebVerticle extends AbstractVerticle {
    private final WhiskeyService service = new WhiskeyServiceImpl();

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        // create router object
        Router router = Router.router(vertx);

        // Bind "/" to our hello message - so we are still compatible.
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>Hello from my first Vert.x 3 application</h1>");
        });

        // Serve static resources from the /assets directory
        router.route("/assets/*").handler(StaticHandler.create("assets"));

        router.get("/api/whiskies").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(service.getAll()));
        });

        // enabling request body read
        router.route("/api/whiskies*").handler(BodyHandler.create());
        // maps HTTP POST to create new whiskey
        router.post("/api/whiskies").handler(routingContext -> {
            final Whisky whisky = Json.decodeValue(routingContext.getBodyAsString(), Whisky.class);
            Whisky responseBody = service.addOne(whisky);
            routingContext.response()
                    .setStatusCode(201)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(responseBody));
        });

        // retrieve one whisky based on ID - (:id) is used as a path variable
        router.get("/api/whiskies/:id").handler(routingContext -> {
            String id = routingContext.request().getParam("id");
            if (id == null) {
                routingContext.response().setStatusCode(400).end();
            } else {
                int idAsInteger = Integer.parseInt(id);
                Whisky whisky = service.findOne(idAsInteger);
                if (whisky == null) {
                    routingContext.response().setStatusCode(404).end();
                } else {
                    routingContext.response()
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(Json.encodePrettily(whisky));
                }
            }
        });

        // update one whisky based on ID - (:id) is used as a path variable
        router.put("/api/whiskies/:id").handler(routingContext -> {
            final String id = routingContext.request().getParam("id");
            JsonObject json = routingContext.getBodyAsJson();
            if (id == null || json == null) {
                routingContext.response().setStatusCode(400).end();
            } else {
                final Integer idAsInteger = Integer.valueOf(id);
                Whisky whisky = service.findOne(idAsInteger);
                if (whisky == null) {
                    routingContext.response().setStatusCode(404).end();
                } else {
                    whisky.setName(json.getString("name"));
                    whisky.setOrigin(json.getString("origin"));
                    Whisky updatedWhisky = service.update(whisky);
                    routingContext.response()
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(Json.encodePrettily(updatedWhisky));
                }
            }
        });

        // deleting a whiskies based on ID - (:id) is used as a path variable
        router.delete("/api/whiskies/:id").handler(routingContext -> {
            String id = routingContext.request().getParam("id");
            if (id == null) {
                routingContext.response().setStatusCode(400).end();
            } else {
                int idAsInteger = Integer.parseInt(id);
                service.remove(idAsInteger);
            }
            routingContext.response().setStatusCode(204).end();
        });

        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        // Retrieve the port from the configuration,
                        // default to 8080.
                        config().getInteger("http.port", 8080),
                        result -> {
                            if (result.succeeded()) {
                                startFuture.complete();
                            } else {
                                startFuture.fail(result.cause());
                            }
                        }
                );
    }
}
