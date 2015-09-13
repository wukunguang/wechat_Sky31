package service;

import beans.WallContentBean;
import beans.Wallmain;
import dao.WallDao;

import javax.print.DocFlavor;
import java.util.List;

/**
 * Created by wukunguang on 15-9-2.
 */
public class WallService {

    public static boolean createMainWall(Wallmain wallmain){
        WallDao wallDao = new WallDao();

        return wallDao.writeWallMain2DB(wallmain);

    }

    public static boolean delMainWall(String wid){
        WallDao wallDao = new WallDao();

        return wallDao.delWallMainByWid(wid);

    }

    public static boolean writeWallContent(String wid , WallContentBean wcb){
        WallDao wallDao = new WallDao();

        return wallDao.writeWallContent2DB(wid, wcb);
    }

    public static List<Wallmain> getWallMainList(){

        WallDao wallDao = new WallDao();

        return wallDao.getWallList();
    }

    public static Wallmain getWallMainByWid(String wid){
        WallDao wallDao = new WallDao();
        return wallDao.getWallmainByWid(wid);

    }

    /**
     * actionCode : 1是调用显示出的，0是调用没显示的，2是调用全部数据
     * @param wid
     * @param actionCode
     * @return
     */
    public static List<WallContentBean> getContentListByWid(String wid, int actionCode){

        WallDao wallDao = new WallDao();
        return wallDao.getisShowList(wid, actionCode);
    }


    public static boolean changeContent(String c_id,String i){
        System.out.println("change Show");
        WallDao wallDao = new WallDao();


        return wallDao.changeShowStateByCid(c_id, i);
    }

    public static List<WallContentBean> getUserListByWid(String wid){

        WallDao wallDao = new WallDao();
        return wallDao.getUserListByWid(wid);
    }

}
