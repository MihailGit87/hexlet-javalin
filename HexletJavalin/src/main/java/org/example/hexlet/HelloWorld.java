package org.example.hexlet;

import io.javalin.Javalin;
import org.example.hexlet.model.Course;
import org.example.hexlet.dto.courses.CoursesPage;
import java.util.Collections;
import java.util.List;

public class HelloWorld {
    public static void main(String[] args) {
        var app = Javalin.create(config -> config.plugins.enableDevLogging());
        app.get("/", ctx -> ctx.render("index.jte"));

        app.get("/users", ctx -> ctx.result("GET /users"));
        app.post("/users", ctx -> ctx.result("POST /users"));
        app.get("/hello", ctx -> {
            var page = ctx.queryParamAsClass("name", String.class).getOrDefault("World!");
            ctx.result("Hello, " + page + "!");
        });
        app.get("/courses", ctx -> {
            List<Course> courses = List.of(new Course("1","1"),new Course("2","1"));
            String header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("index.jte", Collections.singletonMap("page", page));
        });
        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParam("id");
            List<Course> courses = List.of(new Course("1","1"),new Course("2","1"));
            String header = "";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/show.jte", Collections.singletonMap("page", page));
        });

        app.get("/users/{id}", ctx -> {
            ctx.result("User ID: " + ctx.pathParam("id"));
        });
        app.get("/courses/{courseId}/post/{postId}", ctx -> {
            ctx.result("Course ID: " + ctx.pathParam("courseId"));
            ctx.result("Post ID: " + ctx.pathParam("postId"));
        });
        app.start(7070);
    }
}
