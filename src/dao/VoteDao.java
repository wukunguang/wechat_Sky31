package dao;

import util.DBConnection;
import beans.VoteBean;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wukunguang on 15-7-30.
 *
 * 本类为单项投票选项的DAO类，用于持久化数据和数据库访问用
 */
public class VoteDao {

    private VoteBean voteBean = null;

    private List<VoteBean> voteBeanList;

    public VoteDao(List<VoteBean> voteBeanList){
        this.voteBeanList = voteBeanList;
    }

    public VoteDao(){

    }

    public  boolean write2DB(String path ,String realpath,int mainVoteIndex){


        Connection conn = DBConnection.getConnection();

        if (conn==null){
            return false;
        }

        if (conn!=null){
            System.out.println("conn不是空的" +
                    "");
        }

        for(VoteBean voteBean: voteBeanList){
            if (voteBean!=null && voteBean.getVotetitle()!=null ){

                String coverurl = FileUtil.multipartFile2f(voteBean.getCoverfile(), realpath);   //获取封面
                PreparedStatement prep;
                try {
                    prep = conn.prepareStatement("INSERT INTO vote (v_title,introduction,cover_url" +
                            ",sort) VALUES (?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);

                    if (prep!=null){
                        prep.setString(1,voteBean.getVotetitle());
                        prep.setString(2,voteBean.getIntroduction());
                        prep.setString(3, coverurl);
                        prep.setInt(4, voteBean.getVotesort());

                        prep.executeUpdate();

                        ResultSet rs = prep.getGeneratedKeys();

                        if (rs.next()){

                            final int CUSTOMER_ID_COLUMN_INDEX = 1;
                            int voteIndex = rs.getInt(CUSTOMER_ID_COLUMN_INDEX);

                            EtcDao ed = new EtcDao();
                            boolean xx =  ed.insertmv(mainVoteIndex,voteIndex);

                        }

                    }
                } catch (SQLException e) {
                    e.printStackTrace();

                    return false;
                }





            }
        }

        DBConnection.closeConn(conn);
        return true;
    }

    public List<VoteBean> getVoteListByMainVoteID(int mainVoteID){



        Connection conn = null;
        Statement prep = null;
        ResultSet rs =null;


        try {

            conn = DBConnection.getConnection();
            prep = conn.createStatement();


            rs = prep.executeQuery("SELECT * FROM vote NATURAL JOIN mv WHERE m_id"+"="+mainVoteID);

            while (rs.next()){
                VoteBean vb = new VoteBean();
                vb.setV_id(rs.getInt("v_id"));
                vb.setVotetitle(rs.getString("v_title"));
                vb.setIntroduction(rs.getString("introduction"));
                vb.setVoted(rs.getInt("vote"));
                vb.setVotecover(rs.getString("cover_url"));
                vb.setVotesort(rs.getInt("sort")+"");

                voteBeanList.add(vb);

            }

        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
        finally {
            DBConnection.closeConn(conn);

        }


        return voteBeanList;

    }





    public List<VoteBean> getVoteBeanListByMid(int m_id){

        Connection conn = null;
        ResultSet rs =null;
        Statement stsm = null ;
        conn = DBConnection.getConnection();

        List<VoteBean> voteBeanList = new ArrayList<VoteBean>();

        if (conn==null){
            return null;
        }

        try {

            stsm = conn.createStatement();
            rs = stsm.executeQuery("SELECT * FROM mv NATURAL JOIN vote WHERE m_id='" + m_id+"'");

            while (rs.next()){
                VoteBean vb = new VoteBean();
                vb.setV_id(rs.getInt("v_id"));
                vb.setVotetitle(rs.getString("v_title"));
                vb.setVotecover(rs.getString("cover_url"));
                vb.setVoted(rs.getInt("voted"));
                vb.setIntroduction(rs.getString("introduction"));
                vb.setVotesort(rs.getInt("sort") + "");

                voteBeanList.add(vb);
            }


        }  catch (SQLException e) {
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
            if (stsm!=null){
                try {
                    stsm.close();
                    stsm = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBConnection.closeConn(conn);
        }

        return voteBeanList;
    }

    public boolean deleteVoteByVid(int vid){

        Connection conn = DBConnection.getConnection();

        if (conn==null){
            return false;
        }

        try {
            PreparedStatement prep = conn.prepareStatement("DELETE FROM vote WHERE v_id="+vid);

            prep.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBConnection.closeConn(conn);
        }

        return true;
    }


    public boolean deleteVoteByMid(int mid){

        Connection conn = DBConnection.getConnection();

        int successCount = 0;
        int size = 0;


        if (conn == null){
            return false;
        }

        try {

            PreparedStatement prep = conn.prepareStatement("SELECT v_id FROM mv NATURAL JOIN vote WHERE m_id = "+mid);

            ResultSet rs = prep.executeQuery();

            if (rs.next()){
                size++;
                if (deleteVoteByVid(rs.getInt("v_id"))){

                    successCount++;

                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBConnection.closeConn(conn);
        }

        if (successCount == size){
            return true; //如果相等，那么表示删除完毕
        }
        else {
            return false; //否则表示删除失败
        }

    }

    public  boolean addAVoteToVid(String user_id,String v_id){

        //插入投票结果进入vote表

        boolean isadd = false;
        Connection conn = DBConnection.getConnection();

        if (conn == null){
            return false;
        }

        try {

            PreparedStatement prep = conn.prepareStatement("UPDATE vote SET voted = voted+1 WHERE v_id ="+v_id);
           if (prep!=null){
               prep.executeUpdate();
               isadd = true;
           }

        } catch (SQLException e) {
            e.printStackTrace();
            isadd = false;
        }
        finally {
            DBConnection.closeConn(conn);
        }

        return isadd;
    }

}
