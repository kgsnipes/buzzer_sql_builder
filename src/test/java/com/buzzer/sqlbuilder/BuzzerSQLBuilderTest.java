package com.buzzer.sqlbuilder;

import com.buzzer.sqlbuilder.service.impl.BuzzerMySQLBuilder;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilder;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilderFactoryImpl;
import org.apache.log4j.Logger;
import org.junit.Before;


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
