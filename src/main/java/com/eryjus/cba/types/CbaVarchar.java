//===================================================================================================================
// CbaVarchar.java -- A concrete class for a variable width character type
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is a variable width character class.
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


//-------------------------------------------------------------------------------------------------------------------

/**
 * A variable width character class.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
public class CbaVarchar extends CbaCharType {
    /**
     * The builder class for initializing a CbaVarchar element
     */
    public static class Builder extends CbaCharType.Builder<Builder> {
        public Builder() {
            setIndicatedType(CbaType.IndicatedType.CBA_VARCHAR);
            setSize(DEFAULT_SIZE);
            setDefaultValue(DEFAULT_VALUE);
        }


        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        /**
         * Build a CbaVarchar from the builder setup
         */
        public CbaVarchar build() {
            return new CbaVarchar(this);
        }
    }

    
    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default value for a CbaVarchar.
     */
    private static final String DEFAULT_VALUE = "";


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default size of a CbaVarchar type.
     */
    private static final int DEFAULT_SIZE = 1;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Create a new CbaVarchar that is bound to a table field.
     * 
     * @param builder the builder class to initialize this instance
     */
    CbaVarchar(Builder builder) {
        super(builder);
        clearField();
    }
    

    //---------------------------------------------------------------------------------------------------------------

    /**
     * Determine equality by returning the equality of {@link CbaVarchar#value}.  Note that we are not checking 
     * any table name or field name or size constraints.  Omitting these extra comparisons is relevant since the 
     * a database field will be compared to a variable to see if the values are the same, and they may not be the 
     * exact same type.  We still want to be able to determine that equality.
     * 
     * @param obj The object against which to evaluate equality.
     * @return Whether the value and the object represent the same thing.
     */
    public boolean equals(Object obj) { 
        if (null == obj) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) {
            return false;
        }

        return (((CbaVarchar)obj).getValue().equals(getValue()));
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

        return getFieldName() + " VARCHAR(" + getSize() + ")";
    }
}