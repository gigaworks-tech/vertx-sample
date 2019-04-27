package com.gigaworks.tech.vertx.sample.controller;

import com.gigaworks.tech.vertx.sample.service.UserService;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class UserController {

    private Vertx vertx;

    public UserController(Vertx vertx) {
        this.vertx = vertx;
    }

    public Router getRouter() {
        Router router = Router.router(vertx);
        definePaths(router);
        return router;
    }

    private void definePaths(Router router) {
        UserService service = new UserService(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/").handler(service::getAllUsers);
        router.post("/").handler(service::insertUser);
    }
}
