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
        jsonObject.put("sex", "��");   
        jsonObject.put("QQ", "999999999");   
        jsonObject.put("Min.score", new Integer(99));   
        jsonObject.put("nickname", "�����ľ�");   
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
        java.util.Date date = new java.util.Date();          // ��ȡһ��Date����
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
//        //���jsonobject����   
//        System.out.println("jsonObject==>"+jsonObject);   
//           
//        //�ж�������������   
//        boolean isArray = jsonObject.isArray();   
//        boolean isEmpty = jsonObject.isEmpty();   
//        boolean isNullObject = jsonObject.isNullObject();   
//        System.out.println("isArray:"+isArray+" isEmpty:"+isEmpty+" isNullObject:"+isNullObject);  
//           
//        //�������   
//        jsonObject.element("address", "����ʡ������");   
//        System.out.println("������Ժ�Ķ���==>"+jsonObject);   
//           
//        //����һ��JSONArray����   
//        JSONArray jsonArray = new JSONArray();   
//        jsonArray.add(0, "this is a jsonArray value");   
//        jsonArray.add(1,"another jsonArray value");   
//        jsonObject.element("jsonArray", jsonArray);   
//        JSONArray array = jsonObject.getJSONArray("jsonArray");   
//        System.out.println("����һ��JSONArray����"+array);   
//        //���JSONArray���ֵ   
////        {"username":"huangwuyi","sex":"��","QQ":"999999999","Min.score":99,"nickname":"�����ľ�","address":"����ʡ������","jsonArray":["this is a jsonArray value","another jsonArray value"]} 
//        System.out.println("���="+jsonObject);   
//           
//        //����key����һ���ַ���   
//        String username = jsonObject.getString("username");   
//        System.out.println("username==>"+username);  
//        
//        //���ַ�ת��Ϊ JSONObject
//        String temp=jsonObject.toString();
//        JSONObject object = JSONObject.fromObject(temp);
//        //ת�������Key����ֵ
//        System.out.println("qq="+object.get("QQ"));
        
    }  

}


