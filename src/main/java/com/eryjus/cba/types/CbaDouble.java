//===================================================================================================================
// CbaDouble.java -- This class implements a primitive double real number
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the concrete implementation of an real data type.  
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-31     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

//-------------------------------------------------------------------------------------------------------------------
// class CbaDouble:
/**
 * A IEEE double implementation of a real number.  
 * <p>
 * NOTE: this is an inexact data type, where the value is estimated.  If precision is required (say for finance), 
 * use {@link CbaDecimal}.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaDouble extends CbaFloatingPointType {
    //---------------------------------------------------------------------------------------------------------------
    // private double value:
    /**
     * The actual value of the element.
     */
    private double value;


    //---------------------------------------------------------------------------------------------------------------
    // private double minValue:
    /**
     * The minimum value allowed for this particular object.
     */
    private static final double MIN_VALUE = Double.MIN_VALUE;


    //---------------------------------------------------------------------------------------------------------------
    // private double minValue:
    /**
     * The maximum value allowed for this particular object.
     */
    private static final double MAX_VALUE = Double.MAX_VALUE;


    //---------------------------------------------------------------------------------------------------------------
    // CbaDouble(String, String, int, int):
    /**
     * This constructor will create an instance that is a database field and initialize it to the value "0".
     * 
     * @param tbl The table name to which this field belongs.
     * @param fld The field name to which this field belongs.
     * @param sz The maximum number of total digits
     * @param dec Tha maximum number of decimals
     */
    public CbaDouble(String tbl, String fld, int sz, int dec) {
        super(tbl, fld, sz, dec);
        value = 0;
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaDouble(int, int):
    /**
     * This constructor will create an instance that is a fixed size variable and initialize it to the value "0".
     * 
     * @param s The maximum number of total digits
     * @param d Tha maximum number of decimals
     */
    public CbaDouble(int s, int d) {
        super(s, d);
        value = 0;
    }


    //---------------------------------------------------------------------------------------------------------------
    // CbaDouble():
    /**
     * This constructor will create an instance that is the default size variable and initialize it to the value "0".
     * This constructor assumes that the variable is not zero filled.
     */
    public CbaDouble() {
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
    public double getValue() { 
        return value; 
    }


    //---------------------------------------------------------------------------------------------------------------
    // getMinValue():
    /**
     * The access method for the minimum value of this object.
     * 
     * @return The minimum value allowed for this instance
     */
    public double getMinValue() { return MIN_VALUE; }


    //---------------------------------------------------------------------------------------------------------------
    // getMaxValue():
    /**
     * The access method for the maximum value of this object.
     * 
     * @return The maximum value allowed for this instance
     */
    public double getMaxValue() { return MAX_VALUE; }


    //---------------------------------------------------------------------------------------------------------------
    // trim():
    /**
     * Trim the newly assigned {@link #value} to the number of digits and the number of decimal places
     */
    private void trim() {
        CbaDecimal wrk = new CbaDecimal(getSize(), getDecimals());
        wrk.assign(new Double(value).toString());
        assign(wrk.toString());
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(double):
    /**
     * Assign a new double value to {@link #value}.  Then, {@link #trim()} the value and then set the field to be 
     * dirty.
     * 
     * @param v The value to assign.
     */
    public void assign(double v) {
        value = v;
        trim();
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String):
    /**
     * Assign a new String value to {@link #value}.  This is done by first converting the String to a {@code double}. 
     * Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param v A String representation of the value to assign.
     */
    @Override
    public void assign(String v) {
        assign(Double.valueOf(v));
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(CbaType):
    /**
     * Assign a new {@link CbaType} value to {@link #value}.  This is done by first converting the {@link CbaType} 
     * to a {@link String}.  Then, {@link #trim()} the value and then set the field to be dirty.
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
     * to a {@code double}.  Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param v The Java numeric value to assign.
     */
    public void assign(Number v) {
        assign(v.doubleValue());
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

        return (((CbaDouble)o).value == value);
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
        return new Double(value).toString();
    }
}