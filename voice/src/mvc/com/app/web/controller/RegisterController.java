package com.app.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.tools.ServiceException;


@Controller
public class RegisterController {
    
   /* @Resource
    private RegisterValidateService service;
    
    @Resource 
    private ResetValidService resetService;
    
    @RequestMapping(value="/user/register",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView  load(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
        String action = request.getParameter("action");
        System.out.println("-----r----"+action);
        ModelAndView mav=new ModelAndView();

        if("register".equals(action)) {
           // ע��
        	System.out.println("[register]: Apply register!!!!!!!!!!!!");
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            service.processregister(email,username,password);//�����伤��
            mav.addObject("text","ע��ɹ�");
            mav.setViewName("/WEB-INF/jsp/register/register_success.jsp");
        	
        	
        } 
        
        else if("activate".equals(action)) {
            //����
            String email = request.getParameter("email");//��ȡemail
            String validateCode = request.getParameter("validateCode");//������
            try {
               service.processActivate(email , validateCode);//���ü����
                mav.setViewName("/WEB-INF/jsp/register/activate_success.jsp");
            } catch (ServiceException e) {
                request.setAttribute("message" , e.getMessage());
                mav.setViewName("/WEB-INF/jsp/register/activate_failure.jsp");
            }
            
        }
        else if("login".equals(action))
        {
        	request.getSession().setAttribute("username",request.getParameter("username"));
        	request.getSession().setAttribute("password", request.getParameter("password"));
        	return null;
        	//mav.setViewName("/profile.html");
        }
//        else if("reset".equals(action))
//        {
//        	String userName = request.getParameter("username");
//        	String Email = request.getParameter("email");
//        	resetService.ProcessResetApply(userName, Email);
//        	//�������� �����ڰ�������reset2��ַ
//        	
//        	mav.setViewName("/WEB-INF/jsp/reset/reset_apply_success.jsp");
//        }
//        else if("reset1".equals(action))
//        {
//        	String userName = request.getParameter("username");
//        	String passWord = request.getParameter("psw1");
//        	resetService.ProcessResetPassword(userName, passWord);
//        	mav.setViewName("/WEB-INF/jsp/reset/reset_change_success.jsp");
//        }
//        else if("reset2".equals(action))
//        {
//        	String userName = request.getParameter("username");
//        	String resetCode = request.getParameter("resetCode");
//        	resetService.ProcessResetActivate(userName, resetCode);
//        	mav.setViewName("/reset1.jsp");
//        	//�ⲿ�������� �ô��ж��Ƿ������������ ����������ת����reset1.jsp
//        }
        return mav;
    }
    */


}
