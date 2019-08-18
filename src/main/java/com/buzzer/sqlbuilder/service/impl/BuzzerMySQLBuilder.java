package com.buzzer.sqlbuilder.service.impl;


import com.buzzer.sqlbuilder.service.SQLBuilder;
import com.buzzer.sqlbuilder.exception.BuzzerSQLBuilderException;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


public class BuzzerMySQLBuilder extends BuzzerSQLBuilder implements SQLBuilder {

    public BuzzerMySQLBuilder() {
        queryTransformers.add(new BuzzerAddAutoIncrementQueryTransformer());
    }

    public SQLBuilder createDatabase(String database)throws BuzzerSQLBuilderException {
        this.validateDatabaseName(database);
        this.sql.append(String.format(BuzzerSQLConstants.CREATE_DATABASE_QUERY_FORMAT,database));
        return this;
    }


    public SQLBuilder dropDatabase(String database) throws BuzzerSQLBuilderException{
        this.validateDatabaseName(database);
        this.sql.append(String.format(BuzzerSQLConstants.DROP_DATABASE_QUERY_FORMAT,database));
        return this;
    }


    public SQLBuilder useDatabase(String database)throws BuzzerSQLBuilderException {
        this.validateDatabaseName(database);
        this.sql.append(String.format(BuzzerSQLConstants.USE_DATABASE_QUERY_FORMAT,database));
        return this;
    }


    @Override
    public SQLBuilder withAutoIncrementValue(String column, Long startValue)throws BuzzerSQLBuilderException {
        if(StringUtils.isEmpty(column))
        {
            throw new BuzzerSQLBuilderException("column name for autoincrement is missing");
        }

            int columnNameIndex=this.sql.indexOf(column);
            int commaIndex=this.sql.indexOf(BuzzerSQLConstants.COMMA,columnNameIndex);
            if(columnNameIndex>0 && commaIndex>0)
            {
                if(ObjectUtils.isNotEmpty(startValue))
                {
                    this.sql.insert(commaIndex,StringUtils.join(BuzzerSQLConstants.SPACE,BuzzerSQLConstants.MYSQL_AUTOINCREMENT,BuzzerSQLConstants.SPACE,String.format(BuzzerSQLConstants.MYSQL_AUTOINCREMENT_VALUE_MARKER,startValue.toString())));
                }
                else
                {
                    this.sql.insert(commaIndex,StringUtils.join(BuzzerSQLConstants.SPACE,BuzzerSQLConstants.MYSQL_AUTOINCREMENT,BuzzerSQLConstants.SPACE));
                }
            }



        return this;
    }


}
