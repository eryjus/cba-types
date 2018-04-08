//===================================================================================================================
// SqlField.java -- In interface for a SQL field to manage a common interface for fields.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is a common interface for managing SQL fields.
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
// class SqlField:
/**
 * This is a common interface for a SQL fields to be interfaced with by a table wanting to read, update, and delete
 * information.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
public interface SqlField {
    /**
     * Flags to indicate whether the field can be updated.
     */
    public enum UpdateStyle {
        PROGRAMMER,
        INSERT,
        UPDATE,
        BOTH
    }


    //---------------------------------------------------------------------------------------------------------------
    // toCreateSpec():
    /**
     * Get a string specifying how to create the field within a {@code CREATE TABLE} statement.
     * 
     * @return A string with the column specification (without any separating columns).
     */
    public String toCreateSpec() throws SQLException;
}