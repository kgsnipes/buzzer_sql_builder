package com.buzzer.sqlbuilder;

import com.buzzer.sqlbuilder.dto.Column;
import com.buzzer.sqlbuilder.exception.BuzzerSQLBuilderException;


public interface SQLBuilder {


    SQLBuilder createDatabase(String database)throws BuzzerSQLBuilderException;
    SQLBuilder dropDatabase(String database)throws BuzzerSQLBuilderException;
    SQLBuilder useDatabase(String database)throws BuzzerSQLBuilderException;
    SQLBuilder backUpDatabase(String database,String diskPath,Boolean isDifferentialBackup)throws BuzzerSQLBuilderException;



//    SQLBuilder selectWithColumns(String []columns);
//    SQLBuilder selectWithColumns(String []columns,String []aliasNames);
//    SQLBuilder selectWithColumns(String []columns,String []aliasNames,String []distinctColumns);
//    SQLBuilder selectWithColumns(Column...columns);
//
//    SQLBuilder withTable(String table,String aliasName);
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


    SQLBuilder createTable(String tableName)throws BuzzerSQLBuilderException;
    SQLBuilder withColumns(Column ...columns)throws BuzzerSQLBuilderException;
    SQLBuilder withColumn(String columnName,String sqlType, Boolean isNull)throws BuzzerSQLBuilderException;
    SQLBuilder withPK(String ...columns)throws BuzzerSQLBuilderException;


    SQLBuilder beginTransaction();
    SQLBuilder beginTransaction(String mode)throws BuzzerSQLBuilderException;
    SQLBuilder setTransaction(String name)throws BuzzerSQLBuilderException;
    SQLBuilder endTransaction();
    SQLBuilder commit();
    SQLBuilder rollback();
    SQLBuilder createSavePoint(String savePoint)throws BuzzerSQLBuilderException;
    SQLBuilder releaseSavePoint(String savePoint)throws BuzzerSQLBuilderException;
    SQLBuilder rollbackToSavePoint(String savePoint)throws BuzzerSQLBuilderException;


    //SQLBuilder batch();
    //SQLBuilder update();

    //SQLBuilder insert(String tableName);
    //SQLBuilder withValues(Object []values);

    String toStringOmitSemiColon();



}
