package main.java.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class Start {

    private static Connection connection = null;
    private static final String MYSQL_URL = "120.24.53.107";
    private static final String MYSQL_USERNAME = "guizhou";
    private static final String MYSQL_PASSWORD = "fE0FMeKS4i!m^6m32gB";
    private static final String MYSQL_PORT = "3306";
    private static final String MYSQL_TABLE = "guizhou";
    public static final String MYSQL_CHARSET = "utf8";//数据库编码 linux用utf8

    /**
     * 初始化检查数据库
     */
    public static boolean Mysql_Start(){
        Date startDate = new Date();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        if (setConnection()) return false;//连接数据库
        if (!Update.initCreateTable()) return false;//数据库初始化失败
        Date endDate = new Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("本次MYSQL复位连接延时 " + span + " 毫秒");
        return true;
    }

    /**
     * 连接数据库
     */
    private static boolean setConnection(){
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + MYSQL_URL + ":" + MYSQL_PORT + "/" + MYSQL_TABLE,
                    MYSQL_USERNAME, MYSQL_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    /**
     * 断开数据库连接
     */
    public static void disConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        connection = null;
        System.out.println("数据库已断开连接...");
    }

    public static Connection getConnection() {
        return connection;
    }

    /**
     * 重新连接
     */
    public static boolean reConnection(){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        //关闭连接数据库
        if (setConnection()) return false;
        System.out.println("数据库重新连接成功!");
        return true;
    }

}
