package com.buzzer.sqlbuilder.service.impl;


import com.buzzer.sqlbuilder.dto.DateValue;
import com.buzzer.sqlbuilder.service.QueryTransformer;
import com.buzzer.sqlbuilder.service.SQLBuilder;
import com.buzzer.sqlbuilder.dto.Column;
import com.buzzer.sqlbuilder.exception.BuzzerSQLBuilderException;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;


public class BuzzerSQLBuilder implements SQLBuilder {


    protected StringBuilder sql=new StringBuilder();
    protected List<QueryTransformer> queryTransformers=new ArrayList<>();

    public BuzzerSQLBuilder() {
        queryTransformers.add(new BuzzerCreateTableQueryTransformer());
        queryTransformers.add(new BuzzerSQLMarkerRemoverQueryTransformer());
        queryTransformers.add(new BuzzerSQLSelectQueryTransformer());
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

    @Override
    public SQLBuilder selectAll()throws BuzzerSQLBuilderException  {
        if(StringUtils.isNotEmpty(this.sql.toString()))
        {
            this.throwExceptionForNewBuilder();
        }
        this.selectColumns(new String[]{"*"});
        return this;
    }


    public SQLBuilder selectColumns(String[] columns)throws BuzzerSQLBuilderException {

        this.selectColumns(columns,null);
        return this;
    }


    public SQLBuilder selectColumns(String[] columns, String[] aliasNames)throws BuzzerSQLBuilderException {
        this.selectColumns(columns,aliasNames,Boolean.FALSE);
        return this;
    }


    public SQLBuilder selectColumns(String[] columns, String[] aliasNames, Boolean useDistinct)throws BuzzerSQLBuilderException {

        Column []cols=new Column[columns.length];
        if(ObjectUtils.isEmpty(columns) ||(ObjectUtils.isNotEmpty(columns) && ObjectUtils.isNotEmpty(aliasNames) && columns.length!=aliasNames.length))
        {
            throw new BuzzerSQLBuilderException("please specify columns for select statement, or the columns do not match the number of aliases");
        }

        IntStream.range(0, columns.length).forEach(index->{
            Column c=new Column();
            c.setName(columns[index]);
            if(ObjectUtils.isNotEmpty(aliasNames) && StringUtils.isNotEmpty(aliasNames[index]))
            {
                c.setAliasName(aliasNames[index]);
            }
            cols[index]=c;

        });

        this.selectColumns(useDistinct,cols);
        return this;
    }


    public SQLBuilder selectColumns(Boolean useDistinct,Column... columns)throws BuzzerSQLBuilderException {
        this.validateColumnsForSelect(columns);
        this.sql.append(BuzzerSQLConstants.SELECT).append(BuzzerSQLConstants.SPACE);
        if(BooleanUtils.isTrue(useDistinct))
        {
            this.sql.append(BuzzerSQLConstants.SPACE).append(BuzzerSQLConstants.DISTINCT).append(BuzzerSQLConstants.SPACE);
        }
        List<String> colNames=new ArrayList<>();
        IntStream.range(0, columns.length).forEach(index->{
            StringBuilder colSpec=new StringBuilder();
            colSpec.append(StringUtils.trimToEmpty(columns[index].getName()));
            if(StringUtils.isNotEmpty(columns[index].getAliasName()))
            {
                colSpec.append(StringUtils.join(BuzzerSQLConstants.SPACE,BuzzerSQLConstants.AS,BuzzerSQLConstants.SPACE,StringUtils.trimToEmpty(columns[index].getAliasName())));
            }
            colNames.add(colSpec.toString());

        });
        this.sql.append(StringUtils.join(colNames,BuzzerSQLConstants.SPACE+BuzzerSQLConstants.COMMA+BuzzerSQLConstants.SPACE));
        this.sql.append(BuzzerSQLConstants.SPACE);
        return this;
    }

    protected void validateColumnsForSelect(Column[] columns) throws BuzzerSQLBuilderException{
        if(ObjectUtils.isEmpty(columns))
        {
            throw new BuzzerSQLBuilderException("please mention some columns on the select statement");
        }
        for(Column col: columns)
        {
            if(StringUtils.isEmpty(col.getName()))
            {
                throw new BuzzerSQLBuilderException("column name cannot be empty");
            }
        }

    }


    public SQLBuilder fromTable(String table, String aliasName)throws BuzzerSQLBuilderException {
        if(this.sql.indexOf(BuzzerSQLConstants.SELECT)==-1)
        {
            this.throwExceptionForNewBuilder();
        }
        this.sql.append(BuzzerSQLConstants.FROM).append(BuzzerSQLConstants.SPACE).append(StringUtils.trimToEmpty(table));

        if(StringUtils.isNotEmpty(aliasName))
        {
            this.sql.append(BuzzerSQLConstants.SPACE).append(BuzzerSQLConstants.AS).append(BuzzerSQLConstants.SPACE).append(aliasName);
        }
        this.sql.append(BuzzerSQLConstants.SPACE);
        return this;
    }


    public SQLBuilder fromQuery(SQLBuilder queryBuilder, String aliasName)throws BuzzerSQLBuilderException {
        if(ObjectUtils.isEmpty(queryBuilder) || StringUtils.isEmpty(aliasName))
        {
            throw new BuzzerSQLBuilderException("the builder or the alias cannot be null");
        }
        if(queryBuilder.toString().indexOf(BuzzerSQLConstants.SELECT)==-1)
        {
            throw new BuzzerSQLBuilderException("the inner query needs to be a select statement");
        }
        this.sql.append(BuzzerSQLConstants.SPACE).append(BuzzerSQLConstants.START_BRACKET).append(queryBuilder.toStringOmitSemiColon()).append(BuzzerSQLConstants.SPACE).append(BuzzerSQLConstants.END_BRACKET).append(BuzzerSQLConstants.SPACE).append(BuzzerSQLConstants.AS).append(StringUtils.trim(aliasName));
        this.sql.append(BuzzerSQLConstants.SPACE);
        return this;
    }


    public SQLBuilder and(String operand, String operator, Object value) throws BuzzerSQLBuilderException{
        this.isSelectOrUpdateStatementValidation();
        this.addConditionToStatement(BuzzerSQLConstants.AND,operand,operator,getStringFromValue(value));
        return this;
    }


    public SQLBuilder or(String operand, String operator, Object value)throws BuzzerSQLBuilderException {
        this.isSelectOrUpdateStatementValidation();
        this.addConditionToStatement(BuzzerSQLConstants.OR,operand,operator,getStringFromValue(value));
        return this;
    }


    public SQLBuilder where(String operand, String operator, Object value)throws BuzzerSQLBuilderException {

        this.isSelectOrUpdateStatementValidation();

        this.addConditionToStatement(BuzzerSQLConstants.WHERE,operand,operator,getStringFromValue(value));
        return this;
    }

    @Override
    public SQLBuilder limit(Long offset, Long limit) throws BuzzerSQLBuilderException {
        throw new BuzzerSQLBuilderException("limit call is database engine specific");
    }

    @Override
    public SQLBuilder limit(Long limit) throws BuzzerSQLBuilderException {
        throw new BuzzerSQLBuilderException("limit call is database engine specific");
    }


    protected String getStringFromValue(Object value) {
        StringBuilder valStr=new StringBuilder();
        String commaMarker=StringUtils.join(BuzzerSQLConstants.SQL_MARKER_BOUNDARY,BuzzerSQLConstants.COMMA,BuzzerSQLConstants.SQL_MARKER_BOUNDARY);
        if(value instanceof DateValue)
        {
            DateValue dateValue= (DateValue) value;
            valStr.append(BuzzerSQLConstants.SINGLE_QUOTE).append(dateValue.getFormattedDate()).append(BuzzerSQLConstants.SINGLE_QUOTE);
        }
        else if(value instanceof Collection)
        {

            Collection collectionValue= (Collection) value;
            collectionValue.stream().forEach(v->valStr.append(BuzzerSQLConstants.SPACE).append(this.getStringFromValue(v)).append(BuzzerSQLConstants.SPACE).append(commaMarker));

            return valStr.substring(0,valStr.length()-commaMarker.length());

        }
        else if(value.getClass().isArray())
        {
            IntStream.range(0, Array.getLength(value)).forEach(index->{
                valStr.append(BuzzerSQLConstants.SPACE).append(this.getStringFromValue(Array.get(value,index))).append(BuzzerSQLConstants.SPACE).append(commaMarker);
            });
            return valStr.substring(0,valStr.length()-commaMarker.length());
        }
        else if(value instanceof CharSequence)
        {
            valStr.append(BuzzerSQLConstants.SINGLE_QUOTE).append(StringUtils.trimToEmpty(value.toString())).append(BuzzerSQLConstants.SINGLE_QUOTE);
        }
        else if(value instanceof  Boolean)
        {
            valStr.append(value.toString());
        }
        else if(value instanceof  Number)
        {
            valStr.append(value.toString());
        }


        return valStr.toString();
    }

    protected void isSelectOrUpdateStatementValidation()throws BuzzerSQLBuilderException
    {
        boolean isValid=false;
        if(this.sql.indexOf(BuzzerSQLConstants.SELECT)==-1)
        {
            throw new BuzzerSQLBuilderException("the inner query needs to be a select statement or update statement");
        }
        else
        {
            isValid=true;
        }

        if(this.sql.indexOf(BuzzerSQLConstants.UPDATE)==-1 && !isValid)
        {
            throw new BuzzerSQLBuilderException("the inner query needs to be a select statement or update statement");
        }
    }

    protected void addConditionToStatement(String condition,String operand, String operator, String value)throws BuzzerSQLBuilderException
    {
        if(StringUtils.isEmpty(operand) || StringUtils.isEmpty(operator) || StringUtils.isEmpty(value))
        {
            throw new BuzzerSQLBuilderException("the operand or operator or value cannot be empty");
        }
        this.sql.append(BuzzerSQLConstants.SPACE).append(condition).append(BuzzerSQLConstants.SPACE).append(StringUtils.trimToEmpty(operand));
        this.sql.append(BuzzerSQLConstants.SPACE).append(StringUtils.trimToEmpty(operator)).append(BuzzerSQLConstants.SPACE);
        switch (operator)
        {
            case BuzzerSQLConstants.SQLOperators.BETWEEN:

                this.sql.append(BuzzerSQLConstants.SPACE).append(StringUtils.trimToEmpty(value).replaceAll(BuzzerSQLConstants.REPLACE_COMMA_WITH_AND_REGEX,StringUtils.join(BuzzerSQLConstants.SPACE,BuzzerSQLConstants.AND,BuzzerSQLConstants.SPACE))).append(BuzzerSQLConstants.SPACE);
                break;
            case BuzzerSQLConstants.SQLOperators.IN:
                this.sql.append(BuzzerSQLConstants.START_BRACKET).append(StringUtils.trimToEmpty(value)).append(BuzzerSQLConstants.END_BRACKET);
                break;
                default:
                    this.sql.append(StringUtils.trimToEmpty(value));
        }
        this.sql.append(BuzzerSQLConstants.SPACE);

    }


    public SQLBuilder groupBy(String[] columns)throws BuzzerSQLBuilderException {
        return this;
    }

    @Override
    public SQLBuilder groupBy(Column... columns) throws BuzzerSQLBuilderException {
        return this;
    }


    public SQLBuilder orderBy(String[] columns)throws BuzzerSQLBuilderException {
        return this;
    }

    @Override
    public SQLBuilder orderBy(Column... columns) throws BuzzerSQLBuilderException {
        return null;
    }


    public SQLBuilder positionalParameters(List parameters)throws BuzzerSQLBuilderException {
        return this;
    }


    public SQLBuilder namedParameters(Map parameters)throws BuzzerSQLBuilderException {
        return this;
    }

    public SQLBuilder createTable(String schema, String tableName)throws BuzzerSQLBuilderException {
        if(StringUtils.isNotEmpty(this.sql.toString()) && this.sql.indexOf(BuzzerSQLConstants.DROP_TABLE_QUERY)==-1)
        {
            this.throwExceptionForNewBuilder();
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


    public SQLBuilder createTable(String schema, String tableName, Boolean dropIfExists) throws BuzzerSQLBuilderException {
        if(StringUtils.isNotEmpty(this.sql.toString()))
        {
            this.throwExceptionForNewBuilder();
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
        if(StringUtils.isNotEmpty(StringUtils.trimToEmpty(this.sql.toString().replaceAll(BuzzerSQLConstants.NON_PRINT_CHARACTERS_REGEX, BuzzerSQLConstants.EMPTY))))
        {
            this.throwExceptionForNewBuilder();
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
            this.throwExceptionForNewBuilder();
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
            this.throwExceptionForNewBuilder();
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
        if(!(this.sql.indexOf(BuzzerSQLConstants.CREATE_VIEW)==-1 || this.sql.indexOf(BuzzerSQLConstants.CREATE_OR_REPLACE_VIEW)==-1))
        {
            this.throwExceptionForNewBuilder();
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
            this.throwExceptionForNewBuilder();
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

    protected void throwExceptionForNewBuilder()throws BuzzerSQLBuilderException
    {
        throw new BuzzerSQLBuilderException("create a new builder instance");
    }
}
