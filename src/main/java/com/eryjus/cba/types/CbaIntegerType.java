//===================================================================================================================
// CbaIntegerType.java -- This abstract class is a generic integer number.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the abstract implementation of an integer data type.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-29     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

//-------------------------------------------------------------------------------------------------------------------
// class CbaIntegerType:
/**
 * An abstract implementation of an integer as represented in MySQL. 
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaIntegerType extends CbaType {
    //---------------------------------------------------------------------------------------------------------------
    // static final int DEFAULT_SIZE:
    /**
     * The default display size for an integer.
     */
    final static int DEFAULT_SIZE = 10;


    //---------------------------------------------------------------------------------------------------------------
    // static final String ZEROS:
    /**
     * For integers that are zer filled, this is a string of zeros that will be used to fill the left side of the
     * integer.  This string is big enough to cover the largest permitted decimal number.
     */
    static final String ZEROS = "00000000000000000000000000000000000000000000000000000000000000000";


    //---------------------------------------------------------------------------------------------------------------
    // private final int SIZE:
    /**
     * For integers that are zer filled, this is a string of zeros that will be used to fill the left side of the
     * integer.  This string is big enough to cover the largest permitted decimal number.  If the number of digits
     * to print is more then SIZE or {@link #ZERO_FILL} is false, then SIZE has no effect.
     */
    private final int SIZE;


    //---------------------------------------------------------------------------------------------------------------
    // private final boolean ZERO_FILL:
    /**
     * When outputting this integer, zero fill to the left of the most significant digit until {@link #SIZE} digits 
     * have been printed?
     */
    private final boolean ZERO_FILL;


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaIntegerType():
    /**
     * The default Constructor, not unsigned and not zero filled.
     */
    protected CbaIntegerType() {
        super();
        SIZE = DEFAULT_SIZE;
        ZERO_FILL = false;
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaIntegerType(int, boolean):
    /**
     * Create a variable-like instance with these attributes.  This instance will not be bound to a database field.
     * 
     * @param s The display size when printing this value. See also {@link #SIZE}.
     * @param z Is the instance a zero-filled integer? 
     */
    protected CbaIntegerType(int s, boolean z) {
        super();
        SIZE = s;
        ZERO_FILL = z;
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaIntegerType(String, String, int, boolean):
    /**
     * Create a database field instance with these attributes.  This instance is bound to a database field.
     * 
     * @param tbl The table to which this field is bound.
     * @param fld The field in the table to which this field is bound.
     * @param s The display size when printing this value. See also {@link #SIZE}.
     * @param z Is the instance a zero-filled integer? 
     */
    protected CbaIntegerType(String tbl, String fld, int s, boolean z) {
        super(tbl, fld);
        SIZE = s;
        ZERO_FILL = z;
    }


    //---------------------------------------------------------------------------------------------------------------
    // isZeroFill():
    /**
     * The access method for the {@link #ZERO_FILL} attribute.
     * 
     * @return Whether this instance is zero filled.
     */
    public boolean isZeroFill() { return ZERO_FILL; }


    //---------------------------------------------------------------------------------------------------------------
    // getSize():
    /**
     * The access method for the {@link #SIZE} attribute.
     * 
     * @return The minimum display size for this instance.  See also {@link #SIZE}.
     */
    public int getSize() { return SIZE; }


    //---------------------------------------------------------------------------------------------------------------    
    // abstract assign(String):
    /**
     * Perform an assignment from a String value.
     * 
     * @param v A String representation of the value to assign to the Cbe Type.
     */
    @Override
    abstract public void assign(String v);
}