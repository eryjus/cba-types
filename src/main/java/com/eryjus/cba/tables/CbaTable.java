
package com.eryjus.cba.tables;

import java.sql.SQLException;

import com.eryjus.cba.sql.SqlTable;
import com.eryjus.cba.types.CbaType;

public class CbaTable implements SqlTable {
    private final String schema;
    private final String table;
    private final CbaType[] fields;

    public String getSchema() { return schema; }
    public String getTable() { return table; }

    public CbaTable(String sch, String tbl) {
        schema = sch;
        table = tbl;
        fields = readTableStructure();
    }

    protected CbaTable(String sch, String tbl, CbaType[] flds) {
        schema = sch;
        table = tbl;
        fields = flds;
    }


    private CbaType[] readTableStructure() {
        return null;
    }


    public String toCreateSpec() throws SQLException {
        String rv = "CREATE TABLE " + getSchema() + "." + getTable() + " (";
        for (int i = 0; i < fields.length; i ++) {
            if (i != 0) rv += ", ";
            rv += fields[i].toCreateSpec();
        }
        rv += ")";

        return rv;
    }


    public void clearBuffer() {
        for (int i = 0; i < fields.length; i ++) {
            fields[i].clearField();
        }
    }
}