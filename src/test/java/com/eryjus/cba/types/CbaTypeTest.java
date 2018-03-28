package com.eryjus.cba.types;

import static org.junit.Assert.*;
import org.junit.*;


public class CbaTypeTest {
    //---------------------------------------------------------------------------------------------------------------
    // decimalTest0001(): test the basic assign functionality
    @Test
    public void decimalTest0001() {
        CbaDecimal v = new CbaDecimal();

        v.assign(1);
        assertEquals(1, v.longValue());

        v.assign(1.1);
        assertEquals(1.1, v.doubleValue(), 0.0);

        v.assign(0);
        assertEquals(0, v.longValue());

        v.assign(0.0);
        assertEquals(0.0, v.doubleValue(), 0.0);

        v.assign(3.14159);
        assertEquals(3.14159, v.doubleValue(), 0.0);

        v.assign("3.1415926");
        assertEquals(3.14159, v.doubleValue(), 0.0);

        v.assign("1234567890.12345");
        assertEquals("1234567890.12345", v.toString());

        v.assign("91234567890.12345");
        assertEquals("1234567890.12345", v.toString());
    }


    //---------------------------------------------------------------------------------------------------------------
    // decimalTest0002(): Perform some tests with limiting the size
    @Test
    public void decimalTest0002() {
        CbaDecimal test = new CbaDecimal(3, 2);
        test.assign("2.3");
        assertEquals("2.3", test.toString());

        test.assign("22.33");
        assertEquals("2.33", test.toString());

        test.assign("2.3333");
        assertEquals("2.33", test.toString());

        test.assign("22.333");
        assertEquals("2.33", new CbaDecimal(test).toString());
    }
}