package JDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectPool {
    private LinkedList<Connection> pool=new LinkedList<Connection>();
    int size ;
    //创建连接池对象时，务必传参，初始化连接池内的连接数。
    public ConnectPool(int size) throws SQLException {
        for(int i=0;i<size;i++){
            Connection c = jdbcUtil.getConnection();
            pool.add(c);
        }
    }

    public  synchronized Connection getConnection(){
        //池子空了，现在很忙，等其他的线程归还资源。
        while (pool.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return pool.removeFirst();
    }
    //有空位了，叫醒其他急不可耐的线程
    public synchronized  void releaseConnection(Connection connection){
        pool.addLast(connection);
        notifyAll();
    }
       class  WorkThread  extends  Thread {
        private  ConnectPool pool;
        public WorkThread( ConnectPool pool) {
            this.pool = pool;
        }
        public void run() {
            Connection c=pool.getConnection();
            try {
                c.createStatement();
                //写sql语句
            } catch (SQLException e) {
                e.printStackTrace();
            }
            releaseConnection(c);
        }
    }


    public  long test (ConnectPool connectPool) throws SQLException {
        long start2= System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            new WorkThread(connectPool).start();
        }
        long end2 = System.currentTimeMillis();
        System.out.println("用连接池的连接1000次用时" + (end2 - start2));
        return end2-start2;
    }

}


