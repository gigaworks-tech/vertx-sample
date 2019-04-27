package com.gigaworks.tech.vertx.sample.repository;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class UserRepository {
    private Vertx vertx;
    private MongoClient mongoClient;

    public UserRepository(Vertx vertx) {
        this.vertx = vertx;
        JsonObject mongoconfig = new JsonObject()
                .put("connection_string", "mongodb://localhost:27017/test");
        mongoClient = MongoClient.createShared(vertx, mongoconfig);
    }

    public Future<JsonArray> getAllUsers() {
        Future<JsonArray> future = Future.future();
        mongoClient.find("person", new JsonObject(), handler -> {
            if(handler.succeeded()) {
                future.complete(new JsonArray(handler.result()));
            } else {
                future.fail(handler.cause());
            }
        });
        return future;
    }

    public Future<String> insertUser(JsonObject user) {
        Future<String> future = Future.future();
        mongoClient.insert("person", user, handler -> {
           if(handler.succeeded()) {
               future.complete(handler.result());
           } else {
               future.fail(handler.cause());
           }
        });
        return future;
    }
}
