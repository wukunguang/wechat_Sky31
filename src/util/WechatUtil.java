package util;


import beans.WechatUser;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wukunguang on 15-8-8.
 */
public class WechatUtil {

    private final static String Appid= "wxc5d217408956f8ea";
    private final static String AppSecret= "143ac50a4abb8a47c9ac8f330fc1972a";
    private final static String ACCESS_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    private final static String OPENID_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    private static String accessToken =null;

    public static JSONObject doGetStr(String url) throws MalformedURLException {

        HttpClient httpClient =new DefaultHttpClient();
        HttpGet get = new HttpGet(url);

        JSONObject jsonObject =null;

        try {

            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity!= null){
                String result = EntityUtils.toString(entity,"UTF-8");

                jsonObject = JSONObject.fromObject(result);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    public static JSONObject doPost(String url,String outStr){

        DefaultHttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost(url);
        JSONObject jsonObject = null;

        post.setEntity(new StringEntity(outStr,"UTF-8"));
        try {
            HttpResponse response = client.execute(post);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");

            jsonObject = JSONObject.fromObject(result);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }


     private static String getAccessToken()  {



        String url = ACCESS_URL.replace("APPID",Appid).replace("APPSECRET",AppSecret);

         String acess_temp= null;

         System.out.println(url);

         JSONObject jsonObject = null;
         try {
             jsonObject = doGetStr(url);
         } catch (MalformedURLException e) {
             e.printStackTrace();
         }
         if (jsonObject!=null){



            acess_temp=(jsonObject.getString("access_token"));


        }

        return acess_temp;

    }





    private static void timeTask2GetToken(){

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getAccessToken();
            }
        },6000000);

    }

    public  static String getToken(){
        if (accessToken == null){
            getAccessToken();
            timeTask2GetToken();
            accessToken = getAccessToken();

            System.out.println(accessToken);

            return accessToken;
        }
        else {
            return accessToken;
        }



    }

    public static WechatUser getUserByOPenId(String openID){

        WechatUser wechatUser = new WechatUser();
        String token = getToken();
        System.out.println(token);
        String url = OPENID_URL.replace("ACCESS_TOKEN",token).replace("OPENID",openID);

        System.out.println(url);
        try {
            JSONObject jsonObject = doGetStr(url);
            System.out.println(jsonObject.size());
            if (jsonObject.size() > 3){
                wechatUser.setUser_id(openID);
                wechatUser.setSubscribe(jsonObject.getInt("subscribe"));
                wechatUser.setNickname(jsonObject.getString("nickname"));

                System.out.println(jsonObject.getString("nickname"));

                wechatUser.setSex(jsonObject.getInt("sex"));
                wechatUser.setLangguage(jsonObject.getString("language"));
                wechatUser.setCity(jsonObject.getString("city"));
                wechatUser.setProvince(jsonObject.getString("province"));
                wechatUser.setCountry(jsonObject.getString("country"));
                wechatUser.setHeadimgurl(jsonObject.getString("headimgurl"));


                wechatUser.setSubscribe_time(jsonObject.getInt("subscribe_time") + "");


                System.out.println("获取到json");
            }
            else {
                wechatUser = null;
                System.out.println("未获取到json");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            wechatUser = null;
        }



        return wechatUser;
    }


    public static String getOpenidByCode(String code){

        String url = StringUtil.BASE_ACCESS_URL.replace("APPID",Appid)
                .replace("SECRET",AppSecret).replace("CODE",code);

        System.out.println("url="+url);

        try {
            JSONObject jsonObject = doGetStr(url);

            System.out.println("CodeByJson--->"+code);

            System.out.println("OpenIDByJson--->"+jsonObject.size());

            return jsonObject.getString("openid");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return null;
    }

}
