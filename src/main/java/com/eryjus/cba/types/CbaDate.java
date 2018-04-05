//===================================================================================================================
// CbaDate.java -- This class is an abstract class for the cba date
// -----------------------------------------------------------------------------------------------------------------
//
// The CbaDate type closely aligns with the MySQL DATE data type, maintaining only a date in the local time zone.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-04-01     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.time.LocalDate;


//-------------------------------------------------------------------------------------------------------------------
// class CbaDate:
/**
 * An implementation of the MySQL date field.  This date is handled relative to the local time zone.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaDate extends CbaTemporalType {
    //---------------------------------------------------------------------------------------------------------------
    // public static final LocalDate ZERO:
    /**
     * This constant value is used to indicate an uninitialized date.  Note that the date is technically valid.
     */
    public static final LocalDate ZERO = LocalDate.of(0, 1, 1);


    //---------------------------------------------------------------------------------------------------------------
    // private LocalDate value:
    /**
     * This is the value of the CbaDate field.
     */
    private LocalDate value;


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaDate():
    /**
     * Construct a ZERO date.
     */
    public CbaDate() {
        value = ZERO;
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String)
    /**
     * Assign a new date to this field.  Note that the date must be in the format 
     * {@link DateTimeFormatter#ISO_LOCAL_DATE}.
     * 
     * @param v A string representation of the date to assign properly formatted.
     */
    public void assign(String v) {
        value = LocalDate.parse(v);
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

        return (((CbaDate)o).value.equals(value));
    }


    //---------------------------------------------------------------------------------------------------------------
    // toString()
    /**
     * Return a string representation of this date in ISO-8601 format.
     * 
     * @return A string representation of the date.
     */
    public String toString() {
        return value.toString();
    }
}