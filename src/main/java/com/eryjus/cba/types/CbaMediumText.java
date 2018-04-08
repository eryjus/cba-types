//===================================================================================================================
// CbaMediumText.java -- A specialization of the CbaVarchar class limited specifically to 16,777,215 bytes.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is a text field that is limited to 16,777,215 bytes.
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
// class CbaMediumText:
/**
 * A variable width text field that is limited to 16,777,215 bytes.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaMediumText extends CbaCharType {
    /**
     * The builder class for initializing a CbaCharType element
     */
    public class Builder extends CbaCharType.Builder<Builder> {
        public Builder() {
            setIndicatedType(CbaType.IndicatedType.CBA_MEDIUM_TEXT);
            setDefaultValue(DEFAULT_VALUE);
            setSize(DEFAULT_SIZE);
        }


        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        /**
         * Build a CbaCharType from the builder setup
         */
        public CbaMediumText build() {
            return new CbaMediumText(this);
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default value for a CbaMediumText.
     */
    private static final String DEFAULT_VALUE = "";


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default size of a CbaVarchar type.
     */
    private static int DEFAULT_SIZE = 16777215;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Create a new CbaTinyText that is bound to a table field.
     * 
     * @param builder the builder class to initialize this instance
     */
    private CbaMediumText(Builder builder) {
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

        return (((CbaMediumText)obj).toString().equals(toString()));
    }


    //---------------------------------------------------------------------------------------------------------------

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

        return getFieldName() + " MEDIUMTEXT";
    }
}