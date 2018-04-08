//===================================================================================================================
// CbaSmallInt.java -- This class implements an 16-bit (2-byte) integer.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the concrete implementation of an integer data type.  At its core is Java's primitive long, but 
// the overall number of bits used will be managed to 16-bits on each assignment.
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
 * A 16-bit implementation of an integer as represented in MySQL.  While the implementation of this class is the 
 * Java primitive {@code long}.  Using {@code long} is required because Java does not support unsigned primitive 
 * integers and I wanted to use a primitive type as much as possible for performance considerations.  So, every time 
 * {@link #value} changes the result will be trimmed to 16-bits.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaSmallInt extends CbaIntegerType {
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
        public CbaSmallInt build() {
            return new CbaSmallInt(this);
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The minimum value allowed for this particular object.
     */
    private static final long MIN = -32768;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The maximum value allowed for this particular object.
     */
    private static final long MAX = 32767;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This constructor will create an instance that is a database field and initialize it to the value "0".
     * 
     * @param builder The builder class from which this instance is initialized
     */
    public CbaSmallInt(Builder builder) {
        super(builder);
        setValue(0);
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Trim the newly assigned {@link #value} to 16-bits.  This critical function must distinguish between signed  
     * and unsigned numbers and manage the value properly.
     */
    void trim() {
        if ((getValue() & 0xffff) == 0x8000) {
            setValue(0x8000);
        } else {
            boolean isNeg = (getValue() < 0 ? true : false);
            if (isNeg) setValue(-getValue());
            setValue(getValue() & 0x7fff);
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
     * @param o The object to which to compare this instance.
     * @return Whether this instance and the object are equal.
     */
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) {
            return false;
        }

        return (((CbaSmallInt)obj).getValue() == getValue());
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

        return getFieldName() + " SMALLINT(" + getSize() + ")";
    }
}