package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.service.QueryTransformer;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;

public class BuzzerCreateTableQueryTransformer implements QueryTransformer {
    @Override
    public StringBuilder transform(StringBuilder sql) {
        if(sql.length()>0 && sql.indexOf(BuzzerSQLConstants.CREATE_TABLE)>-1 && sql.lastIndexOf(BuzzerSQLConstants.COMMA)>-1 && sql.lastIndexOf(BuzzerSQLConstants.COMMA)>(sql.length()/2) && sql.lastIndexOf(BuzzerSQLConstants.COMMA)<sql.length())
        {
            int commaIndex=sql.lastIndexOf(BuzzerSQLConstants.COMMA);
            sql.replace(commaIndex,commaIndex+1,BuzzerSQLConstants.SPACE).insert(commaIndex,BuzzerSQLConstants.CREATE_TABLE_END);
        }
        return sql;
    }
}
