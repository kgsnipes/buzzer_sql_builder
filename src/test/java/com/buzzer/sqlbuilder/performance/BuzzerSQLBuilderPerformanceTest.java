package com.buzzer.sqlbuilder.performance;

import com.buzzer.sqlbuilder.BuzzerDBType;
import com.buzzer.sqlbuilder.BuzzerSQLBuilderTest;
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
    Logger LOG=Logger.getLogger(BuzzerSQLBuilderTest.class);

    @Before
    public void setup()
    {
        LOG.info("Setting up factory for BuzzerSQLBuilderFactoryTest test class");
        factory=new BuzzerSQLBuilderFactoryImpl();
        sqlBuilder= (BuzzerSQLBuilder) factory.getSQLBuilderForDB(BuzzerDBType.GENERIC);
    }

    @Test
    public void createTableTest()throws Exception
    {
        DateTime startTime=new DateTime(new Date());
        for(int i=0;i<10000;i++)
        {
            String sql=sqlBuilder.createTable("ecom","customer")
                    .withColumn("email","varchar","255",Boolean.FALSE,Boolean.TRUE,null,Boolean.FALSE)
                    .withColumn("firstname","varchar","255",Boolean.TRUE,Boolean.FALSE,null,Boolean.FALSE)
                    .withColumn("lastname","varchar","255",Boolean.TRUE,Boolean.FALSE,null,Boolean.FALSE)
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
        for(int i=0;i<10000;i++)
        {
            String sql=sqlBuilder.dropTable("ecom","customer").toString();
        }

        DateTime endTime=new DateTime(new Date());
        int timeTaken= Seconds.secondsBetween(startTime,endTime).getSeconds();
        LOG.info("Execution time seconds - "+ timeTaken);
        Assert.that(Boolean.TRUE,"Execution finished.");
    }
}
