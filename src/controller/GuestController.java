package controller;

import beans.MainVoteBean;
import dao.MainVoteDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.GuestService;
import util.WechatUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wukunguang on 15-8-10.
 */

@Controller

@RequestMapping(value = "/votelist")
public class GuestController {


    private String code;

    @RequestMapping("/list")
    public String showVoteList2User(@RequestParam String code,@RequestParam String mid, Model model,HttpServletRequest request){



        MainVoteBean mainVoteBean;
        //通过mid获取MainVoteBean对象
        mainVoteBean = GuestService.getVoteListByMid(Integer.parseInt(mid));
        MainVoteDao mainVoteDao = new MainVoteDao();



        request.getSession().setAttribute("openID", WechatUtil.getOpenidByCode(code));
        request.getSession().setMaxInactiveInterval(600);



        this.code = code;

        if (mainVoteBean.isFinish().equals("进行中")){
            model.addAttribute("votelist",mainVoteBean);

            model.addAttribute("mid",mid);
            model.addAttribute("message","hello world!");
            return "voteshow/votelist";

        }
        else if (mainVoteBean.isFinish().equals("已结束")){
            model.addAttribute("votelist",mainVoteBean);
            model.addAttribute("mid",mid);
            model.addAttribute("message","hello world!");
            return "voteshow/voteresult";
        }
        else {
            model.addAttribute("message","投票还未开始或者找不到数据");
            return "voteshow/voteresult";
        }


    }

    @RequestMapping(value = "/vote")

    public String voteTovID(@RequestParam String vid,HttpServletRequest request,Model model){
        //MainVoteDao mainVoteDao = new MainVoteDao();
        System.out.println("v_id="+vid);

        String openID = (String) request.getSession().getAttribute("openID");
        String message  = null;



        if (openID!=null) {
            if (GuestService.addVoteByVid(vid,openID)){

                message = "投票成功！";
                model.addAttribute("massageResult","投票成功！");

            }

            else {
                message = "投票失败,您未关注我们或者达到投票上限.";
                model.addAttribute("massageResult","投票失败,您未关注我们或者达到投票上限.");

            }
        }

        else {

            message = "请使用微信打开并关注我们，微信号:isky31.";
            model.addAttribute("massageResult","请使用微信打开并关注我们，微信号:isky31.");
        }

        return "voteshow/votestate";
    }

}
