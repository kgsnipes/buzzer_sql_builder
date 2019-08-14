package com.buzzer.sqlbuilder.dto;


import java.util.List;

public class Column extends BuzzerBean {

    private Boolean isPrimaryKey;
    private Boolean autoIncrement;
    private Boolean isNull;
    private String aliasName;
    private String dataType;
    private Object defaultValue;
    private Boolean isUnique;

    public Column(){}

    public Column(String name,String aliasName){
        this.setName(name);
        this.aliasName=aliasName;
    }

    public Column(String name,String dataType,Boolean isNull,Object defaultValue) {
        super();
        this.isNull = isNull;
        this.setName(name);
        this.dataType=dataType;
        this.defaultValue=defaultValue;
    }

    public Column(String name,String dataType,Boolean isPrimaryKey, Boolean autoIncrement) {
        super();
        this.isPrimaryKey = isPrimaryKey;
        this.autoIncrement = autoIncrement;
        this.setName(name);
        this.dataType=dataType;
    }


}
