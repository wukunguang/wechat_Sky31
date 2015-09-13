package util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by wukunguang on 15-7-15.
 */
public  class DBConnection {

    public static final String DBDRIVER = "com.mysql.jdbc.Driver";

    public static final String DBURL = "jdbc:mysql://localhost:3306/wechat_sky31";
    //连接数据库的用户名
    public static final String DBUSER = "root";
    //连接数据库的密码
    public static final String DBPASS = "";

    public synchronized static java.sql.Connection getConnection(){
        java.sql.Connection conn = null;
        Context ctx =null ;
        DataSource ds = null;


        try {
            Class.forName(DBDRIVER); //1、使用CLASS 类加载驱动程序
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS); //2、连接数据库
            System.out.println("測試");
        }  catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return  conn;
    }

    public static void closeConn(Connection connection){

        if (connection!=null){
            try {
                connection.close();
                connection = null ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
