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

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;


//-------------------------------------------------------------------------------------------------------------------

/**
 * A specialization of the {@link CbaTinyInt} class to hold boolean values.  MySQL implements boolean as 
 * {@code TINYINT(1)} and this class is a similar specialization.  Derived from {@link CbaTinyInt}, CbaBoolean sets 
 * up the super class to have 1 display position, unsigned and to not zero-fill the number.  It also defines the 
 * constants {@link #FALSE} and {@link #TRUE}.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
public class CbaBoolean extends CbaIntegerType {
    /**
     * The builder class for initializing a CbaVarchar element
     */
    public static class Builder extends CbaIntegerType.Builder<Builder> {
        public Builder() {
            setIndicatedType(CbaType.IndicatedType.CBA_BOOLEAN);
            setMinVal(0);
            setMaxVal(1);
        }


        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        /**
         * Build a CbaBoolean from the builder setup
         */
        public CbaBoolean build() {
            return new CbaBoolean(this);
        }


        /**
         * Build a CbaBoolean from the builder setup and assign a default value
         */
        CbaBoolean build(String val) {
            CbaBoolean rv = new CbaBoolean(this);
            rv.assign(val);
            return rv;
        }

    }

    
    //---------------------------------------------------------------------------------------------------------------

    /**
     * The constant value of FALSE.
     */
    public static final CbaBoolean FALSE = new Builder().build("FALSE");


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The constant value of TRUE.
     */
    public static final CbaBoolean TRUE = new Builder().build("TRUE");


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Create a new CbaBoolean that is bound to a table field.
     * 
     * @param builder the builder class to initialize this instance
     */
    private CbaBoolean(Builder builder) {
        super(builder);
        clearField();
    }
    

    //---------------------------------------------------------------------------------------------------------------

    /**
     * Trim a long value to the boolean values of 0 or 1.
     */
    void trim() {
        if (getValue() == 0) setValue(0);
        else setValue(1);
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String)
    /**
     * Assign a new value to {@link CbaTinyInt#value}.  Since a boolean can only store true and false values, we
     * take an approach from C and C++ where `0` is {@link #FALSE} and any other non-zero value is {@link #TRUE}.  
     * This function ensures that the only 2 possible values are {@link #FALSE} and {@link #TRUE}.
     * <p>
     * This function is more complicated since we can effectively take any value representation as a string.  
     * {@code "TRUE"} and {@code "FALSE"} are trivial to handle, even in mixed case.  Even numbers such as 
     * {@code "0"} and {@code "1"} are easy to handle.  However, real numbers such as {@code "3.14"} get problematic
     * since they cannot be cleanly converted to an integer.  So, I have to convert those to a double and then
     * extract out the integer part -- at which point the value {@code "0.234"} will evaluate to {@link #FALSE}.
     * <p>
     * Another problematic scenario is to convert the value {@code "Bob"} to a boolean value.  Anything that cannot
     * be properly converted to an integer or a real value will be treated as {@link #FALSE}.
     */
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

    /**
     * Determine the equality of this instance against a generic object passed in.  This is particularly easy to 
     * implement since we will only allow CbaBoolean to be compared to CbaBoolean.
     */
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (this == obj) return true;
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        return (((CbaBoolean)obj).getValue() == getValue());
    }


    //---------------------------------------------------------------------------------------------------------------
    // toString()
    /**
     * Return a {@link String} representation of this instance in the form {@code "false"} or {@code "true"}.
     */
    public String toString() {
        if (getValue() == 0) return "FALSE";
        else return "TRUE";
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

        return getFieldName() + " BOOLEAN";
    }
}