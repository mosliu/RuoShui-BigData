package com.ruoshui.test;

public class Sqltest {
    public static void main(String[] arg){
        System.out.println("SELECT table_name AS TABLENAME, table_comment AS TABLECOMMENT FROM information_schema.tables where table_schema = '\" + dbName + \"' \"");
    }
}
