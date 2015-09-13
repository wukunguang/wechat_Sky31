package controller;

import beans.*;
import dao.LoginCheck;
import dao.MainVoteDao;
import dao.VoteDao;

import etc.WallListComparator;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.portlet.ModelAndView;
import service.MainVoteService;
import service.WallService;
import service.WechatService;
import util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wukunguang on 15-7-19.
 */

@Controller
@RequestMapping("/admin")
public class ManagerController {



    @RequestMapping(value = "/votecreate")
    public String getVoteModel(HttpServletRequest request){

        HttpSession session = request.getSession();

        LoginCheck loginCheck =new LoginCheck((String)session.getAttribute("username"),(String)session.getAttribute("password"));
        ModelAndView mav = new ModelAndView();



            return "admin/votecreate";

    }

    @RequestMapping(value = "/submit" ,method = RequestMethod.POST)

    public String submitCreateVote(@RequestParam("upload-file") MultipartFile file,HttpServletRequest request,@ModelAttribute VoteModel voteModel){

        String url = "/resource/images/statics/",type =null,path=null;
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/resource/images/statics/");

        //int tt = voteModel.getVoteBeanList().size();


        if (!file.isEmpty()){
            try {


                type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
                path = url + System.currentTimeMillis() + type;

                String temp = request.getServletPath();


                System.out.println();

                File f =new File(realPath+"/"+System.currentTimeMillis()+type);

                FileUtils.copyInputStreamToFile(file.getInputStream(),f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //传入主要建立投票模型参数
        if (request!=null){
            System.out.println("正在传参");

            request.getSession().setMaxInactiveInterval(1440);
            String starttime = null;
            String endtime = null;

            MainVoteBean mb =new MainVoteBean();

            //获取表单数据
            mb.setTitle(request.getParameter("title"));
            mb.setCoverURL(path);
            mb.setKeyWord(request.getParameter("keyword"));

            starttime = request.getParameter("start-years")+"-"+request.getParameter("start-month")+"-"
                    +request.getParameter("start-day")+" "+request.getParameter("start-hour")+":"
                    +request.getParameter("start-minute")+":"+"00";

            endtime = request.getParameter("end-years")+"-"+request.getParameter("end-month")+"-"
                    +request.getParameter("end-day")+" "+request.getParameter("end-hour")+":"
                    +request.getParameter("end-minute")+":"+"00";

            mb.setStarttime(starttime);
            mb.setEndTime(endtime);

            mb.setHovpd(Integer.parseInt(request.getParameter("hovpd")));
            mb.setMvppd(Integer.parseInt(request.getParameter("mvppd")));
            mb.setMppv(Integer.parseInt(request.getParameter("mppv")));

            mb.setIntroduction(request.getParameter("introduction"));

            //实例化，插入javabean, 并存入数据库

            boolean isVsWrite2DB = false;   //投票选项的boolean
            boolean isMvWrite2DB = false;   //投票主主题的boolean
            MainVoteDao vd =new MainVoteDao(mb);

             isMvWrite2DB = vd.write2DB();

            int mainVoteIndex = vd.getRecentInsertKey();    //获取刚刚插入的投票话题的id；

            VoteDao vs;



            if (voteModel!=null){

                 vs =new VoteDao(voteModel.getVoteBeanList());

                 isVsWrite2DB = vs.write2DB(url,realPath,mainVoteIndex);

            }

            if (isMvWrite2DB && isVsWrite2DB){

                return "success";
            }
            else {
                return "error";
            }


        }

        return "error";
    }


    @RequestMapping(value = "/votelist")

    public String showVoteList(HttpServletRequest request,Model model){

        HttpSession session = request.getSession();

        LoginCheck loginCheck =new LoginCheck((String)session.getAttribute("username"),(String)session.getAttribute("password"));


        MainVoteService mainVoteService =new MainVoteService();

        List<MainVoteBean> mainVoteBeanList = mainVoteService.getMainVotelist();

        if (loginCheck.check()){
            if (mainVoteBeanList!=null){


                model.addAttribute("mainVoteBeanList", mainVoteBeanList);
                model.addAttribute("message", "为您找到" + mainVoteBeanList.size() + "条数据");
                UserBean ub = new UserBean();
                ub.setUser((String) request.getSession().getAttribute("username"));
                model.addAttribute("bean", ub);

            }

            else {
                model.addAttribute("message", "未找到数据╮(╯_╰)╭");

            }
            return "admin/votelist";
        }

        else {
            return "error";
        }




    }

    @RequestMapping(value = "/voteedit")


    public String showMainVoteDetail(@RequestParam String mid,Model model){

        MainVoteService service = new MainVoteService();
        MainVoteBean mainVoteBean = service.getMainVoteById(Integer.parseInt(mid));

        model.addAttribute("mainVote",mainVoteBean);

        return "admin/voteedit";

    }

    @RequestMapping(value ="/voteresult")
    public String showMainVoteResult(@RequestParam("mid") int mid,Model model){

        MainVoteService mainVoteService = new MainVoteService();
        MainVoteBean mb = new MainVoteBean();
        mb = mainVoteService.getMainVoteById(mid);



        model.addAttribute("mainVote",mb);
        model.addAttribute("voteList",mb.getVoteBeanList());


        return "admin/voteresult";
    }

    @RequestMapping(value = "/voteDelete")

    public String deleteVote(@RequestParam int mid,Model model,HttpServletRequest request){

        HttpSession session = request.getSession();
        MainVoteService mainVoteService = new MainVoteService();
        LoginCheck loginCheck =new LoginCheck((String)session.getAttribute("username"),(String)session.getAttribute("password"));
        if (loginCheck.check()){
            if (mainVoteService.deleteMainVoteByMid(mid)){

                model.addAttribute("message","操作成功！");

            }

            else {
                model.addAttribute("message","操作失败！");
            }
            return "redirect:/admin/votelist ";
        }

        else {
            return "error";
        }


    }

    @RequestMapping(value = "/wechatWall")

    public String wechatWall(Model model) {

        List<Wallmain> list = WallService.getWallMainList();
        model.addAttribute("WallMainList",list);

        return "/admin/wechatwall";
    }

    @RequestMapping(value = "/wechatWall/create" , method = RequestMethod.POST)
    public String wechatWallCreate(@ModelAttribute Wallmain wallmain,HttpServletRequest request){


        WallService.createMainWall(wallmain);

        return "redirect:/admin/wechatWall";

    }

    @RequestMapping(value = "/wechat/wallmanageshow")

    public String wechatWallManage(@RequestParam String wid,Model model){
        Wallmain wm = WallService.getWallMainByWid(wid);
        model.addAttribute("md", wm);

        return "/admin/wallmanage";
    }

    @RequestMapping(value = "/wechatWall/show")
    public String wechatWallShow(@RequestParam String wid,Model model){

       Wallmain wallmain =  WallService.getWallMainByWid(wid);

        if (wallmain != null){
            model.addAttribute("wall",wallmain);

        }
        else {
            return "error";
        }
        return "/admin/wallshow";
    }


    @RequestMapping(value = "/wechatwall/listdata",method = RequestMethod.GET)

    public  void getwallContent(@RequestParam String wid,@RequestParam String pages,@RequestParam String actionCode,HttpServletResponse response){


        //传入参数。获取总列表

        response.setContentType("application/json;charset=UTF-8");

        int nowPage = Integer.parseInt(pages);

        try {
            List<WallContentBean> wb = WallService.getContentListByWid(wid,Integer.parseInt(actionCode));

            Collections.sort(wb,new WallListComparator());

            //List<WallContentBean> wbList  = new ArrayList<WallContentBean>();

           // Collections.sort(wb,new WallListComparator());

          /*  Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int x=(nowPage-1)*15 ;x<wb.size() && x<(nowPage)*15 ;x++){
                Date date = new Date(Integer.parseInt(wb.get(x).getTime()));
                wb.get(x).setTime(format.format(date));

                wbList.add(wb.get(x));

            }
*/
            PrintWriter writer = response.getWriter();
            JSONArray jsonArray = JSONArray.fromObject(wb);
            writer.println(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/wechatwall/changeShow", method = RequestMethod.GET)

    public void changeContentShow(HttpServletResponse response,@RequestParam String cid,@RequestParam String ishow){

        response.setContentType("application/json;charset=UTF-8");


        PrintWriter writer = null;
        ResponseCode rs = new ResponseCode();
        try {
            writer = response.getWriter();

            if (WallService.changeContent(cid,ishow)) {

                rs.setResponseCode("true");

            }
            else {
                rs.setResponseCode("false");
            }
            JSONArray jsonArray = JSONArray.fromObject(rs);
            writer.println(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/wechatwall/cj",method = RequestMethod.GET)

    public void sentUserList(@RequestParam String wid , HttpServletResponse response){
        response.setContentType("application/json;charset=UTF-8");


        PrintWriter writer = null;
        List<WallContentBean> wb = new ArrayList<>();

        try {
            writer = response.getWriter();

            wb = WallService.getUserListByWid(wid);
            JSONArray jsonArray = JSONArray.fromObject(wb);
            writer.println(jsonArray.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
