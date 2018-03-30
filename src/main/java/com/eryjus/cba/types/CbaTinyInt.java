//===================================================================================================================
// CbaTinyInt.java -- This class implements an 8-bit (1-char) integer.
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


//-------------------------------------------------------------------------------------------------------------------
// class CbaTinyInt:
/**
 * An 8-bit implementation of an integer as represented in MySQL.  While the implementation of this class is the 
 * Java primitive {@code long}.  Using {@code long} is required because Java does not support unsigned primitive 
 * integers and I wanted to use a primitive type as much as possible for performance considerations.  So, every time 
 * {@link #value} changes the result will be trimmed to 8-bits.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaTinyInt extends CbaIntegerType {
    //---------------------------------------------------------------------------------------------------------------
    // private static final long MIN_UNSIGNED:
    /**
     * The smallest unsigned integer supported.
     */
    private static final long MIN_UNSIGNED = 0;


    //---------------------------------------------------------------------------------------------------------------
    // private static final long MAX_UNSIGNED:
    /**
     * The largest unsigned integer supported.
     */
    private static final long MAX_UNSIGNED = 255;


    //---------------------------------------------------------------------------------------------------------------
    // private static final long MIN_SIGNED:
    /**
     * The smallest signed integer supported.
     */
    private static final long MIN_SIGNED = -128;


    //---------------------------------------------------------------------------------------------------------------
    // private static final long MAX_SIGNED:
    /**
     * The largest signed integer supported.
     */
    private static final long MAX_SIGNED = 127;


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
    private final long MIN_VALUE;


    //---------------------------------------------------------------------------------------------------------------
    // private long minValue:
    /**
     * The maximum value allowed for this particular object.
     */
    private final long MAX_VALUE;


    //---------------------------------------------------------------------------------------------------------------
    // CbaTinyInt(String, String, int, boolean, boolean):
    /**
     * This constructor will create an instance that is a database field and initialize it to the value "0".
     * 
     * @param tbl The table name to which this field belongs.
     * @param fld The field name to which this field belongs.
     * @param sz The maximum number of display digits, which is only used with zero-filled integers.
     * @param u Is the integer an unsigned integer.
     * @param z Is the integer zero filled.
     */
    public CbaTinyInt(String tbl, String fld, int sz, boolean u, boolean z) {
        super(tbl, fld, sz, u, z);
        value = 0;

        if (u) {
            MIN_VALUE = MIN_UNSIGNED;
            MAX_VALUE = MAX_UNSIGNED;
        } else {
            MIN_VALUE = MIN_SIGNED;
            MAX_VALUE = MAX_SIGNED;
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaTinyInt(int, boolean, boolean):
    /**
     * This constructor will create an instance that is a fixed size variable and initialize it to the value "0".
     * 
     * @param s The maximum number of display digits, which is only used with zero-filled integers.
     * @param u Is the integer an unsigned integer.
     * @param z Is the integer zero filled.
     */
    public CbaTinyInt(int s, boolean u, boolean z) {
        super(s, u, z);
        value = 0;

        if (u) {
            MIN_VALUE = MIN_UNSIGNED;
            MAX_VALUE = MAX_UNSIGNED;
        } else {
            MIN_VALUE = MIN_SIGNED;
            MAX_VALUE = MAX_SIGNED;
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaTinyInt(boolean, boolean):
    /**
     * This constructor will create an instance that is the default size variable and initialize it to the value "0".
     * 
     * @param u Is the integer an unsigned integer.
     * @param z Is the integer zero filled.
     */
    public CbaTinyInt(boolean u, boolean z) {
        super(CbaIntegerType.DEFAULT_SIZE, u, z);
        value = 0;

        if (u) {
            MIN_VALUE = MIN_UNSIGNED;
            MAX_VALUE = MAX_UNSIGNED;
        } else {
            MIN_VALUE = MIN_SIGNED;
            MAX_VALUE = MAX_SIGNED;
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaTinyInt(int):
    /**
     * This constructor will create an instance that is a fixed size variable and initialize it to the value "0".  
     * This constructor assumes that the variable is signed and not zero filled.
     * 
     * @param s The maximum number of display digits, which is only used with zero-filled integers.
     */
    public CbaTinyInt(int s) {
        super(s, false, false);
        value = 0;
        MIN_VALUE = MIN_SIGNED;
        MAX_VALUE = MAX_SIGNED;
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaTinyInt():
    /**
     * This constructor will create an instance that is the default size variable and initialize it to the value "0".
     * This constructor assumes that the variable is signed and not zero filled.
     */
    public CbaTinyInt() {
        super();
        value = 0;
        MIN_VALUE = MIN_SIGNED;
        MAX_VALUE = MAX_SIGNED;
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
     * Trim the newly assigned {@link #value} to 8-bits.  This critical function must distinguish between signed and 
     * unsigned numbers and manage the value properly.
     */
    private void trim() {
        if (isUnsigned()) {
            value &= 0xff;
        } else {
            boolean isNeg = (value < 0 ? true : false);

            if ((value & 0xff) == 0x80) {
                value = 0x80;
            } else {
                if (isNeg) value = -value;
                value &= 0x7f;
                if (isNeg) value = -value;
            }
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

        return (((CbaTinyInt)o).value == value);
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