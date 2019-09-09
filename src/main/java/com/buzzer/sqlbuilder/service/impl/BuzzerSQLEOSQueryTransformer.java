package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.service.QueryTransformer;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.commons.lang3.ObjectUtils;

public class BuzzerSQLEOSQueryTransformer implements com.buzzer.sqlbuilder.service.QueryTransformer {

    private static BuzzerSQLEOSQueryTransformer instance;
    private BuzzerSQLEOSQueryTransformer(){}


    @Override
    public StringBuilder transform(StringBuilder sql) {
        String sqlStr=sql.toString().trim();
        if(sqlStr.indexOf(BuzzerSQLConstants.END_STATEMENT)!=sqlStr.length()-1)
        {
            sql.append(BuzzerSQLConstants.END_STATEMENT);
        }

        return sql;
    }

    public static QueryTransformer getInstance() {
        if(ObjectUtils.isEmpty(instance))
        {
            instance=new BuzzerSQLEOSQueryTransformer();
        }
        return instance;
    }
}
