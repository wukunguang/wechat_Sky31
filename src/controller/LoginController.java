package controller;

import beans.UserBean;
import dao.LoginCheck;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by wukunguang on 15-6-12.
 */

@Controller
public class LoginController {

    @RequestMapping(value = "/index",method = RequestMethod.POST)

    public org.springframework.web.servlet.ModelAndView checkLogin(HttpServletRequest request,Model model){

        org.springframework.web.servlet.ModelAndView mv =new org.springframework.web.servlet.ModelAndView();

        LoginCheck loginCheck =new LoginCheck(request.getParameter("username"),request.getParameter("password"));



        if (loginCheck.check()){
            mv.addObject("message", "Hello World");
            UserBean ub = loginCheck.getUserBean();
            mv.addObject("bean", ub);
            HttpSession session =  request.getSession();

            session.setMaxInactiveInterval(60*15);

            session.setAttribute("username",request.getParameter("username"));
            session.setAttribute("password",request.getParameter("password"));
            mv.setViewName("admin/index");
        }

        else {
            mv.addObject("message", "hehehe");
            mv.setViewName("error");
        }


        return mv;

    }



}
