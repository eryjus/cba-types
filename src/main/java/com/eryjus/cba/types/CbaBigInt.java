//===================================================================================================================
// CbaBigInt.java -- This class implements an 64-bit (8-byte) integer.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the concrete implementation of an integer data type.  At its core is Java's class BigInteger, but 
// the overall number of bits used will be managed to 64-bits on each assignment.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-30     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.math.BigInteger;

//-------------------------------------------------------------------------------------------------------------------
// class CbaBigInt:
/**
 * A 64-bit implementation of an integer as represented in MySQL.  While the implementation of this class is the 
 * Java class {@code BigInteger}, using  {@code BigInteger} instead of {@code long} is required because Java does not
 * support unsigned primitive integers.  Every time {@link #value} changes the result will be trimmed to 64-bits.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaBigInt extends CbaIntegerType {
    //---------------------------------------------------------------------------------------------------------------
    // private static final BigInteger MIN_UNSIGNED:
    /**
     * The smallest unsigned integer supported.
     */
    private static final BigInteger MIN_UNSIGNED = BigInteger.ZERO;


    //---------------------------------------------------------------------------------------------------------------
    // private static final BigInteger MAX_UNSIGNED:
    /**
     * The largest unsigned integer supported.
     */
    private static final BigInteger MAX_UNSIGNED = new BigInteger("2").pow(64).subtract(BigInteger.ONE);


    //---------------------------------------------------------------------------------------------------------------
    // private static final BigInteger MIN_SIGNED:
    /**
     * The smallest signed integer supported.
     */
    private static final BigInteger MIN_SIGNED = new BigInteger("2").pow(63).negate();


    //---------------------------------------------------------------------------------------------------------------
    // private static final BigInteger MAX_SIGNED:
    /**
     * The largest signed integer supported.
     */
    private static final BigInteger MAX_SIGNED = new BigInteger("2").pow(63).subtract(BigInteger.ONE).negate();


    //---------------------------------------------------------------------------------------------------------------
    // private BigInteger value:
    /**
     * The actual value of the element.
     */
    private BigInteger value;


    //---------------------------------------------------------------------------------------------------------------
    // private BigInteger minValue:
    /**
     * The minimum value allowed for this particular object.
     */
    private final BigInteger MIN_VALUE;


    //---------------------------------------------------------------------------------------------------------------
    // private BigInteger minValue:
    /**
     * The maximum value allowed for this particular object.
     */
    private final BigInteger MAX_VALUE;

    //---------------------------------------------------------------------------------------------------------------
    // CbaBigInt(String, String, int, boolean, boolean):
    /**
     * This constructor will create an instance that is a database field and initialize it to the value "0".
     * 
     * @param tbl The table name to which this field belongs.
     * @param fld The field name to which this field belongs.
     * @param sz The maximum number of display digits, which is only used with zero-filled integers.
     * @param u Is the integer an unsigned integer.
     * @param z Is the integer zero filled.
     */
    public CbaBigInt(String tbl, String fld, int sz, boolean u, boolean z) {
        super(tbl, fld, sz, u, z);
        value = BigInteger.ZERO;

        if (u) {
            MIN_VALUE = MIN_UNSIGNED;
            MAX_VALUE = MAX_UNSIGNED;
        } else {
            MIN_VALUE = MIN_SIGNED;
            MAX_VALUE = MAX_SIGNED;
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaBigInt(int, boolean, boolean):
    /**
     * This constructor will create an instance that is a fixed size variable and initialize it to the value "0".
     * 
     * @param s The maximum number of display digits, which is only used with zero-filled integers.
     * @param u Is the integer an unsigned integer.
     * @param z Is the integer zero filled.
     */
    public CbaBigInt(int s, boolean u, boolean z) {
        super(s, u, z);
        value = BigInteger.ZERO;

        if (u) {
            MIN_VALUE = MIN_UNSIGNED;
            MAX_VALUE = MAX_UNSIGNED;
        } else {
            MIN_VALUE = MIN_SIGNED;
            MAX_VALUE = MAX_SIGNED;
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaBigInt(boolean, boolean):
    /**
     * This constructor will create an instance that is the default size variable and initialize it to the value "0".
     * 
     * @param u Is the integer an unsigned integer.
     * @param z Is the integer zero filled.
     */
    public CbaBigInt(boolean u, boolean z) {
        super(CbaIntegerType.DEFAULT_SIZE, u, z);
        value = BigInteger.ZERO;

        if (u) {
            MIN_VALUE = MIN_UNSIGNED;
            MAX_VALUE = MAX_UNSIGNED;
        } else {
            MIN_VALUE = MIN_SIGNED;
            MAX_VALUE = MAX_SIGNED;
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaBigInt(int):
    /**
     * This constructor will create an instance that is a fixed size variable and initialize it to the value "0".  
     * This constructor assumes that the variable is signed and not zero filled.
     * 
     * @param s The maximum number of display digits, which is only used with zero-filled integers.
     */
    public CbaBigInt(int s) {
        super(s, false, false);
        value = BigInteger.ZERO;
        MIN_VALUE = MIN_SIGNED;
        MAX_VALUE = MAX_SIGNED;
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaBigInt():
    /**
     * This constructor will create an instance that is the default size variable and initialize it to the value "0".
     * This constructor assumes that the variable is signed and not zero filled.
     */
    public CbaBigInt() {
        super();
        value = BigInteger.ZERO;
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
    public BigInteger getValue() { 
        return new BigInteger(value.toString());
    }


    //---------------------------------------------------------------------------------------------------------------
    // getMinValue():
    /**
     * The access method for the minimum value of this object.
     * 
     * @return The minimum value allowed for this instance
     */
    public BigInteger getMinValue() { return MIN_VALUE; }


    //---------------------------------------------------------------------------------------------------------------
    // getMaxValue():
    /**
     * The access method for the maximum value of this object.
     * 
     * @return The maximum value allowed for this instance
     */
    public BigInteger getMaxValue() { return MAX_VALUE; }


    //---------------------------------------------------------------------------------------------------------------
    // trim():
    /**
     * Trim the newly assigned {@link #value} to 64-bits.  This critical function must distinguish between signed  
     * and unsigned numbers and manage the value properly.
     */
    private void trim() {
        if (isUnsigned()) {
            value = value.and(new BigInteger("0xffffffffffffffff"));
        } else {
            boolean isNeg = (value.compareTo(BigInteger.ZERO) < 0 ? true : false);

            if (value.and(new BigInteger("0xffffffffffffffff")).
                        compareTo(new BigInteger("0x8000000000000000")) == 0) {
                value = new BigInteger("0x8000000000000000");
            } else {
                if (isNeg) value = value.negate();
                value = value.and(new BigInteger("0x7fffffffffffffff"));
                if (isNeg) value = value.negate();
            }
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(BigInteger):
    /**
     * Assign a new BigInteger value to {@link #value}.  Then, {@link #trim()} the value and then set the field to be 
     * dirty.
     * 
     * @param v The value to assign.
     */
    public void assign(BigInteger v) {
        value = v;
        trim();
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String):
    /**
     * Assign a new String value to {@link #value}.  This is done by first converting the String to a 
     * {@code BigInteger}.  Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param v A String representation of the value to assign.
     */
    @Override
    public void assign(String v) {
        assign(new BigInteger(v));
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(CbaType):
    /**
     * Assign a new {@link CbaType} value to {@link #value}.  This is done by first converting the {@link CbaType} 
     * to a {@code BigInteger}.  Then, {@link #trim()} the value and then set the field to be dirty.
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
     * to a {@code BigInteger}.  Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param v The Java numeric value to assign.
     */
    public void assign(Number v) {
        assign(v.toString());
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

        return (((CbaBigInt)o).value.compareTo(value) == 0);
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
        String rv = value.toString();

        if (rv.length() >= getSize() || !isZeroFill()) {
            return rv;
        } else if (value.compareTo(BigInteger.ZERO) < 0) {
            rv = value.negate().toString();
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