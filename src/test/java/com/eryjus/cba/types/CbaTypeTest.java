package com.eryjus.cba.types;

import static org.junit.Assert.*;
import org.junit.*;

import com.eryjus.cba.tables.*;
/*

public class CbaTypeTest {
    @Test
    public void decimalTest0001() {
        CbaDecimal v = new CbaDecimal();

        v.assign("1");
        assertEquals("1", v.toString());

        v.assign("1.1");
        assertEquals("1.1", v.toString());

        v.assign(CbaDecimal.ZERO);
        assertEquals("0.0", v.toString());

        v.assign("0.0");
        assertEquals("0.0", v.toString());

        v.assign("3.14159");
        assertEquals("3.14159", v.toString());

        v = new CbaDecimal(15, 5);
        v.assign("3.1415926");
        assertEquals("3.14159", v.toString());

        v.assign("1234567890.12345");
        assertEquals("1234567890.12345", v.toString());

        v.assign("91234567890.12345");
        assertEquals("1234567890.12345", v.toString());
    }


    @Test
    public void decimalTest0002() {
        CbaDecimal test = new CbaDecimal(3, 2);
        test.assign("2.3");
        assertEquals("2.30", test.toString());

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

        test = new CbaTinyInt(true);
        test.assign("1234");
        assertEquals("0000000082", test.toString());

        test = new CbaTinyInt(true);
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

    @Test
    public void TemporalTest001() {
        CbaTimestamp ts = new CbaTimestamp();
        System.out.println(ts.toString());

        CbaDate dt = new CbaDate();
        System.out.println(dt.toString());

        CbaDateTime dtm = new CbaDateTime();
        System.out.println(dtm.toString());
    }

    @Test
    public void SQLTest001() throws Exception {
        final CbaElements elem = new CbaElements();

        String sql = elem.toCreateSpec();
        
        System.out.println(sql);
    }
}

*/