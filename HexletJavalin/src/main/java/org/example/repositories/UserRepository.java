package org.example.repositories;

import org.example.users.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.repositories.BaseRepository.dataSource;


public class UserRepository {
    //    private static List<User> entities = new ArrayList<User>();
//
//    public static void save(User user) {
//        user.setId((long) entities.size() + 1);
//        entities.add(user);
//    }
    public static void save (User user) throws SQLException {
        var sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try(var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1,"name");
            stmt.setString(2, "email");
            stmt.executeUpdate();
            var genKeys = stmt.getGeneratedKeys();
            if(genKeys.next()){
                user.setId(genKeys.getLong("id"));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }
    public static Optional<User> find (Long id) throws SQLException {
        var sql = "SELECT * FROM users WHERE id = ?";
        try(var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resSet = stmt.executeQuery();
            if(resSet.next()){
                var name = resSet.getString("name");
                var email = resSet.getString("email");
                var user = new User(name, email);
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }
    public static List<User> getEntities() throws SQLException {
        var sql = "SELECT * FROM users";
        try(var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(sql)) {
            var resSet = stmt.executeQuery();
            var res = new ArrayList<User>();
            while(resSet.next()) {
                var id = resSet.getLong("id");
                var name = resSet.getString("name");
                var email = resSet.getString("email");
                var user = new User(name, email);
                user.setId(id);
                res.add(user);
            }
            return res;
        }
    }

    public static void delete(Long id) {

    }

}
