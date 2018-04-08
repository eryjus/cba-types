//===================================================================================================================
// CbaTinyInt.java -- This class implements an 8-bit (1-byte) integer.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the concrete implementation of an integer data type.  At its core is Java's primitive long, but 
// the overall number of bits used will be managed to 8-bits on each assignment.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-29     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.sql.SQLException;


//-------------------------------------------------------------------------------------------------------------------

/**
 * An 8-bit implementation of an integer as represented in MySQL.  While the implementation of this class is the 
 * Java primitive {@code long}.  Using {@code long} is required because Java does not support unsigned primitive 
 * integers and I wanted to use a primitive type as much as possible for performance considerations.  So, every time 
 * {@link #value} changes the result will be trimmed to 8-bits.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
public class CbaTinyInt extends CbaIntegerType {
    /**
     * The builder class for initializing a CbaVarchar element
     */
    public static class Builder extends CbaIntegerType.Builder<Builder> {
        public Builder() {
            setIndicatedType(CbaType.IndicatedType.CBA_TINY_INT);
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
        public CbaTinyInt build() {
            return new CbaTinyInt(this);
        }
    }

    
    //---------------------------------------------------------------------------------------------------------------

    /**
     * The minimum value allowed for this particular object.
     */
    private static final long MIN = -128;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The maximum value allowed for this particular object.
     */
    private static final long MAX = 127;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Create a new CbaTinyInt that is bound to a table field.
     * 
     * @param builder the builder class to initialize this instance
     */
    CbaTinyInt(Builder builder) {
        super(builder);
        clearField();
    }
    

    //---------------------------------------------------------------------------------------------------------------

    /**
     * Trim the newly assigned {@link #value} to 8-bits.  This critical function must distinguish between signed and 
     * unsigned numbers and manage the value properly.
     */
    void trim() {
        if ((getValue() & 0xff) == 0x80) {
            setValue(0x80);
        } else {
            boolean isNeg = (getValue() < 0 ? true : false);
            if (isNeg) setValue(-getValue());
            setValue(getValue() & 0x7f);
            if (isNeg) setValue(-getValue());
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new String value to {@link #value}.  This is done by first converting the String to a {@code long}. 
     * Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param val A String representation of the value to assign.
     */
    public void assign(String val) {
        assign(Long.valueOf(val));
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

        return (((CbaTinyInt)obj).getValue() == getValue());
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

        return getFieldName() + " TINYINT(" + getSize() + ")";
    }
}