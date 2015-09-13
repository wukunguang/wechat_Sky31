package etc;

import beans.VoteBean;

import java.util.Comparator;

/**
 * Created by wukunguang on 15-8-11.
 */
public class VoteListComparator implements Comparator<VoteBean> {


    @Override
    public int compare(VoteBean o1, VoteBean o2) {

        if (o1.getVotesort() != o2.getVotesort()){
            return o1.getVotesort()-o2.getVotesort();
        }
        else {
            if (o1.getV_id()!=o2.getV_id()){
                return o1.getV_id()-o2.getV_id();
            }
        }
        return 0;
    }

}
