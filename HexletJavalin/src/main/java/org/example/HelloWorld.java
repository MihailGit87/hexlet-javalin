package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.Javalin;
import org.example.controller.CourseController;
import org.example.controller.SessionController;
import org.example.repositories.BaseRepository;
import org.example.routes.MainPage;
import org.example.routes.NamedRoutes;
import org.example.controller.UserController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


public class HelloWorld {

    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:my_project_1:DB_CLOSE_ELAY=-1");
        var dataSource = new HikariDataSource(hikariConfig);
        //routing
        var url = HelloWorld.class.getClassLoader().getResource("schema.sql");
        var file = new File(url.getFile());
        var sql = Files.lines(file.toPath()).collect(Collectors.joining("/"));
        //connection
        try(var conn = dataSource.getConnection();
            var statement = conn.createStatement()){
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;
        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();

        });
        // courses
        app.get(NamedRoutes.coursesPath(), CourseController::index);
        app.get(NamedRoutes.buildCoursPath(), CourseController::build);
        app.post(NamedRoutes.coursesPath(), CourseController::create);
        app.get(NamedRoutes.coursePath("{id}"), CourseController::show);
        // users
        app.get(NamedRoutes.usersPath(), UserController::index);
        app.get(NamedRoutes.newUserPath(), UserController::build);
        app.post(NamedRoutes.usersPath(), UserController::create);
        // sessions
        app.get("/sessions/build", SessionController::build);
        app.post("/sessions", SessionController::create);
        // start list
        app.get("/", ctx -> {
            var page = new MainPage(ctx.sessionAttribute("currentUser"));
            ctx.render("index.jte", Collections.singletonMap("page", page));
        });
        // start app
        app.start(8080);

        return app;
    }
    public static void main(String[] args) throws SQLException, IOException {

        var app = getApp();
        app.start();









//        Course course1 = new Course("Java 1", "desc1");
//        Course course2 = new Course("PHP 2", "desc2");
//        Course course3 = new Course("HTML 3", "desc3");
//        course1.setId(0L);
//        course2.setId(1L);
//        course3.setId(2L);


//        List<User> userList = new ArrayList<>();
//
//        JavalinJte.init();
//
//        Javalin app = Javalin.create(config -> config.plugins.enableDevLogging());

        //Start testpage

//        app.get("/",ctx -> {
//            var visited = Boolean.valueOf(ctx.cookie("visited"));
//            var page = new MainPage(ctx.sessionAttribute("currentUser"));
//            ctx.render("index.jte", Collections.singletonMap("page", page));
//            ctx.cookie("visited", String.valueOf(true));
//        });
//        app.get(NamedRoutes.coursesPath(), CourseController::flashMes);
//        app.get(NamedRoutes.coursesPath(), CourseController::index); // Course list
//        app.get(NamedRoutes.coursePath("{id}"), CourseController::show); /* -- Specific course -- */
//        app.get(NamedRoutes.buildCoursPath(), CourseController::build);
//        app.post(NamedRoutes.coursesPath(), CourseController::create); /* New course */

//        app.get("/sessions/build,jte", SessionController::build);
//        app.post("/sessions", SessionController::create);
//        app.post("/sessions", SessionController::destroy);

//        app.get(NamedRoutes.usersPath(), UserController::index); // User list
//        app.get(NamedRoutes.newUserPath(), UserController::build); // Specific  user
//        app.post("/users", UserController::create); // new user

//        app.start(8080);
    }

}