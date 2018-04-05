

package com.eryjus.cba.sql;

import java.sql.SQLException;

public interface Sql {
    public String toCreateSpec() throws SQLException;
}