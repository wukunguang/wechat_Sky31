package dao;

import beans.WallContentBean;
import beans.Wallmain;
import util.DBConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wukunguang on 15-8-28.
 */
public class WallDao  {

    /**
     * 本方法用于获取微信墙事件的列表。
     * @return
     */
    public List<Wallmain> getWallList(){

        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        List<Wallmain> wallmainList = null;
        if (conn!=null){
            try {
                Statement statement = conn.createStatement();
                rs = statement.executeQuery("SELECT * FROM wallmain");
                wallmainList = new ArrayList<Wallmain>();

                while (rs.next()){
                    Wallmain wallmain = new Wallmain();
                    wallmain.setW_id(rs.getInt("w_id") + "");
                    wallmain.setCoverurl(rs.getString("coverurl"));
                    wallmain.setTitle(rs.getString("title"));
                    wallmainList.add(wallmain);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                wallmainList = null;
            }
            finally {
                DBConnection.closeConn(conn);
            }

        }

        return wallmainList;
    }

    /**
     * 本方法用于通过wid获取当前事件的详细内容。
     * @param wid
     * @return
     */
    public Wallmain getWallmainByWid(String wid){

        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        Wallmain wallmain = null;
        if (conn!=null){

            try {
                Statement statement = conn.createStatement();
                rs = statement.executeQuery("SELECT * FROM wallmain WHERE w_id = "+wid);


                while (rs.next()){
                    wallmain = new Wallmain();
                    wallmain.setTitle(rs.getString("title"));
                    wallmain.setW_id(rs.getInt("w_id") + "");
                    wallmain.setCoverurl(rs.getString("coverurl"));
                    wallmain.setWallContentBean(getWallContentListByWid(wid));

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DBConnection.closeConn(conn);
            }

        }

        return wallmain;
    }

    /**
     * 本方法用于通过wid获取文字信息等内容。即为当前活动的详细内容。
     * @param wid
     * @return
     */

    public List<WallContentBean> getWallContentListByWid(String wid){

        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        //创建一个集合对象。
        List<WallContentBean> contentBeanList = null;

        if (conn!=null){

            try {
                //实例化集合对象
                contentBeanList = new ArrayList<WallContentBean>();
                Statement statement = conn.createStatement();
                rs = statement.executeQuery("SELECT * FROM wallcache NATURAL JOIN wallcontent WHERE w_id ="+wid);

                while (rs.next()){
                    WallContentBean bean = new WallContentBean();

                    bean.setC_id(rs.getInt("c_id"));
                    bean.setHeadimgurl(rs.getString("headimgurl"));
                    bean.setIsshow(rs.getInt("isshow"));
                    bean.setTextcontent(rs.getString("textcontent"));
                    bean.setNickname(rs.getString("nickname"));
                    bean.setUser_id(rs.getString("user_id"));
                    contentBeanList.add(bean);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                contentBeanList = null;
            }
            finally {
                DBConnection.closeConn(conn);
            }
        }

        return contentBeanList;
    }

    public boolean writeWallMain2DB(Wallmain wallmain){

        Connection conn = DBConnection.getConnection();
        boolean iswrite = false;

       if (conn!=null){

           try {

               PreparedStatement prep = conn.prepareStatement("INSERT INTO wallmain(title)  VALUES (?)");
               if (prep!=null){

                   prep.setString(1,wallmain.getTitle());
                   prep.executeUpdate();
                   iswrite = true;
               }

           } catch (SQLException e) {
               e.printStackTrace();
               iswrite = false;
           }
           finally {
               DBConnection.closeConn(conn);
           }

       }

        return iswrite;
    }

    /**
     * 用来做微信墙话题删除功能的/。
     * @param wid
     * @return
     */
    public boolean delWallMainByWid(String wid){

        Connection conn = DBConnection.getConnection();

        boolean isCacheDel = false;
        boolean isWallMainDel = false;
        if (conn == null){
            return false;
        }
        try {

            Statement stmt  = conn.createStatement();
            isCacheDel = delWallCacheByWid(wid);
            stmt.executeUpdate("DELETE FROM wallmain WHERE w_id = "+wid);
            isWallMainDel = true;

        } catch (SQLException e) {
            e.printStackTrace();
            isWallMainDel = false;
        }
        finally {
            DBConnection.closeConn(conn);
        }

        //返回两个方法的相与值。用来判定是否删除成功。
        return isCacheDel && isWallMainDel;
    }

    public boolean writeWallCache2DB(String wid, WallContentBean wcb){

        Connection conn = DBConnection.getConnection();
        boolean iswrite = false;

        System.out.println("wid="+wid);
        System.out.println("c_id="+wcb.getC_id());
        if (conn == null){
            return false;
        }
        try {

            PreparedStatement prep = conn.prepareStatement("INSERT INTO wallcache(w_id, c_id) VALUES (?,?)");

            if (prep!=null){

                prep.setInt(1, Integer.parseInt(wid));
                prep.setInt(2, wcb.getC_id());
                prep.executeUpdate();
                iswrite = true;

            }

        } catch (SQLException e) {

            e.printStackTrace();
            iswrite = false;
        }
        finally {
            DBConnection.closeConn(conn);
        }

        return iswrite;
    }

    /**
     *
     * @param wid
     * @param wcb
     * @return
     */

    public boolean writeWallContent2DB(String wid, WallContentBean wcb){

        Connection conn = DBConnection.getConnection();
        boolean isWrite = false;


        if (conn == null){
            return false;
        }

        try {

            PreparedStatement prep = conn.prepareStatement("INSERT INTO wallcontent(nickname, user_id, " +
                    "headimgurl, textcontent, isshow,time) VALUES (?,?,?,?,?,?)"
                    ,PreparedStatement.RETURN_GENERATED_KEYS);
            if (prep!=null){
                prep.setString(1, wcb.getNickname());
                prep.setString(2, wcb.getUser_id());
                prep.setString(3, wcb.getHeadimgurl());
                prep.setString(4,wcb.getTextcontent());

                prep.setInt(5,0);
                prep.setString(6,wcb.getTime());
                prep.executeUpdate();

                isWrite = true;

                ResultSet rs = prep.getGeneratedKeys();

                if (rs.next()){

                    final int CUSTOMER_ID_COLUMN_INDEX = 1;
                    int cIndex = rs.getInt(CUSTOMER_ID_COLUMN_INDEX);
                    wcb.setC_id(cIndex);
                    isWrite = isWrite && writeWallCache2DB(wid,wcb);

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            isWrite = false;
        }
        finally {
            DBConnection.closeConn(conn);

        }


        return isWrite;
    }

    /**
     *
     * 通过wid删除内容和主题的关联表。
     * @param wid
     * @return
     */
    public boolean delWallCacheByWid(String wid){

        Connection conn = DBConnection.getConnection();
        boolean isdelete = false ;

        if (conn == null){
            return false;
        }

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM wallcache WHERE w_id = "+wid);
            isdelete = true;

        } catch (SQLException e) {
            e.printStackTrace();
            isdelete = false;
        }
        finally {
            DBConnection.closeConn(conn);
        }

        return isdelete;
    }

    public List<WallContentBean> getisShowList(String wid , int actionCode){

        List<WallContentBean> list = null;
        ResultSet rs = null;
        Connection conn = DBConnection.getConnection();
        if (conn==null){
            return null;
        }
        try {
            Statement stmt = conn.createStatement();
            switch (actionCode){
                case 0:rs = stmt.executeQuery("SELECT * FROM wallcache NATURAL JOIN wallcontent WHERE w_id = '"+wid+"'AND isshow = '0'");
                    break;
                case 1:rs = stmt.executeQuery("SELECT * FROM wallcache NATURAL JOIN wallcontent WHERE w_id = '"+wid+"' AND isshow = '1'");
                    break;
                case 2:rs = stmt.executeQuery("SELECT * FROM wallcache NATURAL JOIN wallcontent WHERE w_id='"+wid+"'");
                    break;
                default:rs = null;
            }

            if (rs!=null){
                list = new ArrayList<>();
                while (rs.next()){
                    WallContentBean wb = new WallContentBean();
                    wb.setC_id(rs.getInt("c_id"));
                    wb.setNickname(rs.getString("nickname"));
                    wb.setUser_id(rs.getString("user_id"));
                    wb.setIsshow(rs.getInt("isshow"));
                    wb.setTextcontent(rs.getString("textcontent"));
                    wb.setHeadimgurl(rs.getString("headimgurl"));
                    wb.setTime(rs.getString("time"));
                    list.add(wb);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        finally {
            DBConnection.closeConn(conn);
        }

        return list;

    }

    public String getWidByTitle(String title){
        ResultSet rs = null;
        String wid = null;
        Connection conn = DBConnection.getConnection();

        if (conn == null){
            return null;
        }
        try {
            Statement stmt = conn.createStatement();
            if (stmt!=null){
                rs = stmt.executeQuery("SELECT w_id FROM wallmain WHERE title = '"+title+"'");
                while (rs.next()){
                    wid = rs.getInt("w_id")+"";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBConnection.closeConn(conn);
        }

        return wid;
    }


    public boolean changeShowStateByCid(String c_id,String i){
        Connection conn = DBConnection.getConnection();

        System.out.println("changeShow dao");
        boolean ischange = false;

        if (conn == null){
            return false;
        }

        try {
            PreparedStatement prep = conn.prepareStatement
                    ("UPDATE wallcontent SET isshow = " +
                            "" + Integer.parseInt(i) + " WHERE c_id = '" + c_id + "'");

            if (prep!=null){
                prep.executeUpdate();
                ischange = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            ischange = false;
        }
        finally {
            DBConnection.closeConn(conn);
        }

        return ischange;
    }




    public List<WallContentBean> getUserListByWid(String wid){

        Connection conn = DBConnection.getConnection();

        List<WallContentBean> wb = null;
        if (conn==null){
            return null;
        }

        try {
            wb = new ArrayList<>();
            Statement stmt = conn.createStatement();

            ResultSet rs= stmt.executeQuery("SELECT DISTINCT(user_id) ,nickname , headimgurl FROM wallcontent NATURAL JOIN wallcache WHERE isshow = 1 AND w_id ="+wid);
            while (rs.next()){
                WallContentBean temp = new WallContentBean();
                temp.setNickname(rs.getString("nickname"));
                temp.setUser_id(rs.getString("user_id"));
                temp.setHeadimgurl(rs.getString("headimgurl"));
                wb.add(temp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DBConnection.closeConn(conn);
        }


        return wb;

    }

}
