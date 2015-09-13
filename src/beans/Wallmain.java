package beans;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wukunguang on 15-8-27.
 */
public class Wallmain implements Serializable{


    private String w_id = null;
    private String title = null;

    private String coverurl = null;

    private MultipartFile coverfile = null;


    private List<WallContentBean> wallContentBean = null;

    public MultipartFile getCoverfile() {
        return coverfile;
    }

    public void setCoverfile(MultipartFile coverfile) {
        this.coverfile = coverfile;
    }

    public String getW_id() {
        return w_id;
    }

    public void setW_id(String w_id) {
        this.w_id = w_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public List<WallContentBean> getWallContentBean() {
        return wallContentBean;
    }

    public void setWallContentBean(List<WallContentBean> wallContentBean) {
        this.wallContentBean = wallContentBean;
    }
}
