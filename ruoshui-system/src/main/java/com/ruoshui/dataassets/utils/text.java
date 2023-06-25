package com.ruoshui.dataassets.utils;

import org.junit.Before;

import java.sql.*;
import java.util.Properties;

public class text {



    private static Connection connection;

    @Before
    public void init() throws Exception {

        String driver ="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://127.0.0.1:3306/";
        String username="root";
        String password="root";

        Properties props = new Properties();
        props.put("remarksReporting","true");//获取数据库的备注信息
        props.put("user",username);
        props.put("password",password);

        //1.获取连接
        Class.forName(driver);//注册驱动
        connection = DriverManager.getConnection(url,props);
    }


    public static void main(String[] arg) throws SQLException, ClassNotFoundException {
        String driver ="com.mysql.cj.jdbc.Driver";
        String url="jdbc:mysql://127.0.0.1:3306/";
        String username="root";
        String password="root";

        Properties props = new Properties();
        props.put("remarksReporting","true");//获取数据库的备注信息
        props.put("user",username);
        props.put("password",password);

        //1.获取连接
        Class.forName(driver);//注册驱动
        connection = DriverManager.getConnection(url,props);

        //1.获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        //2.获取所有数据库列表
        ResultSet rs = metaData.getCatalogs();
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        rs.close();
        connection.close();


    }
}
