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
    static abstract class Builder<T extends Builder<T>> extends CbaRealType.Builder<T> {
        // this is a trivial extension
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This is the default constructor for a fixed point number.
     */    
    CbaFixedPointType(Builder<?> builder) {
        super(builder);
    }
}