package org.example.hexlet;

import io.javalin.Javalin;

public class HelloWorld {
    public static void main(String[] args) {
        var app = Javalin.create(config -> config.plugins.enableDevLogging());
        app.get("/users", ctx -> ctx.result("GET /users"));
        app.post("/users", ctx -> ctx.result("POST /users"));
        app.get("/hello", ctx -> {
            var page = ctx.queryParamAsClass("name", String.class).getOrDefault("World!");
            ctx.result("Hello, " + page + "!");
        });
        app.start(7070);
    }
}
