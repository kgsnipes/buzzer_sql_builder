package com.buzzer.sqlbuilder;

import com.buzzer.sqlbuilder.dto.Column;


public interface SQLBuilder {

    SQLBuilder createDatabase(String database);
    SQLBuilder dropDatabase(String database);
    SQLBuilder useDatabase(String database);

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


    SQLBuilder createTable(String tableName);
    SQLBuilder withColumns(Column ...columns);
    SQLBuilder withColumn(String columnName,String sqlType, Boolean isNull);
    SQLBuilder withPK(String ...columns);


    SQLBuilder beginTransaction();
    SQLBuilder beginTransaction(String mode);
    SQLBuilder setTransaction(String name);
    SQLBuilder endTransaction();
    SQLBuilder commit();
    SQLBuilder rollback();
    SQLBuilder createSavePoint(String savePoint);
    SQLBuilder releaseSavePoint(String savePoint);
    SQLBuilder rollbackToSavePoint(String savePoint);


    //SQLBuilder batch();
    //SQLBuilder update();

    //SQLBuilder insert(String tableName);
    //SQLBuilder withValues(Object []values);

    SQLBuilder toStringOmitSemiColon();



}
