package com.buzzer.sqlbuilder.dto;

public class Column extends BuzzerBean {

    private Boolean autoIncrement;
    private Boolean isNull;
    private String aliasName;
    private String dataType;
    private Object defaultValue;
    private Boolean isUnique;
    private String specification;

    public Column(){}

    public Column(String name){
        this.setName(name);

    }

    public Column(String name,String aliasName){
        this.setName(name);
        this.aliasName=aliasName;
    }

    public Column(String name,String dataType,String specification,Boolean isNull,Object defaultValue) {
        super();
        this.isNull = isNull;
        this.specification=specification;
        this.setName(name);
        this.dataType=dataType;
        this.defaultValue=defaultValue;
    }

    public Column(String name,String dataType, Boolean autoIncrement) {
        super();

        this.autoIncrement = autoIncrement;
        this.setName(name);
        this.dataType=dataType;
    }

    public Boolean getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public Boolean getNull() {
        return isNull;
    }

    public void setNull(Boolean aNull) {
        isNull = aNull;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getUnique() {
        return isUnique;
    }

    public void setUnique(Boolean unique) {
        isUnique = unique;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
