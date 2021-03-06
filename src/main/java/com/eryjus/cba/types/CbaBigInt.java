//===================================================================================================================
// CbaBigInt.java -- This class implements an 64-bit (8-byte) integer.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the concrete implementation of an integer data type.  At its core is Java's class BigInteger, but 
// the overall number of bits used will be managed to 64-bits on each assignment.
//
// This is a big change, since I realized I don't need to support `unsigned`.  I can use the standard long primitive
// in Java.  So this has a bit of a rewrite.  It no longer changes into BigInteger.
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
// class CbaBigInt:
/**
 * A 64-bit implementation of an integer as represented in MySQL.  
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaBigInt extends CbaIntegerType {
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
         * Build a CbaBigInt from the builder setup
         */
        public CbaBigInt build() {
            return new CbaBigInt(this);
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The minimum value allowed for this particular object.
     */
    private static final long MIN = Long.MIN_VALUE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The maximum value allowed for this particular object.
     */
    private static final long MAX = Long.MAX_VALUE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This constructor will create an instance that is a database field and initialize it to the value "0".
     * 
     * @param builder the builder class from which to initialize this instance
     */
    public CbaBigInt(Builder builder) {
        super(builder);
        setValue(0);
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This is a trivial implementation to trim a 64-bit integer to 64-bits.  Obviously, there is nothing to do.
     */
    void trim() {}


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new String value to {@link #value}.  This is done by first converting the String to a {@code long}. 
     * Then, set the field to be dirty.
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

        return (((CbaBigInt)obj).getValue() == getValue());
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

        return getFieldName() + " BIGINT(" + getSize() + ")";
    }
}