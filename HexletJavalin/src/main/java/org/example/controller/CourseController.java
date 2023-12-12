package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.courses.*;
import org.example.repositories.CourseRepository;
import org.example.routes.NamedRoutes;

import java.sql.SQLException;
import java.util.Collections;

public class CourseController {
    public static void index(Context ctx) throws SQLException {
        var courses = CourseRepository.getEntities();
        var page = new CoursesPage(courses);
        ctx.render("courses/index.jte", Collections.singletonMap("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = CourseRepository.find(id)
                .orElseThrow(()-> new NotFoundResponse("The course with the id " + id + " not found"));
        var page = new CoursePage(course);
        ctx.render("courses/show.jte", Collections.singletonMap("page", page));
    }

    public static void build(Context ctx) {
        ctx.render("courses/build");
    }

    public static void create(Context ctx) throws SQLException {
        var courseName = ctx.formParam("name");
        var header = ctx.formParam("header");

        var course = new Course(courseName, header);
        CourseRepository.save(course);
        ctx.sessionAttribute("flash","Course has been created");
        ctx.redirect(NamedRoutes.coursesPath());
    }

    public static void edit(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var name = ctx.formParam("name");
        var header = ctx.formParam("header");

        var course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("The course with the id " + id + " not found"));

        course.setName(name);
        course.setDescription(header);
        CourseRepository.save(course);

        ctx.redirect(NamedRoutes.coursesPath());
    }

    public static void destroy(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        CourseRepository.delete(id);
        ctx.redirect(NamedRoutes.coursesPath());
    }

    public static void flashMes(Context ctx) throws SQLException {
        var term = ctx.path();
        var courses = CourseRepository.getEntities();
        var page = new CoursesPage(courses, term);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("courses/index.jte", Collections.singletonMap("page", page));
    }
}
