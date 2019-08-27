package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.service.QueryTransformer;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.commons.lang3.ObjectUtils;

public class BuzzerSQLSelectQueryTransformer implements com.buzzer.sqlbuilder.service.QueryTransformer {

    private static BuzzerSQLSelectQueryTransformer instance;
    private BuzzerSQLSelectQueryTransformer(){}


    @Override
    public StringBuilder transform(StringBuilder sql) {
        if(sql.indexOf(BuzzerSQLConstants.END_STATEMENT)!=sql.length()-1)
        {
            sql.append(BuzzerSQLConstants.END_STATEMENT);
        }

        return sql;
    }

    public static QueryTransformer getInstance() {
        if(ObjectUtils.isEmpty(instance))
        {
            instance=new BuzzerSQLSelectQueryTransformer();
        }
        return instance;
    }
}
