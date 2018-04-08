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

import org.apache.logging.log4j.LogManager;

//-------------------------------------------------------------------------------------------------------------------

/**
 * An abstraction of a character type in Cba.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaCharType extends CbaType {
    @SuppressWarnings("unchecked")
    public static abstract class Builder<T extends Builder<T>> extends CbaType.Builder<T> {
        int size;

        public T setSize(int sz) { 
            size = sz;
            return (T)this;
        }
    }

    /**
     * The width of the field, be that a fixed width or a variable width.
     */
    private final int SIZE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The actual value of this instance.
     */
    private String value;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Construct a new CbaCharType with the indicated size.
     * 
     * @param builder The builder class to initialize this class
     */
    CbaCharType(Builder<?> builder) {
        super(builder);
        SIZE = builder.size;
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the {@link #SIZE} attribute.
     * 
     * @return The minimum display size for this instance.  See also {@link #SIZE}.
     */
    final public int getSize() { return SIZE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the {@link #value} attribute.
     * 
     * @return The minimum display size for this instance.  See also {@link #SIZE}.
     */
    final public String getValue() { return value; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The access method for the {@link #value} attribute.
     * 
     * @param val The minimum display size for this instance.  See also {@link #SIZE}.
     */
    final public void setValue(String val) { value = val; }

        
    //---------------------------------------------------------------------------------------------------------------    

    /**
     * Convert a CbaVarchar to a string (rather trivial)
     * 
     * @return A trivial return of this value.  Fine value is immutable, this will not cause problems.
     */
    final public String toString() { return value; }


    //---------------------------------------------------------------------------------------------------------------    

    /**
     * Perform an assignment from a String value.
     * 
     * @param val A String representation of the value to assign to the Cbe Type.
     */
    public void assign(String val) {
        if (isReadOnly()) {
            LogManager.getLogger(this.getClass()).warn("Unable to assign to a read-only field; ignoring assignment");
            return;
        }

        if (val.length() > getSize()) {
            setValue(val.substring(0, getSize()));
        } else  {
            setValue(val);
        }
        setDirty();
    }
}