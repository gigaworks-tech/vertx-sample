package com.gigaworks.tech.vertx.sample.service;

import com.gigaworks.tech.vertx.sample.repository.UserRepository;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserService {
    private Vertx vertx;
    private UserRepository repository;

    public UserService(Vertx vertx) {
        this.vertx = vertx;
        this.repository = new UserRepository(vertx);
    }

    public void getAllUsers(RoutingContext routingContext) {
        repository.getAllUsers().setHandler(result -> {
            if(result.succeeded()) {
                routingContext
                        .response()
                        .putHeader("Content-Type", "application/json")
                        .setStatusCode(200)
                        .end(result.result().encode());
            } else {
                routingContext
                        .response()
                        .putHeader("Content-Type", "application/json")
                        .setStatusCode(404)
                        .end(new JsonObject().put("error", result.cause().getMessage()).encode());
            }
        });
    }

    public void insertUser(RoutingContext routingContext) {
        JsonObject user = routingContext.getBodyAsJson();
        repository.insertUser(user).setHandler(handler -> {
           if(handler.succeeded()) {
               routingContext
                       .response()
                       .putHeader("Content-Type", "application/json")
                       .setStatusCode(201)
                       .end(new JsonObject().put("id", handler.result()).encode());
           } else {
               routingContext
                       .response()
                       .putHeader("Content-Type", "application/json")
                       .setStatusCode(404)
                       .end(new JsonObject().put("error", handler.cause().getMessage()).encode());
           }
        });
    }
}
