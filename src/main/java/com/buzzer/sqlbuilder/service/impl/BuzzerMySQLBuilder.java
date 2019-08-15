package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.service.MySqlSQLBuilder;
import com.buzzer.sqlbuilder.service.SQLBuilder;
import com.buzzer.sqlbuilder.exception.BuzzerSQLBuilderException;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;


public class BuzzerMySQLBuilder extends BuzzerSQLBuilder implements MySqlSQLBuilder {

    public SQLBuilder createDatabase(String database)throws BuzzerSQLBuilderException {
        this.validateDatabaseName(database);
        this.sql.append(String.format(BuzzerSQLConstants.CREATE_DATABASE_QUERY_FORMAT,database));
        return this;
    }


    public SQLBuilder dropDatabase(String database) throws BuzzerSQLBuilderException{
        this.validateDatabaseName(database);
        this.sql.append(String.format(BuzzerSQLConstants.DROP_DATABASE_QUERY_FORMAT,database));
        return this;
    }


    public SQLBuilder useDatabase(String database)throws BuzzerSQLBuilderException {
        this.validateDatabaseName(database);
        this.sql.append(String.format(BuzzerSQLConstants.USE_DATABASE_QUERY_FORMAT,database));
        return this;
    }


}
