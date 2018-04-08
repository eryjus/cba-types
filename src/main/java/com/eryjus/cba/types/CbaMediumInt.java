//===================================================================================================================
// CbaMediumInt.java -- This class implements an 24-bit (3-byte) integer.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the concrete implementation of an integer data type.  At its core is Java's primitive long, but 
// the overall number of bits used will be managed to 24-bits on each assignment.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-30     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.sql.SQLException;


//-------------------------------------------------------------------------------------------------------------------

/**
 * A 24-bit implementation of an integer as represented in MySQL.  While the implementation of this class is the 
 * Java primitive {@code long}.  Using {@code long} is required because Java does not support unsigned primitive 
 * integers and I wanted to use a primitive type as much as possible for performance considerations.  So, every time 
 * {@link #value} changes the result will be trimmed to 24-bits.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
public class CbaMediumInt extends CbaIntegerType {
    /**
     * This is the builder class for a small integer
     */
    static class Builder extends CbaIntegerType.Builder<Builder> {
        Builder() {
            setIndicatedType(CbaType.IndicatedType.CBA_SMALL_INT);
            setSize(DEFAULT_SIZE);
            setDefaultValue(DEFAULT_VALUE);
            setMinVal(MIN);
            setMaxVal(MAX);

        }


        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        /**
         * Build a CbaTinyInt from the builder setup
         */
        public CbaMediumInt build() {
            return new CbaMediumInt(this);
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The minimum value allowed for this particular object.
     */
    private static final long MIN = -8388608;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The maximum value allowed for this particular object.
     */
    private static final long MAX = 8388607;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This constructor will create an instance that is a database field and initialize it to the value "0".
     * 
     * @param builder the class with all the required setup values
     */
    public CbaMediumInt(Builder builder) {
        super(builder);
        setValue(0);
    }


    //---------------------------------------------------------------------------------------------------------------
    // trim():
    /**
     * Trim the newly assigned {@link #value} to 24-bits.  This critical function must distinguish between signed  
     * and unsigned numbers and manage the value properly.
     */
    void trim() {
        if ((getValue() & 0xffffff) == 0x800000) {
            setValue(0x800000);
        } else {
            boolean isNeg = (getValue() < 0 ? true : false);
            if (isNeg) setValue(-getValue());
            setValue(getValue() & 0x7fffff);
            if (isNeg) setValue(-getValue());
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new String value to {@link #value}.  This is done by first converting the String to a {@code long}. 
     * Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param v A String representation of the value to assign.
     */
     public void assign(String v) {
        assign(Long.valueOf(v));
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Determine equality by comparing the {@link Object} {@code o} to a boxed version of {@link #value}.
     * 
     * @param obj The object to which to compare this instance.
     * @return Whether this instance and the object are equal.
     */
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) {
            return false;
        }

        return (((CbaMediumInt)obj).getValue() == getValue());
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

        return getFieldName() + " MEDIUMINT(" + getSize() + ")";
    }
}