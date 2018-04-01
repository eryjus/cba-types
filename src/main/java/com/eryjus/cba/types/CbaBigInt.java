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

//-------------------------------------------------------------------------------------------------------------------
// class CbaBigInt:
/**
 * A 64-bit implementation of an integer as represented in MySQL.  
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaBigInt extends CbaIntegerType {
    //---------------------------------------------------------------------------------------------------------------
    // private long value:
    /**
     * The actual value of the element.
     */
    private long value;


    //---------------------------------------------------------------------------------------------------------------
    // private long minValue:
    /**
     * The minimum value allowed for this particular object.
     */
    private static final long MIN_VALUE = Long.MIN_VALUE;


    //---------------------------------------------------------------------------------------------------------------
    // private long minValue:
    /**
     * The maximum value allowed for this particular object.
     */
    private static final long MAX_VALUE = Long.MAX_VALUE;


    //---------------------------------------------------------------------------------------------------------------
    // CbaMediumInt(String, String, int, boolean):
    /**
     * This constructor will create an instance that is a database field and initialize it to the value "0".
     * 
     * @param tbl The table name to which this field belongs.
     * @param fld The field name to which this field belongs.
     * @param sz The maximum number of display digits, which is only used with zero-filled integers.
     * @param z Is the integer zero filled.
     */
    public CbaBigInt(String tbl, String fld, int sz, boolean z) {
        super(tbl, fld, sz, z);
        value = 0;
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaMediumInt(int, boolean):
    /**
     * This constructor will create an instance that is a fixed size variable and initialize it to the value "0".
     * 
     * @param s The maximum number of display digits, which is only used with zero-filled integers.
     * @param z Is the integer zero filled.
     */
    public CbaBigInt(int s, boolean z) {
        super(s, z);
        value = 0;
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaMediumInt(boolean):
    /**
     * This constructor will create an instance that is the default size variable and initialize it to the value "0".
     * 
     * @param z Is the integer zero filled.
     */
    public CbaBigInt(boolean z) {
        super(CbaIntegerType.DEFAULT_SIZE, z);
        value = 0;
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaMediumInt(int):
    /**
     * This constructor will create an instance that is a fixed size variable and initialize it to the value "0".  
     * This constructor assumes that the variable is not zero filled.
     * 
     * @param s The maximum number of display digits, which is only used with zero-filled integers.
     */
    public CbaBigInt(int s) {
        super(s, false);
        value = 0;
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaMediumInt():
    /**
     * This constructor will create an instance that is the default size variable and initialize it to the value "0".
     * This constructor assumes that the variable is not zero filled.
     */
    public CbaBigInt() {
        super();
        value = 0;
    }


    //---------------------------------------------------------------------------------------------------------------
    // getValue():
    /**
     * The access method for the actual value of this object.
     * 
     * @return The value of this instance
     */
    public long getValue() { 
        return value; 
    }


    //---------------------------------------------------------------------------------------------------------------
    // getMinValue():
    /**
     * The access method for the minimum value of this object.
     * 
     * @return The minimum value allowed for this instance
     */
    public long getMinValue() { return MIN_VALUE; }


    //---------------------------------------------------------------------------------------------------------------
    // getMaxValue():
    /**
     * The access method for the maximum value of this object.
     * 
     * @return The maximum value allowed for this instance
     */
    public long getMaxValue() { return MAX_VALUE; }


    //---------------------------------------------------------------------------------------------------------------
    // assign(long):
    /**
     * Assign a new long value to {@link #value}.  Then, set the field to be dirty.
     * 
     * @param v The value to assign.
     */
    public void assign(long v) {
        value = v;
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String):
    /**
     * Assign a new String value to {@link #value}.  This is done by first converting the String to a {@code long}. 
     * Then, set the field to be dirty.
     * 
     * @param v A String representation of the value to assign.
     */
    @Override
    public void assign(String v) {
        assign(Long.valueOf(v));
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(CbaType):
    /**
     * Assign a new {@link CbaType} value to {@link #value}.  This is done by first converting the {@link CbaType} 
     * to a {@code long}.  Then, set the field to be dirty.
     * 
     * @param v The CBA value to assign.
     */
    public void assign(CbaType v) {
        assign(v.toString());
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(Number):
    /**
     * Assign a new {@link Number} value to {@link #value}.  This is done by first converting the {@link Number} 
     * to a {@code long}.  Then, set the field to be dirty.
     * 
     * @param v The Java numeric value to assign.
     */
    public void assign(Number v) {
        assign(v.longValue());
    }


    //---------------------------------------------------------------------------------------------------------------
    // equals(Object):
    /**
     * Determine equality by comparing the {@link Object} {@code o} to a boxed version of {@link #value}.
     * 
     * @param o The object to which to compare this instance.
     * @return Whether this instance and the object are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (null == o) return false;
        if (this == o) return true;
        if (getClass() != o.getClass()) {
            return false;
        }

        return (((CbaBigInt)o).value == value);
    }


    //---------------------------------------------------------------------------------------------------------------
    // toString():
    /**
     * Convert this value to a string representation, carefully taking care of signed numbers and zero-filled 
     * numbers.
     * 
     * @return A String representation of {@link #value}.
     */
    @Override
    public String toString() {
        String rv = new Long(value).toString();

        if (rv.length() >= getSize() || !isZeroFill()) {
            return rv;
        } else if (value < 0) {
            rv = new Long(-value).toString();
            String wrk = (ZEROS + rv);
            rv = "-" + wrk.substring(wrk.length() - getSize());

            return rv;
        } else {
            String wrk = (ZEROS + rv);
            rv = wrk.substring(wrk.length() - getSize());

            return rv;
        }
    }
}