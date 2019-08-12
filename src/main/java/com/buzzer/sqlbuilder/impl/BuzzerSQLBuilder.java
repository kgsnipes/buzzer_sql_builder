package com.buzzer.sqlbuilder.impl;

import com.buzzer.sqlbuilder.SQLBuilder;
import com.buzzer.sqlbuilder.BuzzerDBType;
import com.buzzer.sqlbuilder.dto.Column;


public class BuzzerSQLBuilder implements SQLBuilder {

    private BuzzerDBType dbType;
    private StringBuilder sql=new StringBuilder();
    public BuzzerSQLBuilder() {
        this.dbType=BuzzerDBType.GENERIC;
    }

    public BuzzerSQLBuilder(BuzzerDBType dbType) {
        this.dbType=dbType;
    }

    
    public SQLBuilder createDatabase(String database) {

        this.sql.append(String.format("CREATE DATABASE %s",database));
        return this;
    }

    
    public SQLBuilder dropDatabase(String database) {
        return this;
    }

    
    public SQLBuilder useDatabase(String database) {
        return this;
    }

    
    public SQLBuilder createTable(String tableName) {
        return this;
    }

    
    public SQLBuilder withColumns(Column... columns) {
        return this;
    }

    
    public SQLBuilder withColumn(String columnName, String sqlType, Boolean isNull) {
        return this;
    }

    
    public SQLBuilder withPK(String... columns) {
        return this;
    }

    
    public SQLBuilder beginTransaction() {
        return this;
    }

    
    public SQLBuilder beginTransaction(String mode) {
        return this;
    }

    
    public SQLBuilder setTransaction(String name) {
        return this;
    }

    
    public SQLBuilder endTransaction() {
        return this;
    }

    
    public SQLBuilder commit() {
        return this;
    }

    
    public SQLBuilder rollback() {
        return null;
    }

    
    public SQLBuilder createSavePoint(String savePoint) {
        return null;
    }

    
    public SQLBuilder releaseSavePoint(String savePoint) {
        return null;
    }

    
    public SQLBuilder rollbackToSavePoint(String savePoint) {
        return null;
    }

    
    public SQLBuilder toStringOmitSemiColon() {
        return null;
    }
}
