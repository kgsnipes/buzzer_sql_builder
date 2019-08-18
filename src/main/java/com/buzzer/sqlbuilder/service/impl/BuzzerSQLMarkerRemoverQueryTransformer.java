package com.buzzer.sqlbuilder.service.impl;

import com.buzzer.sqlbuilder.service.QueryTransformer;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.commons.lang3.RegExUtils;

public class BuzzerSQLMarkerRemoverQueryTransformer implements QueryTransformer {
    @Override
    public StringBuilder transform(StringBuilder sql) {
       sql=new StringBuilder(RegExUtils.replaceAll(sql.toString(),BuzzerSQLConstants.SQL_MARKER_REGEX,BuzzerSQLConstants.EMPTY));
        return sql;
    }
}
