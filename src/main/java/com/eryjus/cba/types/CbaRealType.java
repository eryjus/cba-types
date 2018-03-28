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

import java.math.BigDecimal;
import java.math.BigInteger;

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
    private final int size;


    //---------------------------------------------------------------------------------------------------------------
    // decimals:
    /**
     * The number of digits to the right of the decimal point allowed for this field/variable.  A value less than 0 
     * is unrestricted.  If decimals is less than 0, {@link CbaRealType#size} will also be less than 0.
     */
    private final int decimals;


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaRealType():
    /**
     * This is the default constructor for a real number.
     */
    protected CbaRealType() {
        super();
        int s = DEFAULT_SIZE;
        int d = DEFAULT_DECIMALS;

        if (s < d + 1) {
            size = d + 1;
            decimals = d;
        } else {
            size = s;
            decimals = d;
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaRealType(CbaRealType):
    /**
     * This is a copy constructor which will be used to transform data types.  This constructor is starting out
     * as deprecated so that I can determine if the need is really there or not.
     *
     * @param rt A CbaRealType
     */
    @Deprecated
    protected CbaRealType(CbaRealType rt) {
        super(rt);
        size = rt.size;
        decimals = rt.decimals;
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaRealType(int, int):
    /**
     * For a variable class instance, set it's fixed size.  This constructor will manage the {@link CbaRealType#size}
     * and {@link CbaRealType#decimals} attributes so that both are {@link CbaRealType#UNRESTRICTED} if either s or 
     * d are less than 0.
     * 
     * @param s The requested total number of digits in the number.
     * @param d teh requested number of decimal places to the right of the decimal place.
     */
    protected CbaRealType(int s, int d) {
        super();

        if (s < 0 || d < 0) {
            size = UNRESTRICTED; 
            decimals = UNRESTRICTED;
        } else {
            if (s < d + 1) {
                size = d + 1;
                decimals = d;
            } else {
                size = s;
                decimals = d;
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

            size = DEFAULT_SIZE;
            decimals = DEFAULT_DECIMALS;
        } else {
            if (s < d + 1) {
                size = d + 1;
                decimals = d;
            } else {
                size = s;
                decimals = d;
            }
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // getSize():
    /**
     * The {@link CbaRealType#size} access method.  
     * 
     * @return The total number of digits for this element, -1 if unrestricted.
     */
    int getSize() { return size; }


    //---------------------------------------------------------------------------------------------------------------
    // getDecimals():
    /**
     * The decimals access method.
     * 
     * @return The number of digits to the right of the decimal place for this element, -1 if unrestricted.
     */
    int getDecimals() { return decimals; }


    //---------------------------------------------------------------------------------------------------------------
    // abstract assign(double):
    /**
     * Abstract method to update the value of this element
     * 
     * @param v The value to which the value of this element will be updated.
     */
    abstract public void assign(double v);


    //---------------------------------------------------------------------------------------------------------------
    // abstract assign(BigDecimal):
    /**
     * Abstract method to update the value of this element
     * 
     * @param v The value to which the value of this element will be updated.
     */
    abstract public void assign(BigDecimal v);


    //---------------------------------------------------------------------------------------------------------------
    // abstract assign(long):
    /**
     * Abstract method to update the value of this element
     * 
     * @param v The value to which the value of this element will be updated.
     */
    abstract public void assign(long v);


    //---------------------------------------------------------------------------------------------------------------
    // abstract assign(BigInteger):
    /**
     * Abstract method to update the value of this element
     * 
     * @param v The value to which the value of this element will be updated.
     */
    abstract public void assign(BigInteger v);


    //---------------------------------------------------------------------------------------------------------------
    // abstract assign(CbaRealType):
    /**
     * Abstract method to update the value of this element
     * 
     * @param v The value to which the value of this element will be updated.
     */
    abstract public void assign(CbaRealType v);


    //---------------------------------------------------------------------------------------------------------------
    // abstract assign(CbaIntegerType):
    /**
     * Abstract method to update the value of this element
     * 
     * @param v The value to which the value of this element will be updated.
     */
    abstract public void assign(CbaIntegerType v);


    //---------------------------------------------------------------------------------------------------------------
    // abstract assign(String):
    /**
     * Abstract method to update the value of this element
     * 
     * @param v The value to which the value of this element will be updated.
     */
    abstract public void assign(String v);


    //---------------------------------------------------------------------------------------------------------------
    // abstract floatValue():
    /**
     * Abstract method to convert this element to a float type.
     * 
     * @return The value of this element as a float value.
     */
    abstract public float floatValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract doubleValue():
    /**
     * Abstract method to convert this element to a double type.
     * 
     * @return The value of this element as a double value.
     */
    abstract public double doubleValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract bigDecimalValue():
    /**
     * Abstract method to convert this element to a BigDecimal type.
     * 
     * @return The value of this element as a BigDecimal value.
     */
    abstract public BigDecimal bigDecimalValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract intValue():
    /**
     * Abstract method to convert this element to a int type, truncating the decimal places.
     * 
     * @return The value of this element as a int value, with the decimal places truncated.
     */
    abstract public int intValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract longValue():
    /**
     * Abstract method to convert this element to a long type, truncating the decimal places.
     * 
     * @return The value of this element as a long value, with the decimal places truncated.
     */
    abstract public long longValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract bigIntegerValue():
    /**
     * Abstract method to convert this element to a BinInteger type, truncating the decimal places.
     * 
     * @return The value of this element as a BinInteger value, with the decimal places truncated.
     */
    abstract public BigInteger bigIntegerValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract cbaFloatValue():
    /**
     * Abstract method to convert this element to a CbaFloat type.
     * 
     * @return The value of this element as a CbaFloat value.
     */
    abstract public CbaFloat cbaFloatValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract cbaDoubleValue():
    /**
     * Abstract method to convert this element to a CbaDouble type.
     * 
     * @return The value of this element as a CbaDouble value.
     */
    abstract public CbaDouble cbaDoubleValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract cbaTinyIntValue():
    /**
     * Abstract method to convert this element to a CbaTinyInt type, truncating the decimal places.
     * 
     * @return The value of this element as a CbaTinyInt value, with the decimal places truncated.
     */
    abstract public CbaTinyInt cbaTinyIntValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract cbaSmallIntValue():
    /**
     * Abstract method to convert this element to a CbaSmallInt type, truncating the decimal places.
     * 
     * @return The value of this element as a CbaSmallInt value, with the decimal places truncated.
     */
    abstract public CbaSmallInt cbaSmallIntValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract cbaIntValue():
    /**
     * Abstract method to convert this element to a CbaInt type, truncating the decimal places.
     * 
     * @return The value of this element as a CbaInt value, with the decimal places truncated.
     */
    abstract public CbaInt cbaIntValue();


    //---------------------------------------------------------------------------------------------------------------
    // abstract cbaLongIntValue():
    /**
     * Abstract method to convert this element to a CbaLongInt type, truncating the decimal places.
     * 
     * @return The value of this element as a CbaLongInt value, with the decimal places truncated.
     */
    abstract public CbaLongInt cbaLongIntValue();
}
