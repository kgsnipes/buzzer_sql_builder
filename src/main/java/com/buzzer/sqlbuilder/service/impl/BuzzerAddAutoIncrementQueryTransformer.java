package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.service.QueryTransformer;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public class BuzzerAddAutoIncrementQueryTransformer implements QueryTransformer {

    private static BuzzerAddAutoIncrementQueryTransformer instance;
    private BuzzerAddAutoIncrementQueryTransformer(){}

    @Override
    public StringBuilder transform(StringBuilder sql) {
        int autoIncrementFlagIndex=sql.indexOf(BuzzerSQLConstants.MYSQL_AUTOINCREMENT_VALUE_FLAG);
        if(autoIncrementFlagIndex>-1)
        {
            String autoIncrementValue= sql.substring(autoIncrementFlagIndex+BuzzerSQLConstants.MYSQL_AUTOINCREMENT_VALUE_FLAG.length()+1,sql.indexOf(BuzzerSQLConstants.SQL_MARKER_BOUNDARY,autoIncrementFlagIndex)).trim();
            String tableName=sql.substring(sql.indexOf(BuzzerSQLConstants.CREATE_TABLE)+BuzzerSQLConstants.CREATE_TABLE.length()+1,sql.indexOf(BuzzerSQLConstants.START_BRACKET)).trim();
            if(ObjectUtils.isNotEmpty(autoIncrementValue) && ObjectUtils.isNotEmpty(tableName))
            {
                sql.append(String.format(BuzzerSQLConstants.MYSQL_AUTOINCREMENT_ALTER_STATEMENT,tableName,autoIncrementValue));
            }
        }

        return sql;
    }


    public static QueryTransformer getInstance() {
        if(ObjectUtils.isEmpty(instance))
        {
            instance=new BuzzerAddAutoIncrementQueryTransformer();
        }
        return instance;
    }
}
