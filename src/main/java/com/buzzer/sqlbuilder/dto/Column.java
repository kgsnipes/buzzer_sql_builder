package com.buzzer.sqlbuilder.dto;

import sun.jvm.hotspot.debugger.SymbolLookup;

import java.util.List;

public class Column extends BuzzerBean {

    private Boolean isPrimaryKey;
    private Boolean autoIncrement;
    private Boolean isNull;
    private String aliasName;
    private String dataType;
    private Object defaultValue;
    private Boolean isUnique;


    public Column(String name,String dataType,String aliasName,Boolean isPrimaryKey, Boolean autoIncrement, Boolean isNull ) {
        super();
        this.isPrimaryKey = isPrimaryKey;
        this.autoIncrement = autoIncrement;
        this.isNull = isNull;
        this.aliasName = aliasName;
        this.setName(name);
        this.dataType=dataType;
    }


}
