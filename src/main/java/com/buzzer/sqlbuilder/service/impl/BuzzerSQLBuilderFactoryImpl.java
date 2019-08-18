package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.BuzzerDBType;
import com.buzzer.sqlbuilder.service.BuzzerSQLBuilderFactory;

import com.buzzer.sqlbuilder.service.SQLBuilder;

public class BuzzerSQLBuilderFactoryImpl implements  BuzzerSQLBuilderFactory{

    public  SQLBuilder getSQLBuilderForDB(BuzzerDBType dbType) {

        SQLBuilder builder=null;

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
