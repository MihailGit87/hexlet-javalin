package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import org.example.routes.NamedRoutes;

public class SessionController {
    public static void build(Context ctx) {
        ctx.render("session/build.jte");
    }
    public static void create(Context ctx) {
        var nickname = ctx.formParam("nickname");

        try {
            var passCon = ctx.formParam("password-confirmation");
            var password = ctx.formParamAsClass("password", String.class)
                    .check(v -> v == passCon, "passwords doesn't match").get();
        } catch (ValidationException e){
            ctx.sessionAttribute("currentuser", e.getErrors());
        }
        ctx.sessionAttribute("currentuser", nickname);
        ctx.redirect("/");
    }

    public static void destroy(Context ctx) {
        ctx.sessionAttribute("currentuser", null);
        ctx.redirect("/");
    }
}
