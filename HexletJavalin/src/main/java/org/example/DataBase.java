package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.Javalin;
import org.example.repositories.BaseRepository;

import java.io.ObjectInputFilter;

public class DataBase {
    public static Javalin getApp() {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:my_projec_db:DB_CLOSE_DELAY=-1");

        var dataSource = new HikariDataSource(hikariConfig);
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {config.plugins.enableDevLogging();});

        return app;
    }
}
