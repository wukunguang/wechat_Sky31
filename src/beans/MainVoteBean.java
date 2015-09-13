package beans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wukunguang on 15-7-28.
 */
public class MainVoteBean implements Serializable{

    private String title;
    private String keyWord;
    private String coverURL;
    private String Starttime;
    private String endTime;
    private int    mvppd = 0;
    private int    hovpd = 0;

    public int getMppv() {
        return mppv;
    }

    public void setMppv(int mppv) {
        this.mppv = mppv;
    }

    private int    mppv = 0;
    private String introduction;
    private int isFinish = 0;
    private int m_id =0;
    private List<VoteBean> voteBeanList;



    public List<VoteBean> getVoteBeanList() {
        return voteBeanList;
    }

    public void setVoteBeanList(List<VoteBean> voteBeanList) {
        this.voteBeanList = voteBeanList;
    }


    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }


    public String isFinish() {
       //状态转化
        switch (isFinish){

            case -1:
                return "未开始";
            case 0 :
                return "进行中";
            case 1 :
                return "已结束";
            default:
                return "已结束";
        }

    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStarttime() {
        return Starttime;
    }

    public void setStarttime(String starttime) {
        Starttime = starttime;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public int getMvppd() {
        return mvppd;
    }

    public void setMvppd(int mvppd) {
        this.mvppd = mvppd;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getHovpd() {
        return hovpd;
    }

    public void setHovpd(int hovpd) {
        this.hovpd = hovpd;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


}
