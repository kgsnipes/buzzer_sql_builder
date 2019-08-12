package com.buzzer.sqlbuilder;

import com.buzzer.sqlbuilder.impl.BuzzerMySQLBuilder;
import com.buzzer.sqlbuilder.impl.BuzzerSQLBuilder;
import com.buzzer.sqlbuilder.impl.BuzzerSQLBuilderFactoryImpl;
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
        LOG.info("Setting up factory junit test class");
        factory=new BuzzerSQLBuilderFactoryImpl();
    }

    @Test
    public void getGenericBuilder()
    {
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
