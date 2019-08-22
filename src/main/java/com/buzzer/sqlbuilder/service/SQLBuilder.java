package com.buzzer.sqlbuilder.service;

import com.buzzer.sqlbuilder.dto.Column;
import com.buzzer.sqlbuilder.exception.BuzzerSQLBuilderException;

import java.util.List;

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
    SQLBuilder useDatabase(String database)throws BuzzerSQLBuilderException;

    SQLBuilder selectAll()throws BuzzerSQLBuilderException ;
    SQLBuilder selectColumns(String []columns)throws BuzzerSQLBuilderException;
    SQLBuilder selectColumns(String []columns,String []aliasNames)throws BuzzerSQLBuilderException;
    SQLBuilder selectColumns(String []columns,String []aliasNames,Boolean useDistinct)throws BuzzerSQLBuilderException;
    SQLBuilder selectColumns(Boolean useDistinct,Column...columns)throws BuzzerSQLBuilderException;

    SQLBuilder fromTable(String table,String aliasName)throws BuzzerSQLBuilderException;
//    SQLBuilder fromInnerQuery(SQLBuilder queryBuilder,String aliasName);
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
//    SQLBuilder and();
//    SQLBuilder or();
//    SQLBuilder where(String operand,String operator,String value);
//    SQLBuilder groupBy(String []columns);
//    SQLBuilder orderBy(String []columns);
//    SQLBuilder positionalParameters(List parameters);
//    SQLBuilder namedParameters(Map parameters);


    SQLBuilder createTable(String schema,String tableName)throws BuzzerSQLBuilderException;
    SQLBuilder createTable(String schema,String tableName,Boolean dropIfExists)throws BuzzerSQLBuilderException;
    SQLBuilder withColumns(Column ...columns)throws BuzzerSQLBuilderException;
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
    List<QueryTransformer> getQueryTransformers();

    //SQLBuilder batch();
    //SQLBuilder update();

    //SQLBuilder insert(String tableName);
    //SQLBuilder withValues(Object []values);

    String toStringOmitSemiColon();



}
