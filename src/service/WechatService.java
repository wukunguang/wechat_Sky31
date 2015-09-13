package service;

import beans.MessageBean;
import beans.WallContentBean;
import beans.WechatUser;
import dao.WallDao;
import dao.WechatUserDao;
import net.sf.json.JSONArray;
import org.dom4j.DocumentException;
import util.DBConnection;
import util.MessageUntil;
import util.StringUtil;
import util.WechatUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wukunguang on 15-8-7.
 */
public class WechatService {


    public String switchActionByText(HttpServletRequest request) throws IOException, DocumentException {

        request.setCharacterEncoding("UTF-8");
        Map<String,String> map = new HashMap<String, String>();


        System.out.println("收到请求");


        MessageBean messageBean =new MessageBean();

        String textContent = null;

        map = MessageUntil.xmlToMap(request);
        textContent = map.get("Content");
        String type = map.get("MsgType");

        if (concernMsgType(type)){
            System.out.println(textContent);
            if (textContent.contains("投票")){
                System.out.println("投票");
                return replyToUser4Vote(map);
            }
            else{
                //如果不是投票，那么进行微信墙话题判定。
                System.out.println("微信");
                return replyTextmsg4Wall(map);
            }
        }
        return null;
    }


    /**
     * 确定消息类型/
     */
    private boolean concernMsgType(String type){

       return type.equals("text");
    }

    public String replyToUser4Vote(Map<String,String> map) {


        WechatUserDao wechatUserDao = new WechatUserDao();
        String type = map.get("MsgType");
        String openid = map.get("FromUserName");
        String keyword = map.get("Content").split("@")[1]; //获取"#"后的关键词。
                System.out.println("获取关键词"+keyword);
               String mid = wechatUserDao.getMidByKeyword(keyword); //获取关键词




                WechatUser wechatUser = WechatUtil.getUserByOPenId(openid);


                if (wechatUserDao.isConntentUser(openid)){
                    //如果openID在数据库存在的话，那么就更新用户数据

                    System.out.println("存在");
                    wechatUserDao.updateUser2DB(wechatUser);


                }
                else {
                    //否则就写入数据
                    System.out.println("不存在");
                    wechatUserDao.WriteUser2DB(wechatUser);
                    wechatUserDao.writeMu2DB(openid,Integer.parseInt(mid));

                }


        String wechatURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";



        return replayTextMsg(map, MessageUntil.reWriteURL(wechatURL, Integer.parseInt(mid)));
    }


    public boolean getOpenIDByTag(String openID,String mid){
        //判定openID是否存在
        WechatUserDao wechatUserDao = new WechatUserDao();



        return wechatUserDao.isContentOpenID(openID, mid);


    }

    private static String replyTextmsg4Wall(Map map){

        WallDao wallDao = new WallDao();

        WechatUser wechatUser = null;

        String title = null;

        title = (String) map.get("Content");
        title = title.split("#")[1].trim();

        String wid = wallDao.getWidByTitle(title);
        //判定关键字对应的ID是否存在。如果存在那么就执行写入操作。
        if (wid!=null){

            wechatUser = WechatUtil.getUserByOPenId((String) map.get("FromUserName"));


            if (wechatUser == null){
                return replayTextMsg(map,"话题发表失败╮(╯_╰)╭");
            }

            WallContentBean wb = new WallContentBean();
            wb.setHeadimgurl(wechatUser.getHeadimgurl());
            wb.setIsshow(0);

            wb.setNickname(wechatUser.getNickname());

            String content = (String) map.get("Content");

          //  content = JSONArray.fromObject(content);

            wb.setTextcontent(StringUtil.str2Text(content));
            wb.setUser_id(wechatUser.getUser_id());
            wb.setTime((String) map.get("CreateTime"));

            if (wallDao.writeWallContent2DB(wid, wb)){
                return replayTextMsg(map,"话题发表成功");


        }
            else {
                return replayTextMsg(map,"话题发表失败╮(╯_╰)╭");
            }
        }

        else {

            return replayTextMsg(map,"话题发表成功");
        }





    }



    private static String replayTextMsg(Map map,String content){



        MessageBean messageBean = new MessageBean();
        messageBean.setToUserName("<![CDATA["+  (String) map.get("FromUserName")+"]]>");
        messageBean.setFromUserName("<![CDATA["+(String) map.get("ToUserName")+"]]>");
        messageBean.setCreateTime(new Date(System.currentTimeMillis()).getTime());
        messageBean.setMsgType("<![CDATA["+(String) map.get("MsgType")+"]]>");
        messageBean.setContent("<![CDATA["+content+"]]>");


        String xmlstr = MessageUntil.messageToXml(messageBean);


        System.out.println(xmlstr);

        return xmlstr;
    }



}
