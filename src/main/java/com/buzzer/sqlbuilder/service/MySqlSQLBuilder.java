package com.buzzer.sqlbuilder.service;

import com.buzzer.sqlbuilder.dto.Column;
import com.buzzer.sqlbuilder.exception.BuzzerSQLBuilderException;


public interface MySqlSQLBuilder extends SQLBuilder {

    SQLBuilder createDatabase(String database)throws BuzzerSQLBuilderException;
    SQLBuilder dropDatabase(String database)throws BuzzerSQLBuilderException;
    SQLBuilder useDatabase(String database)throws BuzzerSQLBuilderException;

}
