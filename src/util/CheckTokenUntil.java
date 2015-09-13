package util;

import java.util.Arrays;

/**
 * Created by wukunguang on 15-8-5.
 */
public class CheckTokenUntil {

    public static final String token = "magicguang";

    public static String getToken(String timestamp,String nonce, String echostr){

        String[] s = new String[]{token,timestamp,nonce};

        Arrays.sort(s);

        StringBuffer sb = new StringBuffer();

        for (int x=0; x< s.length ; x++){
            sb.append(s[x]);

        }


        return org.apache.commons.codec.digest.DigestUtils.sha1Hex(sb.toString());
    }

}
