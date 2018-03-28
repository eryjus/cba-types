
package com.eryjus.cba.types;

import java.math.BigDecimal;
import java.math.BigInteger;

abstract class CbaIntegerType extends CbaType {
    final static int DEFAULT_SIZE = 15;

    private final int size;

    protected CbaIntegerType() {
        super();
        size = DEFAULT_SIZE;
    }

    @Deprecated
    protected CbaIntegerType(CbaIntegerType it) {
        super(it);
        size = it.size;
    }

    protected CbaIntegerType(int s) {
        super();
        size = s;
    }

    protected CbaIntegerType(String tbl, String fld, int s) {
        super(tbl, fld);
        size = s;
    }


    abstract public void assign(double v);
    abstract public void assign(BigDecimal v);
    abstract public void assign(long v);
    abstract public void assign(BigInteger v);
    abstract public void assign(CbaRealType v);
    abstract public void assign(CbaIntegerType v);
    abstract public void assign(String v);

    abstract public float floatValue();
    abstract public double doubleValue();
    abstract public BigDecimal bigDecimalValue();
    abstract public int intValue();
    abstract public long longValue();
    abstract public BigInteger bigIntegerValue();
    abstract public CbaFloat cbaFloatValue();
    abstract public CbaDouble cbaDoubleValue();
    abstract public CbaTinyInt cbaTinyIntValue();
    abstract public CbaSmallInt cbaSmallIntValue();
    abstract public CbaInt cbaIntValue();
    abstract public CbaLongInt cbaLongIntValue();
}