package JDBC;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

/*
* 功能类，简化代码
* */
public class jdbcUtil {
    private static String url;
    private static String user;
    private static String password;//在配置文件里的密码是错的，o(*￣︶￣*)o。
    private static String driver;

    //类被加载时就执行静态代码块,把配置文件的信息加载到类中。
    static {
        try {
            Properties pro=new Properties();
            pro.load(new FileReader("src/jdbc.properties"));
            url=pro.getProperty("url");
            user=pro.getProperty("user");
            password=pro.getProperty("password");
            driver=pro.getProperty("driver");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //获取连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
    //释放连接
    public  static void close(ResultSet rs, Statement stm,Connection conn){
        try {
            if(rs!=null){
                rs.close();
            }
            if(stm!=null){
                stm.close();
            }
            if(conn!=null){
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
