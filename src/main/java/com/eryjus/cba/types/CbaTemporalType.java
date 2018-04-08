//===================================================================================================================
// CbaTemporalType.java -- This class is an abstract class for the cba date and time types
// -----------------------------------------------------------------------------------------------------------------
//
// All CBA temporal data types will be derived from this class.  It provides the minimum method
// implementations required for any CBA date and/or time.
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

/**
 * This is the base real number class against which all other types will be defined.  It is intended to define the 
 * minimum requirements for operating on a real number in CBA.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaTemporalType extends CbaType {
    static abstract class Builder<T extends Builder<T>> extends CbaType.Builder<T> {
        // we add no additional fields at this point, so this is a trivial extension
    }
    /**
     * Construct the parent of the CbaTemporalType.
     * 
     * @param builder The class builder
     */
    CbaTemporalType(Builder<?> builder) {
        super(builder);
    }


    /**
     * Is this temporal value equivalent to a ZERO value?
     * 
     * @return Whether or not this date is equivalent to a ZERO value.  Note a zero value is a very valid normal 
     * value in every case.
     */
    abstract public boolean isZero();
}
