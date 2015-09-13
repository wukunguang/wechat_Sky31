package dao;

import beans.MainVoteBean;
import beans.MuBean;
import beans.WechatUser;
import util.DBConnection;

import java.sql.*;

/**
 * Created by wukunguang on 15-8-8.
 */
public class WechatUserDao {

    /**
     * 本方法通是利用OpenID判断是否数据库是否存在同一数据。
     * @param openID
     * @return
     */
    public boolean isConntentUser(String openID){

        boolean isContent = false;
        Connection conn = DBConnection.getConnection();

        if (conn == null){
            return false;
        }

        try {
            PreparedStatement prep = conn.
                    prepareStatement("SELECT user_id FROM user " +
                            "WHERE user_id = '" + openID+"'");
            if (prep!=null){
                ResultSet rs = prep.executeQuery();
                while (rs.next()){
                    String resultStr = rs.getString("user_id");
                    isContent = resultStr.equals(openID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        finally {
            DBConnection.closeConn(conn);
        }

        return isContent;


    }

    public boolean isContentOpenID(String openID,String m_id){

        //

        boolean isContent =false;
        Connection connection = DBConnection.getConnection();
        if (connection == null){
            return false;
        }
        try {
            Statement statement = connection.createStatement();

            if (statement==null){
                return false;
            }
            ResultSet resultSet = statement.executeQuery("SELECT user_id FROM user NATURAL JOIN mu WHERE m_id ='"+m_id+"' AND user_id = '"+ openID+"'");

            while (resultSet.next()){
                String user_id = resultSet.getString("user_id");
                if (openID.equals(user_id)){
                    //DBConnection.closeConn(connection);
                    isContent = true;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBConnection.closeConn(connection);
        }



        return isContent;
    }

    /**
     * 通过关键字获取Mid。
     * @param keyword
     * @return
     */
    public String getMidByKeyword(String keyword){

        Connection connection = DBConnection.getConnection();
        String resultStr = null;

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT m_id FROM main WHERE keyword = '"+keyword+"'");
            while (resultSet.next()){

                resultStr = resultSet.getInt("m_id")+"";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBConnection.closeConn(connection);
        }

        return resultStr;
    }

    /**
     * 写入用户数据。
     * @param wechatUser
     */
    public void WriteUser2DB(WechatUser wechatUser){


        Connection connection = DBConnection.getConnection();
        try {

            if (connection!=null) {
                PreparedStatement prep = connection.prepareStatement("INSERT INTO user(user_id, subscribe, nickname, sex, language, city, province, country, headimgurl, subscribe_time) VALUES (" +
                        "?,?,?,?,?,?,?,?,?,?)");

                if (prep != null) {
                    prep.setString(1, wechatUser.getUser_id());
                    prep.setInt(2, wechatUser.getSubscribe());
                    prep.setString(3, wechatUser.getNickname());
                    prep.setInt(4, wechatUser.getSex());
                    prep.setString(5, wechatUser.getLangguage());
                    prep.setString(6, wechatUser.getCity());
                    prep.setString(7, wechatUser.getProvince());
                    prep.setString(8, wechatUser.getCountry());
                    prep.setString(9, wechatUser.getHeadimgurl());
                    prep.setString(10, wechatUser.getSubscribe_time());


                    //提交数据。
                    prep.executeUpdate();




                    System.out.println("执行写入操作");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库出现异常");
        }
        finally {
            DBConnection.closeConn(connection);
        }

    }

    public void updateUser2DB(WechatUser wechatUser){

        Connection connection = DBConnection.getConnection();

        try {
            if (connection!=null) {
                PreparedStatement prep = connection.prepareStatement("UPDATE user SET nickname = ?,headimgurl = ? ,subscribe_time = ? WHERE user_id = '"+wechatUser.getUser_id()+"'");

                if (prep != null) {
                    prep.setString(1, wechatUser.getNickname());
                    prep.setString(2, wechatUser.getHeadimgurl());
                    prep.setString(3, wechatUser.getSubscribe_time());
                    prep.executeUpdate();
                    System.out.println("执行更新操作");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBConnection.closeConn(connection);
        }


    }

    public void writeMu2DB(String openID,int mid){

        Connection connection = DBConnection.getConnection();

        try {

            if (connection!=null) {
                PreparedStatement prep = connection.prepareStatement("INSERT INTO mu(m_id, user_id, have_voted, total_voted)  VALUES (?,?,?,?)");
                prep.setInt(1,mid);
                prep.setString(2, openID);
                prep.setInt(3,0);
                prep.setInt(4,0);
                prep.executeUpdate();

            }

            } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBConnection.closeConn(connection);
        }

    }

    /**
     * 投票方法，必须在判定是否关注后执行。
     * @param openID
     * @param mid
     * @param vid
     * @return
     */
    public boolean guestVoteByID(String openID,String mid ,String vid){



        MainVoteDao mainVoteDao = new MainVoteDao();

        //System.out.println("vid="+vid);
        MainVoteBean mainVoteBean = mainVoteDao.getMainVoteBeanById(Integer.parseInt(mid));
        Connection connection = DBConnection.getConnection();
        boolean haveVoted = false;
        int mppv = mainVoteBean.getMppv();
        VoteDao voteDao = new VoteDao();


        if (connection==null){
            return false;
        }
        int mvppd = mainVoteBean.getMvppd();        //获取当前用户当天的投票限制。

        if (isContentOpenID(openID, mid)){

            //如过已经在此主体投过票，那么执行判定，判断是否小于当天最大投票限制
            EtcDao etcDao = new EtcDao();
            MuBean muBean = etcDao.getMuByID(mid, openID);
            int haveVote = Integer.parseInt(muBean.getHave_voted());    //该用户今天已投票数量
            int haveMaxVote = Integer.parseInt(muBean.getTotal_voted());    //该用户已经投票总数。对于当前主题。

            if (mvppd > haveVote && mppv >haveMaxVote){
                //小于当天投票限制。
                try {
                    PreparedStatement prep = connection.prepareStatement(
                    "UPDATE mu SET have_voted=have_voted+1, total_voted" +
                            "=have_voted+1 WHERE m_id ='"+mid+"'AND user_id ='"+openID+"'");

                    if (prep!=null){
                        prep.executeUpdate();


                        haveVoted = voteDao.addAVoteToVid(openID,vid);  //增加一个票数进入vote表。
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    haveVoted = false;
                }
                finally {
                    DBConnection.closeConn(connection);
                }
            }

            else {
                //否则投票失败
                return false;
            }

        }
        else {
            //如果不存在，那么生成一个Mubean，插入Mu表和Vote表。
            Connection conn = DBConnection.getConnection();

            if (conn==null){
                return false;
            }
            try {
                PreparedStatement prep = conn
                        .prepareStatement("INSERT INTO " +
                                "mu(m_id, user_id, have_voted, total_voted) " +
                                "VALUES (?,?,?,?)");
                if (prep!=null){
                    prep.setInt(1,Integer.parseInt(mid));
                    prep.setString(2, openID);
                    prep.setInt(3, 1);
                    prep.setInt(4, 1);
                    prep.executeUpdate();   //提交
                    haveVoted = voteDao.addAVoteToVid(openID,vid);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DBConnection.closeConn(connection);
            }

        }

        return haveVoted;

    }






}
