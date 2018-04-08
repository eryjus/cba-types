//===================================================================================================================
// CbaDecimal.java -- This class implements a fixed point decimal number data type.
//                         
// -----------------------------------------------------------------------------------------------------------------
//
// This class is the concrete implementation of a decimal data type.  At its core is Java's BigDecimal class, 
// which is an attribute called `value`.  However, the hardest working method for this class in the 
// `editedBigDecimal` method.  This method takes managed a string to the limitations defined in this type, both
// in total size and in number of decimal places.
//
// -----------------------------------------------------------------------------------------------------------------
//
//    Date     Programmer    Version    Comment
// ----------  ----------  -----------  ----------------------------------------------------------------------------
// 2018-03-25     adcl       v0.1.0     Initial version
//
//===================================================================================================================


package com.eryjus.cba.types;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;


//-------------------------------------------------------------------------------------------------------------------
// class CbaDecimal:
/**
 * The concrete class of a MySQL decimal data type for use within cba.  This class will encapsulate the messiness 
 * of interacting with the decimal value making sure it fits with the constraints.
 */
public class CbaDecimal extends CbaFixedPointType {
    public static class Builder extends CbaFixedPointType.Builder<Builder> {
        public CbaDecimal build() {
            return new CbaDecimal(this);
        }

        
        /**
         * Return this in the proper type
         */
        public Builder getThis() { return this; }

        
        CbaDecimal build(String value) {
            CbaDecimal rv = build();
            rv.assign(value);
            return rv;
        }
    }

    //---------------------------------------------------------------------------------------------------------------

    /**
     * This is a constant value for a ZERO value expressed as a {@link CbaDecimal}.
     */
    public static final CbaDecimal ZERO = new CbaDecimal.Builder().build("0.0");


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The actual value of this CbaDecimal.  While the implementation is a core Java class, this class managed its 
     * value within the constraints for this type.
     */
    private BigDecimal value; 


    //---------------------------------------------------------------------------------------------------------------

    /**
     * The default constructor for a CbaDecimal element, initializing the value to ZERO.
     */
    CbaDecimal(Builder builder) {
        super(builder);
        value = new BigDecimal(0.0);
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Edit a string representation of a real number into a BigDecimal that conforms to the size and decimals 
     * characteristics of the current object.  In the event that this object is {@link CbaRealType#UNRESTRICTED}, 
     * nothing is done to manage this value.  However, if the number of decimal places is greater then
     * {@link CbaRealType#SIZE} or the total number of digits overall is greater than {@link CbaRealType#DECIMALS}, 
     * then the value is managed accordingly.  Either decimal places are truncated (not rounded) or the most 
     * significant digits will be removed from the value.  This will be done without throwing an exception.
     * 
     * @param val A string representation of the value to manage to fit in the field 
     * ({@link CbaRealType#SIZE},{@link CbaRealType#DECIMALS}).
     *
     * @return A new copy of a BigDecimal appropriately edited to fit within the constraints of this element.
     */
    private BigDecimal editedBigDecimal(final String val) {
        BigDecimal rv = new BigDecimal(val);

        if (CbaDecimal.UNRESTRICTED != getSize()) {
            rv = rv.remainder(BigDecimal.TEN.pow(getSize() - getDecimals()));
            rv = rv.setScale(getDecimals(), RoundingMode.FLOOR);
        }

        LogManager.getLogger(BigDecimal.class).debug("Trimming value '" + val + 
                    "' to (" + getSize() + "," + getDecimals() + "), resulting in a value of " + rv.toString());

        return rv;
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Assign a new String value to this element.  This function edits it to fit in this element's size restrictions 
     * (if any).  Finally the value is updated with a new BigDecimal instance.
     * 
     * @param v the new value to assign
     */
    public void assign(String v) { 
        if (isReadOnly()) {
            LogManager.getLogger(this.getClass()).warn("Unable to assign to a read-only field; ignoring assignment");
            return;
        }

        value = editedBigDecimal(v); 
        setDirty();
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Create a string representation of this element.
     * 
     * @return A new String representation of the element.
     */
    public String toString() { return value.toString(); }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Determine equality by returning the equality of {@link CbaDecimal#value}.  Note that we are not checking 
     * any table name or field name or size constraints.  Omitting these extra comparisons is relevant since the 
     * a database field will be compared to a variable to see if the values are the same, and they may not be the 
     * exact same type.  We still want to be able to determine that equality.
     * 
     * @param obj The object against which to evaluate equality.
     * @return Whether the value and the object represent the same thing.
     */
    public boolean equals(Object obj) { 
        if (null == obj) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) {
            return false;
        }

        return (((CbaDecimal)obj).value.equals(value));
    }


    //---------------------------------------------------------------------------------------------------------------

    /**
     * Create a spec for the field to be used in a {@code CREATE TABLE} specification, returning the specific clause
     * for this field in the column specifications.
     * 
     * @return The column spec clause for this field.
     * @throws SQLException When the field name is empty since the field must have a name.
     */
    public String toCreateSpec() throws SQLException {
        if (getFieldName().isEmpty()) {
            throw new SQLException("Field name is not set; cannot create a table spec from a variable");
        }

        return getFieldName() + " DECIMAL(" + getSize() + "," + getDecimals() + ")";
    }
}