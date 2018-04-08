//===================================================================================================================
// CbaInt.java -- This class implements an 32-bit (4-byte) integer.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the concrete implementation of an integer data type.  At its core is Java's class BigInteger, but 
// the overall number of bits used will be managed to 32-bits on each assignment.
//
// This is a big change, since I realized I don't need to support `unsigned`.  I can use the standard long primitive
// in Java.  So this has a bit of a rewrite.  It no longer needs BigInteger.
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
// class CbaInt:
/**
 * A 32-bit implementation of an integer as represented in MySQL.  
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
public class CbaInt extends CbaIntegerType {
    /**
     * This is the builder class for a small integer
     */
    public static class Builder extends CbaIntegerType.Builder<Builder> {
        public Builder() {
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
        public CbaInt build() {
            return new CbaInt(this);
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The minimum value allowed for this particular object.
     */
    private static final long MIN = -2147483648;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The maximum value allowed for this particular object.
     */
    private static final long MAX = 2147483647;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This constructor will create an instance that is a database field and initialize it to the value "0".
     * 
     * @param builder the builder class for this instance.
     */
    public CbaInt(Builder builder) {
        super(builder);
        setValue(0);
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Trim the newly assigned {@link #value} to 32-bits.  This critical function must distinguish between signed  
     * and unsigned numbers and manage the value properly.
     */
    void trim() {
        if ((getValue() & 0xffffffff) == 0x80000000) {
            setValue(0x80000000);
        } else {
            boolean isNeg = (getValue() < 0 ? true : false);
            if (isNeg) setValue(-getValue());
            setValue(getValue() & 0x7fffffff);
            if (isNeg) setValue(-getValue());
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String):
    /**
     * Assign a new String value to {@link #value}.  This is done by first converting the String to a {@code long}. 
     * Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param v A String representation of the value to assign.
     */
    @Override
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
    @Override
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) {
            return false;
        }

        return (((CbaInt)obj).getValue() == getValue());
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

        return getFieldName() + " INT(" + getSize() + ")";
    }
}