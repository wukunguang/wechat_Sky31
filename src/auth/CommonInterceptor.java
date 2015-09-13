package auth;

import javax.servlet.http.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Objects;

/**
 * Created by wukunguang on 15-8-5.
 */


public class CommonInterceptor extends HandlerInterceptorAdapter {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {



        String username = (String) request.getSession().getAttribute("username");
        String password = (String) request.getSession().getAttribute("password");

        ModelAndView modelAndView = new ModelAndView();
        if (username == null && password==null){

           // modelAndView.addObject()
            modelAndView.addObject("message","提示！");
            modelAndView.addObject("messageWarning","提示，请先登录！！");
            modelAndView.setViewName("/Login");
            request.getRequestDispatcher("/Login.jsp").forward(request,response);

            return false;


        }

        else {
            return true;
        }


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
              //加入当前时间



    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
