package dao;

import beans.UserBean;
import org.springframework.stereotype.Service;
import util.DBConnection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by wukunguang on 15-7-15.
 *
 * 用户检测
 */
@Service
public class LoginCheck {


    private String password;

    private String username;

    private UserBean ub;

    private Connection connection=null;
    private Statement prep=null;

    public LoginCheck(String username,String password){
        this.username = username;
        this.password = password;
        UserBean userBean =new UserBean();

        ub = userBean;




    }

    public boolean check(){
        ResultSet rs= null;

        boolean isAccess =false;
        try {


            connection = DBConnection.getConnection();


            prep = connection.createStatement();


            rs = prep.executeQuery("SELECT password FROM  manager WHERE username = " + "'" + username + "'");



        } catch ( SQLException e) {
            e.printStackTrace();
        }

        if (rs ==null){
            return  false;
        }
        else {
            try {
                if (rs.first()){
                   String truePassword = (String) rs.getObject("password");
                    if (password.equals("") || password.equals(" ") ){
                        isAccess = false;
                    }
                    else if (password.equals(truePassword)){
                        System.out.println("hehehe");
                        ub.setPassword(truePassword);
                        ub.setUser(username);

                        isAccess = true;
                    }
                }
                else {
                    isAccess = false;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }

        if (rs!=null){
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (prep!=null){
            try {
                prep.close();
                prep = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBConnection.closeConn(connection);


        return isAccess;


    }


    public UserBean getUserBean(){
        return ub;
    }
}
