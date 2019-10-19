package com.buzzer.sqlbuilder;

import com.buzzer.sqlbuilder.dto.DateValue;
import com.buzzer.sqlbuilder.service.SQLBuilder;
import com.buzzer.sqlbuilder.service.impl.BuzzerSQLBuilderFactoryImpl;
import com.buzzer.sqlbuilder.util.BuzzerSQLBuilderTestUtil;
import com.buzzer.sqlbuilder.util.BuzzerSQLConstants;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.io.File;
import java.util.*;


public class BuzzerMySQLBuilderTest {

    BuzzerSQLBuilderFactoryImpl factory;
    SQLBuilder sqlBuilder;
    Logger LOG=Logger.getLogger(BuzzerMySQLBuilderTest.class);
    List<String> sqlGenerated;

    @Before
    public void setup()
    {
        LOG.info("Setting up factory for BuzzerSQLBuilderFactoryTest test class");
        sqlGenerated=new ArrayList<>();
        factory=new BuzzerSQLBuilderFactoryImpl();
        sqlBuilder= factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
    }

    @After
    public void end()throws Exception
    {
        sqlGenerated.forEach(str->BuzzerSQLBuilderTestUtil.appendToFile(new File("sqldump.txt"),str));
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
        sqlGenerated.add(sql);
        Assert.that(sql.contains("customer"),"name of the table is not available");

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
        sqlGenerated.add(sql);
        Assert.that(sql.contains("customer"),"name of the table is not available");

    }

    @Test
    public void dropTableTest()throws Exception
    {
        String sql=sqlBuilder.dropTable("ecom","customer").toString();
        LOG.info("SQL generated - "+ sql);
        sqlGenerated.add(sql);
        Assert.that(sql.contains("IF EXISTS"),"drop table statement has if exists");
        Assert.that(sql.contains("DROP TABLE"),"drop table statement has drop table");

    }

    @Test
    public void selectAllQueryTest()throws Exception
    {
        String sql=sqlBuilder.selectAll().fromTable("ecom.customers","current_customers")
                .toString();
        LOG.info("SQL generated - "+ sql);
        sqlGenerated.add(sql);
        Assert.that(sql.contains("ecom.customers"),"name of the table is not available");

    }

    @Test
    public void selectWithColumnsQueryTest()throws Exception
    {
        Map<String,Object> namedParams=new HashMap<>();
        namedParams.put("country","india");
        namedParams.put("state","TN");
        String sql=sqlBuilder.selectColumns(new String[]{"email","name","age"}).fromTable("ecom.customers","current_customers")
                .where("dob", BuzzerSQLConstants.SQLOperators.GE,DateValue.create(new Date(),BuzzerSQLConstants.DateFormats.SQL.DATETIME))
                .and("email",BuzzerSQLConstants.SQLOperators.LIKE,"%@gmail.com")
                .or("name",BuzzerSQLConstants.SQLOperators.LIKE,"randy%")
                .and("age",BuzzerSQLConstants.SQLOperators.BETWEEN,new String[]{"hello","world"})
                .and("city",BuzzerSQLConstants.SQLOperators.EQ,"?")
                .and("country",BuzzerSQLConstants.SQLOperators.EQ,"?country")
                .and("state",BuzzerSQLConstants.SQLOperators.EQ,"?state")
                .limit(10l,190890l)
                .positionalParameters(Arrays.asList(new String[]{"radiocity","chennai"}))
                .namedParameters(namedParams)
                .toString();
        LOG.info("SQL generated - "+ sql);
        sqlGenerated.add(sql);
        Assert.that(sql.contains("ecom.customers"),"name of the table is not available");

    }

    @Test
    public void selectWithInnerQueryTest()throws Exception
    {
        Map<String,Object> namedParams=new HashMap<>();
        namedParams.put("country","india");
        namedParams.put("state","TN");
        SQLBuilder innerQuery=factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
        innerQuery.selectColumns(new String[]{"points"}).fromTable("ecom.rewardpoints","rewards").where("customerID",BuzzerSQLConstants.SQLOperators.EQ,"kaushik@test.com");


        String sql=sqlBuilder.selectColumns(new String[]{"rewards.pointsearned"}).fromQuery(innerQuery,"rewards")
                .limit(10l,190890l)
                .toString();
        LOG.info("SQL generated - "+ sql);
        sqlGenerated.add(sql);
        Assert.that(sql.contains("ecom.rewardpoints"),"name of the table is not available");

    }



    @Test
    public void createDatabaseTest()throws Exception
    {
        String sql=sqlBuilder.createDatabase("ecom")
                .toString();
        LOG.info("SQL generated - "+ sql);
        sqlGenerated.add(sql);
        Assert.that(sql.contains("ecom"),"name of the table is available");

    }

    @Test
    public void dropDatabaseTest()throws Exception
    {
        String sql=sqlBuilder.dropDatabase("ecom")
                .toString();
        LOG.info("SQL generated - "+ sql);
        sqlGenerated.add(sql);
        Assert.that(sql.contains("ecom"),"name of the table is available");

    }

    @Test
    public void useDatabaseTest()throws Exception
    {
        String sql=sqlBuilder.useDatabase("ecom")
                .toString();
        LOG.info("SQL generated - "+ sql);
        sqlGenerated.add(sql);
        Assert.that(sql.contains("ecom"),"name of the table is available");

    }


    @Test
    public void insertWithColumnsTest()throws Exception
    {

        String sql=sqlBuilder.insertTo("ecom.customers").withColumns("name","age","city").withValues("kaushik",30,"Princeton")
                .toString();
        LOG.info("SQL generated - "+ sql);
        sqlGenerated.add(sql);
        Assert.that(sql.contains("ecom.customers"),"name of the table is available");

    }

    @Test
    public void runOnDB()throws Exception
    {
        //drop database if exists
        String sql=sqlBuilder.dropDatabaseIfExists("ecom").toString();
        LOG.info("SQL generated - "+ sql);
        BuzzerSQLBuilderTestUtil.getTestDBConnection().createStatement().execute(sql);
        Thread.sleep(TIME_LAG);

        //create database
        sqlBuilder= factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
        sql=sqlBuilder.createDatabase("ecom").toString();
        LOG.info("SQL generated - "+ sql);
        BuzzerSQLBuilderTestUtil.getTestDBConnection().createStatement().execute(sql);
        Thread.sleep(TIME_LAG);

        //use database
        sqlBuilder= factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
        sql=sqlBuilder.useDatabase("ecom").toString();
        LOG.info("SQL generated - "+ sql);
        BuzzerSQLBuilderTestUtil.getTestDBConnection().createStatement().execute(sql);
        Thread.sleep(TIME_LAG);

        //create a table
        sqlBuilder= factory.getSQLBuilderForDB(BuzzerDBType.MYSQL);
        sql=sqlBuilder.createTable("ecom","customer",Boolean.TRUE)
                .withColumn("pk","bigint",null,Boolean.FALSE,Boolean.TRUE,null,Boolean.FALSE)
                .withColumn("email","varchar","255",Boolean.FALSE,Boolean.TRUE,null,Boolean.FALSE)
                .withColumn("firstname","varchar","255",Boolean.TRUE,Boolean.FALSE,null,Boolean.FALSE)
                .withColumn("lastname","varchar","255",Boolean.TRUE,Boolean.FALSE,null,Boolean.FALSE)
                .withAutoIncrementValue("pk",new Long(1000))
                .withIndexOnColumns("customer_email_index","email")
                .withIndexOnColumns("customer_name_index","firstname","lastname")
                .toString();
        LOG.info("SQL generated - "+ sql);
        BuzzerSQLBuilderTestUtil.getTestDBConnection().createStatement().execute(sql);
        Thread.sleep(TIME_LAG);

        BuzzerSQLBuilderTestUtil.getTestDBConnection().close();


        Assert.that(true,"done");
    }

    static int TIME_LAG=10000;

}
