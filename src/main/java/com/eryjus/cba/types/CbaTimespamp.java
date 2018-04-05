//===================================================================================================================
// CbaDateTime.java -- This class is an abstract class for the cba date and time combined, with microsecond accuracy.
// -----------------------------------------------------------------------------------------------------------------
//
// The CbaDate type closely aligns with the MySQL DATETIME data type.  This type differs from the 
// {@link CbaDateTime} type in 2 ways: it 1) maintains a date and time with microsecond accuracy, and 2) it is 
// stored and retrieved in the UTC time zone.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-04-01     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.time.Instant;


//-------------------------------------------------------------------------------------------------------------------
// class CbaDateTime:
/**
 * An implementation of the MySQL date and time (DATETIME) field.  This date and time are handled relative to the 
 * UTC.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaTimestamp extends CbaTemporalType {
    //---------------------------------------------------------------------------------------------------------------
    // public static final String ZERO_STRING:
    /**
     * This constant String value is used to indicate an uninitialized date adn time .  Note that the date and time 
     * are technically valid.
     */
    private static final String ZERO_STRING = "0000-01-01T00:00:00.000000Z";


    //---------------------------------------------------------------------------------------------------------------
    // public static final CbaTimestamp ZERO:
    /**
     * This constant value is used to indicate an uninitialized date and time.  Note that the date and time is
     *  technically valid.
     */
    public static final CbaTimestamp ZERO = initZero();


    //---------------------------------------------------------------------------------------------------------------
    // private Instant value:
    /**
     * This is the value of the CbaTimestamp field.
     */
    private Instant value;


    //---------------------------------------------------------------------------------------------------------------
    // private initZero():
    /**
     * The Private method specifically to initialize {@link CbaTimestamp#ZERO}.
     * 
     * @return A version of CbaTimestamp that is initialized to a 0 timestamp.
     */
	private static CbaTimestamp initZero() {
        CbaTimestamp rv = new CbaTimestamp();
        rv.value = Instant.parse(ZERO_STRING);
        return rv;
	}


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaTimestamp():
    /**
     * Construct a ZERO date and time in UTC.
     */
    CbaTimestamp() {
        value = Instant.parse(ZERO_STRING);;
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String)
    /**
     * Assign a new date and time to this field.  Note that the date and time must be in the format 
     * {@link java.time.format.DateTimeFormatter#ISO_INSTANT}.
     * 
     * @param v A string representation of the date and time to assign properly formatted.
     */
    public void assign(String v) {
        value = Instant.parse(v);
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

        return (((CbaTimestamp)o).value.equals(value));
    }


    //---------------------------------------------------------------------------------------------------------------
    // toString()
    /**
     * Return a string representation of this date and time in ISO-8601 format.
     * 
     * @return A string representation of the date and time.
     */
    public String toString() {
        String rv = value.toString();
        rv = rv.substring(0, rv.length() - 1);
        String nano = "00000" + (value.getNano() / 1000);
        nano = nano.substring(nano.length() - 6);

        return rv + "." + nano + "Z";
    }
}