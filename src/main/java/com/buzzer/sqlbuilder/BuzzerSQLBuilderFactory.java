package com.buzzer.sqlbuilder;

import com.buzzer.sqlbuilder.impl.BuzzerSQLBuilder;

public interface BuzzerSQLBuilderFactory {
    BuzzerSQLBuilder getSQLBuilderForDB(BuzzerDBType dbType);
}
