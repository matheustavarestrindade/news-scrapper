package com.news.scrapper.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private final String host;
    private final String port;
    private final String user;
    private final String pass;
    private final String db;
    private final String connectionUrl;

    public Database(String host, String port, String user, String pass, String db) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.db = db;
        this.connectionUrl = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.db + "?serverTimezone=UTC";

    }

    public void init() {
        String newsTables = "CREATE TABLE IF NOT EXISTS news (headline VARCHAR(255) NOT NULL, url VARCHAR(255) NOT NULL, content TEXT NOT NULL DEFAULT '', timestamp DATETIME NOT NULL, publisher VARCHAR(60) NOT NULL, UNIQUE KEY headline (headline) USING HASH, PRIMARY KEY (headline)) ENGINE=InnoDB;";
        String papersTables = "CREATE TABLE IF NOT EXISTS papers (paper VARCHAR(255) NOT NULL, price DOUBLE NOT NULL, timestamp DATETIME NOT NULL, KEY timestamp (timestamp), KEY paper (paper)) ENGINE=InnoDB;";
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try (Connection conn = DriverManager.getConnection(connectionUrl, this.user, this.pass)) {
            ps1 = conn.prepareStatement(newsTables);
            ps2 = conn.prepareStatement(papersTables);
            ps1.execute();
            ps2.execute();
            ps1.close();
            ps2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute(String statement, DatabaseExecute query) {
        PreparedStatement ps;
        try (Connection conn = DriverManager.getConnection(connectionUrl, this.user, this.pass)) {
            ps = conn.prepareStatement(statement);
            query.handle(ps);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
