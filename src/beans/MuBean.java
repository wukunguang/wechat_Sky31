package beans;

import java.io.Serializable;

/**
 * Created by wukunguang on 15-8-11.
 */
public class MuBean implements Serializable {

    private String m_id;
    private String user_id;
    private String total_voted;
    private String have_voted;

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTotal_voted() {
        return total_voted;
    }

    public void setTotal_voted(String total_voted) {
        this.total_voted = total_voted;
    }

    public String getHave_voted() {
        return have_voted;
    }

    public void setHave_voted(String have_voted) {
        this.have_voted = have_voted;
    }
}
