//===================================================================================================================
// CbaBoolean.java -- This class implements a special case of a TinyInt for Boolean values
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class extends a CbaTinyInt to represent a binary value.
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
// class CbaBoolean:
/**
 * A specialization of the {@link CbaTinyInt} class to hold boolean values.  MySQL implements boolean as 
 * {@code TINYINT(1)} and this class is a similar specialization.  Derived from {@link CbaTinyInt}, CbaBoolean sets 
 * up the super class to have 1 display position, unsigned and to not zero-fill the number.  It also defines the 
 * constants {@link #FALSE} and {@link #TRUE}.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaBoolean extends CbaTinyInt {
    //---------------------------------------------------------------------------------------------------------------
    // static final FALSE:
    /**
     * The constant value of FALSE.
     */
    public static final CbaBoolean FALSE = new CbaBoolean(0);


    //---------------------------------------------------------------------------------------------------------------
    // static final TRUE:
    /**
     * The constant value of TRUE.
     */
    public static final CbaBoolean TRUE = new CbaBoolean(1);


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaBoolean()
    /**
     * The default constructor for CbaBoolean.  The constructor ensures that the {@link CbaTinyInt} is initialized
     * as 1 display digit, unsigned, and not zero-filled.
     */
    public CbaBoolean() {
        super(1, true, false);
    }


    //---------------------------------------------------------------------------------------------------------------
    // private constructor CbaBoolean(long)
    /**
     * The private constructor is used to initialize the constant values {@link #FALSE} and {@link #TRUE}.
     * 
     * @param v The initial value to assign.
     */
    private CbaBoolean(long v) {
        super(1, true, false);
        assign(v);
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(long)
    /**
     * Assign a new value to {@link CbaTinyInt#value}.  Since a boolean can only store true and false values, we
     * take an approach from C and C++ where {@code 0} is {@link #FALSE} and any other non-zero value is 
     * {@link #TRUE}.  This function ensures that the only 2 possible values are {@link #FALSE} and {@link #TRUE}.
     * 
     * @param v The new value to assign to the boolean.
     */
    @Override
    public void assign(long v) {
        if (v == 0) super.assign(0);
        else super.assign(1);
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String)
    /**
     * Assign a new value to {@link CbaTinyInt#value}.  Since a boolean can only store true and false values, we
     * take an approach from C and C++ where `0` is {@link #FALSE} and any other non-zero value is {@link #TRUE}.  
     * This function ensures that the only 2 possible values are {@link #FALSE} and {@link #TRUE}.
     * <p>
     * This function is more complicated since we can effectively take any value representation as a string.  
     * {@code "true"} and {@code "false"} are trivial to handle, even in mixed case.  Even numbers such as 
     * {@code "0"} and {@code "1"} are easy to handle.  However, real numbers such as {@code "3.14"} get problematic
     * since they cannot be cleanly converted to an integer.  So, I have to convert those to a double and then
     * extract out the integer part -- at which point the value {@code "0.234"} will evaluate to {@link #FALSE}.
     * <p>
     * Another problematic scenario is to convert the value {@code "Bob"} to a boolean value.  Anything that cannot
     * be properly converted to an integer or a real value will be treated as {@link #FALSE}.
     */
    @Override
    public void assign(String v) {
        if (null == v) {
            super.assign(0);
            return;
        }

        if (v.toLowerCase().equals("true")) {
            super.assign(1);
            return;
        } 
        
        if (v.toLowerCase().equals("false")) {
            super.assign(0);
            return;
        }

        Long val = new Long(0);
        try { 
            val = Long.valueOf(v); 
        } catch (Exception ex) { 
            // -- Issue a warning in the log here because this is likely bad software design
            LogManager.getLogger(this.getClass()).warn("Attempt to convert the value \"" + v + 
                    "\" to a boolean value is problematic and results is loss of fidelity", ex);

            try {
                Double dbl = Double.valueOf(v);
                val = dbl.longValue();
            } catch (Exception ex2) {
                val = new Long(0); 
            }
        }

        if (val == 0) super.assign(0);
        else super.assign(1);
    }


    //---------------------------------------------------------------------------------------------------------------
    // equals(Object)
    /**
     * Determine the equality of this instance against a generic object passed in.  This is particularly easy to 
     * implement since we will only allow CbaBoolean to be compared to CbaBoolean.
     */
    @Override
    public boolean equals(Object o) {
        if (null == o) return false;
        if (this == o) return true;
        if (this.getClass() != o.getClass()) {
            return false;
        }

        return (((CbaTinyInt)o).getValue() == getValue());
    }


    //---------------------------------------------------------------------------------------------------------------
    // toString()
    /**
     * Return a {@link String} representation of this instance in the form {@code "false"} or {@code "true"}.
     */
    @Override
    public String toString() {
        if (getValue() == 0) return "false";
        else return "true";
    }
}