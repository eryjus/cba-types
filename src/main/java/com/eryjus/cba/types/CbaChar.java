
package com.eryjus.cba.types;

class CbaChar extends CbaCharType {
    public static final int defaultSize = 20;
    private final String value;

    public CbaChar(String tbl, String fieldName, int size) {
        super(tbl, fieldName, size);
        value = "";
    }
    
    public CbaChar(String tbl, String fieldName) {
        super(tbl, fieldName, defaultSize);
        value = "";
    }

    public CbaChar(int size) {
        super("", "", size);
        value = "";
    }

    public CbaChar() {
        super("", "", defaultSize);
        value = "";
    }

    public String toString() { return value; }
    public boolean equals(Object o) { return false; }
}