package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.service.SQLBuilder;
import com.buzzer.sqlbuilder.dto.Column;
import com.buzzer.sqlbuilder.exception.BuzzerSQLBuilderException;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;


public class BuzzerSQLBuilder implements SQLBuilder {


    protected StringBuilder sql=new StringBuilder();



    public SQLBuilder createTable(String tableName)throws BuzzerSQLBuilderException {
        this.validateTableName(tableName);
        this.sql.append(String.format(BuzzerSQLConstants.CREATE_TABLE_START,tableName));
        return this;
    }

    
    public SQLBuilder withColumns(Column... columns)throws BuzzerSQLBuilderException {
        this.validateColumns(columns);
        Arrays.stream(columns).forEach(c->{
            this.sql.append(this.getSQLForColumn(c));
        });
        return this;
    }

    @Override
    public SQLBuilder withColumn(String name, String dataType, String spec, Boolean isNull, Boolean isUnique, Object defaultValue, Boolean isAutoIncrement) throws BuzzerSQLBuilderException {
        Column c=new Column();
        c.setName(name);
        c.setDataType(dataType);
        c.setSpecification(spec);
        c.setUnique(isUnique);
        c.setNull(isNull);
        c.setAutoIncrement(isAutoIncrement);
        this.validateColumn(c);
        this.sql.append(this.getSQLForColumn(c));
        return this;
    }

    private String getSQLForColumn(Column c) {

        StringBuilder columnSql=new StringBuilder();
        columnSql.append(" "+c.getName());
        columnSql.append(" "+c.getDataType());
        if(StringUtils.isNotEmpty(c.getSpecification()))
        {
            columnSql.append(BuzzerSQLConstants.START_BRACKET+c.getSpecification()+BuzzerSQLConstants.END_BRACKET);
        }
        columnSql.append(BuzzerSQLConstants.SPACE);

        if(c.getNull())
        {
            columnSql.append(BuzzerSQLConstants.SPACE+BuzzerSQLConstants.NULL+BuzzerSQLConstants.SPACE);
        }
        else
        {
            columnSql.append(BuzzerSQLConstants.SPACE+BuzzerSQLConstants.NOT_NULL+BuzzerSQLConstants.SPACE);
        }

        if(c.getAutoIncrement())
        {
            columnSql.append(BuzzerSQLConstants.SPACE+BuzzerSQLConstants.AUTO_INCREMENT_FLAG+BuzzerSQLConstants.SPACE);
        }

        if(c.getUnique())
        {
            columnSql.append(BuzzerSQLConstants.SPACE+BuzzerSQLConstants.UNIQUE_FLAG+BuzzerSQLConstants.SPACE);
        }

        columnSql.append(BuzzerSQLConstants.COMMA+BuzzerSQLConstants.NEW_LINE);

        return columnSql.toString();
    }


    public SQLBuilder withColumn(String columnName, String sqlType, String aliasName, Boolean isNotNull, Boolean isAutoIncrement,Boolean isPK) throws BuzzerSQLBuilderException{


        return this;
    }

    
    public SQLBuilder withPK(String... columns) throws BuzzerSQLBuilderException{
        return this;
    }

    
    public SQLBuilder beginTransaction() {
        return this;
    }

    
    public SQLBuilder beginTransaction(String mode)throws BuzzerSQLBuilderException {
        return this;
    }

    
    public SQLBuilder setTransaction(String name) throws BuzzerSQLBuilderException{
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

    
    public SQLBuilder createSavePoint(String savePoint) throws BuzzerSQLBuilderException{
        return null;
    }

    
    public SQLBuilder releaseSavePoint(String savePoint) throws BuzzerSQLBuilderException{
        return null;
    }

    
    public SQLBuilder rollbackToSavePoint(String savePoint)throws BuzzerSQLBuilderException {
        return null;
    }

    
    public String toStringOmitSemiColon() {
        String sqlStatement=this.sql.toString();
        return sqlStatement.substring(0,sqlStatement.length()-1);
    }

    @Override
    public String toString() {
        return this.sql.toString();
    }


    protected void validateDatabaseName(String database)throws BuzzerSQLBuilderException
    {
        if(StringUtils.isEmpty(database))
        {
            throw new BuzzerSQLBuilderException("Database name cannot be empty or null");
        }
    }

    protected void validateTableName(String tablename)throws BuzzerSQLBuilderException
    {
        if(StringUtils.isEmpty(tablename))
        {
            throw new BuzzerSQLBuilderException("Table name cannot be empty or null");
        }
    }

    protected void validateColumn(Column column)throws BuzzerSQLBuilderException
    {
        if(Objects.isNull(column) || StringUtils.isEmpty(column.getName()) ||StringUtils.isEmpty(column.getDataType()))
        {
            throw new BuzzerSQLBuilderException("column name or data type cannot be empty or null");
        }
    }

    protected void validateColumns(Column[] columns)throws BuzzerSQLBuilderException {
        if(ObjectUtils.isEmpty(columns) || columns.length==0)
        {
            throw new BuzzerSQLBuilderException("table cannot be created without the columns");
        }
        else
        {
            for(Column c:columns)
            {
                this.validateColumn(c);
            }
        }

    }
}
