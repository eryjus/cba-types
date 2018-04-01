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


//-------------------------------------------------------------------------------------------------------------------
// class CbaMediumInt:
/**
 * A 24-bit implementation of an integer as represented in MySQL.  While the implementation of this class is the 
 * Java primitive {@code long}.  Using {@code long} is required because Java does not support unsigned primitive 
 * integers and I wanted to use a primitive type as much as possible for performance considerations.  So, every time 
 * {@link #value} changes the result will be trimmed to 24-bits.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaMediumInt extends CbaIntegerType {
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
    private static final long MIN_VALUE = -8388608;


    //---------------------------------------------------------------------------------------------------------------
    // private long minValue:
    /**
     * The maximum value allowed for this particular object.
     */
    private static final long MAX_VALUE = 8388607;


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
    public CbaMediumInt(String tbl, String fld, int sz, boolean z) {
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
    public CbaMediumInt(int s, boolean z) {
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
    public CbaMediumInt(boolean z) {
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
    public CbaMediumInt(int s) {
        super(s, false);
        value = 0;
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaMediumInt():
    /**
     * This constructor will create an instance that is the default size variable and initialize it to the value "0".
     * This constructor assumes that the variable is not zero filled.
     */
    public CbaMediumInt() {
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
    // trim():
    /**
     * Trim the newly assigned {@link #value} to 24-bits.  This critical function must distinguish between signed  
     * and unsigned numbers and manage the value properly.
     */
    private void trim() {
        if ((value & 0xffffff) == 0x800000) {
            value = 0x800000;
        } else {
            boolean isNeg = (value < 0 ? true : false);
            if (isNeg) value = -value;
            value &= 0x7fffff;
            if (isNeg) value = -value;
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(long):
    /**
     * Assign a new long value to {@link #value}.  Then, {@link #trim()} the value and then set the field to be 
     * dirty.
     * 
     * @param v The value to assign.
     */
    public void assign(long v) {
        value = v;
        trim();
        setDirty();
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
    // assign(CbaType):
    /**
     * Assign a new {@link CbaType} value to {@link #value}.  This is done by first converting the {@link CbaType} 
     * to a {@code long}.  Then, {@link #trim()} the value and then set the field to be dirty.
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
     * to a {@code long}.  Then, {@link #trim()} the value and then set the field to be dirty.
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

        return (((CbaMediumInt)o).value == value);
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