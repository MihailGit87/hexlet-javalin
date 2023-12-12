package org.example.routes;

public class NamedRoutes {
    public static String usersPath() {
        return "/users";
    }
    public static String newUserPath() {
        return "/users/new";
    }
    public static String coursesPath() {
        return "/courses";
    }
    public static String buildCoursPath() {
        return "/courses/build";
    }
    public static String coursePath(String id) {
        return "/courses/" + id;
    }
    public static String coursePath(Long id) {
        return coursePath(String.valueOf(id));
    }

    public static String sessionsPath() {
        return "/sessions";
    }

}