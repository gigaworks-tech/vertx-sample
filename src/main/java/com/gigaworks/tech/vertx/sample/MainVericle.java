package com.gigaworks.tech.vertx.sample;

import com.gigaworks.tech.vertx.sample.controller.UserController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class MainVericle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();

        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        Router userRouter = new UserController(vertx).getRouter();

        router.mountSubRouter("/user", userRouter);

        server.requestHandler(router).listen(80);

    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVericle());
    }

}
