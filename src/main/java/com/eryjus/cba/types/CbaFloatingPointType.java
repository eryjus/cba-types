//===================================================================================================================
// CbaFloatingPointType.java -- This class is an abstract class for the cba floating point data types.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// All CBA floating point data types will be derived from this class.  
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-31     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;


//-------------------------------------------------------------------------------------------------------------------
// class CbaFloatingPointType:
/**
 * This is the basic floating point data type for Cba.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaFloatingPointType extends CbaRealType {
    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaFloatingPointType():
    /**
     * This is the default constructor for a floating point number.
     */    
    protected CbaFloatingPointType() {
        super();
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaFloatingPointType(int, int):
    /**
     * For a variable class instance, set it's fixed size.
     * 
     * @param s The total number of digits in the CbaDecimal, or {@link CbaRealType#UNRESTRICTED} if unrestricted.
     * @param d The number of digits to the right of the decimal place in the CbaDecimal, or 
     * {@link CbaRealType#UNRESTRICTED} if unrestricted.
     */
    protected CbaFloatingPointType(int s, int d) {
        super(s, d);
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaFloatingPointType(String, String, int, int):
    /**
     * For a field class instance, set it's attributes.
     * 
     * @param tbl The name of the table holding this value in the database.
     * @param fld THe name of the field holding this value in the database.
     * @param s The total number of digits in the CbaDecimal.
     * @param d The number of digits to the right of the decimal place in the CbaDecimal.
     */
    protected CbaFloatingPointType(String tbl, String fld, int s, int d) {
        super(tbl, fld, s, d);
    }
}