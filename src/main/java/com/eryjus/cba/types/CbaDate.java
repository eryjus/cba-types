//===================================================================================================================
// CbaDate.java -- This class is an abstract class for the cba date
// -----------------------------------------------------------------------------------------------------------------
//
// The CbaDate type closely aligns with the MySQL DATE data type, maintaining only a date in the local time zone.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-04-01     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;

import java.sql.SQLException;


//-------------------------------------------------------------------------------------------------------------------

/**
 * An implementation of the MySQL date field.  This date is handled relative to the local time zone.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaDate extends CbaTemporalType {
    /**
     * The builder class for initializing a CbaVarchar element
     */
    public class Builder extends CbaTemporalType.Builder<Builder> {
        public Builder() {
            setIndicatedType(CbaType.IndicatedType.CBA_DATE);
            setDefaultValue(DEFAULT_VALUE);
        }


        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        /**
         * Build a CbaDate from the builder setup
         */
        public CbaDate build() {
            return new CbaDate(this);
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default value for a CbaVarchar.
     */
    private static final String DEFAULT_VALUE = "0000-01-01";


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This constant value is used to indicate an uninitialized date.  Note that the date is technically valid.
     */
    private static final LocalDate ZERO = LocalDate.parse(DEFAULT_VALUE);


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This is the value of the CbaDate field.
     */
    private LocalDate value;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Create a new CbaVarchar that is bound to a table field.
     * 
     * @param builder the builder class to initialize this instance
     */
    private CbaDate(Builder builder) {
        super(builder);
        clearField();
    }
    

    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new date to this field.  Note that the date must be in the format 
     * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE}.
     * 
     * @param val A string representation of the date to assign properly formatted.
     */
    public void assign(String val) {
        if (isReadOnly()) {
            LogManager.getLogger(this.getClass()).warn("Unable to assign to a read-only field; ignoring assignment");
            return;
        }

        value = LocalDate.parse(val);
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

        return (((CbaDate)obj).value.equals(value));
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Return a string representation of this date in ISO-8601 format.
     * 
     * @return A string representation of the date.
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

        return getFieldName() + " DATE";
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