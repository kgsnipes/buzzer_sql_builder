package com.buzzer.sqlbuilder.service;

import com.buzzer.sqlbuilder.dto.Column;
import com.buzzer.sqlbuilder.exception.BuzzerSQLBuilderException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface defines all the methods that are implemented for the sql builder API
 */
public interface SQLBuilder {

    /**
     * This methods creates the SQL statement for creating a database in MySQL. This will not be supported on the Generic SQL flavour.
     * @param database
     * @return
     * @throws BuzzerSQLBuilderException
     */
    SQLBuilder createDatabase(String database)throws BuzzerSQLBuilderException;

    /**
     * This methods creates the SQL statement for creating a database in MySQL. This will not be supported on the Generic SQL flavour.
     * @param database
     * @return
     * @throws BuzzerSQLBuilderException
     */
    SQLBuilder dropDatabase(String database)throws BuzzerSQLBuilderException;
    SQLBuilder dropDatabaseIfExists(String database)throws BuzzerSQLBuilderException;
    SQLBuilder useDatabase(String database)throws BuzzerSQLBuilderException;

    SQLBuilder selectAll()throws BuzzerSQLBuilderException ;
    SQLBuilder selectColumns(String []columns)throws BuzzerSQLBuilderException;
    SQLBuilder selectColumns(String []columns,String []aliasNames)throws BuzzerSQLBuilderException;
    SQLBuilder selectColumns(String []columns,String []aliasNames,Boolean useDistinct)throws BuzzerSQLBuilderException;
    SQLBuilder selectColumns(Boolean useDistinct,Column...columns)throws BuzzerSQLBuilderException;

    SQLBuilder fromTable(String table,String aliasName)throws BuzzerSQLBuilderException;
    SQLBuilder fromQuery(SQLBuilder queryBuilder,String aliasName)throws BuzzerSQLBuilderException;
//
//
//    SQLBuilder join(String table,String alias);
//    SQLBuilder leftJoin(String table,String alias);
//    SQLBuilder rightJoin(String table,String alias);
//    SQLBuilder fullJoin(String table,String alias);
//    SQLBuilder selfJoin(String table,String alias);
//    SQLBuilder cartesianJoin(String table,String alias);
//
//    SQLBuilder on(String sourceColumn,String targetColumn);
//
    SQLBuilder and(String operand,String operator,Object value)throws BuzzerSQLBuilderException;
    SQLBuilder or(String operand,String operator,Object value)throws BuzzerSQLBuilderException;
    SQLBuilder where(String operand,String operator,Object value)throws BuzzerSQLBuilderException;
    SQLBuilder limit(Long limit,Long offset)throws BuzzerSQLBuilderException;
    SQLBuilder limit(Long limit)throws BuzzerSQLBuilderException;

    SQLBuilder groupBy(String []columns)throws BuzzerSQLBuilderException;
    SQLBuilder groupBy(Column ...columns)throws BuzzerSQLBuilderException;
    SQLBuilder orderBy(String []columns)throws BuzzerSQLBuilderException;
    SQLBuilder orderBy(Column ...columns)throws BuzzerSQLBuilderException;
    SQLBuilder positionalParameters(List parameters)throws BuzzerSQLBuilderException;
    SQLBuilder namedParameters(Map<String,Object> parameters)throws BuzzerSQLBuilderException;


    SQLBuilder createTable(String schema,String tableName)throws BuzzerSQLBuilderException;
    SQLBuilder createTable(String schema,String tableName,Boolean dropIfExists)throws BuzzerSQLBuilderException;
    SQLBuilder withColumns(Column ...columns)throws BuzzerSQLBuilderException;
    SQLBuilder withColumns(List<Column> columns)throws BuzzerSQLBuilderException;
    SQLBuilder withColumns(String ...columns)throws BuzzerSQLBuilderException;
    SQLBuilder withColumn(String name,String dataType,String spec,Boolean isNull,Boolean isUnique,Object defaultValue,Boolean isAutoIncrement)throws BuzzerSQLBuilderException;
    SQLBuilder withAutoIncrementValue(String column,Long startValue)throws BuzzerSQLBuilderException;
    SQLBuilder withIndexOnColumns(String indexName,String ...columns)throws BuzzerSQLBuilderException;
    SQLBuilder dropTable(String schemaName,String tableName)throws BuzzerSQLBuilderException;

    SQLBuilder createView(String schema,String viewName)throws BuzzerSQLBuilderException;
    SQLBuilder updateView(String schema,String viewName)throws BuzzerSQLBuilderException;
    SQLBuilder AsSelect(SQLBuilder selectQuery)throws BuzzerSQLBuilderException;
    SQLBuilder dropView(String schema,String viewName)throws BuzzerSQLBuilderException;

//    SQLBuilder beginTransaction();
//    SQLBuilder beginTransaction(String mode)throws BuzzerSQLBuilderException;
//    SQLBuilder setTransaction(String name)throws BuzzerSQLBuilderException;
//    SQLBuilder endTransaction();
//    SQLBuilder commit();
//    SQLBuilder rollback();
//    SQLBuilder createSavePoint(String savePoint)throws BuzzerSQLBuilderException;
//    SQLBuilder releaseSavePoint(String savePoint)throws BuzzerSQLBuilderException;
//    SQLBuilder rollbackToSavePoint(String savePoint)throws BuzzerSQLBuilderException;

    void addQueryTransformer(QueryTransformer queryTransformer);
    Set<QueryTransformer> getQueryTransformers();

    //SQLBuilder batch();
    //SQLBuilder update();

    SQLBuilder insertTo(String tableName)throws BuzzerSQLBuilderException;
    SQLBuilder withValues(Object...values)throws BuzzerSQLBuilderException;

    String toStringOmitSemiColon();



}
