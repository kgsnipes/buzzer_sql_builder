package com.buzzer.sqlbuilder.performance;

import com.buzzer.sqlbuilder.BuzzerDBType;

import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilder;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilderFactoryImpl;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
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
        DateTime startTime=new DateTime(new Date());
        for(int i=0;i<1000;i++)
        {
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

        DateTime endTime=new DateTime(new Date());
        int timeTaken= Seconds.secondsBetween(startTime,endTime).getSeconds();
        LOG.info("Execution time seconds - "+ timeTaken);
        Assert.that(Boolean.TRUE,"Execution finished.");
    }

    @Test
    public void dropTableTest()throws Exception
    {
        DateTime startTime=new DateTime(new Date());
        for(int i=0;i<1000;i++)
        {
            String sql=sqlBuilder.dropTable("ecom","customer").toString();
        }

        DateTime endTime=new DateTime(new Date());
        int timeTaken= Seconds.secondsBetween(startTime,endTime).getSeconds();
        LOG.info("Execution time seconds - "+ timeTaken);
        Assert.that(Boolean.TRUE,"Execution finished.");
    }
}