//===================================================================================================================
// SqlTable.java -- In interface for a SQL field to manage a common interface for tables.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is a common interface for managing SQL tables.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-04-04     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.sql;

import java.sql.SQLException;

//-------------------------------------------------------------------------------------------------------------------
// class SqlTable:
/**
 * This is a common interface for a SQL tables to be used to read, update, and delete information.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
public interface SqlTable {
    public String toCreateSpec() throws SQLException;
    public void clearBuffer();
}