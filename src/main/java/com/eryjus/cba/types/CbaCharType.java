

package com.eryjus.cba.types;

abstract class CbaCharType extends CbaType {
    protected final int size;

    public CbaCharType(final String tbl, final String fn, final int sz) { 
        super(tbl, fn);
        size = sz;
    }
}