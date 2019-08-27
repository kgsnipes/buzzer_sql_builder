package com.buzzer.sqlbuilder.service.impl;


import com.buzzer.sqlbuilder.service.SQLBuilder;
import com.buzzer.sqlbuilder.exception.BuzzerSQLBuilderException;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


public class BuzzerMySQLBuilder extends BuzzerSQLBuilder implements SQLBuilder {

    public BuzzerMySQLBuilder() {
        queryTransformers.add(BuzzerAddAutoIncrementQueryTransformer.getInstance());
    }

    public SQLBuilder createDatabase(String database)throws BuzzerSQLBuilderException {
        if(StringUtils.isNotEmpty(this.sql.toString()))
        {
            this.throwExceptionForNewBuilder();
        }
        this.validateDatabaseName(database);
        this.sql.append(String.format(BuzzerSQLConstants.CREATE_DATABASE_QUERY_FORMAT,database));
        return this;
    }


    public SQLBuilder dropDatabase(String database) throws BuzzerSQLBuilderException{
        if(StringUtils.isNotEmpty(this.sql.toString()))
        {
            this.throwExceptionForNewBuilder();
        }
        this.validateDatabaseName(database);
        this.sql.append(String.format(BuzzerSQLConstants.DROP_DATABASE_QUERY_FORMAT,database));
        return this;
    }


    public SQLBuilder useDatabase(String database)throws BuzzerSQLBuilderException {
        if(StringUtils.isNotEmpty(this.sql.toString()))
        {
            this.throwExceptionForNewBuilder();
        }
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

    @Override
    public SQLBuilder limit(Long limit) throws BuzzerSQLBuilderException {
        return this.limit(limit, null);
    }

    @Override
    public SQLBuilder limit(Long limit, Long offset) throws BuzzerSQLBuilderException {
        if(ObjectUtils.isEmpty(limit) && limit<=0l)
        {
            throw new BuzzerSQLBuilderException("need valid values for limit");
        }
        this.sql.append(BuzzerSQLConstants.SPACE).append(BuzzerSQLConstants.LIMIT).append(BuzzerSQLConstants.SPACE);
        if(ObjectUtils.isEmpty(offset))
        {
            this.sql.append(limit.toString());
        }
        else
        {
            this.sql.append(BuzzerSQLConstants.SPACE).append(limit.toString()).append(BuzzerSQLConstants.SPACE).append(BuzzerSQLConstants.COMMA).append(BuzzerSQLConstants.SPACE).append(offset.toString()).append(BuzzerSQLConstants.SPACE);
        }
        return this;
    }


}
