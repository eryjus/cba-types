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

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;


//-------------------------------------------------------------------------------------------------------------------

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
    public static class Builder extends CbaFloatingPointType.Builder<Builder> {
        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        public CbaDouble build() {
            return new CbaDouble(this);
        }

    }

    //---------------------------------------------------------------------------------------------------------------

    /**
     * The actual value of the element.
     */
    private double value;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The minimum value allowed for this particular object.
     */
    private static final double MIN_VALUE = Double.MIN_VALUE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The maximum value allowed for this particular object.
     */
    private static final double MAX_VALUE = Double.MAX_VALUE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This constructor will create an instance that is a database field and initialize it to the value "0".
     * 
     * @param builder the class from which this instance will be initialized
     */
    public CbaDouble(Builder builder) {
        super(builder);
        value = 0;
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the actual value of this object.
     * 
     * @return The value of this instance
     */
    public double getValue() { 
        return value; 
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the minimum value of this object.
     * 
     * @return The minimum value allowed for this instance
     */
    public double getMinValue() { return MIN_VALUE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the maximum value of this object.
     * 
     * @return The maximum value allowed for this instance
     */
    public double getMaxValue() { return MAX_VALUE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Trim the newly assigned {@link #value} to the number of digits and the number of decimal places
     */
    private void trim() {
        CbaDecimal wrk = new CbaDecimal.Builder().setSize(getSize(), getDecimals()).build();
        wrk.assign(new Double(value).toString());
        assign(wrk.toString());
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new double value to {@link #value}.  Then, {@link #trim()} the value and then set the field to be 
     * dirty.
     * 
     * @param v The value to assign.
     */
    public void assign(double v) {
        if (isReadOnly()) {
            LogManager.getLogger(this.getClass()).warn("Unable to assign to a read-only field; ignoring assignment");
            return;
        }

        value = v;
        trim();
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------

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

    /**
     * Determine equality by comparing the {@link Object} {@code o} to a boxed version of {@link #value}.
     * 
     * @param obj The object to which to compare this instance.
     * @return Whether this instance and the object are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) {
            return false;
        }

        return (((CbaDouble)obj).value == value);
    }


    //---------------------------------------------------------------------------------------------------------------

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

        return getFieldName() + " DOUBLE(" + getSize() + "," + getDecimals() + ")";
    }
}