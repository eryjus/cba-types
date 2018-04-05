//===================================================================================================================
// CbaSmallText.java -- A specialization of the CbaVarchar class limited specifically to 65535 bytes.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is a text field that is limited to 65535 bytes.
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
// class CbaSmallText:
/**
 * A variable width text field that is limited to 65535 bytes.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaSmallText extends CbaVarchar {
    //---------------------------------------------------------------------------------------------------------------
    // static final int DEFAULT_SIZE:
    /**
     * The default size of a CbaSmallText type.
     */
    private static int DEFAULT_SIZE = 65535;


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaSmallText(String, String):
    /**
     * Create a new CbaSmallText that is bound to a table field.
     * 
     * @param tbl The name of the table to which this field is bound.
     * @param fld The name of the field to which this field is bound.
     */
    public CbaSmallText(String tbl, String fieldName) {
        super(tbl, fieldName, DEFAULT_SIZE);
        assign("");
    }
    

    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaSmallText():
    /**
     * Construct a new CbaSmallText with the default size.
     */
    public CbaSmallText() {
        super(DEFAULT_SIZE);
        assign("");
    }


    //---------------------------------------------------------------------------------------------------------------
    // equals(o):
    /**
     * Determine equality by returning the equality of {@link CbaVarchar#value}.  Note that we are not checking 
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

        return (((CbaSmallText)o).toString().equals(toString()));
    }
}