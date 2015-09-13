package util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by wukunguang on 15-7-28.
 */
public class TimeUtil {

    public static String times2Str(Timestamp ts){

        String datestr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);


        return datestr;
    }


    public static Timestamp str2Times(String s){
        Timestamp ts = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            ts = (Timestamp) sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ts;
    }

    public static Date times2Date(Timestamp timestamp){
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);

        SimpleDateFormat sdf = new SimpleDateFormat();

        Date d = null;

        try {
            d=new Date(sdf.parse(s).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;

    }


    public static int comparetime(String startTime, String endTime){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int status = 1;

        try {

            java.util.Date start = new java.util.Date(sdf.parse(startTime).getTime());
            java.util.Date end = new java.util.Date(sdf.parse(endTime).getTime());

             int temp = end.compareTo(new java.util.Date(System.currentTimeMillis())); //相等为0 ，大于为1，小于为-1

            if (temp==1){
                int temp2 =  start.compareTo(new java.util.Date(System.currentTimeMillis()));
                if (temp2 == 1){
                    status = -1;
                }
                else if (temp2 == -1){
                    status = 0;
                }
            }

            else if (temp == (-1)){
                status = 1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        //status 表示状态，-1为在这段时间之前，0为在这段时间之间，1为在这段时间之后
        return status;
    }




}
