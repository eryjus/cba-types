//===================================================================================================================
// CbaDecimal.java -- This class implements a fixed point decimal number data type.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the concrete implementation of a decimal data type.  At its core is Java's BigDecimal class, 
// which is an attribute called `value`.  However, the hardest working method for this class in the 
// `editedBigDecimal` method.  This method takes managed a string to the limitations defined in this type, both
// in total size and in number of decimal places.
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
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;


//-------------------------------------------------------------------------------------------------------------------
// class CbaDecimal:
/**
 * The concrete class of a MySQL decimal data type for use within cba.  This class will encapsulate the messiness 
 * of interacting with the decimal value making sure it fits with the constraints.
 */
public class CbaDecimal extends CbaFixedPointType {
    //---------------------------------------------------------------------------------------------------------------
    // static final ZERO:
    /**
     * This is a constant value for a ZERO value expressed as a {@link CbaDecimal}.
     */
    public static final CbaDecimal ZERO = new CbaDecimal("0.0");


    //---------------------------------------------------------------------------------------------------------------
    // value:
    /**
     * The actual value of this CbaDecimal.  While the implementation is a core Java class, this class managed its 
     * value within the constraints for this type.
     */
    private BigDecimal value; 


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaDecimal:
    /**
     * The default constructor for a CbaDecimal element, initializing the value to ZERO.
     */
    CbaDecimal() {
        super();
        value = new BigDecimal(0.0);
    }


    //---------------------------------------------------------------------------------------------------------------
    // private constructor CbaDecimal(String):
    /**
     * This is a copy constructor for String value.
     * 
     * @param rv The real type element value to copy.
     */
    private CbaDecimal(String rv) {
        super();
        assign(rv);
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaDecimal(CbaType):
    /**
     * This is a copy constructor for any {@link CbaType}.
     * 
     * @param rv The real type element value to copy.
     */
    CbaDecimal(CbaType rv) {
        super();
        assign(rv.toString());
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaDecimal(int, int):
    /**
     * For a variable class instance, create an object and set it's fixed size.
     * 
     * @param s The total number of digits in the CbaDecimal, or {@link CbaRealType#UNRESTRICTED} if unrestricted.
     * @param d The number of digits to the right of the decimal place in the CbaDecimal, or 
     * {@link CbaRealType#UNRESTRICTED} if unrestricted.
     */
   CbaDecimal(int s, int d) {
        super(s, d);
        value = new BigDecimal(0.0);
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaDecimal(String, String, int, int):
    /**
     * For a field class instance, create an object, bind it to its database table and column name and set it's fixed 
     * size.
     * 
     * @param tbl The name of the table holding this value in the database.
     * @param fld THe name of the field holding this value in the database.
     * @param s The total number of digits in the CbaDecimal, or {@link CbaRealType#UNRESTRICTED} if unrestricted.
     * @param d The number of digits to the right of the decimal place in the CbaDecimal, or 
     * {@link CbaRealType#UNRESTRICTED} if unrestricted.
     */
    CbaDecimal(String tbl, String fld, int s, int d) {
        super(tbl, fld, s, d);
        value = new BigDecimal(0.0);
    }


    //---------------------------------------------------------------------------------------------------------------
    // editedBigDecimal(String):
    /**
     * Edit a string representation of a real number into a BigDecimal that conforms to the size and decimals 
     * characteristics of the current object.  In the event that this object is {@link CbaRealType#UNRESTRICTED}, 
     * nothing is done to manage this value.  However, if the number of decimal places is greater then
     * {@link CbaRealType#size} or the total number of digits overall is greater than {@link CbaRealType#decimals}, 
     * then the value is managed accordingly.  Either decimal places are truncated (not rounded) or the most 
     * significant digits will be removed from the value.  This will be done without throwing an exception.
     * 
     * @param val A string representation of the value to manage to fit in the field 
     * ({@link CbaRealType#size},{@link CbaRealType#decimals}).
     *
     * @return A new copy of a BigDecimal appropriately edited to fit within the constraints of this element.
     */
    private BigDecimal editedBigDecimal(final String val) {
        BigDecimal rv = new BigDecimal(val);

        if (CbaDecimal.UNRESTRICTED != getSize()) {
            rv = rv.remainder(BigDecimal.TEN.pow(getSize() - getDecimals()));
            rv = rv.setScale(getDecimals(), RoundingMode.FLOOR);
        }

        LogManager.getLogger(BigDecimal.class).debug("Trimming value '" + val + 
                    "' to (" + getSize() + "," + getDecimals() + "), resulting in a value of " + rv.toString());

        return rv;
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(String):
    /**
     * Assign a new String value to this element.  This function edits it to fit in this element's size restrictions 
     * (if any).  Finally the value is updated with a new BigDecimal instance.
     * 
     * @param v the new value to assign
     */
    public void assign(String v) { 
        value = editedBigDecimal(v); 
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(CbaType):
    /**
     * Assign a new CbaRealType value to this element.  This function first converts this new value to a String and 
     * then edits it to fit in this element's size restrictions (if any).  Finally the value is updated with a new 
     * BigDecimal instance.
     * 
     * @param v the new value to assign
     */
    public void assign(CbaType v) { 
        value = editedBigDecimal(v.toString()); 
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // toString():
    /**
     * Create a string representation of this element.
     * 
     * @return A new String representation of the element.
     */
    public String toString() { return value.toString(); }


    //---------------------------------------------------------------------------------------------------------------
    // equals(o):
    /**
     * Determine equality by returning the equality of {@link CbaDecimal#value}.  Note that we are not checking 
     * any table name or field name or size constraints.  Omitting these extra comparisons is relevant since the 
     * a database field will be compared to a variable to see if the values are the same, and they may not be the 
     * exact same type.  We still want to be able to determine that equality.
     * 
     * @param o The object against which to evaluate equality.
     * @return Whether the value and the object represent the same thing.
     */
    public boolean equals(Object o) { 
        if (null == o) return false;
        if (this == o) return true;
        if (getClass() != o.getClass()) {
            return false;
        }

        return (((CbaDecimal)o).value.equals(value));
    }
}