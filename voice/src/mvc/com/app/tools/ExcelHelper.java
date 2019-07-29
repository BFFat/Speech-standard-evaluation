package com.app.tools;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONArray;

public class ExcelHelper{
	
	/**
	 * ����Excel ���ŵ�ָ��λ��
	 *@param filepath �ļ�·��(Ҫ����·��)
	 *@param filename �ļ����� (��: demo.xls  �ǵü�.xlsŶ)
	 *@param titlelist ��������list 
	 *@param zdlist �ֶ�list
	 *@param datalist ����list (����Ҳ���Ըĳ�List<Map<String,String>>  ��ʽ������)
	 *@return �Ƿ���������
	 *@throws IOException
	 *@author:
	 *2018��11��24�� ����11:40:39
	 * (titlelist  ��  zdlist  ˳��Ҫһֱ, Ҫһһ��Ӧ)
	 */
	public static boolean createExcel(String filepath,String filename,List<String> titlelist,List<String> zdlist,JSONArray datalist) throws IOException{
	    boolean success = false;
	    try {
	        //����HSSFWorkbook����(excel���ĵ�����)  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        // �����µ�sheet����excel�ı���
	        HSSFSheet sheet = wb.createSheet("sheet1");
	        // ��sheet�ﴴ����һ�У�����Ϊ������(excel����)��������0��65535֮����κ�һ��
	        HSSFRow row0 = sheet.createRow(0);
	        // ��ӱ�ͷ
	        for(int i = 0;i<titlelist.size();i++){
	            row0.createCell(i).setCellValue(titlelist.get(i));
	        }
	        //��ӱ�������
	        for(int row = 0;row<datalist.size();row++){//������
	            //��������
	            HSSFRow newrow = sheet.createRow(row+1);//���ݴӵڶ��п�ʼ
	            //��ȡ���е�����
	            @SuppressWarnings("unchecked")
	             Map<String,Object> data = (Map<String, Object>) datalist.get(row);
	            
	            for(int col = 0;col<zdlist.size();col++){//��
	                //���ݴӵ�һ�п�ʼ
	                //������Ԫ�񲢷�������
	               newrow.createCell(col).setCellValue(data!=null&&data.get(zdlist.get(col))!=null?String.valueOf(data.get(zdlist.get(col))):"");
	            }
	        }
	        
	        //�ж��Ƿ����Ŀ¼. �������򴴽�
	        isChartPathExist(filepath);
	        //���Excel�ļ�1  
	        FileOutputStream output=new FileOutputStream(filepath+filename);  
	        wb.write(output);//д�����  
	        output.close(); 
	        success = true;
	    } catch (Exception e) {
	        success = false;
	        e.printStackTrace();
	    }
	    return success;
	}


	/**
	 * �ж��ļ����Ƿ���ڣ�������������½�
	 * 
	 * @param dirPath �ļ���·��
	 */
	private static void isChartPathExist(String dirPath) {
	    File file = new File(dirPath);
	    if (!file.exists()) {
	        file.mkdirs();
	    }
	}
	
}
