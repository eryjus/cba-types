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

import com.eryjus.cba.sql.SqlField;

import org.apache.logging.log4j.LogManager;


//-------------------------------------------------------------------------------------------------------------------

/**
 * This is the base class against which all other types will be defined.  It is intended to allow for some more fancy 
 * things once we get down to a collection of fields that are stored in a table.  If is also intended to be the basis 
 * for any variables later when we get to a development phase of this application.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract public class CbaType implements SqlField {
    /**
     * The actual type implemented in this instance
     */
    enum IndicatedType {
        CBA_CHAR,
        CBA_VARCHAR,
        CBA_TINY_TEXT,
        CBA_SMALL_TEXT,
        CBA_MEDIUM_TEXT,
        CBA_DATE,
        CBA_TIME,
        CBA_DATE_TIME,
        CBA_TIMESTAMP,
        CBA_BOOLEAN,
        CBA_TINY_INT,
        CBA_SMALL_INT,
        CBA_MEDIUM_INT,
        CBA_INT,
        CBA_BIG_INT,
        CBA_FLOAT,
        CBA_DOUBLE,
        CBA_DECIMAL,
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The builder class for initializing a CbaType abstract class
     */
    public abstract static class Builder<T extends Builder<T>> {
        private IndicatedType indicatedType;
        private String table = "";
        private String field = "";
        private UpdateStyle updateStyle = UpdateStyle.PROGRAMMER;
        private boolean notNull = false;
        private String defaultValue = "";


        /**
         * Bind this element to a table and field
         */
        T setIndicatedType(IndicatedType type) {
            indicatedType = type;
            return getThis();
        }


        /**
         * Bind this element to a table and field
         */
        public T setField(String tbl, String fld) {
            table = tbl;
            field = fld;
            return getThis();
        }


        /**
         * Set the update style of this element
         */
        public T setUpdateStyle(UpdateStyle style) {
            updateStyle = style;
            return getThis();
        }


        /**
         * Set whether the field is nullable
         */
        public T setNotNull(boolean nn) {
            notNull = nn;
            return getThis();
        }


        /**
         * set the default value for this instance
         */
        public T setDefaultValue(String dft) {
            defaultValue = dft;
            return getThis();
        }

        public abstract T getThis();
    }

    
    //---------------------------------------------------------------------------------------------------------------

    /**
     * The indicated type of this particular instance.
     */
    private final IndicatedType INDICATED_TYPE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The persistent database table that this data belongs to.  Will be blank if a variable and not a table field.
     */
    private final String TABLE_NAME;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The persistent database table column that this data belongs to.  Will be blank if a variable and not a table 
     * field.
     */
    private final String FIELD_NAME;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Is this field updated by the system and therefore read only? 
     */
    private final UpdateStyle UPDATE_STYLE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Can this field be set to null?
     */
    private final boolean NOT_NULL;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * A String representation of the default value of this instance.
     */
    private final String FIELD_DEFAULT_VALUE;


    //---------------------------------------------------------------------------------------------------------------

    /** 
     * Has this data been changed since it was read//written to disk/initialized?  This is an attribute to track 
     * the state of this instance.
     */
    private boolean dirty = false;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Is this field empty?  This is an attribute to track the state of this instance.
     */
    private boolean emptyContents = true;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Construct this instance, taking all the static instance attributes.  The status attributes are able to be 
     * read and updated through getters and setters.
     * <p>
     * The constructor takes care to make sure that the table and field are not null and are in a consistent 
     * state.
     * 
     * @param builder The builder object for initializing the CbaType class
     */
    CbaType(Builder<?> builder) {
        UPDATE_STYLE = builder.updateStyle;
        INDICATED_TYPE = builder.indicatedType;
        NOT_NULL = builder.notNull;

        if (builder.table == null) {
            LogManager.getLogger(this.getClass()).info("Binding table was null; assuming blank");
        }

        if (builder.field == null) {
            LogManager.getLogger(this.getClass()).info("Binding field was null; assuming blank");
        }

        if (builder.defaultValue == null) {
            LogManager.getLogger(this.getClass()).info("Default Value was null; assuming blank");
        }

        if (null == builder.table || null == builder.field || builder.table.trim().isEmpty() || 
                    builder.field.trim().isEmpty()) {
            if (builder.table != null && builder.field != null && 
                        (!builder.table.trim().isEmpty() || !builder.field.trim().isEmpty())) {
                LogManager.getLogger(this.getClass()).info("Table and field were not consistent; assuming blank");
            }

            TABLE_NAME = "";
            FIELD_NAME = "";
        } else {
            TABLE_NAME = builder.table;
            FIELD_NAME = builder.field;    
        }

        if (builder.defaultValue == null) {
            FIELD_DEFAULT_VALUE = "";
        } else {
            FIELD_DEFAULT_VALUE = builder.defaultValue;
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Clears {@link CbaType#dirty} attribute.  This is a subclass only access method.
     */
    final void clrDirty() { dirty = false; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Sets {@link CbaType#dirty} attribute.  This is a subclass only access method.
     */
    final void setDirty() { dirty = true; }


    //---------------------------------------------------------------------------------------------------------------
    // clrEmpty():
    /**
     * Clears {@link CbaType#emptyContents} attribute.  Making the contents not empty also sets the dirty flag.
     * This method is only visible to subclasses in this class
     */
    final void clrEmpty() { 
        emptyContents = false; 
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------
    // setEmpty():
    /**
     * Sets {@link CbaType#emptyContents} attribute.  Making the contents empty also clears the dirty flag.
     */
    final void setEmpty() { 
        emptyContents = true; 
        clrDirty();
    }


    //---------------------------------------------------------------------------------------------------------------    

    /**
     * Clear a field by either setting its value to its default and if nullable then set the empty field.
     */
    public final void clearField() {
        // this order is critical since assign() will set the dirty flag
        assign(FIELD_DEFAULT_VALUE);
        clrDirty();

        if (isNullable()) {
            setEmpty();
        }
    }


    //---------------------------------------------------------------------------------------------------------------    

    /**
     * {@link CbaType#TABLE_NAME} access method.
     * 
     * @return The name of the database table where this element should be stored; blank if a variable and not 
     * stored in a database.
     */
    public final String getTableName() { return TABLE_NAME; }


    //---------------------------------------------------------------------------------------------------------------    

    /**
     * {@link CbaType#FIELD_NAME} access method.
     * 
     * @return The name of the database column where this element is stored; blank if a variable and not stored
     * in a database.
     */
    public final String getFieldName() { return FIELD_NAME; }


    //---------------------------------------------------------------------------------------------------------------    

    /**
     * {@link CbaType#DEFAULT_VALUE} access method.
     * 
     * @return A String representation of the default value.
     */
    public final String getDefaultValue() { return FIELD_DEFAULT_VALUE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Determine if this particular data element is a variable and return true if it is.  The determination of a 
     * variable is if the {@link CbaType#TABLE_NAME} or {@link CbaType#FIELD_NAME} are blank.  Since they both are
     * populated or both are blank, it is only necessary to check one, and {@link CbaType#TABLE_NAME} was chosen.
     * 
     * @return Is this element considered to be a variable and not stored in a database column?
     */
    public final boolean isVariable() { return TABLE_NAME.isEmpty(); }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * If the instance of this CbaType is not a variable, then it must be a field.  This function simply returns the 
     * logical not of {@link CbaType#isField}.
     * 
     * @return Is this element considered to be a field and stores in a database column?
     */
    public final boolean isField() { return !isVariable(); }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * {@link CbaType#dirty} access method.
     * 
     * @return Is the element dirty and in need to be written?
     */
    public final boolean isDirty() { return dirty; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * {@link CbaType#NOT_NULL} access method.
     * 
     * @return Can this element or instance be set to null?
     */
    public final boolean isNullable() { return !NOT_NULL; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * {@link CbaType#emptyContents} access method.
     * 
     * @return Is the element dirty and in need to be written?
     */
    public final boolean isEmpty() { return emptyContents; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * {@link CbaType#updStyle} access method.
     * 
     * @return Is the element system managed and therefore ready only and in need to be written?
     */
    public final boolean isReadOnly() { return (UPDATE_STYLE != UpdateStyle.PROGRAMMER); }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * {@link CbaType#updStyle} access method.
     * 
     * @return The current UpdateStyle
     */
    public final UpdateStyle getUpdateStyle() { return UPDATE_STYLE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * {@link CbaType#INDICATED_TYPE} access method, returning a String
     * 
     * @return The String representation of the indicated type of this instance.
     */
    public final String getIndicatedType() { return INDICATED_TYPE.name(); }


    //---------------------------------------------------------------------------------------------------------------    

    /**
     * Perform an assignment from a String value.
     * 
     * @param value A String representation of the value to assign to the Cbe Type.
     */
    public abstract void assign(String value);


    //---------------------------------------------------------------------------------------------------------------    
    // abstract equals():
    /**
     * Inherited from the Object class but made abstract in this class, which will force it to be implemented in child 
     * classes.
     * 
     * @param other The object against which to compare.
     * @return Whether this element and the supplied object have the same meaning or content.
     */
    @Override
    public abstract boolean equals(Object other);


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
