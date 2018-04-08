//===================================================================================================================
// CbaDateTime.java -- This class is an abstract class for the cba date and time combined
// -----------------------------------------------------------------------------------------------------------------
//
// The CbaDate type closely aligns with the MySQL DATETIME data type, maintaining only a date and time in the local 
// time zone.  This type differs from the {@link CbaTimestamp} type in 2 ways: it 1) maintains a date and time to 
// only whole second accuracy, and 2) it is stored and retrieved in the local time zone.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-04-01     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;

import java.sql.SQLException;


//-------------------------------------------------------------------------------------------------------------------

/**
 * An implementation of the MySQL date and time (DATETIME) field.  This date and time are handled relative to the 
 * local time zone.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
public class CbaDateTime extends CbaTemporalType {
    /**
     * The builder class for initializing a CbaVarchar element
     */
    public class Builder extends CbaTemporalType.Builder<Builder> {
        public Builder() {
            setIndicatedType(CbaType.IndicatedType.CBA_DATE_TIME);
            setDefaultValue(DEFAULT_VALUE);
        }


        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        /**
         * Build a CbaDateTime from the builder setup
         */
        public CbaDateTime build() {
            return new CbaDateTime(this);
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This constant String value is used to indicate an uninitialized date and time .  Note that the date and time 
     * are technically valid.
     */
    private static final String DEFAULT_VALUE = "0000-01-01T00:00:00";


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This constant value is used to indicate an uninitialized date and time.  Note that the date and time is 
     * technically valid.
     */
    public static final LocalDateTime ZERO = LocalDateTime.parse(DEFAULT_VALUE);


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This is the value of the CbaDateTime field.
     */
    private LocalDateTime value;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Create a new CbaDateTime that is bound to a table field.
     * 
     * @param builder the builder class to initialize this instance
     */
    private CbaDateTime(Builder builder) {
        super(builder);
        clearField();
    }
    

    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new date and time to this field.  Note that the date and time must be in the format 
     * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE_TIME}.
     * 
     * @param v A string representation of the date and time to assign properly formatted.
     */
    public void assign(String v) {
        if (isReadOnly()) {
            LogManager.getLogger(this.getClass()).warn("Unable to assign to a read-only field; ignoring assignment");
            return;
        }

        value = LocalDateTime.parse(v);
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

        return (((CbaDateTime)obj).value.equals(value));
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Return a string representation of this date and time in ISO-8601 format.
     * 
     * @return A string representation of the date and time.
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

        return getFieldName() + " DATETIME";
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