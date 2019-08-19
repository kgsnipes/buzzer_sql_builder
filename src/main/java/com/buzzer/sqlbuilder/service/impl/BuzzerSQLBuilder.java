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
        if(StringUtils.isEmpty(this.sql.toString()))
        {
            throw new BuzzerSQLBuilderException("Not a fresh create table query");
        }
        this.validateTableName(tableName);
        if(StringUtils.isNotEmpty(schema))
        {
            this.sql.append(String.format(BuzzerSQLConstants.CREATE_TABLE_START,StringUtils.join(schema,BuzzerSQLConstants.PERIOD,tableName)));
        }
        else
        {
            this.sql.append(String.format(BuzzerSQLConstants.CREATE_TABLE_START,tableName));
        }

        this.sql.append(BuzzerSQLConstants.CREATE_TABLE_ENDING_MARKER);

        return this;
    }

    @Override
    public SQLBuilder createTable(String schema, String tableName, Boolean dropIfExists) throws BuzzerSQLBuilderException {
        if(StringUtils.isEmpty(this.sql.toString()))
        {
            throw new BuzzerSQLBuilderException("create a new builder instance");
        }
        if(BooleanUtils.isTrue(dropIfExists))
        {
            this.sql.append(BuzzerSQLConstants.NEW_LINE);
            this.dropTable(schema,tableName);
            this.sql.append(BuzzerSQLConstants.NEW_LINE);
        }
        this.createTable(schema,tableName);
        return this;
    }


    public SQLBuilder withColumns(Column... columns)throws BuzzerSQLBuilderException {
        if(this.sql.indexOf(BuzzerSQLConstants.CREATE_TABLE)==-1)
        {
            throw new BuzzerSQLBuilderException("first call the create table method");
        }
        this.validateColumns(columns);
        Arrays.stream(columns).forEach(c->{
            this.sql.insert(this.sql.indexOf(BuzzerSQLConstants.CREATE_TABLE_ENDING_MARKER),this.getSQLForColumn(c));
        });
        return this;
    }

    @Override
    public SQLBuilder withColumn(String name, String dataType, String spec, Boolean isNull, Boolean isUnique, Object defaultValue, Boolean isAutoIncrement) throws BuzzerSQLBuilderException {
        if(this.sql.indexOf(BuzzerSQLConstants.CREATE_TABLE)==-1)
        {
            throw new BuzzerSQLBuilderException("first call the create table method");
        }
        Column c=new Column();
        c.setName(name);
        c.setDataType(dataType);
        c.setSpecification(spec);
        c.setUnique(isUnique);
        c.setNull(isNull);
        c.setAutoIncrement(isAutoIncrement);
        this.validateColumn(c);
        this.sql.insert(this.sql.indexOf(BuzzerSQLConstants.CREATE_TABLE_ENDING_MARKER),this.getSQLForColumn(c));
        return this;
    }


    public SQLBuilder withAutoIncrementValue(String column, Long startValue)throws BuzzerSQLBuilderException {

        throw new BuzzerSQLBuilderException("Not supported by Generic DB");
    }


    public SQLBuilder withIndexOnColumns(String indexName, String... columns) throws BuzzerSQLBuilderException {
        if(this.sql.indexOf(BuzzerSQLConstants.CREATE_TABLE)==-1)
        {
            throw new BuzzerSQLBuilderException("first call the create table method");
        }
        if(StringUtils.isEmpty(indexName) || ObjectUtils.isEmpty(columns))
        {
            throw new BuzzerSQLBuilderException("indexname or columns cannot be empty");
        }
        String tableName=sql.substring(sql.indexOf(BuzzerSQLConstants.CREATE_TABLE)+BuzzerSQLConstants.CREATE_TABLE.length()+1,sql.indexOf(BuzzerSQLConstants.START_BRACKET)).trim();
        if(StringUtils.isNotEmpty(tableName))
        {
            this.sql.append(String.format(BuzzerSQLConstants.CREATE_INDEX_QUERY_FORMAT,indexName,tableName,StringUtils.join(columns,BuzzerSQLConstants.COMMA)));
        }
        return this;
    }

    @Override
    public SQLBuilder dropTable(String schemaName,String tableName) throws BuzzerSQLBuilderException {
        if(StringUtils.isNotEmpty(this.sql.toString()))
        {
            throw new BuzzerSQLBuilderException("create a new query instance");
        }
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

    @Override
    public SQLBuilder createView(String schema, String viewName) throws BuzzerSQLBuilderException {
        if(StringUtils.isNotEmpty(this.sql.toString()))
        {
            throw new BuzzerSQLBuilderException("create a new builder instance");
        }
        if(StringUtils.isEmpty(schema) || StringUtils.isEmpty(viewName))
        {
            throw new BuzzerSQLBuilderException("schema or view name cannot be empty");
        }
        if(StringUtils.isNotEmpty(schema))
        {
            viewName=StringUtils.join(schema,BuzzerSQLConstants.PERIOD,viewName);
        }
        this.sql.append(BuzzerSQLConstants.NEW_LINE);
        this.sql.append(String.format(BuzzerSQLConstants.CREATE_VIEW_QUERY_FORMAT,viewName));
        this.sql.append(BuzzerSQLConstants.NEW_LINE);
        return this;
    }

    @Override
    public SQLBuilder updateView(String schema, String viewName) throws BuzzerSQLBuilderException {
        if(StringUtils.isNotEmpty(this.sql.toString()))
        {
            throw new BuzzerSQLBuilderException("create a new builder instance");
        }
        if(StringUtils.isEmpty(schema) || StringUtils.isEmpty(viewName))
        {
            throw new BuzzerSQLBuilderException("schema or view name cannot be empty");
        }
        if(StringUtils.isNotEmpty(schema))
        {
            viewName=StringUtils.join(schema,BuzzerSQLConstants.PERIOD,viewName);
        }
        this.sql.append(BuzzerSQLConstants.NEW_LINE);
        this.sql.append(String.format(BuzzerSQLConstants.CREATE_OR_REPLACE_VIEW_QUERY_FORMAT,viewName));
        this.sql.append(BuzzerSQLConstants.NEW_LINE);
        return this;
    }

    @Override
    public SQLBuilder AsSelect(SQLBuilder selectQuery) throws BuzzerSQLBuilderException {
        if(!(this.sql.indexOf(BuzzerSQLConstants.CREATE_VIEW)>-1 || this.sql.indexOf(BuzzerSQLConstants.CREATE_OR_REPLACE_VIEW)>-1))
        {
            throw new BuzzerSQLBuilderException("create a new builder instance");
        }
        if(ObjectUtils.isEmpty(selectQuery))
        {
            throw new BuzzerSQLBuilderException("select query for a view cannot be empty");
        }
        this.sql.append(selectQuery.toString());
        this.sql.append(BuzzerSQLConstants.NEW_LINE);
        return this;
    }

    @Override
    public SQLBuilder dropView(String schema, String viewName) throws BuzzerSQLBuilderException {
        if(StringUtils.isNotEmpty(this.sql.toString()))
        {
            throw new BuzzerSQLBuilderException("create a new builder instance");
        }
        if(StringUtils.isEmpty(schema) || StringUtils.isEmpty(viewName))
        {
            throw new BuzzerSQLBuilderException("schema or view name cannot be empty");
        }
        if(StringUtils.isNotEmpty(schema))
        {
            viewName=StringUtils.join(schema,BuzzerSQLConstants.PERIOD,viewName);
        }
        this.sql.append(BuzzerSQLConstants.NEW_LINE);
        this.sql.append(String.format(BuzzerSQLConstants.DROP_VIEW_QUERY_FORMAT,viewName));
        this.sql.append(BuzzerSQLConstants.NEW_LINE);
        return this;
    }


    private String getSQLForColumn(Column c) {

        StringBuilder columnSql=new StringBuilder();
        columnSql.append(BuzzerSQLConstants.NEW_LINE);
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

       columnSql.append(BuzzerSQLConstants.COMMA);

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
