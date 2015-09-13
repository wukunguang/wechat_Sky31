package beans;

import java.io.Serializable;

/**
 * Created by wukunguang on 15-8-8.
 */
public class WechatUser implements Serializable {

   private String user_id;
   private int subscribe = 0;
   private String nickname;
   private int sex ;
   private String langguage;
   private String city;
   private String province;
   private String country;
   private String headimgurl;
   private String subscribe_time;
   private String unionid;

   public String getUser_id() {
      return user_id;
   }

   public void setUser_id(String user_id) {
      this.user_id = user_id;
   }

   public int getSubscribe() {
      return subscribe;
   }

   public void setSubscribe(int subscribe) {
      this.subscribe = subscribe;
   }

   public String getNickname() {
      return nickname;
   }

   public void setNickname(String nickname) {
      this.nickname = nickname;
   }

   public int getSex() {
      return sex;
   }

   public void setSex(int sex) {
      this.sex = sex;
   }

   public String getLangguage() {
      return langguage;
   }

   public void setLangguage(String langguage) {
      this.langguage = langguage;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getProvince() {
      return province;
   }

   public void setProvince(String province) {
      this.province = province;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public String getHeadimgurl() {
      return headimgurl;
   }

   public void setHeadimgurl(String headimgurl) {
      this.headimgurl = headimgurl;
   }

   public String getSubscribe_time() {
      return subscribe_time;
   }

   public void setSubscribe_time(String subscribe_time) {
      this.subscribe_time = subscribe_time;
   }

   public String getUnionid() {
      return unionid;
   }

   public void setUnionid(String unionid) {
      this.unionid = unionid;
   }
}
