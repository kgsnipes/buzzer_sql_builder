package com.buzzer.sqlbuilder.util;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class BuzzerSQLBuilderTestUtil {

    private static final Logger LOG= Logger.getLogger(BuzzerSQLBuilderTestUtil.class);

    public static void appendToFile(File f, String text)
    {
        CharSink chs = Files.asCharSink(f, Charsets.UTF_8, FileWriteMode.APPEND);
        try {
            chs.write(text);
        } catch (IOException e) {
            LOG.error("Exception while writing to file ",e);
        }
    }
}
