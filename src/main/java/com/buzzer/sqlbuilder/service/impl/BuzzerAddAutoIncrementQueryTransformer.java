package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.service.QueryTransformer;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import com.buzzer.sqlbuilder.util.BuzzerUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RegExUtils;

import java.util.List;

public class BuzzerAddAutoIncrementQueryTransformer implements QueryTransformer {
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
}
