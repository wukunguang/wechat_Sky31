package dao;

import beans.VoteBean;
import util.DBConnection;
import util.TimeUtil;
import beans.MainVoteBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wukunguang on 15-7-28.
 * 本类为投票开启的DAO类，用于持久化数据和数据库访问用
 */
public class MainVoteDao {

    private MainVoteBean mainVoteBean;



    private int recentInsertKey = 0;

    public MainVoteDao(MainVoteBean mainVoteBean){
        this.mainVoteBean = mainVoteBean;
    }


    public MainVoteDao(){

    }

    public boolean write2DB(){

        Connection conn = null;
        PreparedStatement prep = null;

        boolean isWrite = false;
        try {

            conn = DBConnection.getConnection();

            prep = conn.prepareStatement("INSERT INTO main (title,cover_url,start_time,end_time,introduction,keyword,mvppd," +
                    "hovpd) VALUES (?,?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);




            if (prep!=null){

                System.out.println("正在插入");

                prep.setString(1, mainVoteBean.getTitle());
                prep.setString(2,mainVoteBean.getCoverURL());
                prep.setString(3, mainVoteBean.getStarttime());
                prep.setString(4, mainVoteBean.getEndTime());
                prep.setString(5,mainVoteBean.getIntroduction());
                prep.setString(6, mainVoteBean.getKeyWord());
                prep.setInt(7, mainVoteBean.getMvppd());
                prep.setInt(8, mainVoteBean.getHovpd());

                prep.executeUpdate();

                isWrite = true;

                ResultSet rs = prep.getGeneratedKeys();
                if (rs.next()){

                    final int CUSTOMER_ID_COLUMN_INDEX = 1;
                    recentInsertKey =rs.getInt(CUSTOMER_ID_COLUMN_INDEX);

                }
                if (rs!=null){
                    rs.close();
                    rs = null;
                }


            }



        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL语句出现异常");

            isWrite = false;
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
        System.out.println("执行成功");

        return isWrite;

    }


    public List<MainVoteBean> readVoteList4DB(){

        //返回投票的列表

        List<MainVoteBean> voteBeanList = new ArrayList<MainVoteBean>();

        Connection conn = null;
        ResultSet rs =null;

        Statement prep = null;

        try {


            conn = DBConnection.getConnection();

            if (conn==null){
                return  null;
            }
            prep = conn.createStatement();


            rs = prep.executeQuery("SELECT m_id, title, cover_url, start_time, end_time, introduction, keyword, mvppd, hovpd FROM main");


            while (rs.next()){

                MainVoteBean mb =new MainVoteBean();
                mb.setM_id(rs.getInt("m_id"));
                mb.setTitle(rs.getString("title"));
                mb.setCoverURL(rs.getString("cover_url"));

                String st = rs.getString("start_time");
                String et = rs.getString("end_time");
                int temp = TimeUtil.comparetime(st,et);

                mb.setStarttime(st);
                mb.setEndTime(et);
                mb.setIsFinish(temp);

                mb.setIntroduction(rs.getString("introduction"));
                mb.setKeyWord(rs.getString("keyword"));
                mb.setMvppd(rs.getInt("mvppd"));
                mb.setHovpd(rs.getInt("hovpd"));

                voteBeanList.add(mb);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            if (rs!=null){
                try {
                    rs.close();
                    rs =null;
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
            DBConnection.closeConn(conn);
        }


        return voteBeanList;
    }


    public MainVoteBean getMainVoteBeanById(int m_id){
        //返回单个投票的选项
        MainVoteBean mb =null;


        Connection conn = null;
        ResultSet rs =null;
        Context ctx = null;

        List<VoteBean> voteBeanList = new ArrayList<VoteBean>();
        try {

            conn = DBConnection.getConnection();


            if (conn == null){
                return null;
            }

            Statement prep = null;

            prep = conn.createStatement();

            rs = prep.executeQuery("SELECT * FROM main WHERE m_id="+m_id);

            while (rs.next()){
                mb = new MainVoteBean();

                mb.setM_id(rs.getInt("m_id"));
                mb.setTitle(rs.getString("title"));
                mb.setCoverURL(rs.getString("cover_url"));

                String st = rs.getString("start_time");
                String et = rs.getString("end_time");
                int temp = TimeUtil.comparetime(st,et);

                mb.setStarttime(st);
                mb.setEndTime(et);
                mb.setIsFinish(temp);

                mb.setIntroduction(rs.getString("introduction"));
                mb.setKeyWord(rs.getString("keyword"));
                mb.setMvppd(rs.getInt("mvppd"));
                mb.setHovpd(rs.getInt("hovpd"));
                mb.setMppv(rs.getInt("mppv"));
                VoteDao voteDao =new VoteDao();
                voteBeanList = voteDao.getVoteBeanListByMid(m_id);

                mb.setVoteBeanList(voteBeanList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            if (rs!=null){
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            DBConnection.closeConn(conn);


        }

        return mb;
    }


    public boolean deleteByMid(int m_id){


        Connection conn = DBConnection.getConnection();

        if (conn==null){
            return false;
        }


        boolean isDeleteMv = EtcDao.deleteMvByMid(m_id);

        boolean isDeleteMu = EtcDao.deleteMuByMid(m_id+"");


        try {
            PreparedStatement prep = conn.prepareStatement("DELETE FROM main WHERE m_id ="+ m_id);
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }



        return isDeleteMv && isDeleteMu;
    }


    public  String getMidByVid(String vid){

        //获取mid通过vid；

        Connection connection = DBConnection.getConnection();
        String mid = null;
        if (connection!=null){

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet;
                resultSet = statement.executeQuery("SELECT m_id FROM mv WHERE v_id ='"+vid+"'");

                while (resultSet.next()){
                    mid = resultSet.getInt("m_id")+"";
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DBConnection.closeConn(connection);
            }

        }
        return mid;

    }

    public String getMppvByMid(String mid){

        //获取最大投票数
        String resultStr = null;
        Connection conn = DBConnection.getConnection();
        if (conn == null){
            return null;
        }

        try {
            PreparedStatement prep = conn.prepareStatement("SELECT mppv FROM main WHERE m_id=" +mid);
            if (prep!=null){

                ResultSet rs = prep.executeQuery();
                while (rs.next()){
                    resultStr = rs.getInt("mppv")+"";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBConnection.closeConn(conn);
        }

        return resultStr;
    }


    public int getRecentInsertKey() {
        return recentInsertKey;
    }





}
