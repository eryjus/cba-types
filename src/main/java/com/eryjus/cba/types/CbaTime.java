//===================================================================================================================
// CbaTime.java -- This class is an abstract class for the cba time
// -----------------------------------------------------------------------------------------------------------------
//
// The CbaDate type closely aligns with the MySQL TIME data type, maintaining only a time in the local time zone.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-04-01     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.time.LocalTime;

import org.apache.logging.log4j.LogManager;

import java.sql.SQLException;


//-------------------------------------------------------------------------------------------------------------------

/**
 * An implementation of the MySQL time field.  This time is handled relative to the local time zone.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaTime extends CbaTemporalType {
    /**
     * The builder class for initializing a CbaVarchar element
     */
    public class Builder extends CbaTemporalType.Builder<Builder> {
        public Builder() {
            setIndicatedType(CbaType.IndicatedType.CBA_TIME);
            setDefaultValue(DEFAULT_VALUE);
        }
    
    
        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        /**
         * Build a CbaTime from the builder setup
         */
        public CbaTime build() {
            return new CbaTime(this);
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default value for a CbaVarchar.
     */
    private static final String DEFAULT_VALUE = "00:00:00";


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This constant value is used to indicate an uninitialized date.  Note that the date is technically valid.
     */
    private static final LocalTime ZERO = LocalTime.parse(DEFAULT_VALUE);


    //---------------------------------------------------------------------------------------------------------------

    private LocalTime value;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Create a new CbaVarchar that is bound to a table field.
     * 
     * @param builder the builder class to initialize this instance
     */
    private CbaTime(Builder builder) {
        super(builder);
        clearField();
    }
    

    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new time to this field.  Note that the time must be in the format 
     * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_TIME}.
     * 
     * @param v A string representation of the time to assign properly formatted.
     */
    public void assign(String v) {
        if (isReadOnly()) {
            LogManager.getLogger(this.getClass()).warn("Unable to assign to a read-only field; ignoring assignment");
            return;
        }

        value = LocalTime.parse(v);
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Test for equality.
     * 
     * @param obj The object against which equality will be determined.
     * @return Whether Object o is equal to this instance.
     */
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) {
            return false;
        }

        return (((CbaTime)obj).value.equals(value));
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Return a string representation of this time in ISO-8601 format.
     * 
     * @return A string representation of the time.
     */
    public String toString() {
        return value.toString();
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

        return getFieldName() + " TIME";
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Determine if the value of this instance is equivalent to a ZERO value.  Be warned that a ZERO value is a 
     * legitimate value for this type, do it must not be used as an indication that the value has not been 
     * initialized.
     * 
     * @return Whether the value of this instance is equivalent to {@link #ZERO}.
     */
    public boolean isZero() {
        return value.equals(ZERO);
    }
}