package com.ruoshui.test;


import com.ruoshui.common.datasource.MySqlSource;
import com.ruoshui.common.domian.Column;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MysqlToHive {

    public static void main(String [] arg){
        String tableName = "bas_brand";
        String database = "e3plus_goods";
        String name = "ods_e3_v2_品牌";
        String hiveTable = "ods_e3_v2";
        String TbleName = hiveTable+"_"+tableName.toLowerCase();
        Boolean isPartition = Boolean.FALSE;
        String sqlString = "create table "+ TbleName +" \n ( \n";




        //Connection conn  = OracleSourse.getconn("jdbc:oracle:thin:@//192.168.172.113:1521/devdb", "E3", "E3", "E3");
        //Connection conn  = OracleSourse.getconn("jdbc:oracle:thin:@//192.168.173.16:1521/orcl.mendale.com", "dbc", "dbc2397", "dbc");
        Connection conn  = MySqlSource.getconn("jdbc:mysql://192.168.173.142:3306/e3plus_order", "root", "VKkQrq7F5z3&cBE");

        //List<Column> TableList = OracleSourse.getColumnComment(conn,tableName);

        List<Column> TableList = MySqlSource.getMySqlColumnComment(conn,tableName,database);
        int i = TableList.size();
        if(i>0){
            //获取集合中最大的字段长度
            List<Integer> strlen = new ArrayList<>();
            String sql = "";
            String sql1 = "";
            for(Column column : TableList){
                if(column.getType().toLowerCase().indexOf("CHAR".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NCHAR".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NVARCHAR2".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NVARCHAR".toLowerCase())>=0){
                    sql1+="instr("+column.getName()+",'`')>0  or ";
                    sql+="replace(replace(replace(replace(c."+column.getName().toLowerCase()+",char(13),'|'),char(10),'|'),char(32),'|'),'`','') "+ column.getName().toLowerCase() +" , ";
                }else{
                    sql+="c."+column.getName()+" , ";
                }


                strlen.add(column.getName().length());
            }
            System.out.println("-------------SQL语句------------------");
            System.out.println("select "+sql+" from "+ tableName);
            System.out.println(sql1);




            Integer max = Collections.max(strlen);
            String kg="";
            for(Column column : TableList){
                Integer I = max+10;
                Integer x = I-column.getName().length();
                for(int y= 0;y<x;y++){
                    kg+=" ";
                }

                if(column.getType().toLowerCase().indexOf("bigint".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("int".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("smallint".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("tinyint".toLowerCase())>=0){
                    sqlString += column.getName().toLowerCase()+kg+"BIGINT"+" comment"+"  "+"'"+column.getComment()+"',\n";
                }

                if(column.getType().toLowerCase().indexOf("decimal".toLowerCase())>=0){
                    sqlString += column.getName().toLowerCase()+kg+"decimal("+column.getDataPrecision()+","+column.getDataScale()+")"+" comment"+"  "+"'"+column.getComment()+"',\n";
                }

                if(column.getType().toLowerCase().indexOf("double".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("float".toLowerCase())>=0){
                    sqlString += column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',\n";
                }

                if(column.getType().toLowerCase().indexOf("binary".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("varbinary".toLowerCase())>=0){
                    sqlString += column.getName().toLowerCase()+kg+"BINARY"+" comment"+"  "+"'"+column.getComment()+"',\n";
                }

                if(column.getType().toLowerCase().indexOf("char".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("varchar".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("mediumtext".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("text".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("datetime".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("time".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("timestamp".toLowerCase())>=0 ){
                    sqlString += column.getName().toLowerCase()+kg+"String"+" comment"+"  "+"'"+column.getComment()+"',\n";
                }

                if(column.getType().toLowerCase().indexOf("DATE".toLowerCase())>=0 && column.getType().toLowerCase().indexOf("tetime".toLowerCase())<=0){
                    System.out.println(column.getType().toLowerCase());
                    System.out.println(column.getType().toLowerCase().indexOf("DATE".toLowerCase()));
                    System.out.println(column.getType().toLowerCase().indexOf("tetime".toLowerCase()));
                    sqlString += column.getName().toLowerCase()+kg+"date"+" comment"+"  "+"'"+column.getComment()+"',\n";

                }


                I = 0;
                x = 0;
                kg = "";

            }
            if(isPartition){
                sqlString += ")partitioned by (dt string)\n" +
                        "row format delimited fields terminated by '`'\n" +
                        "location  '/MendaleHadoop/user/hive/warehouse/mendale_ods.db/"+TbleName+"';\n" +
                        "alter table "+TbleName+"\n" +
                        "    set tblproperties ('comment' = '"+name+"');";
            }else{
                sqlString += ")row format delimited fields terminated by '`'\n" +
                        "location  '/MendaleHadoop/user/hive/warehouse/mendale_ods.db/"+TbleName+"';\n" +
                        "alter table "+TbleName+"\n" +
                        "    set tblproperties ('comment' = '"+name+"');";
            }

            System.out.println(sqlString);
        }
    }
}


