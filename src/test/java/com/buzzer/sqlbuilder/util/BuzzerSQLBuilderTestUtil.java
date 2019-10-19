package com.buzzer.sqlbuilder.util;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class BuzzerSQLBuilderTestUtil {

    private static final Logger LOG= Logger.getLogger(BuzzerSQLBuilderTestUtil.class);
    private static Connection connection;

    public static void appendToFile(File f, String text)
    {
        CharSink chs = Files.asCharSink(f, Charsets.UTF_8, FileWriteMode.APPEND);
        try {
            chs.write(BuzzerSQLConstants.NEW_LINE);
            chs.write(text);
        } catch (IOException e) {
            LOG.error("Exception while writing to file ",e);
        }
    }

    public static Connection getTestDBConnection() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        if(ObjectUtils.isEmpty(connection))
        {
            connection=DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=EST","root","root");
        }
        return connection;
    }
}
