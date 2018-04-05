//===================================================================================================================
// CbaDateTime.java -- This class is an abstract class for the cba date and time combined
// -----------------------------------------------------------------------------------------------------------------
//
// The CbaDate type closely aligns with the MySQL DATETIME data type, maintaining only a date and time in the local 
// time zone.  This type differs from the {@link CbaTimestamp} type in 2 ways: it 1) maintains a date and time to 
// only whole second accuracy, and 2) it is stored and retrieved in the local time zone.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-04-01     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.time.LocalDateTime;;


//-------------------------------------------------------------------------------------------------------------------
// class CbaDateTime:
/**
 * An implementation of the MySQL date and time (DATETIME) field.  This date and time are handled relative to the 
 * local time zone.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaDateTime extends CbaTemporalType {
    //---------------------------------------------------------------------------------------------------------------
    // public static final String ZERO_STRING:
    /**
     * This constant String value is used to indicate an uninitialized date and time .  Note that the date and time 
     * are technically valid.
     */
    private static final String ZERO_STRING = "0000-01-01T00:00:00";


    //---------------------------------------------------------------------------------------------------------------
    // public static final CbaDateTime ZERO:
    /**
     * This constant value is used to indicate an uninitialized date and time.  Note that the date and time is 
     * technically valid.
     */
    public static final CbaDateTime ZERO = initZero();


    //---------------------------------------------------------------------------------------------------------------
    // private LocalDateTime value:
    /**
     * This is the value of the CbaDateTime field.
     */
    private LocalDateTime value;


    //---------------------------------------------------------------------------------------------------------------
    // private initZero():
    /**
     * The Private method specifically to initialize {@link CbaDateTime#ZERO}.
     * 
     * @return A CbaDateTime instance of a zero date and time.
     */
    private static CbaDateTime initZero() {
        CbaDateTime rv = new CbaDateTime();
        rv.value = LocalDateTime.parse(ZERO_STRING);
        return rv;
	}


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaDateTime():
    /**
     * Construct a ZERO date and time.
     */
    CbaDateTime() {
        value = LocalDateTime.parse(ZERO_STRING);
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String)
    /**
     * Assign a new date and time to this field.  Note that the date and time must be in the format 
     * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE_TIME}.
     * 
     * @param v A string representation of the date and time to assign properly formatted.
     */
    public void assign(String v) {
        value = LocalDateTime.parse(v);
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

        return (((CbaDateTime)o).value.equals(value));
    }


    //---------------------------------------------------------------------------------------------------------------
    // toString()
    /**
     * Return a string representation of this date and time in ISO-8601 format.
     * 
     * @return A string representation of the date and time.
     */
    public String toString() {
        return value.toString();
    }
}