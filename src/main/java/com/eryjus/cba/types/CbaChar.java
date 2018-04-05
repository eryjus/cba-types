//===================================================================================================================
// CbaChar.java -- A concrete class for a fixed width character type
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is a fixed width character class.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-04-04     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.sql.SQLException;

import com.eryjus.cba.sql.Sql;


//-------------------------------------------------------------------------------------------------------------------
// class CbaChar:
/**
 * A fixed width character class.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaChar extends CbaCharType implements Sql {
    //---------------------------------------------------------------------------------------------------------------
    // static final int DEFAULT_SIZE:
    /**
     * The default size of a CbaChar type.
     */
    private static int DEFAULT_SIZE = 1;


    //---------------------------------------------------------------------------------------------------------------
    // String value:
    /**
     * The actual value of this instance.
     */
    private String value;


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaChar(String, String, int):
    /**
     * Create a new CbaChar that is bound to a table field.
     * 
     * @param tbl The name of the table to which this field is bound.
     * @param fld The name of the field to which this field is bound.
     * @param sz The fixed width of the character field.
     */
    public CbaChar(String tbl, String fld, int sz) {
        super(tbl, fld, sz);
        value = "";
    }
    

    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaChar(String, String):
    /**
     * Create a new CbaChar that is bound to a table field.
     * 
     * @param tbl The name of the table to which this field is bound.
     * @param fld The name of the field to which this field is bound.
     */
    public CbaChar(String tbl, String fld) {
        super(tbl, fld, DEFAULT_SIZE);
        value = "";
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaChar(int):
    /**
     * Construct a new CbaChar with the indicated size.
     * 
     * @param sz The fixed width of the character field.
     */
    public CbaChar(int sz) {
        super(sz);
        value = "";
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaChar():
    /**
     * Construct a new CbaChar with the default size.
     */
    public CbaChar() {
        super(DEFAULT_SIZE);
        value = "";
    }


    //---------------------------------------------------------------------------------------------------------------    
    // assign(String):
    /**
     * Perform an assignment from a String value.
     * 
     * @param v A String representation of the value to assign to the Cbe Type.
     */
    public void assign(String v) {
        if (v.length() > getSize()) {
            value = v.substring(0, getSize());
        } else  {
            value = v;
        }
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------    
    // toString():
    /**
     * Convert a CbaChar to a string (rather trivial)
     * 
     * @return A trivial return of this value.  Fine value is immutable, this will not cause problems.
     */
    public String toString() { return value; }


    //---------------------------------------------------------------------------------------------------------------
    // equals(o):
    /**
     * Determine equality by returning the equality of {@link CbaChar#value}.  Note that we are not checking 
     * any table name or field name or size constraints.  Omitting these extra comparisons is relevant since the 
     * a database field will be compared to a variable to see if the values are the same, and they may not be the 
     * exact same type.  We still want to be able to determine that equality.
     * 
     * @param o The object against which to evaluate equality.
     * @return Whether the value and the object represent the same thing.
     */
    public boolean equals(Object o) { 
        if (null == o) return false;
        if (this == o) return true;
        if (getClass() != o.getClass()) {
            return false;
        }

        return (((CbaChar)o).value.equals(value));
    }


    //---------------------------------------------------------------------------------------------------------------
    // toCreateSpec()
    /**
     * Create a spec for the field to be used in a {@code CREATE TABLE} specification, returning the specific clause
     * for this field in the column specifications.
     * 
     * @return The column spec clause for this field.
     * @throws SQLException When the field name is empty since the field must have a name.
     */
    public String toCreateSpec() throws SQLException {
        if (getFieldName().isEmpty()) {
            throw new SQLException("Field name is not set; cannot create a table spec from a variable");
        }

        return getFieldName() + " CHAR(" + getSize() + ")";
    }
}