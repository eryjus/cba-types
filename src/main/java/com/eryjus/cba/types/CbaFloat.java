//===================================================================================================================
// CbaFloat.java -- This class implements a primitive float real number
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
 * A IEEE float implementation of a real number.  
 * <p>
 * NOTE: this is an inexact data type, where the value is estimated.  If precision is required (say for finance), 
 * use {@link CbaDecimal}.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaFloat extends CbaFloatingPointType {
    public static class Builder extends CbaFloatingPointType.Builder<Builder> {
        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        public CbaFloat build() {
            return new CbaFloat(this);
        }

    }

    //---------------------------------------------------------------------------------------------------------------

    /**
     * The actual value of the element.
     */
    private float value;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The minimum value allowed for this particular object.
     */
    private static final float MIN_VALUE = Float.MIN_VALUE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The maximum value allowed for this particular object.
     */
    private static final float MAX_VALUE = Float.MAX_VALUE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This constructor will create an instance that is a database field and initialize it to the value "0".
     * 
     * @param builder the class from which this instance will be initialized
     */
    public CbaFloat(Builder builder) {
        super(builder);
        value = 0;
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the actual value of this object.
     * 
     * @return The value of this instance
     */
    public float getValue() { 
        return value; 
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the minimum value of this object.
     * 
     * @return The minimum value allowed for this instance
     */
    public float getMinValue() { return MIN_VALUE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the maximum value of this object.
     * 
     * @return The maximum value allowed for this instance
     */
    public float getMaxValue() { return MAX_VALUE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Trim the newly assigned {@link #value} to the number of digits and the number of decimal places
     */
    private void trim() {
        CbaDecimal wrk = new CbaDecimal.Builder().setSize(getSize(), getDecimals()).build();
        wrk.assign(new Float(value).toString());
        assign(wrk.toString());
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new float value to {@link #value}.  Then, {@link #trim()} the value and then set the field to be 
     * dirty.
     * 
     * @param v The value to assign.
     */
    public void assign(float v) {
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
     * Assign a new String value to {@link #value}.  This is done by first converting the String to a {@code float}. 
     * Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param v A String representation of the value to assign.
     */
    public void assign(String v) {
        assign(Float.valueOf(v));
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

        return (((CbaFloat)obj).value == value);
    }


    //---------------------------------------------------------------------------------------------------------------
    // toString():
    /**
     * Convert this value to a string representation, carefully taking care of signed numbers and zero-filled 
     * numbers.
     * 
     * @return A String representation of {@link #value}.
     */
    public String toString() {
        return new Float(value).toString();
    }


    //---------------------------------------------------------------------------------------------------------------
    // toCreateSpec()
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

        return getFieldName() + " FLOAT(" + getSize() + "," + getDecimals() + ")";
    }
}