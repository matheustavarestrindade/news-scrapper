package com.news.scrapper.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DatabaseExecute {

    public void handle(PreparedStatement stmt) throws SQLException;

}
