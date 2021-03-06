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
    static abstract class Builder<T extends Builder<T>> extends CbaRealType.Builder<T> {
        // this is a trivial extension
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This is the default constructor for a fixed point number.
     */    
    CbaFloatingPointType(Builder<?> builder) {
        super(builder);
    }
}