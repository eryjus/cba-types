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

import org.apache.logging.log4j.LogManager;


//-------------------------------------------------------------------------------------------------------------------

/**
 * A fixed width character class.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaChar extends CbaCharType {
    /**
     * The builder class for initializing a CbaChar element
     */
    public static class Builder extends CbaCharType.Builder<Builder> {
        public Builder() {
            setIndicatedType(CbaType.IndicatedType.CBA_CHAR);
            setSize(DEFAULT_SIZE);
            setDefaultValue(DEFAULT_VALUE);
        }


        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        /**
         * Build a CbaChar from the builder setup
         */
        public CbaChar build() {
            return new CbaChar(this);
        }
    }

    
    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default value for a CbaChar.
     */
    private static final String DEFAULT_VALUE = "";


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default size of a CbaChar type.
     */
    private static final int DEFAULT_SIZE = 1;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Create a new CbaChar that is bound to a table field.
     * 
     * @param builder the builder class to initialize this instance
     */
    public CbaChar(Builder builder) {
        super(builder);
        clearField();
    }
    

    //---------------------------------------------------------------------------------------------------------------    

    /**
     * Perform an assignment from a String value.
     * 
     * @param val A String representation of the value to assign to the Cbe Type.
     */
    public void assign(String val) {
        if (isReadOnly()) {
            LogManager.getLogger(this.getClass()).warn("Unable to assign to a read-only field; ignoring assignment");
            return;
        }

        if (val.length() > getSize()) {
            setValue(val.substring(0, getSize()));
        } else  {
            setValue(val);
        }
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Determine equality by returning the equality of {@link CbaChar#value}.  Note that we are not checking 
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

        return (((CbaChar)obj).getValue().equals(getValue()));
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

        return getFieldName() + " CHAR(" + getSize() + ")";
    }
}