package com.buzzer.sqlbuilder.util;


import java.text.SimpleDateFormat;

public interface BuzzerSQLConstants {

    String SQL_MARKER_BOUNDARY="##";
    String SQL_MARKER_REGEX="("+SQL_MARKER_BOUNDARY+".*"+SQL_MARKER_BOUNDARY+")";
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
    String PERIOD=".";
    String EMPTY="";
    String END_STATEMENT_BRACKET_REGEX="(\\,\\s{0,}+\\)\\;)";
    String NON_PRINT_CHARACTERS_REGEX="\\p{C}";
    String AS="AS";
    String SINGLE_QUOTE="'";

    String ASTERISK="*";

    String SELECT="SELECT";
    String FROM="FROM";
    String WHERE="WHERE";
    String JOIN="JOIN";
    String INNER_JOIN="INNER JOIN";
    String FULL_OUTER_JOIN="FULL OUTER JOIN";
    String LEFT_JOIN="LEFT JOIN";
    String RIGHT_JOIN="RIGHT JOIN";
    String ON="ON";
    String GROUP_BY="GROUP BY";
    String ORDER_BY="ORDER BY";
    String DISTINCT="DISTINCT";
    String AND="AND";
    String OR="OR";
    String NOT="NOT";
    String IS="IS";
    String IN="IN";
    String LIKE="LIKE";
    String BETWEEN="BETWEEN";
    String UNION="UNION";
    String UNION_ALL="UNION ALL";


    String UPDATE="UPDATE";



    String CREATE_DATABASE_QUERY_FORMAT="CREATE DATABASE %s "+END_STATEMENT;
    String DROP_DATABASE_QUERY_FORMAT="DROP DATABASE %s "+END_STATEMENT;
    String USE_DATABASE_QUERY_FORMAT="USE DATABASE %s "+END_STATEMENT;


    String CREATE_TABLE="CREATE TABLE";
    String CREATE_TABLE_START=NEW_LINE+CREATE_TABLE+" %s "+START_BRACKET;
    String CREATE_TABLE_END=" "+END_BRACKET+END_STATEMENT;
    String DROP_TABLE_QUERY="DROP TABLE IF EXISTS";
    String DROP_TABLE_QUERY_FORMAT=DROP_TABLE_QUERY+" %s "+END_STATEMENT;
    String CREATE_TABLE_ENDING_FLAG="CREATE_TABLE_END_MARKER";
    String CREATE_TABLE_ENDING_MARKER=SQL_MARKER_BOUNDARY+CREATE_TABLE_ENDING_FLAG+SQL_MARKER_BOUNDARY;





    String MYSQL_AUTOINCREMENT="AUTO_INCREMENT";
    String MYSQL_AUTOINCREMENT_VALUE_FLAG="AUTO_INCREMENT_VALUE";
    String MYSQL_AUTOINCREMENT_VALUE_MARKER=SQL_MARKER_BOUNDARY+MYSQL_AUTOINCREMENT_VALUE_FLAG+"= %s "+SQL_MARKER_BOUNDARY;
    String MYSQL_AUTOINCREMENT_ALTER_STATEMENT=NEW_LINE+" ALTER TABLE %s AUTO_INCREMENT=%s ;";

    String CREATE_INDEX="CREATE INDEX";
    String CREATE_INDEX_QUERY_FORMAT=NEW_LINE+CREATE_INDEX+" %s ON %s (%s);";

    String CREATE_VIEW="CREATE VIEW";
    String CREATE_VIEW_QUERY_FORMAT=CREATE_VIEW+" %s "+AS;
    String CREATE_OR_REPLACE_VIEW="CREATE OR REPLACE VIEW";
    String CREATE_OR_REPLACE_VIEW_QUERY_FORMAT=CREATE_OR_REPLACE_VIEW+" %s "+AS;
    String DROP_VIEW_QUERY_FORMAT="DROP VIEW IF EXISTS %s ;";



    interface SQLOperators
    {
        String GT=">";
        String LT="<";
        String GE=">=";
        String LE="<=";
        String EQ="=";
        String NE="<>";
        String AND="AND";
        String OR="OR";
        String NOT="NOT";
        String IS="IS";
        String IN="IN";
        String LIKE="LIKE";
        String BETWEEN="BETWEEN";
    }

    interface DateFormats
    {
        interface SQL
        {
            SimpleDateFormat DATE=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat DATETIME=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat TIMESTAMP=DATETIME;
            SimpleDateFormat TIME=new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat YEAR_4=new SimpleDateFormat("yyyy");
            SimpleDateFormat YEAR_2=new SimpleDateFormat("yy");
        }
    }

}
