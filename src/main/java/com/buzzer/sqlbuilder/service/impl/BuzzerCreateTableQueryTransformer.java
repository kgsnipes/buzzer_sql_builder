package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.service.QueryTransformer;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import com.buzzer.sqlbuilder.util.BuzzerUtil;


public class BuzzerCreateTableQueryTransformer implements QueryTransformer {
    @Override
    public StringBuilder transform(StringBuilder sql) {

        int tableEndMarkerIndex=sql.indexOf(BuzzerSQLConstants.CREATE_TABLE_ENDING_MARKER);
        if(tableEndMarkerIndex>-1)
        {
            sql.insert(tableEndMarkerIndex,BuzzerSQLConstants.CREATE_TABLE_END);
            sql=new StringBuilder(BuzzerUtil.replaceAllInStringBuilder(sql,BuzzerSQLConstants.END_STATEMENT_BRACKET_REGEX,BuzzerSQLConstants.END_BRACKET+BuzzerSQLConstants.END_STATEMENT));
        }
        return sql;
    }
}
