//===================================================================================================================
// CbaCharType.java -- An abstract class for character fields.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is an abstraction of a character field/variable.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-04-04     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;


//-------------------------------------------------------------------------------------------------------------------
// class CbaCharType:
/**
 * An abstraction of a character type in Cba.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaCharType extends CbaType {
    //---------------------------------------------------------------------------------------------------------------
    // final int SIZE:
    /**
     * The width of the field, be that a fixed width or a variable width.
     */
    protected final int SIZE;


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaCharType(int):
    /**
     * Construct a new CbaCharType with the indicated size.
     * 
     * @param sz The fixed or maximum width of the character field.
     */
    public CbaCharType(final int sz) {
        super();
        SIZE = sz;
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaCharType(String, String, int):
    /**
     * Construct a new CbaCharType bound to a table with the indicated size.
     * 
     * @param tbl The name of the table to which this field is bound.
     * @param fld The name of the field to which this field is bound.
     * @param sz The fixed or maximum width of the character field.
     */
    public CbaCharType(final String tbl, final String fld, final int sz) { 
        super(tbl, fld);
        SIZE = sz;
    }


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