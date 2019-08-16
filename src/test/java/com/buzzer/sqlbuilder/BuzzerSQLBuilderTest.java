package com.buzzer.sqlbuilder;

import com.buzzer.sqlbuilder.service.impl.BuzzerMySQLBuilder;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilder;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilderFactoryImpl;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;


public class BuzzerSQLBuilderTest {

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
        String sql=sqlBuilder.createTable("kcpq","customer")
                .withColumn("email","varchar","255",Boolean.FALSE,Boolean.TRUE,null,Boolean.FALSE)
                .withColumn("firstname","varchar","255",Boolean.TRUE,Boolean.FALSE,null,Boolean.FALSE)
                .withColumn("lastname","varchar","255",Boolean.TRUE,Boolean.FALSE,null,Boolean.FALSE)
                .toString();
        LOG.info("SQL generated "+ sql);
        Assert.that(sql.contains("customer"),"name of the table is available");
        
    }


}
