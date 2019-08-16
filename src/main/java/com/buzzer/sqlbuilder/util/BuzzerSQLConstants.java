package com.buzzer.sqlbuilder.util;

public interface BuzzerSQLConstants {
    String NEW_LINE="\n";
    String START_BRACKET="(";
    String END_BRACKET=")";
    String SPACE=" ";
    String NULL="NULL";
    String NOT_NULL="NOT NULL";
    String AUTO_INCREMENT_FLAG="AUTO_INCREMENT";
    String UNIQUE_FLAG="UNIQUE";
    String END_STATEMENT="; ";
    String COMMA=",";

    String CREATE_DATABASE_QUERY_FORMAT="CREATE DATABASE %s "+END_STATEMENT;
    String DROP_DATABASE_QUERY_FORMAT="DROP DATABASE %s "+END_STATEMENT;
    String USE_DATABASE_QUERY_FORMAT="USE DATABASE %s "+END_STATEMENT;
    String CREATE_TABLE_START="CREATE TABLE %s "+START_BRACKET;
    String CREATE_TABLE_END=" "+END_BRACKET+END_STATEMENT;

}
