package service;

import beans.MainVoteBean;
import dao.MainVoteDao;

import java.util.List;

/**
 * Created by wukunguang on 15-8-1.
 */
public class MainVoteService {

    public MainVoteService(){


    }


    public List<MainVoteBean> getMainVotelist(){

        MainVoteDao mainVoteDao = new MainVoteDao();

        List<MainVoteBean> mainVoteBeanList = mainVoteDao.readVoteList4DB();

        return mainVoteBeanList;
    }


    public MainVoteBean getMainVoteById(int m_id){

        MainVoteDao mainVoteDao = new MainVoteDao();

        return mainVoteDao.getMainVoteBeanById(m_id);
    }


    public boolean deleteMainVoteByMid(int mid){

        MainVoteDao mainVoteDao = new MainVoteDao();


        return mainVoteDao.deleteByMid(mid);
    }


}
