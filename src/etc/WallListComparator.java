package etc;

import beans.WallContentBean;

import java.util.Comparator;

/**
 * Created by wukunguang on 15-9-8.
 */
public class WallListComparator implements Comparator<WallContentBean> {

    //对WallContent进行以时间为主的排序。
    @Override
    public int compare(WallContentBean o1, WallContentBean o2) {

        if (!o1.getTime().equals(o2.getTime())){
            return Integer.parseInt(o2.getTime())-Integer.parseInt(o1.getTime());
        }

        return 0;
    }
}
