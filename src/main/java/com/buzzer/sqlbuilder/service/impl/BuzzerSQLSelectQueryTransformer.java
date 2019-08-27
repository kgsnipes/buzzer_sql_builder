package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;

public class BuzzerSQLSelectQueryTransformer implements com.buzzer.sqlbuilder.service.QueryTransformer {
    @Override
    public StringBuilder transform(StringBuilder sql) {
        if(sql.indexOf(BuzzerSQLConstants.END_STATEMENT)!=sql.length()-1)
        {
            sql.append(BuzzerSQLConstants.END_STATEMENT);
        }

        return sql;
    }
}
