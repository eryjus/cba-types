
package com.eryjus.cba.types;

abstract class CbaFloatingPointType extends CbaRealType {
    protected CbaFloatingPointType() {
        super();
    }

    @Deprecated
    protected CbaFloatingPointType(CbaFloatingPointType rt) {
        super(rt);
    }

    protected CbaFloatingPointType(int s, int d) {
        super(s, d);
    }

    protected CbaFloatingPointType(String tbl, String fld, int s, int d) {
        super(tbl, fld, s, d);
    }
}