package com.ruoshui.test;


import com.ruoshui.common.datasource.OracleSourse;
import com.ruoshui.common.domian.Column;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OracleToCk {

    public static void main(String [] arg){
        String tableName = "SD_FINANCE_PLAN";
        Connection conn  = OracleSourse.getconn("jdbc:oracle:thin:@//192.168.173.16:1521/orcl.mendale.com", "dbc", "dbc2397", "DBC");
        List<Column> OrcTableList = OracleSourse.getColumnComment(conn,tableName);
        int i = OrcTableList.size();
        if(i>0){
            //获取集合中最大的字段长度
            List<Integer> strlen = new ArrayList<>();
            String sql = "";
            for(Column column : OrcTableList){
                System.out.println("\""+column.getName()+"\""+",");
                System.out.println("");
                System.out.println("");
                System.out.println("");

            if(column.getType().toLowerCase().indexOf("Number".toLowerCase())>=0){
                sql+="nvl("+column.getName()+",0) "+column.getName()+" , ";
            }else{
                sql+=column.getName()+" , ";
            }


                strlen.add(column.getName().length());
            }
            System.out.println("-------------SQL语句------------------");
            System.out.println("select "+sql+" from "+ tableName);
            System.out.println("");
            System.out.println("");
            System.out.println("");

            Integer max = Collections.max(strlen);
            String kg="";
            for(Column column : OrcTableList){
                Integer I = max+10;
                Integer x = I-column.getName().length();
                for(int y= 0;y<x;y++){
                    kg+=" ";
                }

                if(column.getType().toLowerCase().indexOf("Integer".toLowerCase())>=0){
                    System.out.println(column.getName().toLowerCase()+kg+"Nullable(Int64)"+" comment"+"  "+"'"+column.getComment()+"',");
                }


                if(column.getType().toLowerCase().indexOf("Number".toLowerCase())>=0){

                    if(("".equals(column.getDataPrecision()) || column.getDataPrecision()==null) && ("".equals(column.getDataScale()) || column.getDataScale()==null)){
                        System.out.println(column.getName().toLowerCase()+kg+"Nullable(Int64)"+" comment"+"  "+"'"+column.getComment()+"',");
                    }else if((!"".equals(column.getDataPrecision()) || column.getDataPrecision()!=null) && ("".equals(column.getDataScale()) || column.getDataScale()==null)||(!"".equals(column.getDataPrecision()) || column.getDataPrecision()!=null) && (!"".equals(column.getDataScale()) || column.getDataScale()!=null)){
                            System.out.println(column.getName().toLowerCase()+kg+"Nullable(Float64)"+" comment"+"  "+"'"+column.getComment()+"',");
                    }
                }


                if(column.getType().toLowerCase().indexOf("FLOAT".toLowerCase())>=0){
                    System.out.println(column.getName().toLowerCase()+kg+"Nullable(Float64)"+" comment"+"  "+"'"+column.getComment()+"',");
                }

                if(column.getType().toLowerCase().indexOf("BINARY_FLOAT".toLowerCase())>=0){
                    System.out.println(column.getName().toLowerCase()+kg+"Nullable(Float64)"+" comment"+"  "+"'"+column.getComment()+"',");
                }

                if(column.getType().toLowerCase().indexOf("BINARY_DOUBLE".toLowerCase())>=0){
                    System.out.println(column.getName().toLowerCase()+kg+"Nullable(Float64)"+" comment"+"  "+"'"+column.getComment()+"',");
                }

                if(column.getType().toLowerCase().indexOf("DATE".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("TIMESTAMP".toLowerCase())>=0){
                    System.out.println(column.getName().toLowerCase()+kg+"Nullable(Date)"+" comment"+"  "+"'"+column.getComment()+"',");
                }

                if(column.getType().toLowerCase().indexOf("CHAR".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NCHAR".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NVARCHAR2".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NVARCHAR".toLowerCase())>=0){
                    System.out.println(column.getName().toLowerCase()+kg+"Nullable(String)"+" comment"+"  "+"'"+column.getComment()+"',");
                }
                I = 0;
                x = 0;
                kg = "";

            }
        }
    }
}
