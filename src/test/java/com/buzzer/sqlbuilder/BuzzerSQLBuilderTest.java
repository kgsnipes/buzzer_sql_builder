package com.buzzer.sqlbuilder;

import com.buzzer.sqlbuilder.impl.BuzzerMySQLBuilder;
import com.buzzer.sqlbuilder.impl.BuzzerSQLBuilder;
import com.buzzer.sqlbuilder.impl.BuzzerSQLBuilderFactoryImpl;
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
        sqlBuilder=factory.getSQLBuilderForDB(BuzzerDBType.GENERIC);
    }


}
