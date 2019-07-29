package com.app.web.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.app.service.impl.SrService;
import com.app.service.impl.UserService;
import com.app.tools.FileHelper;
import com.app.tools.RandomString;
import com.app.tools.RandomValidateCode;
import com.baidu.aip.speech.AipSpeech;
import com.code.model.Problem;
import com.code.model.Response;
import com.code.model.Solution;
import com.code.model.User;

import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Controller
public class UserController {
	
	@Resource 
	private SrService srService;

	@Resource
	private UserService userService;
	
	FileHelper fh = new FileHelper();
	
	
    @RequestMapping(value="user/GetText",produces="text/html;charset=UTF-8")
    @ResponseBody
    private String GetText(HttpServletRequest request) {
    	Response response = new Response();
    	response.setSuccess(0);
    	String username = (String)request.getSession().getAttribute("username");
    	if(username==null)
    	{
    		response.setMsg("参数错误!");
            return JSONObject.fromObject(response).toString();
    	}
    	String type = request.getParameter("type");
    	int t = Integer.parseInt(type);
    	Problem pro =  srService.GetRanText(t);
    	JSONObject obj = JSONObject.fromObject(pro);
    	if(pro.getType()!=3)
    	{
    		String pinyin = pro.getPinyin();
        	String[] pinyinArr = pinyin.split(" ");
        	obj.remove("pinyin");
        	obj.put("pinyin", pinyinArr);
    	}
    	request.getSession().setAttribute("content", obj.getString("content"));
    	response.setReObj(obj);
    	response.setSuccess(1);
    	response.setMsg("成功获取文本");
    	return JSONObject.fromObject(response).toString();

    }
   
    @RequestMapping(value="user/GetHistoryMistake",produces="text/html;charset=UTF-8")
    @ResponseBody
    private String GetHistoryMistake(HttpServletRequest request)
    {
    	String username = (String)request.getSession().getAttribute("username");
    	if(username==null) return null;
    	List<Solution> solutions = srService.GetHistoryMistake(username);
    	net.sf.json.JSONArray arr = net.sf.json.JSONArray.fromObject(solutions);
    	for(int i=0;i<arr.size();i++)
    	{

    		if(arr.getJSONObject(i).getJSONObject("problem").getInt("type")!=3)
    		{
    			String pinyin = arr.getJSONObject(i).getJSONObject("problem").getString("pinyin");
    			arr.getJSONObject(i).getJSONObject("problem").remove("pinyin");
    			arr.getJSONObject(i).getJSONObject("problem").put("pinyin", pinyin.split(" "));
    		}
    	}
    	return arr.toString();
    }
    
    @RequestMapping(value="user/login",produces="text/html;charset=UTF-8")
    @ResponseBody
    private String Login(HttpServletRequest request)
    {
    	Response response = new Response();
        response.setSuccess(0);
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	if(username==null || password==null)
    	{
    		response.setMsg("参数错误!");
            return JSONObject.fromObject(response).toString();
    	}
    	int status = userService.Login(username, password);
    	if(status==-2)
    		response.setMsg("用户名不存在");
    	else if(status==-1)
    		response.setMsg("密码错误");
    	else
    	{
    		response.setMsg("登录成功");
    		response.setSuccess(1);
    		request.getSession().setAttribute("username", username);
    		request.getSession().setAttribute("password", password);
    	}
    	return JSONObject.fromObject(response).toString();
    }
    //@RequestParam("file")CommonsMultipartFile partFile 
    @RequestMapping(value="load/uploadMav",produces="text/html;charset=UTF-8")
    @ResponseBody
    private String saveMav(@RequestParam("file")CommonsMultipartFile partFile ,@RequestParam("content")String oldContent,HttpServletRequest request) {
    	try {
    		String username = (String)request.getSession().getAttribute("username");
    		//String oldContent = (String)request.getSession().getAttribute("content");
    		if(username==null || oldContent==null)
    		{
    			Response response = new Response();
                response.setMsg("参数错误!");
                response.setSuccess(0);
                return JSONObject.fromObject(response).toString();
    		}

            String path = request.getServletContext().getRealPath("\\uploadWav") + "\\"  + RandomString.getRandomString(18) + ".wav";
            File file = new File(path);
            
            InputStream inputStream = partFile.getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, file); 
            if(inputStream!=null){
                inputStream.close();
            }
    	//	String path = request.getServletContext().getRealPath("\\uploadWav") + "\\ZYWM6LpytxGcFMC8qx.wav";
            JSONObject obj = srService.recognize(path, oldContent,username);
             
            Response response = new Response();
            response.setMsg("后台发生错误!");
            response.setSuccess(0);
            
            System.out.println(obj.toString()); 
            
            if(obj.getInt("type")==0)
            {
            	if(obj.getString("msg").contains("speech quality error"))
            	{
            		response.setMsg("您的录音不清晰，请重试!");
            	}
            	return JSONObject.fromObject(response).toString();
            	
            }
            
            String vPath = srService.GetFilePath(request.getServletContext().getRealPath(""), oldContent);
            System.out.println("vPath=" + vPath);

            obj.put("path", vPath);
            System.out.println(obj.toString());
            response.setSuccess(1);
            response.setReObj(obj);
            response.setMsg("获取成功");
            System.out.println(JSONObject.fromObject(response).toString());
            
            return JSONObject.fromObject(response).toString();

            
        } catch (Exception e) {
            e.printStackTrace();
            Response response = new Response();
            response.setMsg("后台发生错误!");
            response.setSuccess(0);
            return JSONObject.fromObject(response).toString();
        } 
    }
    
    @RequestMapping(value="load/uploadMav1",produces="text/html;charset=UTF-8")
    @ResponseBody
    private String saveMav1(@RequestParam("file")CommonsMultipartFile partFile ,@RequestParam("content")String oldContent,HttpServletRequest request) {
    	try {
    		String username = (String)request.getSession().getAttribute("username");
    		//String oldContent = (String)request.getSession().getAttribute("content");
    		if(username==null || oldContent==null)
    		{
    			Response response = new Response();
                response.setMsg("参数错误!");
                response.setSuccess(0);
                return JSONObject.fromObject(response).toString();
    		}

            String path = request.getServletContext().getRealPath("\\uploadWav") + "\\"  + RandomString.getRandomString(18) + ".wav";
            File file = new File(path);
            
            InputStream inputStream = partFile.getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, file); 
            if(inputStream!=null){
                inputStream.close();
            }
    	//	String path = request.getServletContext().getRealPath("\\uploadWav") + "\\ZYWM6LpytxGcFMC8qx.wav";
            JSONObject obj = srService.recognize1(path, oldContent,username);
             
            Response response = new Response();
            response.setMsg("后台发生错误!");
            response.setSuccess(0);
            
            System.out.println(obj.toString()); 
            
            if(obj.getInt("type")==0)
            	return JSONObject.fromObject(response).toString();
            
            String vPath = srService.GetFilePath(request.getServletContext().getRealPath(""), oldContent);
            System.out.println("vPath=" + vPath);

            obj.put("path", vPath);
            System.out.println(obj.toString());
            response.setSuccess(1);
            response.setReObj(obj);
            response.setMsg("获取成功");
            System.out.println(JSONObject.fromObject(response).toString());
            
            return JSONObject.fromObject(response).toString();

            
        } catch (Exception e) {
            e.printStackTrace();
            Response response = new Response();
            response.setMsg("后台发生错误!");
            response.setSuccess(0);
            return JSONObject.fromObject(response).toString();
        } 
    }
    
    
    
	

}
