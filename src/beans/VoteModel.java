package beans;

import java.util.List;

/**
 * Created by wukunguang on 15-7-30.
 */
public class VoteModel {


    public void setVoteBeanList(List<VoteBean> voteBeanList) {
        this.voteBeanList = voteBeanList;
    }

    private List<VoteBean> voteBeanList;

    public VoteModel (){
        super();

    }

    public VoteModel (List<VoteBean> voteBeanList){
        super();

        this.voteBeanList = voteBeanList ;

    }

    public List<VoteBean> getVoteBeanList() {
        return voteBeanList;
    }
}
