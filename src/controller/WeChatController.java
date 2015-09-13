package controller;

import beans.MessageBean;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.WechatService;
import util.CheckTokenUntil;
import util.MessageUntil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wukunguang on 15-8-5.
 */


@Controller

@RequestMapping("/wechat")

public class WeChatController {


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getOpen(HttpServletRequest request,Model model){

        String signature,timestamp,nonce,echostr;
        signature = request.getParameter("signature");
        timestamp = request.getParameter("timestamp");

        nonce = request.getParameter("nonce");
        echostr = request.getParameter("echostr");

        String str = CheckTokenUntil.getToken(timestamp,nonce,echostr);



        if (str .equals(signature)){


            return echostr;

        }

        else {

            model.addAttribute("message","error");
            return "error";
        }

    }

    @RequestMapping(method = RequestMethod.POST)


    public void getMessage(HttpServletRequest request,HttpServletResponse response){

        WechatService service = new WechatService();

        response.setCharacterEncoding("UTF-8");


        try {
            PrintWriter writer = response.getWriter();
            writer.println(service.switchActionByText(request));
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            System.out.println("出错");
        }


    }

}
