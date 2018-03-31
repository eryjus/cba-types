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
import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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
    public static final CbaDecimal ZERO = new CbaDecimal(new BigDecimal("0.0"));


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
    // constructor CbaDecimal(CbaRealType):
    /**
     * This is a copy constructor for any {@link CbaRealType} other than {@link CbaDecimal}.  The reason for the 
     * distinction is that we cannot convert a database field into another type (though we can copy it).  So, any
     * database field information is dropped in the copy from another real type.
     * 
     * @param rv The real type element value to copy.
     */
    CbaDecimal(CbaRealType rv) {
        super(rv.getSize(), rv.getDecimals());
        value = rv.bigDecimalValue();
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaDecimal(CbaDecimal):
    /**
     * This is a copy constructor for the CbaDecimal type, which is more specific than the {@link CbaRealType} 
     * class.  This copy constructor will maintain the database table and field bindings when copying the object.
     * 
     * @param rv The object to copy
     */
    CbaDecimal(CbaDecimal rv) {
        super(rv.getTableName(), rv.getFieldName(), rv.getSize(), rv.getDecimals());
        value = rv.bigDecimalValue();
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
    // constructor CbaDecimal(BigDecimal):
    /**
     * This is an internal copy constructor for the value type, for use when managing the value of the object after 
     * manipulating the results to fit within the fixed size.  An object created via this method is expected to be 
     * transient as a working variable.  Therefore the sizes are unrestricted before generating the final result.
     * 
     * @param v The value to copy into this object.
     */
    private CbaDecimal(BigDecimal v) {
        super(UNRESTRICTED, UNRESTRICTED);
        value = v;
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
        final Logger LOGGER = LogManager.getLogger(BigDecimal.class);
        LOGGER.debug("Attempting to edit '" + val + "' into a number " + getSize() + 
                " digits long with " + getDecimals() + " decimal places.");
                
        String v = val;         // make sure we have our own copy.
        BigDecimal rv;

        //-- are we unconstrained?
        if (-1 != getSize()) {
            LOGGER.debug("We need to manage a constrained size: (" + getSize() + ", " + getDecimals() + ")");

            // -- get the starting sizes we are working with
            int sz = v.length();
            int pt = v.indexOf('.');
            int dec;

            if (-1 == pt) dec = 0;
            else {
                dec = sz - pt - 1;
                sz --;
            }

            LOGGER.debug("The source string size: (" + sz + ", " + dec + ")");

            // -- in the case we are working with an integer without a decimal place, add it in
            if (-1 == dec && 0 != getDecimals()) {
                v = v.concat(".0");
                dec ++;
                sz ++;

                LOGGER.debug("The source had no decimal places and we need more than 1, so 1 was added: " + v);
            }

            // -- first we need to manage the number of decimal places
            if (dec > getDecimals()) {
                int diff = dec - getDecimals();
                int newSize = sz - diff;

                v = v.substring(0, newSize + 1);
                dec -= diff;
                sz -= diff;

                LOGGER.debug("The number of decimals was managed, resulting with: " + v);
            }

            // -- now, we need to manage the total digit count
            if (sz > getSize()) {
                v = v.substring(sz - getSize());

                LOGGER.debug("The overall size was managed, resulting with: " + v);
            }
        }

        // -- now, we can create a new value to return
        rv = new BigDecimal(v);

        return rv;
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(long):
    /**
     * Assign a new long value to this element.  This function first converts this new value to a String and then 
     * edits it to fit in this element's size restrictions (if any).  Finally the value is updated with a new 
     * BigDecimal instance.
     * 
     * @param v the new value to assign
     */
    public void assign(long v) { 
        value = editedBigDecimal(new Long(v).toString()); 
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(double):
    /**
     * Assign a new double value to this element.  This function first converts this new value to a String and then 
     * edits it to fit in this element's size restrictions (if any).  Finally the value is updated with a new 
     * BigDecimal instance.
     * 
     * @param v the new value to assign
     */
    public void assign(double v) { 
        value = editedBigDecimal(new Double(v).toString()); 
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(BigInteger):
    /**
     * Assign a new BigInteger value to this element.  This function first converts this new value to a String and 
     * then edits it to fit in this element's size restrictions (if any).  Finally the value is updated with a new 
     * BigDecimal instance.
     * 
     * @param v the new value to assign
     */
    public void assign(BigInteger v) { 
        value = editedBigDecimal(v.toString()); 
        setDirty();
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
    // assign(CbaRealType):
    /**
     * Assign a new CbaRealType value to this element.  This function first converts this new value to a String and 
     * then edits it to fit in this element's size restrictions (if any).  Finally the value is updated with a new 
     * BigDecimal instance.
     * 
     * @param v the new value to assign
     */
    public void assign(CbaRealType v) { 
        value = editedBigDecimal(v.toString()); 
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(BigDecimal):
    /**
     * Assign a new BigDecimal value to this element.  This function first converts this new value to a String and 
     * then edits it to fit in this element's size restrictions (if any).  Finally the value is updated with a new 
     * BigDecimal instance.
     * 
     * @param v the new value to assign
     */
    public void assign(BigDecimal v) { 
        value = editedBigDecimal(v.toString()); 
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // assign(CbaIntegerType):
    /**
     * Assign a new CbaIntegerType value to this element.  This function first converts this new value to a String 
     * and then edits it to fit in this element's size restrictions (if any).  Finally the value is updated with a new 
     * BigDecimal instance.
     * 
     * @param v the new value to assign
     */
    public void assign(CbaIntegerType v) { 
        value = editedBigDecimal(v.toString()); 
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // bigDecimalValue():
    /**
     * Convert this value to a new BigDecimal instance.  Be aware that this function is cautious to not pass the 
     * value by reference and instead creates a new instance to return.  This is to keep the value of this element
     * from being modified outside of the controls of this class.
     * 
     * @return A new instance of the BigDecimal value of this element.
     */
    public BigDecimal bigDecimalValue() { return new BigDecimal(toString()); }


    //---------------------------------------------------------------------------------------------------------------
    // bigIntegerValue():
    /**
     * Convert this value to a new BigInteger instance.
     * 
     * @return A new instance of the BigInteger value of this element.
     */
    public BigInteger bigIntegerValue() { return value.toBigInteger(); }


    //---------------------------------------------------------------------------------------------------------------
    // longValue():
    /**
     * Convert this value to a new long.
     * 
     * @return A long value of this element.
     */
    public long longValue()  { return Integer.valueOf(toString()).longValue(); }


    //---------------------------------------------------------------------------------------------------------------
    // intValue():
    /**
     * Convert this value to a new int.
     * 
     * @return An int value of this element.
     */
    public int intValue() { return (int)longValue(); }


    //---------------------------------------------------------------------------------------------------------------
    // doubleValue():
    /**
     * Convert this value to a new double.
     * 
     * @return A double value of this element.
     */
    public double doubleValue() { return value.doubleValue(); }


    //---------------------------------------------------------------------------------------------------------------
    // floatValue():
    /**
     * Convert this value to a new float.
     * 
     * @return A float value of this element.
     */
    public float floatValue() { return (float)doubleValue(); }


    //---------------------------------------------------------------------------------------------------------------
    // cbaFloatValue():
    /**
     * Convert this value to a new CbaFloat instance.
     * 
     * @return A new instance of the CbaFloat value of this element.
     */
    public CbaFloat cbaFloatValue() { return new CbaFloat(value.toString()); }


    //---------------------------------------------------------------------------------------------------------------
    // cbaDoubleValue():
    /**
     * Convert this value to a new CbaDouble instance.
     * 
     * @return A new instance of the CbaDouble value of this element.
     */
    public CbaDouble cbaDoubleValue() { return new CbaDouble(value.toString()); }


    //---------------------------------------------------------------------------------------------------------------
    // cbaTinyIntValue():
    /**
     * Convert this value to a new CbaTinyInt instance.
     * 
     * @return A new instance of the CbaTinyInt value of this element.
     */
    public CbaTinyInt cbaTinyIntValue() { return new CbaTinyInt((byte)intValue()); }


    //---------------------------------------------------------------------------------------------------------------
    // cbaSmallIntValue():
    /**
     * Convert this value to a new CbaSmallInt instance.
     * 
     * @return A new instance of the CbaSmallInt value of this element.
     */
    public CbaSmallInt cbaSmallIntValue() { return new CbaSmallInt((short)intValue()); }


    //---------------------------------------------------------------------------------------------------------------
    // cbaIntValue():
    /**
     * Convert this value to a new CbaInt instance.
     * 
     * @return A new instance of the CbaInt value of this element.
     */
    public CbaInt cbaIntValue() { return new CbaInt(intValue()); }


    //---------------------------------------------------------------------------------------------------------------
    // cbaBigIntValue():
    /**
     * Convert this value to a new CbaBigInt instance.
     * 
     * @return A new instance of the CbaBigInt value of this element.
     */
    public CbaBigInt cbaBigIntValue() { 
        CbaBigInt rv = new CbaBigInt();
        rv.assign(toString());
        return rv; 
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
    public boolean equals(Object o) { return value.equals(o); }


    //---------------------------------------------------------------------------------------------------------------
    // static add(CbaDecimal, CbaRealType):
    /**
     * Perform addition between a {@link CbaDecimal} and a {@link CbaRealType} and return a new {@link CbaDecimal}
     * instance with the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It 
     * is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an addition operation
     * @param r The right side of an addition operation
     * @return A new {@link CbaDecimal} instance representing the sum of the two operands.
     */
    public static CbaDecimal add(CbaDecimal l, CbaRealType r) {
        return new CbaDecimal(l.value.add(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static add(CbaDecimal, CbaIntegerType):
    /**
     * Perform addition between a {@link CbaDecimal} and a {@link CbaIntegerType} and return a new {@link CbaDecimal}
     * instance with the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It 
     * is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an addition operation
     * @param r The right side of an addition operation
     * @return A new {@link CbaDecimal} instance representing the sum of the two operands.
     */
    public static CbaDecimal add(CbaDecimal l, CbaIntegerType r) {
        return new CbaDecimal(l.value.add(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static add(CbaDecimal, BigDecimal):
    /**
     * Perform addition between a {@link CbaDecimal} and a {@link BigDecimal} and return a new {@link CbaDecimal}
     * instance with the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It 
     * is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an addition operation
     * @param r The right side of an addition operation
     * @return A new {@link CbaDecimal} instance representing the sum of the two operands.
     */
    public static CbaDecimal add(CbaDecimal l, BigDecimal r) {
        return new CbaDecimal(l.value.add(r));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static add(CbaDecimal, BigInteger):
    /**
     * Perform addition between a {@link CbaDecimal} and a {@link BigInteger} and return a new {@link CbaDecimal}
     * instance with the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It 
     * is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an addition operation
     * @param r The right side of an addition operation
     * @return A new {@link CbaDecimal} instance representing the sum of the two operands.
     */
    public static CbaDecimal add(CbaDecimal l, BigInteger r) {
        return new CbaDecimal(l.value.add(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static add(CbaDecimal, long):
    /**
     * Perform addition between a {@link CbaDecimal} and a long and return a new {@link CbaDecimal} instance with 
     * the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It is up to the 
     * calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an addition operation
     * @param r The right side of an addition operation
     * @return A new {@link CbaDecimal} instance representing the sum of the two operands.
     */
    public static CbaDecimal add(CbaDecimal l, long r) {
        return new CbaDecimal(l.value.add(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static add(CbaDecimal, double):
    /**
     * Perform addition between a {@link CbaDecimal} and a double and return a new {@link CbaDecimal} instance with 
     * the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It is up to the 
     * calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an addition operation
     * @param r The right side of an addition operation
     * @return A new {@link CbaDecimal} instance representing the sum of the two operands.
     */
    public static CbaDecimal add(CbaDecimal l, double r) {
        return new CbaDecimal(l.value.add(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static subtract(CbaDecimal, CbaRealType):
    /**
     * Perform subtraction between a {@link CbaDecimal} and a {@link CbaRealType} and return a new {@link CbaDecimal}
     * instance with the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It 
     * is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an subtraction operation
     * @param r The right side of an subtraction operation
     * @return A new {@link CbaDecimal} instance representing the difference between the two operands.
     */
    public static CbaDecimal subtract(CbaDecimal l, CbaRealType r) {
        return new CbaDecimal(l.value.subtract(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static subtract(CbaDecimal, CbaIntegerType):
    /**
     * Perform subtraction between a {@link CbaDecimal} and a {@link CbaIntegerType} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an subtraction operation
     * @param r The right side of an subtraction operation
     * @return A new {@link CbaDecimal} instance representing the difference between the two operands.
     */
    public static CbaDecimal subtract(CbaDecimal l, CbaIntegerType r) {
        return new CbaDecimal(l.value.subtract(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static subtract(CbaDecimal, BigDecimal):
    /**
     * Perform subtraction between a {@link CbaDecimal} and a {@link BigDecimal} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an subtraction operation
     * @param r The right side of an subtraction operation
     * @return A new {@link CbaDecimal} instance representing the difference between the two operands.
     */
    public static CbaDecimal subtract(CbaDecimal l, BigDecimal r) {
        return new CbaDecimal(l.value.subtract(r));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static subtract(CbaDecimal, BigInteger):
    /**
     * Perform subtraction between a {@link CbaDecimal} and a {@link BigInteger} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an subtraction operation
     * @param r The right side of an subtraction operation
     * @return A new {@link CbaDecimal} instance representing the difference between the two operands.
     */
    public static CbaDecimal subtract(CbaDecimal l, BigInteger r) {
        return new CbaDecimal(l.value.subtract(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static subtract(CbaDecimal, long):
    /**
     * Perform subtraction between a {@link CbaDecimal} and a long and return a new {@link CbaDecimal} instance 
     * with the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It is up to 
     * the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an subtraction operation
     * @param r The right side of an subtraction operation
     * @return A new {@link CbaDecimal} instance representing the difference between the two operands.
     */
    public static CbaDecimal subtract(CbaDecimal l, long r) {
        return new CbaDecimal(l.value.subtract(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static subtract(CbaDecimal, double):
    /**
     * Perform subtraction between a {@link CbaDecimal} and a double and return a new {@link CbaDecimal} instance 
     * with the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It is up to 
     * the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an subtraction operation
     * @param r The right side of an subtraction operation
     * @return A new {@link CbaDecimal} instance representing the difference between the two operands.
     */
    public static CbaDecimal subtract(CbaDecimal l, double r) {
        return new CbaDecimal(l.value.subtract(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static multiply(CbaDecimal, CbaRealType):
    /**
     * Perform multiplication between a {@link CbaDecimal} and a {@link CbaRealType} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an multiplication operation
     * @param r The right side of an multiplication operation
     * @return A new {@link CbaDecimal} instance representing the product of the two operands.
     */
    public static CbaDecimal multiply(CbaDecimal l, CbaRealType r) {
        return new CbaDecimal(l.value.multiply(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static multiply(CbaDecimal, CbaIntegerType):
    /**
     * Perform multiplication between a {@link CbaDecimal} and a {@link CbaIntegerType} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an multiplication operation
     * @param r The right side of an multiplication operation
     * @return A new {@link CbaDecimal} instance representing the product of the two operands.
     */
    public static CbaDecimal multiply(CbaDecimal l, CbaIntegerType r) {
        return new CbaDecimal(l.value.multiply(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static multiply(CbaDecimal, BigDecimal):
    /**
     * Perform multiplication between a {@link CbaDecimal} and a {@link BigDecimal} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an multiplication operation
     * @param r The right side of an multiplication operation
     * @return A new {@link CbaDecimal} instance representing the product of the two operands.
     */
    public static CbaDecimal multiply(CbaDecimal l, BigDecimal r) {
        return new CbaDecimal(l.value.multiply(r));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static multiply(CbaDecimal, BigInteger):
    /**
     * Perform multiplication between a {@link CbaDecimal} and a {@link BigInteger} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an multiplication operation
     * @param r The right side of an multiplication operation
     * @return A new {@link CbaDecimal} instance representing the product of the two operands.
     */
    public static CbaDecimal multiply(CbaDecimal l, BigInteger r) {
        return new CbaDecimal(l.value.multiply(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static multiply(CbaDecimal, long):
    /**
     * Perform multiplication between a {@link CbaDecimal} and a long and return a new {@link CbaDecimal} instance 
     * with the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It is up to 
     * the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an multiplication operation
     * @param r The right side of an multiplication operation
     * @return A new {@link CbaDecimal} instance representing the product of the two operands.
     */
    public static CbaDecimal multiply(CbaDecimal l, long r) {
        return new CbaDecimal(l.value.multiply(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static multiply(CbaDecimal, double):
    /**
     * Perform multiplication between a {@link CbaDecimal} and a double and return a new {@link CbaDecimal} instance 
     * with the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It is up to 
     * the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an multiplication operation
     * @param r The right side of an multiplication operation
     * @return A new {@link CbaDecimal} instance representing the product of the two operands.
     */
    public static CbaDecimal multiply(CbaDecimal l, double r) {
        return new CbaDecimal(l.value.multiply(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divide(CbaDecimal, CbaRealType):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link CbaRealType} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the ratio between the two operands.
     */
    public static CbaDecimal divide(CbaDecimal l, CbaRealType r) {
        return new CbaDecimal(l.value.divide(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divide(CbaDecimal, CbaIntegerType):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link CbaIntegerType} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the ratio between the two operands.
     */
    public static CbaDecimal divide(CbaDecimal l, CbaIntegerType r) {
        return new CbaDecimal(l.value.divide(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divide(CbaDecimal, BigDecimal):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link BigDecimal} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the ratio between the two operands.
     */
    public static CbaDecimal divide(CbaDecimal l, BigDecimal r) {
        return new CbaDecimal(l.value.divide(r));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divide(CbaDecimal, BigInteger):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link BigInteger} and return a new 
     * {@link CbaDecimal} instance with the results.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the ratio between the two operands.
     */
    public static CbaDecimal divide(CbaDecimal l, BigInteger r) {
        return new CbaDecimal(l.value.divide(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divide(CbaDecimal, long):
    /**
     * Perform division between a {@link CbaDecimal} and a long and return a new {@link CbaDecimal} instance with 
     * the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It is up to the 
     * calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the ratio between the two operands.
     */
    public static CbaDecimal divide(CbaDecimal l, long r) {
        return new CbaDecimal(l.value.divide(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divide(CbaDecimal, double):
    /**
     * Perform division between a {@link CbaDecimal} and a double and return a new {@link CbaDecimal} instance with 
     * the results.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  It is up to the 
     * calling procedure to assign this to a field/variable as appropriate.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the ratio between the two operands.
     */
    public static CbaDecimal divide(CbaDecimal l, double r) {
        return new CbaDecimal(l.value.divide(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divideToIntegralValue(CbaDecimal, CbaRealType):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link CbaRealType} and return a new {@link CbaDecimal} 
     * instance with the whole number part of the ratio.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * While the return type is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the whole part of the ratio between the two operands.
     */
    public static CbaDecimal divideToIntegralValue(CbaDecimal l, CbaRealType r) {
        return new CbaDecimal(l.value.divideToIntegralValue(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divideToIntegralValue(CbaDecimal, CbaIntegerType):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link CbaIntegerType} and return a new {@link CbaDecimal} 
     * instance with the whole number part of the ratio.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * While the return type is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the whole part of the ratio between the two operands.
     */
    public static CbaDecimal divideToIntegralValue(CbaDecimal l, CbaIntegerType r) {
        return new CbaDecimal(l.value.divideToIntegralValue(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divideToIntegralValue(CbaDecimal, BigDecimal):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link BigDecimal} and return a new {@link CbaDecimal} 
     * instance with the whole number part of the ratio.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * While the return type is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the whole part of the ratio between the two operands.
     */
    public static CbaDecimal divideToIntegralValue(CbaDecimal l, BigDecimal r) {
        return new CbaDecimal(l.value.divideToIntegralValue(r));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divideToIntegralValue(CbaDecimal, BigInteger):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link BigInteger} and return a new {@link CbaDecimal} 
     * instance with the whole number part of the ratio.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * While the return type is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the whole part of the ratio between the two operands.
     */
    public static CbaDecimal divideToIntegralValue(CbaDecimal l, BigInteger r) {
        return new CbaDecimal(l.value.divideToIntegralValue(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divideToIntegralValue(CbaDecimal, long):
    /**
     * Perform division between a {@link CbaDecimal} and a long and return a new {@link CbaDecimal} instance with 
     * the whole number part of the ratio.  The newly created {@link CbaDecimal} instance will have unrestricted 
     * sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.  While the return
     * type is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the whole part of the ratio between the two operands.
     */
    public static CbaDecimal divideToIntegralValue(CbaDecimal l, long r) {
        return new CbaDecimal(l.value.divideToIntegralValue(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static divideToIntegralValue(CbaDecimal, double):
    /**
     * Perform division between a {@link CbaDecimal} and a double and return a new {@link CbaDecimal} instance with 
     * the whole number part of the ratio.  The newly created {@link CbaDecimal} instance will have unrestricted 
     * sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.  While the return
     * type is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the whole part of the ratio between the two operands.
     */
    public static CbaDecimal divideToIntegralValue(CbaDecimal l, double r) {
        return new CbaDecimal(l.value.divideToIntegralValue(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static remainder(CbaDecimal, CbaRealType):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link CbaRealType} and return a new {@link CbaDecimal} 
     * instance with the remainder part of the ratio.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * While the return type is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the remainder after ratio between the two operands.
     */
    public static CbaDecimal remainder(CbaDecimal l, CbaRealType r) {
        return new CbaDecimal(l.value.remainder(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static remainder(CbaDecimal, CbaIntegerType):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link CbaIntegerType} and return a new {@link CbaDecimal} 
     * instance with the remainder part of the ratio.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * While the return type is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the remainder after ratio between the two operands.
     */
    public static CbaDecimal remainder(CbaDecimal l, CbaIntegerType r) {
        return new CbaDecimal(l.value.remainder(new BigDecimal(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static remainder(CbaDecimal, BigDecimal):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link BigDecimal} and return a new {@link CbaDecimal} 
     * instance with the remainder part of the ratio.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * While the return type is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the remainder after ratio between the two operands.
     */
    public static CbaDecimal remainder(CbaDecimal l, BigDecimal r) {
        return new CbaDecimal(l.value.remainder(r));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static remainder(CbaDecimal, BigInteger):
    /**
     * Perform division between a {@link CbaDecimal} and a {@link BigInteger} and return a new {@link CbaDecimal} 
     * instance with the remainder part of the ratio.  The newly created {@link CbaDecimal} instance will have 
     * unrestricted sizes.  It is up to the calling procedure to assign this to a field/variable as appropriate.
     * While the return type is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the remainder after ratio between the two operands.
     */
    public static CbaDecimal remainder(CbaDecimal l, BigInteger r) {
        return new CbaDecimal(l.value.remainder(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static remainder(CbaDecimal, long):
    /**
     * Perform division between a {@link CbaDecimal} and a long and return a new {@link CbaDecimal} instance with 
     * the remainder part of the ratio.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  
     * It is up to the calling procedure to assign this to a field/variable as appropriate.  While the return type 
     * is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the remainder after ratio between the two operands.
     */
    public static CbaDecimal remainder(CbaDecimal l, long r) {
        return new CbaDecimal(l.value.remainder(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static remainder(CbaDecimal, double):
    /**
     * Perform division between a {@link CbaDecimal} and a double and return a new {@link CbaDecimal} instance with 
     * the remainder part of the ratio.  The newly created {@link CbaDecimal} instance will have unrestricted sizes.  
     * It is up to the calling procedure to assign this to a field/variable as appropriate.  While the return type 
     * is {@link CbaDecimal}, the result will be a whole number.
     * 
     * @param l The left side of an division operation
     * @param r The right side of an division operation
     * @return A new {@link CbaDecimal} instance representing the remainder after ratio between the two operands.
     */
    public static CbaDecimal remainder(CbaDecimal l, double r) {
        return new CbaDecimal(l.value.remainder(new BigDecimal(r)));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static pow(CbaDecimal, CbaIntegerType):
    /**
     * Raise the number of the left side to the power represented on the right, which must be a whole number.
     * 
     * @param l The base number to raise to a power
     * @param r The right power to which to raise the number
     * @return A new {@link CbaDecimal} representing the final numeric result.
     */
    public static CbaDecimal pow(CbaDecimal l, CbaIntegerType r) {
        return new CbaDecimal(l.value.pow(Integer.valueOf(r.toString())));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static pow(CbaDecimal, int):
    /**
     * Raise the number of the left side to the power represented on the right, which must be a whole number.
     * 
     * @param l The base number to raise to a power
     * @param r The right power to which to raise the number
     * @return A new {@link CbaDecimal} representing the final numeric result.
     */
    public static CbaDecimal pow(CbaDecimal l, int r) {
        return new CbaDecimal(l.value.pow(r));
    }


    //---------------------------------------------------------------------------------------------------------------
    // static abs(CbaDecimal):
    /**
     * Return the absolute value of the element.
     * 
     * @param o The base number to evaluate
     * @return A new {@link CbaDecimal} representing the absolute value, even if it is already positive.
     */
    public static CbaDecimal abs(CbaDecimal o) {
        return new CbaDecimal(o.value.abs());
    }


    //---------------------------------------------------------------------------------------------------------------
    // static negate(CbaDecimal):
    /**
     * Return the negated value of the element.
     * 
     * @param o The base number to evaluate
     * @return A new {@link CbaDecimal} representing the negated value.
     */
    public static CbaDecimal negate(CbaDecimal o) {
        return new CbaDecimal(o.value.negate());
    }
}