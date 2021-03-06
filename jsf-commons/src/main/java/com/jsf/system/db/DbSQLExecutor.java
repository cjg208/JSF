package com.jsf.system.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL SQL执行器
 */
@Configuration
public class DbSQLExecutor {

    @Value("${spring.datasource.druid.url}")
    public String url;
    @Value("${spring.datasource.druid.username}")
    public String username;
    @Value("${spring.datasource.druid.password}")
    public String password;
    @Value("${spring.datasource.druid.driver-class-name}")
    public String driver;

    /**
     * @return 获取conn对象
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * @param driver
     * @param url
     * @param username
     * @param password
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConn(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 字段名列表
     *
     * @param conn
     * @param table
     * @return
     * @throws SQLException
     */
    public List<String> getFieldLsit(Connection conn, String table) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(table);
        pstmt.execute();
        List<String> columnList = new ArrayList<String>();    //存放字段
        ResultSetMetaData rsmd = pstmt.getMetaData();
        for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
            columnList.add(rsmd.getColumnName(i));            //把字段名放list里
        }
        return columnList;
    }

}
