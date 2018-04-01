//===================================================================================================================
// CbaRealType.java -- This class is an abstract class for the cba real value data types
// -----------------------------------------------------------------------------------------------------------------
//
// All CBA non-integer numeric data types will be derived from this class.  It provides the minimum method
// implementations required for any CBA real number.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-25     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import org.apache.logging.log4j.LogManager;

//-------------------------------------------------------------------------------------------------------------------
// class CbaRealType:
/**
 * This is the base real number class against which all other types will be defined.  It is intended to define the 
 * minimum requirements for operating on a real number in CBA.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaRealType extends CbaType {
    //---------------------------------------------------------------------------------------------------------------
    // UNRESTRICTED:
    /**
     * This is the value of an unrestricted real number and is used in either {@link CbaRealType#size} and
     * {@link CbaRealType#decimals}.  It is required that both {@link CbaRealType#size} and 
     * {@link CbaRealType#decimals} are set to UNRESTRICTED if either are set to this value.
     */
    protected final static int UNRESTRICTED = -1;


    //---------------------------------------------------------------------------------------------------------------
    // DEFAULT_SIZE:
    /**
     * The default for the total number of digits allowed for a real number.
     */
    protected final static int DEFAULT_SIZE = 15;


    //---------------------------------------------------------------------------------------------------------------
    // DEFAULT_DECIMALS:
    /**
     * The default for the number of digits to the right of the decimal place for a real number.
     */
    protected final static int DEFAULT_DECIMALS = 5;


    //---------------------------------------------------------------------------------------------------------------
    // size:
    /** 
     * The total number of digits allowed for this field/variable instance.  A value less than 0 is unrestricted.  
     * If size is less than 0, {@link CbaRealType#decimals} will also be less than 0.
     */
    private final int SIZE;


    //---------------------------------------------------------------------------------------------------------------
    // decimals:
    /**
     * The number of digits to the right of the decimal point allowed for this field/variable.  A value less than 0 
     * is unrestricted.  If decimals is less than 0, {@link CbaRealType#size} will also be less than 0.
     */
    private final int DECIMALS;


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaRealType():
    /**
     * This is the default constructor for a real number.
     */
    protected CbaRealType() {
        super();
        SIZE = UNRESTRICTED;
        DECIMALS = UNRESTRICTED;
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaRealType(int, int):
    /**
     * For a variable class instance, set it's fixed size.  This constructor will manage the {@link CbaRealType#SIZE}
     * and {@link CbaRealType#DECIMALS} attributes so that both are {@link CbaRealType#UNRESTRICTED} if either s or 
     * d are less than 0.
     * 
     * @param s The requested total number of digits in the number.
     * @param d teh requested number of decimal places to the right of the decimal place.
     */
    protected CbaRealType(int s, int d) {
        super();

        if (s < 0 || d < 0) {
            SIZE = UNRESTRICTED; 
            DECIMALS = UNRESTRICTED;
        } else {
            if (s < d + 1) {
                SIZE = d + 1;
                DECIMALS = d;
            } else {
                SIZE = s;
                DECIMALS = d;
            }
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaRealType(String, String, int, int):
    /**
     * For a field class instance, set it's attributes.
     *
     * @param tbl The table name where this data element is stored.
     * @param fld The field name where this data element is stored.
     * @param s The total number of digits for the database field.
     * @param d The number of decimal places to the right of the decimal point for the database field.
     */
    protected CbaRealType(String tbl, String fld, int s, int d) {
        super(tbl, fld);

        if (s < 0 || d < 0) {
            LogManager.getLogger(CbaRealType.class).warn("Asked for a database field to be UNRESTRICTED, " + 
                    "which is not allowed; assuming (" + DEFAULT_SIZE + "," + DEFAULT_DECIMALS + ")",
                    new Exception("Database field cannot be UNRESTRICTED"));

            SIZE = DEFAULT_SIZE;
            DECIMALS = DEFAULT_DECIMALS;
        } else {
            if (s < d + 1) {
                SIZE = d + 1;
                DECIMALS = d;
            } else {
                SIZE = s;
                DECIMALS = d;
            }
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // getSize():
    /**
     * The {@link CbaRealType#SIZE} access method.  
     * 
     * @return The total number of digits for this element, -1 if unrestricted.
     */
    int getSize() { return SIZE; }


    //---------------------------------------------------------------------------------------------------------------
    // getDecimals():
    /**
     * The decimals access method.
     * 
     * @return The number of digits to the right of the decimal place for this element, -1 if unrestricted.
     */
    int getDecimals() { return DECIMALS; }


    //---------------------------------------------------------------------------------------------------------------
    // abstract assign(String):
    /**
     * Abstract method to update the value of this element
     * 
     * @param v The value to which the value of this element will be updated.
     */
    abstract public void assign(String v);
}
