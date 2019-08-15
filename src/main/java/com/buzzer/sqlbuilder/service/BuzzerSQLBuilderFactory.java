package com.buzzer.sqlbuilder.service;

import com.buzzer.sqlbuilder.BuzzerDBType;
import com.buzzer.sqlbuilder.service.SQLBuilder;

public interface BuzzerSQLBuilderFactory {
    SQLBuilder getSQLBuilderForDB(BuzzerDBType dbType);
}
