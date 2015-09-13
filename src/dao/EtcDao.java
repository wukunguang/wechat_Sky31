package dao;

import beans.MuBean;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wukunguang on 15-8-1.
 */
public class EtcDao {


    public EtcDao (){


    }

    public boolean insertmv(int mainVoteIndex, int voteIndex){

        Connection conn = null;

        boolean isinsert = false;
        PreparedStatement prep = null;
        try {

            conn = DBConnection.getConnection();

            prep = conn.prepareStatement("INSERT INTO mv(v_id,m_id) VALUES (?,?)");

            if (prep!=null) {
                prep.setInt(1, voteIndex);
                prep.setInt(2, mainVoteIndex);

                prep.executeUpdate();
                isinsert = true;


            }

        }  catch (SQLException e) {

            e.printStackTrace();
            isinsert = false;
        }
        finally {
            try {
                if (prep!=null) {
                    prep.close();
                    prep = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConn(conn);
        }

        return isinsert;
    }


    public static boolean deleteMvByMid(int mid){

        Connection conn = DBConnection.getConnection();

        PreparedStatement prep = null;
        boolean isdelete = false;

        if (conn==null){
            return false;
        }

        try {
            prep = conn.prepareStatement("DELETE FROM mv WHERE m_id= "+mid);

            prep.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            isdelete =  false;
        }

        finally {
            if (prep!=null){
                try {
                    prep.close();
                    prep = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            DBConnection.closeConn(conn);
        }

        return true;
    }

    public static boolean deleteMuByMid(String mid){

        Connection connection = DBConnection.getConnection();
        if (connection ==null ){
            return false;
        }
        try {
            PreparedStatement prep = connection.prepareStatement("DELETE FROM mu WHERE m_id ="+mid);
            prep.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        finally {
            DBConnection.closeConn(connection);
        }


        return true;
    }




    public  MuBean getMuByID(String m_id,String user_id){

        //获取Mu表的值
        Connection connection = DBConnection.getConnection();
        MuBean muBean = null;
        if (connection==null){
            return null;
        }

        PreparedStatement prep= null;
        ResultSet resultSet = null;

        try {
            prep = connection.prepareStatement("SELECT * FROM mu WHERE m_id= '"+m_id+"' AND "+"user_id= '"+user_id+"'");

            if (prep!=null){
                resultSet = prep.executeQuery();
                while (resultSet.next()){
                     muBean = new MuBean();
                    muBean.setM_id(resultSet.getInt("m_id")+"");
                    muBean.setUser_id(resultSet.getString("user_id"));
                    muBean.setHave_voted(resultSet.getInt("have_voted") + "");
                    muBean.setTotal_voted(resultSet.getInt("total_voted")+"");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(resultSet!=null){
                try {
                    resultSet.close();
                    resultSet = null;
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
        }

        return muBean;
    }

}
