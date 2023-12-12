package org.example.repositories;


import org.example.courses.Course;
import org.example.users.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.repositories.BaseRepository.dataSource;

public class CourseRepository {
//    private static List<Course> entities = new ArrayList<Course>();

//    public static void save(Course course) {
//        course.setId((long) entities.size() + 1);
//        entities.add(course);
//    }

    public static void save (Course course) throws SQLException {
        var sql = "INSERT INTO courses (name, description) VALUES (?, ?)";
        try(var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1,"name");
            stmt.setString(2, "description");
            stmt.executeUpdate();
            var genKeys = stmt.getGeneratedKeys();
            // install id into created entities
            if(genKeys.next()){
                course.setId(genKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }
    public static Optional<Course> find(Long id) throws SQLException {
        var sql = "SELECT * FROM courses WHERE id=?";
        try(var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resSet = stmt.executeQuery();
            if(resSet.next()) {
                var name = resSet.getString("name");
                var description = resSet.getString("description");
                var course = new Course(name, description);
                course.setId(id);
                return Optional.of(course);
            }
            return Optional.empty();
        }
    }
    public static List<Course> getEntities() throws SQLException {
        var sql = "SELECT * FROM courses";
        try(var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(sql)) {
            var resSet = stmt.executeQuery();
            var res = new ArrayList<Course>();
            while(resSet.next()) {
                var id =resSet.getLong("id");
                var name = resSet.getString("name");
                var desc = resSet.getString("description");
                var course = new Course(name, desc);
                course.setId(id);
                res.add(course);
            }
            return res;
        }
    }


    public static void delete(Long id) {

    }
}





//    public static void clear() {
//        entities.clear();
//    }

//    public static List<Course> search(String term) {
//        var courses = entities.stream()
//                .filter(entity -> entity.getName().startsWith(term))
//                .toList();
//        return courses;
//    }
//
//    public static Optional<Course> find(Long id) {
//        var course = entities.stream()
//                .filter(entity -> entity.getId().equals(id))
//                .findAny()
//                .orElse(null);
//        return Optional.of(course);
//    }