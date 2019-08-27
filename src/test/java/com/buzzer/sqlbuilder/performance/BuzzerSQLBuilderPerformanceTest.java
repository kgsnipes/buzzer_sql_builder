package com.buzzer.sqlbuilder.performance;

import com.buzzer.sqlbuilder.BuzzerDBType;

import com.buzzer.sqlbuilder.dto.DateValue;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilder;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilderFactoryImpl;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;

import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.util.Date;

public class BuzzerSQLBuilderPerformanceTest {

    BuzzerSQLBuilderFactoryImpl factory;
    BuzzerSQLBuilder sqlBuilder;
    Logger LOG=Logger.getLogger(BuzzerSQLBuilderPerformanceTest.class);

    @Before
    public void setup()
    {
        LOG.info("Setting up factory for BuzzerSQLBuilderFactoryTest test class");
        factory=new BuzzerSQLBuilderFactoryImpl();
        sqlBuilder= (BuzzerSQLBuilder) factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
    }

    @Test
    public void createTableTest()throws Exception
    {
        StopWatch watch = new StopWatch();
        watch.start();
        for(int i=0;i<10000;i++)
        {
            sqlBuilder= (BuzzerSQLBuilder) factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
            String sql=sqlBuilder.createTable("ecom","customer",Boolean.TRUE)
                    .withColumn("pk","bigint",null,Boolean.FALSE,Boolean.TRUE,null,Boolean.FALSE)
                    .withColumn("email","varchar","255",Boolean.FALSE,Boolean.TRUE,null,Boolean.FALSE)
                    .withColumn("firstname","varchar","255",Boolean.TRUE,Boolean.FALSE,null,Boolean.FALSE)
                    .withColumn("lastname","varchar","255",Boolean.TRUE,Boolean.FALSE,null,Boolean.FALSE)
                    .withAutoIncrementValue("pk",new Long(1000))
                    .withIndexOnColumns("customer_email_index","email")
                    .withIndexOnColumns("customer_name_index","firstname","lastname")
                    .toString();
        }

        watch.stop();
        LOG.info("Execution time milliseconds - "+ watch.getTime());
        LOG.info("Execution time seconds - "+ (watch.getTime()/1000));
        Assert.that(Boolean.TRUE,"Execution finished.");
    }

    @Test
    public void dropTableTest()throws Exception
    {
        StopWatch watch = new StopWatch();
        watch.start();
        for(int i=0;i<1000;i++)
        {
            sqlBuilder= (BuzzerSQLBuilder) factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
            String sql=sqlBuilder.dropTable("ecom","customer").toString();
        }

        watch.stop();
        LOG.info("Execution time milliseconds - "+ watch.getTime());
        LOG.info("Execution time seconds - "+ (watch.getTime()/1000));
        Assert.that(Boolean.TRUE,"Execution finished.");
    }

    @Test
    public void selectAllQueryTest()throws Exception
    {
        StopWatch watch = new StopWatch();
        watch.start();
        for(int i=0;i<1000;i++)
        {
            sqlBuilder= (BuzzerSQLBuilder) factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
        String sql=sqlBuilder.selectAll().fromTable("ecom.customers","current_customers")
                .toString();
        }

        watch.stop();
        LOG.info("Execution time milliseconds - "+ watch.getTime());
        LOG.info("Execution time seconds - "+ (watch.getTime()/1000));
        Assert.that(Boolean.TRUE,"Execution finished.");

    }


    @Test
    public void selectWithColumnsQueryTest()throws Exception
    {
        StopWatch watch = new StopWatch();
        watch.start();
        for(int i=0;i<10000;i++)
        {
            sqlBuilder= (BuzzerSQLBuilder) factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
            String sql=sqlBuilder.selectColumns(new String[]{"email","name","age"}).fromTable("ecom.customers","current_customers")
                .where("dob", BuzzerSQLConstants.SQLOperators.GE, DateValue.create(new Date(),BuzzerSQLConstants.DateFormats.SQL.DATETIME))
                .and("email",BuzzerSQLConstants.SQLOperators.LIKE,"%@gmail.com")
                .or("name",BuzzerSQLConstants.SQLOperators.LIKE,"randy%")
                .and("age",BuzzerSQLConstants.SQLOperators.BETWEEN,new String[]{"hello","world"})
                .limit(10l,190890l)
                .toString();

        }

        watch.stop();
        LOG.info("Execution time milliseconds - "+ watch.getTime());
        LOG.info("Execution time seconds - "+ (watch.getTime()/1000));
        Assert.that(Boolean.TRUE,"Execution finished.");

    }
}
