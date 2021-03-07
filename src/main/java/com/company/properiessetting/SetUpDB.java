package com.company.properiessetting;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;



public class SetUpDB {
    private final String dsn;
    private final String user;
    private final String password;
    private DataSource dataSource;
    public SetUpDB(String dsn, String user, String password) {
        this.dsn = dsn;
        this.user=user;
        this.password=password;
        dataSource = new HikariDataSource(createPool());

    }
    private HikariConfig createPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dsn);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(4);
        hikariConfig.setMinimumIdle(4);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        return (HikariConfig) dataSource;
    }

    public Connection createConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
