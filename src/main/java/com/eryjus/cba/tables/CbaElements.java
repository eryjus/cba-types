

package com.eryjus.cba.tables;

import com.eryjus.cba.types.CbaType;
import com.eryjus.cba.types.CbaVarchar;
import com.eryjus.cba.types.CbaTinyText;
import com.eryjus.cba.types.CbaTinyInt;
import com.eryjus.cba.types.CbaInt;
import com.eryjus.cba.types.CbaTimestamp;

import com.eryjus.cba.types.CbaBoolean;


public class CbaElements extends CbaTable {
    private static final String ELEMENTS = "elements";
    private static final CbaType[] FIELDS = {
        new CbaVarchar.Builder().setField(ELEMENTS, "element_name").setSize(64).build(),
        new CbaVarchar.Builder().setField(ELEMENTS, "element_type").setSize(10).build(),
        new CbaTinyText.Builder().setField(ELEMENTS, "element_description").build(),
        new CbaInt.Builder().setField(ELEMENTS, "element_size").setSize(10).build(),
        new CbaTinyInt.Builder().setField(ELEMENTS, "element_decimals").setSize(3).build(),
        new CbaBoolean.Builder().setField(ELEMENTS, "element_unsigned").build(),
        new CbaBoolean.Builder().setField(ELEMENTS, "element_zero_fill").build(),
        new CbaBoolean.Builder().setField(ELEMENTS, "element_auto_increment").build(),
        new CbaVarchar.Builder().setField(ELEMENTS, "element_char_set").setSize(25).build(),
        new CbaVarchar.Builder().setField(ELEMENTS, "element_collate_name").setSize(25).build(),
        new CbaBoolean.Builder().setField(ELEMENTS, "element_not_null").build(),
        new CbaVarchar.Builder().setField(ELEMENTS, "element_short_description").setSize(120).build(),
        new CbaVarchar.Builder().setField(ELEMENTS, "element_default_value").setSize(1000).build(),
        new CbaVarchar.Builder().setField(ELEMENTS, "element_create_id").setSize(25).setUpdateStyle(CbaType.UpdateStyle.INSERT).build(),
        new CbaTimestamp.Builder().setField(ELEMENTS, "element_create_date_time").setUpdateStyle(CbaType.UpdateStyle.INSERT).build(),
        new CbaVarchar.Builder().setField(ELEMENTS, "element_create_system").setSize(120).setUpdateStyle(CbaType.UpdateStyle.INSERT).build(),
        new CbaVarchar.Builder().setField(ELEMENTS, "element_modify_id").setSize(25).setUpdateStyle(CbaType.UpdateStyle.UPDATE).build(),
        new CbaTimestamp.Builder().setField(ELEMENTS, "element_modify_date_time").setUpdateStyle(CbaType.UpdateStyle.UPDATE).build(),
        new CbaVarchar.Builder().setField(ELEMENTS, "element_modify_system").setSize(120).setUpdateStyle(CbaType.UpdateStyle.UPDATE).build()
    };


    public CbaElements() {
        super("cba_metadata", ELEMENTS, FIELDS);
    }

    public void selectAll() {

    }

    public void select() {

    }

    public void fetchNext() {

    }

    public void fetchSingle() {

    }

    public void insert() {

    }

    public void delete() {

    }

    public void update() {
        
    }
    
}