//===================================================================================================================
// CbaIntegerType.java -- This abstract class is a generic integer number.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the abstract implementation of an integer data type.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-29     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import org.apache.logging.log4j.LogManager;

//-------------------------------------------------------------------------------------------------------------------

/**
 * An abstract implementation of an integer as represented in MySQL. 
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaIntegerType extends CbaType {
    static abstract class Builder<T extends CbaType.Builder<T>> extends CbaType.Builder<T> {
        private boolean zeroFill = false;
        private long minVal = 0;
        private long maxVal = 0;
        private int size = 0;

        
        /**
         * Set the display size of the integer builder
         */
        public T setSize(int sz) {
            size = sz;
            return getThis();
        }


        /**
         * Set the zeroFill attribute of the integer builder.
         */
        public T setZeroFill(boolean zf) {
            zeroFill = zf;
            return getThis();
        }

        
        /**
         * set the minimum value for this integer builder.
         */
        public T setMinVal(long val) {
            minVal = val;
            return getThis();
        }


        /**
         * set the maximum value for this integer builder.
         */
        public T setMaxVal(long val) {
            maxVal = val;
            return getThis();
        }
    }


    /**
     * For integers that are zer filled, this is a string of zeros that will be used to fill the left side of the
     * integer.  This string is big enough to cover the largest permitted decimal number.
     */
    private static final String ZEROS = "00000000000000000000000000000000000000000000000000000000000000000";


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default display size for an integer.
     */
    static final int DEFAULT_SIZE = 10;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default value for an integer type
     */
    static final String DEFAULT_VALUE = "0";


    //---------------------------------------------------------------------------------------------------------------

    /**
     * When outputting this integer, zero fill to the left of the most significant digit until {@link #SIZE} digits 
     * have been printed?
     */
    private final boolean ZERO_FILL;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The minimum value allowed for this particular object.
     */
    private final long MIN_VALUE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The maximum value allowed for this particular object.
     */
    private final long MAX_VALUE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The minimum number of printed digits.  This value does not specify the maximum number of printed digits.  Nor
     * does it have any impact on the values that can be stored in the integer.
     */
    private final int SIZE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The actual value of the element.
     */
    private long value;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Construct a new CbaCharType with the indicated size.
     * 
     * @param builder The builder class from which this instance will be built.
     */
    CbaIntegerType(Builder<?> builder) {
        super(builder);
        MIN_VALUE = builder.minVal;
        MAX_VALUE = builder.maxVal;
        SIZE = builder.size;
        ZERO_FILL = builder.zeroFill;
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Zero fill a string representation of the integer to a minimum of {@link #SIZE} digits.  This function does 
     * not check the validity of the integer; it merely prepends zeros to the left to the minimum size.
     * 
     * @param val The integer representation of the integer to fill.
     * @return A zero-filled integer.
     */
    final String fillWithZeros(final String val) {
        boolean isNeg = false;
        String wrk = val;

        if (val.charAt(0) == '-') {
            isNeg = true;
            wrk = wrk.substring(1);
        } else if (wrk.charAt(wrk.length() - 1) == '-') {
            isNeg = true;
            wrk = wrk.substring(0, wrk.length() - 1);
        }

        // If the cleaned up length is good (less any '-' char), return the original value
        if (wrk.length() >= SIZE) return val;

        String rv = ZEROS + val;
        rv = wrk.substring(wrk.length() - SIZE);
        if (isNeg) rv = "-" + rv;
        return rv;
    }



    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new {@link CbaType} value to {@link #value}.  This is done by first converting the {@link CbaType} 
     * to a {@code long}.  Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param val The CBA value to assign.
     */
    public final void assign(CbaType val) {
        assign(val.toString());
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new long value to {@link #value}.  Then, {@link #trim()} the value and then set the field to be 
     * dirty.
     * 
     * @param val The value to assign.
     */
    final public void assign(long val) {
        if (isReadOnly()) {
            LogManager.getLogger(this.getClass()).warn("Unable to assign to a read-only field; ignoring assignment");
            return;
        }

        value = val;
        trim();
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new {@link Number} value to {@link #value}.  This is done by first converting the {@link Number} 
     * to a {@code long}.  Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param v The Java numeric value to assign.
     */
    public final void assign(Number val) {
        assign(val.longValue());
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Convert this value to a string representation, carefully taking care of signed numbers and zero-filled 
     * numbers.
     * 
     * @return A String representation of {@link #value}.
     */
    public String toString() {
        String rv = new Long(getValue()).toString();

        if (rv.length() >= getSize() || !isZeroFill()) {
            return rv;
        } else {
            return fillWithZeros(rv);
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the {@link #ZERO_FILL} attribute.
     * 
     * @return Whether this instance is zero filled.
     */
    final public boolean isZeroFill() { return ZERO_FILL; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the {@link #SIZE} attribute.
     * 
     * @return The minimum display size for this instance.  See also {@link #SIZE}.
     */
    final public int getSize() { return SIZE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the actual value of this object.
     * 
     * @return The value of this instance
     */
    public final long getValue() { 
        return value; 
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the actual value of this object.
     * 
     * @param val The new value of this instance
     */
    public final void setValue(long val) { 
        value = val; 
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the minimum value of this object.
     * 
     * @return The minimum value allowed for this instance
     */
    public long getMinValue() { return MIN_VALUE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the maximum value of this object.
     * 
     * @return The maximum value allowed for this instance
     */
    public long getMaxValue() { return MAX_VALUE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Trim the newly assigned {@link #value} to 8-bits.  This critical function must distinguish between signed and 
     * unsigned numbers and manage the value properly.
     */
    abstract void trim();
}