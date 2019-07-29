package com.app.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class APIHelper {

	public static String httpRequest(String requestUrl,Map params) {  
    	//buffer���ڽ��ܷ��ص��ַ�
    	StringBuffer buffer = new StringBuffer();
        try {  
        	//����URL���������ַ����ȫ������urlencode�����������ڰ�params��Ĳ�����ȡ����
            URL url = new URL(requestUrl+"?"+urlencode(params));  
            //��http����
            System.out.println(url.toString());
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("POST");  
            httpUrlConn.connect();  
            
            //�������
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            //��bufferReader��ֵ���ŵ�buffer��
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            //�ر�bufferReader��������
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            inputStream = null;  
            //�Ͽ�����
            httpUrlConn.disconnect();
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        	//�����ַ���
        return buffer.toString();  
    }
	
	public static String urlencode(Map<String,Object>data) {
    	//��map��Ĳ�������� showapi_appid=###&showapi_sign=###&������
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
           
                sb.append(i.getKey()).append("=").append(i.getValue()).append("&");
            
        }
        return sb.toString();
    }
	
	
}
