package com.ruoshui.test;


import com.ruoshui.common.datasource.MySqlSource;
import com.ruoshui.common.datasource.OracleSourse;
import com.ruoshui.common.domian.Column;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class OracleToHive {

    public static void main(String [] arg){
        String tableName = "SD_GOODS_RETAILINFO";
        String name = "指标_商品零售宽表";
        String hiveTable = "dwd_index";
        String TbleName = hiveTable+"_"+tableName.toLowerCase();
        Boolean isPartition = Boolean.TRUE;
        String sqlString = "create table "+ TbleName +" \n ( \n";




//        Connection conn  = OracleSourse.getconn("jdbc:oracle:thin:@//192.168.172.113:1521/devdb", "E3", "E3", "E3");
        Connection conn  = OracleSourse.getconn("jdbc:oracle:thin:@//192.168.173.16:1521/orcl.mendale.com", "dbc", "dbc2397", "dbc");

        List<Column> TableList = OracleSourse.getColumnComment(conn,tableName);

        int i = TableList.size();
        if(i>0){
            //获取集合中最大的字段长度
            List<Integer> strlen = new ArrayList<>();
            String sql = "";
            String sql1 = "";
            for(Column column : TableList){
                if(column.getType().toLowerCase().indexOf("CHAR".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NCHAR".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NVARCHAR2".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NVARCHAR".toLowerCase())>=0){
                    sql1+="instr("+column.getName()+",'`')>0  or ";
                    sql+="replace(replace(replace(replace(c."+column.getName().toLowerCase()+",chr(13),'|'),chr(10),'|'),chr(32),'|'),'`','') "+ column.getName().toLowerCase() +" , ";
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

                if(column.getType().toLowerCase().indexOf("Integer".toLowerCase())>=0){
                    sqlString += column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',\n";
                    //System.out.println(column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',");
                }


                if(column.getType().toLowerCase().indexOf("Number".toLowerCase())>=0){

                    if(("".equals(column.getDataPrecision()) || column.getDataPrecision()==null) && ("".equals(column.getDataScale()) || column.getDataScale()==null)){
                        sqlString += column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',\n";
                        //System.out.println(column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',");
                    }else if((!"".equals(column.getDataPrecision()) || column.getDataPrecision()!=null) && ("".equals(column.getDataScale()) || column.getDataScale()==null)){
                        if(Integer.valueOf(column.getDataPrecision())==1){
                            sqlString += column.getName().toLowerCase()+kg+"tinyint"+" comment"+"  "+"'"+column.getComment()+"',\n";
                            //System.out.println(column.getName().toLowerCase()+kg+"tinyint"+" comment"+"  "+"'"+column.getComment()+"',");
                        }
                        if(Integer.valueOf(column.getDataPrecision())>=2 && Integer.valueOf(column.getDataPrecision())<=4){
                            sqlString += column.getName().toLowerCase()+kg+"smallint"+" comment"+"  "+"'"+column.getComment()+"',\n";
                            //System.out.println(column.getName().toLowerCase()+kg+"smallint"+" comment"+"  "+"'"+column.getComment()+"',");
                        }
                        if(Integer.valueOf(column.getDataPrecision())>=5 && Integer.valueOf(column.getDataPrecision())<=9){
                            sqlString += column.getName().toLowerCase()+kg+"int"+" comment"+"  "+"'"+column.getComment()+"',\n";
                            //System.out.println(column.getName().toLowerCase()+kg+"int"+" comment"+"  "+"'"+column.getComment()+"',");
                        }
                        if(Integer.valueOf(column.getDataPrecision())>=10 && Integer.valueOf(column.getDataPrecision())<=18){
                            sqlString += column.getName().toLowerCase()+kg+"Bigint"+" comment"+"  "+"'"+column.getComment()+"',\n";
                            //System.out.println(column.getName().toLowerCase()+kg+"Bigint"+" comment"+"  "+"'"+column.getComment()+"',");
                        }
                    }else if((!"".equals(column.getDataPrecision()) || column.getDataPrecision()!=null) && (!"".equals(column.getDataScale()) || column.getDataScale()!=null)){
                        sqlString += column.getName().toLowerCase()+kg+"decimal("+column.getDataPrecision()+","+column.getDataScale()+")"+" comment"+"  "+"'"+column.getComment()+"',\n";
                        //System.out.println(column.getName().toLowerCase()+kg+"decimal("+column.getDataPrecision()+","+column.getDataScale()+")"+" comment"+"  "+"'"+column.getComment()+"',");
                    }
                }


                if(column.getType().toLowerCase().indexOf("FLOAT".toLowerCase())>=0){
                    sqlString += column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',\n";
                    //System.out.println(column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',");
                }

                if(column.getType().toLowerCase().indexOf("BINARY_FLOAT".toLowerCase())>=0){
                    sqlString += column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',\n";
                    //System.out.println(column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',");
                }

                if(column.getType().toLowerCase().indexOf("BINARY_DOUBLE".toLowerCase())>=0){
                    sqlString += column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',\n";
                    //System.out.println(column.getName().toLowerCase()+kg+"double"+" comment"+"  "+"'"+column.getComment()+"',");
                }

                if(column.getType().toLowerCase().indexOf("DATE".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("TIMESTAMP".toLowerCase())>=0){
                    sqlString += column.getName().toLowerCase()+kg+"timestamp"+" comment"+"  "+"'"+column.getComment()+"',\n";
                    //System.out.println(column.getName().toLowerCase()+kg+"timestamp"+" comment"+"  "+"'"+column.getComment()+"',");
                }

//                if(column.getType().toLowerCase().indexOf("CHAR".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NCHAR".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NVARCHAR2".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NVARCHAR".toLowerCase())>=0){
//                    sqlString += column.getName().toLowerCase()+kg+"varchar("+Integer.valueOf(column.getCharLength())+")"+" comment"+"  "+"'"+column.getComment()+"',\n";
//                    //System.out.println(column.getName().toLowerCase()+kg+"varchar("+Integer.valueOf(column.getCharLength())+")"+" comment"+"  "+"'"+column.getComment()+"',");
//                }
                if(column.getType().toLowerCase().indexOf("CLOB".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("CHAR".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NCHAR".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NVARCHAR2".toLowerCase())>=0 || column.getType().toLowerCase().indexOf("NVARCHAR".toLowerCase())>=0){
                    sqlString += column.getName().toLowerCase()+kg+"String"+" comment"+"  "+"'"+column.getComment()+"',\n";
                    //System.out.println(column.getName().toLowerCase()+kg+"String"+" comment"+"  "+"'"+column.getComment()+"',");
                }
                I = 0;
                x = 0;
                kg = "";

            }
            if(isPartition){
                sqlString += ")partitioned by (dt string,type String)\n" +
                        "row format delimited fields terminated by '`'\n" +
                        "location  '/MendaleHadoop/user/hive/warehouse/mendale_cdm.db/"+TbleName+"';\n" +
                        "alter table "+TbleName+"\n" +
                        "    set tblproperties ('comment' = '"+name+"');";
            }else{
                sqlString += ")row format delimited fields terminated by '`'\n" +
                        "location  '/MendaleHadoop/user/hive/warehouse/mendale_cdm.db/"+TbleName+"';\n" +
                        "alter table "+TbleName+"\n" +
                        "    set tblproperties ('comment' = '"+name+"');";
            }

            System.out.println(sqlString);
        }
    }
}


