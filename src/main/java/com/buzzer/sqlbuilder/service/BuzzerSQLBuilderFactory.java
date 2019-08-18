package com.buzzer.sqlbuilder.service;

import com.buzzer.sqlbuilder.BuzzerDBType;

public interface BuzzerSQLBuilderFactory{
    SQLBuilder getSQLBuilderForDB(BuzzerDBType dbType);
}
