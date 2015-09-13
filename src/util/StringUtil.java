package util;

/**
 * Created by wukunguang on 15-8-10.
 */
public class StringUtil {


    public final static String Appid= "wxc5d217408956f8ea";
    public final static String AppSecret= "143ac50a4abb8a47c9ac8f330fc1972a";
    public final static String ACCESS_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    public final static String OPENID_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";


    public final static String MYURL = "http://magic-guang.tunnel.mobi/votelist/list";

    public final static String BASE_ACCESS_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";


    public static String str2Text(String str){

        StringBuffer sb = new StringBuffer();

        for (int x = 0; x<str.length(); x++){
            char temp = str.charAt(x);
            String s = null;
            switch (temp){
                case '<':s = "&lt;";
                    break;
                case '>':s = "&gt";
                    break;
                case ' ':s = "&nbsp;";
                    break;
                case '"':s = "&quot";
                    break;
                case '&':s = "&amp;";
                    break;
                default:s = temp+"";

            }
            sb.append(s);
        }

        return sb.toString();

    }

}
