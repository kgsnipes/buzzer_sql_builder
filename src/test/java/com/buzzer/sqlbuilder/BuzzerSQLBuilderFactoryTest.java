package com.buzzer.sqlbuilder;

import com.buzzer.sqlbuilder.service.impl.BuzzerMySQLBuilder;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilder;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilderFactoryImpl;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;




public class BuzzerSQLBuilderFactoryTest {

    BuzzerSQLBuilderFactoryImpl factory;
    Logger LOG=Logger.getLogger(BuzzerSQLBuilderFactoryTest.class);

    @Before
    public void setup()
    {
        LOG.info("Setting up factory for BuzzerSQLBuilderFactoryTest test class");
        factory=new BuzzerSQLBuilderFactoryImpl();
    }

    @Test
    public void getGenericBuilder()
    {
        LOG.info("trying to fetch the generic sql builder");
        BuzzerSQLBuilder builder=factory.getSQLBuilderForDB(BuzzerDBType.GENERIC);
        Assert.that(builder.getClass().equals(BuzzerSQLBuilder.class) ,"instance retrieved is generic builder");
    }

    @Test
    public void getMySQLBuilder()
    {
        BuzzerSQLBuilder builder=factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
        Assert.that(builder.getClass().equals(BuzzerMySQLBuilder.class) ,"instance retrieved is MySQL builder");
    }

    @Test
    public void getMariaDBBuilder()
    {
        BuzzerSQLBuilder builder=factory.getSQLBuilderForDB(BuzzerDBType.MARIADB);
        Assert.that(builder.getClass().equals(BuzzerMySQLBuilder.class) ,"instance retrieved is MSQL builder and can be used for MariaDB");
    }
}
