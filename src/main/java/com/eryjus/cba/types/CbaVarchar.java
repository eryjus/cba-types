//===================================================================================================================
// CbaVarchar.java -- A concrete class for a variable width character type
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is a variable width character class.
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
// class CbaVarchar:
/**
 * A variable width character class.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
class CbaVarchar extends CbaCharType {
    //---------------------------------------------------------------------------------------------------------------
    // static final int DEFAULT_SIZE:
    /**
     * The default size of a CbaVarchar type.
     */
    private static int DEFAULT_SIZE = 1;


    //---------------------------------------------------------------------------------------------------------------
    // String value:
    /**
     * The actual value of this instance.
     */
    private String value;


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaVarchar(String, String, int):
    /**
     * Create a new CbaVarchar that is bound to a table field.
     * 
     * @param tbl The name of the table to which this field is bound.
     * @param fld The name of the field to which this field is bound.
     * @param sz The maximum width of the character field.
     */
    public CbaVarchar(String tbl, String fieldName, int size) {
        super(tbl, fieldName, size);
        value = "";
    }
    

    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaVarchar(String, String):
    /**
     * Create a new CbaVarchar that is bound to a table field.
     * 
     * @param tbl The name of the table to which this field is bound.
     * @param fld The name of the field to which this field is bound.
     */
    public CbaVarchar(String tbl, String fieldName) {
        super(tbl, fieldName, DEFAULT_SIZE);
        value = "";
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaVarchar(int):
    /**
     * Construct a new CbaVarchar with the indicated size.
     * 
     * @param sz The maximum width of the character field.
     */
    public CbaVarchar(int size) {
        super(size);
        value = "";
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaVarchar():
    /**
     * Construct a new CbaVarchar with the default size.
     */
    public CbaVarchar() {
        super(DEFAULT_SIZE);
        value = "";
    }


    //---------------------------------------------------------------------------------------------------------------    
    // assign(String):
    /**
     * Perform an assignment from a String value.
     * 
     * @param v A String representation of the value to assign to the Cbe Type.
     */
    public void assign(String v) {
        if (v.length() > getSize()) {
            value = v.substring(0, getSize());
        } else  {
            value = v;
        }
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------    
    // toString():
    /**
     * Convert a CbaChar to a string (rather trivial)
     * 
     * @return A trivial return of this value.  Fine value is immutable, this will not cause problems.
     */
    public String toString() { return value; }


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

        return (((CbaVarchar)o).value.equals(value));
    }
}