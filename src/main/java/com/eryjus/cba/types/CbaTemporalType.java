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
// class CbaTemporalType:
/**
 * This is the base real number class against which all other types will be defined.  It is intended to define the 
 * minimum requirements for operating on a real number in CBA.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaTemporalType extends CbaType {
    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaTemporalType():
    /**
     * This is the default constructor for a real number.
     */
    protected CbaTemporalType() {
        super();
    }


    //---------------------------------------------------------------------------------------------------------------    
    // abstract equals():
    /**
     * Inherited from the Object class but made abstract in this class, which will force it to be implemented in child 
     * classes.
     * 
     * @param o The object against which to compare.
     * @return Whether this element and the supplied object have the same meaning or content.
     */
    @Override
    public abstract boolean equals(Object o);


    //---------------------------------------------------------------------------------------------------------------
    // abstract assign(String):
    /**
     * Abstract method to update the value of this element
     * 
     * @param v The value to which the value of this element will be updated.
     */
    @Override
    abstract public void assign(String v);
}
