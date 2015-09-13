package beans;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by wukunguang on 15-7-30.
 */
public class VoteBean implements Serializable {

    private String votetitle = null;
    private String introduction = null;
    private String votecover =null ;
    private int voted = 0;
    private String votesort = null;
    private int v_id = 0;



    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public MultipartFile getCoverfile() {
        return coverfile;
    }

    public void setCoverfile(MultipartFile coverfile) {
        this.coverfile = coverfile;
    }

    private MultipartFile coverfile;

    public int getVotesort() {
        return Integer.parseInt(votesort);
    }

    public void setVotesort(String votesort) {
        this.votesort = votesort;
    }








    public String getVotetitle() {
        return votetitle;
    }

    public void setVotetitle(String votetitle) {
        this.votetitle = votetitle;
    }



    public int getVoted() {
        return voted;
    }

    public void setVoted(int voted) {
        this.voted = voted;
    }

    public String getVotecover() {
        return votecover;
    }

    public void setVotecover(String votecover) {
        this.votecover = votecover;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }






}
