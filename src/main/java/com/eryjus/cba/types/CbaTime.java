//===================================================================================================================
// CbaTime.java -- This class is an abstract class for the cba time
// -----------------------------------------------------------------------------------------------------------------
//
// The CbaDate type closely aligns with the MySQL TIME data type, maintaining only a time in the local time zone.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-04-01     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.time.LocalTime;


//-------------------------------------------------------------------------------------------------------------------
// class CbaTime:
/**
 * An implementation of the MySQL time field.  This time is handled relative to the local time zone.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaTime extends CbaTemporalType {
    //---------------------------------------------------------------------------------------------------------------
    // public static final LocalTime ZERO:
    /**
     * This constant value is used to indicate midnight, which is also a default initialized value of CbaTime.
     */
    public static final LocalTime ZERO = LocalTime.of(0, 0, 0);
    
    
    //---------------------------------------------------------------------------------------------------------------
    // private LocalTime value:
    /**
     * This is the value of the CbaTime field.
     */
    private LocalTime value;


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaTime():
    /**
     * Construct a ZERO time.
     */
    public CbaTime() {
        value = ZERO;
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String)
    /**
     * Assign a new time to this field.  Note that the time must be in the format 
     * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_TIME}.
     * 
     * @param v A string representation of the time to assign properly formatted.
     */
    public void assign(String v) {
        value = LocalTime.parse(v);
    }


    //---------------------------------------------------------------------------------------------------------------
    // equals(Object)
    /**
     * Test for equality.
     * 
     * @param o The object against which equality will be determined.
     * @return Whether Object o is equal to this instance.
     */
    public boolean equals(Object o) {
        if (null == o) return false;
        if (this == o) return true;
        if (getClass() != o.getClass()) {
            return false;
        }

        return (((CbaTime)o).value.equals(value));
    }


    //---------------------------------------------------------------------------------------------------------------
    // toString()
    /**
     * Return a string representation of this time in ISO-8601 format.
     * 
     * @return A string representation of the time.
     */
    public String toString() {
        return value.toString();
    }
}