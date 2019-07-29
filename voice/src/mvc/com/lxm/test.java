package com.lxm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;

/**
 * @author Administrator
 *
 */
public class test {

    private static JSONObject createJSONObject(){   
        JSONObject jsonObject = new JSONObject();   
        jsonObject.put("username","huangwuyi");   
        jsonObject.put("sex", "男");   
        jsonObject.put("QQ", "999999999");   
        jsonObject.put("Min.score", new Integer(99));   
        jsonObject.put("nickname", "梦中心境");   
        return jsonObject;   
    }
    
    private List<Date> GetPreWeek()
    {
    	List<Date> list = new ArrayList<Date>();
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	while(cal.get(Calendar.DAY_OF_WEEK)!=1)
    		cal.add(Calendar.DATE, -1);
    	
    	for(int i=0;i<7;i++)
    	{
    		date = cal.getTime();
    		list.add(date);
    		cal.add(Calendar.DATE, -1);
    	}
    	Collections.reverse(list);
    	return list;
    }

    private void Query() throws SQLException, ClassNotFoundException
    {
    	//Date date = new java.sql.Time(new Date().getTime());
    	String sql = "insert into course(courseid,aa) values(74,''{0}'')";
     //   jdbcHelper jd = new jdbcHelper();
        java.util.Date date = new java.util.Date();          // 获取一个Date对象
        Timestamp ts = new Timestamp(date.getTime());
       // jd.Insert(MessageFormat.format(sql,ts.toString()));
    }
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {   
		test t = new test();
		t.Query();
		//t.GetPreWeek();
//        JSONObject jsonObject = test.createJSONObject();   
//        //输出jsonobject对象   
//        System.out.println("jsonObject==>"+jsonObject);   
//           
//        //判读输出对象的类型   
//        boolean isArray = jsonObject.isArray();   
//        boolean isEmpty = jsonObject.isEmpty();   
//        boolean isNullObject = jsonObject.isNullObject();   
//        System.out.println("isArray:"+isArray+" isEmpty:"+isEmpty+" isNullObject:"+isNullObject);  
//           
//        //添加属性   
//        jsonObject.element("address", "福建省厦门市");   
//        System.out.println("添加属性后的对象==>"+jsonObject);   
//           
//        //返回一个JSONArray对象   
//        JSONArray jsonArray = new JSONArray();   
//        jsonArray.add(0, "this is a jsonArray value");   
//        jsonArray.add(1,"another jsonArray value");   
//        jsonObject.element("jsonArray", jsonArray);   
//        JSONArray array = jsonObject.getJSONArray("jsonArray");   
//        System.out.println("返回一个JSONArray对象："+array);   
//        //添加JSONArray后的值   
////        {"username":"huangwuyi","sex":"男","QQ":"999999999","Min.score":99,"nickname":"梦中心境","address":"福建省厦门市","jsonArray":["this is a jsonArray value","another jsonArray value"]} 
//        System.out.println("结果="+jsonObject);   
//           
//        //根据key返回一个字符串   
//        String username = jsonObject.getString("username");   
//        System.out.println("username==>"+username);  
//        
//        //把字符转换为 JSONObject
//        String temp=jsonObject.toString();
//        JSONObject object = JSONObject.fromObject(temp);
//        //转换后根据Key返回值
//        System.out.println("qq="+object.get("QQ"));
        
    }  

}


