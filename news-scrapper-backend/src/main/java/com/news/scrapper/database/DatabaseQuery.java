package com.news.scrapper.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseQuery {

    public Object handle(ResultSet rs) throws SQLException;

}
