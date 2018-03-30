package com.eryjus.cba.types;

import static org.junit.Assert.*;
import org.junit.*;


public class CbaTypeTest {
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


    @Test
    public void tinyIntTest() {
        CbaTinyInt test = new CbaTinyInt();
        test.assign("1234");
        assertEquals("82", test.toString());

        test = new CbaTinyInt(true, true);
        test.assign("1234");
        assertEquals("0000000210", test.toString());

        test = new CbaTinyInt(false, true);
        test.assign("-1234");
        assertEquals("-0000000082", test.toString());

        test = new CbaTinyInt();
        test.assign(12);
        assertFalse(test.equals(null));
        assertTrue(test.equals(test));

        CbaTinyInt tmp = new CbaTinyInt();
        tmp.assign(12);
        assertTrue(test.equals(tmp));
        tmp.assign(21);
        assertFalse(test.equals(tmp));
    }


    @Test
    public void booleanTest() {
        CbaBoolean test = new CbaBoolean();
        test.assign("1234");
        assertEquals("true", test.toString());

        test.assign("12.34");
        assertEquals("true", test.toString());

        test.assign(CbaBoolean.FALSE);
        assertEquals("false", test.toString());

        test.assign(CbaBoolean.TRUE);
        assertEquals("true", test.toString());

        test = new CbaBoolean();
        test.assign(1);
        assertFalse(test.equals(null));
        assertTrue(test.equals(test));

        CbaBoolean tmp = new CbaBoolean();
        tmp.assign(CbaBoolean.TRUE);
        assertTrue(test.equals(tmp));
        tmp.assign(CbaBoolean.FALSE);
        assertFalse(test.equals(tmp));

        assertFalse(CbaBoolean.TRUE.equals(CbaBoolean.FALSE));
        assertTrue(CbaBoolean.TRUE.equals(CbaBoolean.TRUE));
    }
}