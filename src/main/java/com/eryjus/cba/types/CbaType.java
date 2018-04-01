//===================================================================================================================
// CbaType.java -- This class is an abstract class for the cba data types
// -----------------------------------------------------------------------------------------------------------------
//
// All CBA data types will be derived from this class, so it will define the minimal interface for a data type 
// class.  It defines the core attributes and required functions to be implemented by derived classes.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-23     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

//-------------------------------------------------------------------------------------------------------------------
// class CbaType:
/**
 * This is the base class against which all other types will be defined.  It is intended to allow for some more fancy 
 * things once we get down to a collection of fields that are stored in a table.  If is also intended to be the basis 
 * for any variables later when we get to a development phase of this application.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaType {
    //---------------------------------------------------------------------------------------------------------------
    // dirty:
    /** 
     * Has this data been changed since it was read//written to disk/initialized? 
     */
    private boolean dirty = false;


    //---------------------------------------------------------------------------------------------------------------
    // tableName:
    /**
     * The persistent database table that this data belongs to.  Will be blank if a variable and not a table field.
     */
    private final String TABLE_NAME;


    //---------------------------------------------------------------------------------------------------------------
    // fieldName:
    /**
     * The persistent database table column that this data belongs to.  Will be blank if a variable and not a table 
     * field.
     */
    private final String FIELD_NAME;


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaType():
    /**
     * This is the default constructor, which will be used only for variables and not table fields.  This constructor 
     * will ensure that the {@link CbaType#TABLE_NAME} and {@link CbaType#FIELD_NAME} fields are blank.
     */
    protected CbaType() {
        TABLE_NAME = "";
        FIELD_NAME = "";
    }


    //---------------------------------------------------------------------------------------------------------------
    // constructor CbaType(String, String):
    /**
     * This is the typical constructor for table columns, saving the table and field name to which this data is 
     * bound.  This constructor will ensure that either both the {@link CbaType#TABLE_NAME} and 
     * {@link CbaType#FIELD_NAME} are set (will not validate against the database) or both are blank.  null values 
     * are converted to blanks.
     *
     * @param tbl The table name where this data element is stored.
     * @param fn The field name where this data element is stored.
     */
    protected CbaType(final String tbl, final String fn) {
        if (null == tbl || null == fn || tbl.trim().isEmpty() || fn.trim().isEmpty()) {
            TABLE_NAME = "";
            FIELD_NAME = "";
        } else {
            TABLE_NAME = tbl;
            FIELD_NAME = fn;    
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    // isVariable():
    /**
     * Determine if this particular data element is a variable and return true if it is.  The determination of a 
     * variable is if the {@link CbaType#TABLE_NAME} or {@link CbaType#FIELD_NAME} are blank.  Since they both are
     * populated or both are blank, it is only necessary to check one, and {@link CbaType#TABLE_NAME} was chosen.
     * 
     * @return Is this element considered to be a variable and not stored in a database column?
     */
    public boolean isVariable() { return TABLE_NAME.isEmpty(); }


    //---------------------------------------------------------------------------------------------------------------
    // isField():
    /**
     * If the instance of this CbaType is not a variable, then it must be a field.  This function simply returns the 
     * logical not of {@link CbaType#isField}.
     * 
     * @return Is this element considered to be a field and stores in a database column?
     */
    public boolean isField() { return !isVariable(); }


    //---------------------------------------------------------------------------------------------------------------
    // isDirty():
    /**
     * {@link CbaType#dirty} access method.
     * 
     * @return Is the element dirty and in need to be written?
     */
    public boolean isDirty() { return dirty; }


    //---------------------------------------------------------------------------------------------------------------
    // clrDirty():
    /**
     * Clears {@link CbaType#dirty} attribute.
     */
    protected void clrDirty() { dirty = false; }


    //---------------------------------------------------------------------------------------------------------------
    // setDirty():
    /**
     * Sets {@link CbaType#dirty} attribute.
     */
    protected void setDirty() { dirty = true; }


    //---------------------------------------------------------------------------------------------------------------    
    // getTableName():
    /**
     * {@link CbaType#TABLE_NAME} access method.
     * 
     * @return The name of the database table where this element should be stored; blank if a variable and not 
     * stored in a database.
     */
    public String getTableName() { return TABLE_NAME; }


    //---------------------------------------------------------------------------------------------------------------    
    // getFieldName():
    /**
     * {@link CbaType#FIELD_NAME} access method.
     * 
     * @return The name of the database column where this element is stored; blank if a variable and not stored
     * in a database.
     */
    public String getFieldName() { return FIELD_NAME; }


    //---------------------------------------------------------------------------------------------------------------    
    // abstract assign(String):
    /**
     * Perform an assignment from a String value.
     * 
     * @param v A String representation of the value to assign to the Cbe Type.
     */
    public abstract void assign(String v);


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
    // abstract toString():
    /**
     * Inherited from the Object class but made abstract in this class, which will force it to be implemented in child 
     * classes.
     * 
     * @return A string representation of the object.
     */
    @Override
    public abstract String toString();
}
