package service;

import beans.MainVoteBean;
import beans.VoteBean;
import beans.WechatUser;
import dao.MainVoteDao;
import dao.VoteDao;
import dao.WechatUserDao;
import etc.VoteListComparator;
import org.springframework.stereotype.Service;
import util.WechatUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by wukunguang on 15-8-11.
 */
public class GuestService {

    public static MainVoteBean getVoteListByMid(int mid){

        MainVoteBean mainVoteBean = new MainVoteBean();
        MainVoteDao mainVoteDao = new MainVoteDao();
        mainVoteBean = mainVoteDao.getMainVoteBeanById(mid);

        VoteListComparator w = new VoteListComparator();
        Collections.sort(mainVoteBean.getVoteBeanList(),w);
        return mainVoteBean;
    }


    public static boolean addVoteByVid(String vid,String user_id){


        WechatUser wechatUser = new WechatUser();

        MainVoteDao mainVoteDao = new MainVoteDao();
        String mid = mainVoteDao.getMidByVid(vid);

        wechatUser = WechatUtil.getUserByOPenId(user_id);   //通過OpenID拉取用戶数据




        WechatUserDao wechatUserDao = new WechatUserDao();
        if (wechatUser.getSubscribe() == 1){
            //如果ta关注了该微信号，那么通过投票。
            if (wechatUserDao.isConntentUser(user_id)){

                //如果包含了用户数据，那么选择更新用户数据
                wechatUserDao.updateUser2DB(wechatUser);


            }
            else {
                //否则就写入新的数据进数据库
                wechatUserDao.WriteUser2DB(wechatUser);
            }

            //开始投票
            //分别传入三个参数，OpenID(user_id),mid,vid
           return wechatUserDao.guestVoteByID(user_id,mid,vid);

        }

        return false;
    }



}
