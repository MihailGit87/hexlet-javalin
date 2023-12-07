package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.validation.ValidationException;
import org.example.hexlet.util.NamedRoutes;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.UserRepository;

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

        app.get("/users/{id}", ctx -> {
            var id = ctx.pathParam("id");
            ctx.contentType("html");
            ctx.result("<h1>" + id + "</h1>");
        });
        app.get(NamedRoutes.buildUserPath(), ctx -> {
            var page = new BuildUserPage();
            ctx.render("users/build.jte", Collections.singletonMap("page", page));
        });
        app.post("/users", ctx -> {
            var name = ctx.formParam("name").trim();
            var email = ctx.formParam("email").trim().toLowerCase();

            try {
                var passwordConfirmation = ctx.formParam("passwordConfirmation");
                var password = ctx.formParamAsClass("password", String.class)
                        .check(value -> value == passwordConfirmation, "Пароли не совпадают")
                        .check(value -> value.length() > 6, "У пароля недостаточная длина")
                        .get();
                var user = new User(name, email, password);
                UserRepository.save(user);
                ctx.redirect("/users");
            } catch (ValidationException e) {
                var page = new BuildUserPage(name, email, e.getErrors());
                ctx.render("users/build.jte", Collections.singletonMap("page", page));
            }
        });



//        app.get("/courses", ctx -> {
//            List<Course> courses = List.of(new Course("1","1"),new Course("2","1"));
//            String header = "Курсы по программированию";
//            var page = new CoursesPage(courses, header);
//            ctx.render("index.jte", Collections.singletonMap("page", page));
//        });
        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParam("id");
            List<Course> courses = List.of(new Course("1","1"),new Course("2","1"));
            String header = "";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/show.jte", Collections.singletonMap("page", page));
        });

//        app.get("/courses", ctx -> {
//            var term = ctx.queryParam("term");
//            ArrayList<Course> courses;
//            // Фильтруем, только если была отправлена форма
//            if (term != null) {
//                courses = /* Фильтруем курсы по term */
//            } else {
//                courses = /* Извлекаем все курсы, которые хотим показать */
//            }
//            var page = new CoursesPage(courses);
//            ctx.render("courses/index.jte", Collections.singletonMap("page", page));
//        });

        app.get("/courses/{courseId}/post/{postId}", ctx -> {
            ctx.result("Course ID: " + ctx.pathParam("courseId"));
            ctx.result("Post ID: " + ctx.pathParam("postId"));
        });
        app.start(7070);
    }
}
