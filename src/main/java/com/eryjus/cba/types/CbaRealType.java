//===================================================================================================================
// CbaRealType.java -- This class is an abstract class for the cba real value data types
// -----------------------------------------------------------------------------------------------------------------
//
// All CBA non-integer numeric data types will be derived from this class.  It provides the minimum method
// implementations required for any CBA real number.
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
 * This is the base real number class against which all other types will be defined.  It is intended to define the 
 * minimum requirements for operating on a real number in CBA.
 * 
 * @author Adam Clark
 * @since v0.1.0
 */
abstract class CbaRealType extends CbaType {
    static abstract class Builder<T extends Builder<T>> extends CbaType.Builder<T> {
        private int size = DEFAULT_SIZE;
        private int decimals = DEFAULT_DECIMALS;


        /**
         * Set the size of this instance
         */
        public T setSize(int sz, int dec) {
            size = sz;
            decimals = dec;
            return getThis();
        }
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This is the value of an unrestricted real number and is used in either {@link CbaRealType#SIZE} and
     * {@link CbaRealType#DECIMALS}.  It is required that both {@link CbaRealType#SIZE} and 
     * {@link CbaRealType#DECIMALS} are set to UNRESTRICTED if either are set to this value.
     */
    protected final static int UNRESTRICTED = -1;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default for the total number of digits allowed for a real number.
     */
    protected final static int DEFAULT_SIZE = 15;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default for the number of digits to the right of the decimal place for a real number.
     */
    protected final static int DEFAULT_DECIMALS = 5;


    //---------------------------------------------------------------------------------------------------------------

    /** 
     * The total number of digits allowed for this field/variable instance.  A value less than 0 is unrestricted.  
     * If size is less than 0, {@link CbaRealType#DECIMALS} will also be less than 0.
     */
    private final int SIZE;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The number of digits to the right of the decimal point allowed for this field/variable.  A value less than 0 
     * is unrestricted.  If decimals is less than 0, {@link CbaRealType#SIZE} will also be less than 0.
     */
    private final int DECIMALS;


    //---------------------------------------------------------------------------------------------------------------

    /**
     * This is the default constructor for a real number.
     */
    CbaRealType(Builder<?> builder) {
        super(builder);
        SIZE = builder.size;
        DECIMALS = builder.decimals ;
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The {@link CbaRealType#SIZE} access method.  
     * 
     * @return The total number of digits for this element, -1 if unrestricted.
     */
    int getSize() { return SIZE; }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The decimals access method.
     * 
     * @return The number of digits to the right of the decimal place for this element, -1 if unrestricted.
     */
    int getDecimals() { return DECIMALS; }


        //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new {@link CbaType} value to {@link #value}.  This is done by first converting the {@link CbaType} 
     * to a {@link String}.  Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param v The CBA value to assign.
     */
    public void assign(CbaType v) {
        assign(v.toString());
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new {@link Number} value to {@link #value}.  This is done by first converting the {@link Number} 
     * to a {@code float}.  Then, {@link #trim()} the value and then set the field to be dirty.
     * 
     * @param v The Java numeric value to assign.
     */
    public void assign(Number v) {
        assign(v.toString());
    }



}
