package com.buzzer.sqlbuilder;

import com.buzzer.sqlbuilder.service.SQLBuilder;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilderFactoryImpl;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;


public class BuzzerMySQLBuilderTest {

    BuzzerSQLBuilderFactoryImpl factory;
    SQLBuilder sqlBuilder;
    Logger LOG=Logger.getLogger(BuzzerMySQLBuilderTest.class);

    @Before
    public void setup()
    {
        LOG.info("Setting up factory for BuzzerSQLBuilderFactoryTest test class");
        factory=new BuzzerSQLBuilderFactoryImpl();
        sqlBuilder= factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
    }

    @Test
    public void createTableTest()throws Exception
    {
        String sql=sqlBuilder.createTable("ecom","customer")
                .withColumn("pk","bigint",null,Boolean.FALSE,Boolean.TRUE,null,Boolean.FALSE)
                .withColumn("email","varchar","255",Boolean.FALSE,Boolean.TRUE,null,Boolean.FALSE)
                .withColumn("firstname","varchar","255",Boolean.TRUE,Boolean.FALSE,null,Boolean.FALSE)
                .withColumn("lastname","varchar","255",Boolean.TRUE,Boolean.FALSE,null,Boolean.FALSE)
                .withAutoIncrementValue("pk",new Long(1000))
                .withIndexOnColumns("customer_email_index","email")
                .withIndexOnColumns("customer_name_index","firstname","lastname")
                .toString();
        LOG.info("SQL generated - "+ sql);
        Assert.that(sql.contains("customer"),"name of the table is available");

    }

    @Test
    public void createTableWithDropTest()throws Exception
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
        LOG.info("SQL generated - "+ sql);
        Assert.that(sql.contains("customer"),"name of the table is available");

    }

    @Test
    public void dropTableTest()throws Exception
    {
        String sql=sqlBuilder.dropTable("ecom","customer").toString();
        LOG.info("SQL generated - "+ sql);
        Assert.that(sql.contains("IF EXISTS"),"drop table statement has if exists");
        Assert.that(sql.contains("DROP TABLE"),"drop table statement has drop table");

    }

    @Test
    public void selectAllQueryTest()throws Exception
    {
        String sql=sqlBuilder.selectAll().fromTable("ecom.customers","current_customers")
                .toString();
        LOG.info("SQL generated - "+ sql);
        Assert.that(sql.contains("ecom.customers"),"name of the table is available");

    }

    @Test
    public void selectWithColumnsQueryTest()throws Exception
    {
        String sql=sqlBuilder.selectColumns(new String[]{"email","name","age"}).fromTable("ecom.customers","current_customers")
                .toString();
        LOG.info("SQL generated - "+ sql);
        Assert.that(sql.contains("ecom.customers"),"name of the table is available");

    }


    @Test
    public void createDatabaseTest()throws Exception
    {
        String sql=sqlBuilder.createDatabase("ecom")
                .toString();
        LOG.info("SQL generated - "+ sql);
        Assert.that(sql.contains("ecom"),"name of the table is available");

    }

    @Test
    public void dropDatabaseTest()throws Exception
    {
        String sql=sqlBuilder.dropDatabase("ecom")
                .toString();
        LOG.info("SQL generated - "+ sql);
        Assert.that(sql.contains("ecom"),"name of the table is available");

    }

    @Test
    public void useDatabaseTest()throws Exception
    {
        String sql=sqlBuilder.useDatabase("ecom")
                .toString();
        LOG.info("SQL generated - "+ sql);
        Assert.that(sql.contains("ecom"),"name of the table is available");

    }



}
