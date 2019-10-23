package JDBC;

import java.sql.Connection;
import java.sql.SQLException;
import  JDBC.ConnectPool;

public class Test {
    public static void main(String[] args) throws SQLException {
        long start= System.currentTimeMillis();
        for(int i=0;i<5;i++){
            Connection connection=jdbcUtil.getConnection();
            jdbcUtil.close(null,null,connection);
        }
        long end = System.currentTimeMillis();
        System.out.println("普通的连接5次用时"+(end-start));
        ConnectPool connectPool=new ConnectPool(10);
        connectPool.test(connectPool);//测试连接用时
    }

}
