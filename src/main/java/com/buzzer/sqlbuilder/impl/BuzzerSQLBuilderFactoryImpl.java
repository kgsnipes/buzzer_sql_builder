package com.buzzer.sqlbuilder.impl;

import com.buzzer.sqlbuilder.BuzzerDBType;
import com.buzzer.sqlbuilder.BuzzerSQLBuilderFactory;

public class BuzzerSQLBuilderFactoryImpl implements BuzzerSQLBuilderFactory {
    public BuzzerSQLBuilder getSQLBuilderForDB(BuzzerDBType dbType) {

        BuzzerSQLBuilder builder=null;

        switch (dbType)
        {
            case MARIADB:
            case MYSQL:
                builder=new BuzzerMySQLBuilder();
                break;
            default:
                builder=new BuzzerSQLBuilder();
                break;

        }
        return  builder;

    }
}
