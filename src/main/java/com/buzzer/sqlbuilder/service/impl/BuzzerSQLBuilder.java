package com.buzzer.sqlbuilder.service.impl;


import com.buzzer.sqlbuilder.service.QueryTransformer;
import com.buzzer.sqlbuilder.service.SQLBuilder;
import com.buzzer.sqlbuilder.dto.Column;
import com.buzzer.sqlbuilder.exception.BuzzerSQLBuilderException;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class BuzzerSQLBuilder implements SQLBuilder {


    protected StringBuilder sql=new StringBuilder();
    protected List<QueryTransformer> queryTransformers=new ArrayList<>();

    public BuzzerSQLBuilder() {
        queryTransformers.add(new BuzzerCreateTableQueryTransformer());
        queryTransformers.add(new BuzzerSQLMarkerRemoverQueryTransformer());
    }


    public SQLBuilder createDatabase(String database) throws BuzzerSQLBuilderException {
        throw new BuzzerSQLBuilderException("Not supported by Generic DB");
    }


    public SQLBuilder dropDatabase(String database) throws BuzzerSQLBuilderException {
        throw new BuzzerSQLBuilderException("Not supported by Generic DB");
    }


    public SQLBuilder useDatabase(String database) throws BuzzerSQLBuilderException {
        throw new BuzzerSQLBuilderException("Not supported by Generic DB");
    }

    public SQLBuilder createTable(String schema, String tableName)throws BuzzerSQLBuilderException {
        this.validateTableName(tableName);
        if(StringUtils.isNotEmpty(schema))
        {
            this.sql.append(String.format(BuzzerSQLConstants.CREATE_TABLE_START,StringUtils.join(schema,BuzzerSQLConstants.PERIOD,tableName)));
        }
        else
        {
            this.sql.append(String.format(BuzzerSQLConstants.CREATE_TABLE_START,tableName));
        }

        return this;
    }

    @Override
    public SQLBuilder createTable(String schema, String tableName, Boolean dropIfExists) throws BuzzerSQLBuilderException {
        if(BooleanUtils.isTrue(dropIfExists))
        {
            this.dropTable(schema,tableName);
        }
        this.createTable(schema,tableName);
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


    public SQLBuilder withAutoIncrementValue(String column, Long startValue)throws BuzzerSQLBuilderException {

        throw new BuzzerSQLBuilderException("Not supported by Generic DB");
    }

    @Override
    public SQLBuilder dropTable(String schemaName,String tableName) throws BuzzerSQLBuilderException {
        if(StringUtils.isNotEmpty(schemaName))
        {
            this.sql.append(String.format(BuzzerSQLConstants.DROP_TABLE_QUERY_FORMAT,StringUtils.join(schemaName.trim(),BuzzerSQLConstants.PERIOD,tableName.trim())));
        }
        else
        {
            this.sql.append(String.format(BuzzerSQLConstants.DROP_TABLE_QUERY_FORMAT,tableName.trim()));
        }

        return this;
    }

    private String getSQLForColumn(Column c) {

        StringBuilder columnSql=new StringBuilder();
        columnSql.append(BuzzerSQLConstants.SPACE+c.getName().trim());
        columnSql.append(BuzzerSQLConstants.SPACE+c.getDataType().trim());
        if(StringUtils.isNotEmpty(c.getSpecification()))
        {
            columnSql.append(BuzzerSQLConstants.START_BRACKET+c.getSpecification().trim()+BuzzerSQLConstants.END_BRACKET);
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

    @Override
    public void addQueryTransformer(QueryTransformer queryTransformer) {
        this.queryTransformers.add(queryTransformer);
    }

    @Override
    public List<QueryTransformer> getQueryTransformers() {
        return this.queryTransformers;
    }


    public String toStringOmitSemiColon() {
        String sqlStatement=toString();
        return sqlStatement.substring(0,sqlStatement.length()-1);
    }

    @Override
    public String toString() {
        getQueryTransformers().stream().forEach(qt->{
            this.sql=new StringBuilder(qt.transform(this.sql));
        });
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
