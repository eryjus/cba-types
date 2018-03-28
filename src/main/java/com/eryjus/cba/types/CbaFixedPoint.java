//===================================================================================================================
// CbaFixedPointType.java -- This class is an abstract class for the cba fixed point data types.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// All CBA fixed point data types will be derived from this class.  Currently, there is only CbaDecimal.  However,
// there may be a need for something different like CbaNumeric (not needed now since MySQL implements DECIMAL and 
// NUMERIC exactly the same).  I want to be prepared for this without a ton of refactoring.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-25     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;


//-------------------------------------------------------------------------------------------------------------------
// class CbaFixedPointType:
/**
 * This is the basic fixed point data type for Cba.
 * <p>
 * This class in implemented in case we need to add another fixed point class such as CbaNumeric.  MySQL implements
 * `numeric` data types the same as `decimal`, so there is no need.  However, if we add DB2, there is a difference
 * between packed and unpacked decimals which could cause a new type to be added.  I want to be prepared.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaFixedPointType extends CbaRealType {
    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaFixedPointType():
    /**
     * This is the default constructor for a fixed point number.
     */    
    protected CbaFixedPointType() {
        super();
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaFixedPointType(CbaFixedPointType):
    /**
     * This is a copy constructor which will be used to transform data types.  This constructor is starting out
     * as deprecated so that I can determine if the need is really there or not.
     * 
     * @param rt The element to clone
     */
    @Deprecated
    protected CbaFixedPointType(CbaFloatingPointType rt) {
        super(rt);
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaFixedPointType(int, int):
    /**
     * For a variable class instance, set it's fixed size.
     * 
     * @param s The total number of digits in the CbaDecimal, or {@link CbaRealType#UNRESTRICTED} if unrestricted.
     * @param d The number of digits to the right of the decimal place in the CbaDecimal, or 
     * {@link CbaRealType#UNRESTRICTED} if unrestricted.
     */
    protected CbaFixedPointType(int s, int d) {
        super(s, d);
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaFixedPointType(String, String, int, int):
    /**
     * For a field class instance, set it's attributes.
     * 
     * @param tbl The name of the table holding this value in the database.
     * @param fld THe name of the field holding this value in the database.
     * @param s The total number of digits in the CbaDecimal.
     * @param d The number of digits to the right of the decimal place in the CbaDecimal.
     */
    protected CbaFixedPointType(String tbl, String fld, int s, int d) {
        super(tbl, fld, s, d);
    }
}