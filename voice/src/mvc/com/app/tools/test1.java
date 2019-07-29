
package com.app.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.apache.poi.poifs.storage.ListManagedBlock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import junit.framework.TestCase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinBase.SYSTEM_INFO.PI;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.MSG;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;
import com.sun.jna.ptr.IntByReference;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

import java.awt.Color; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException; 
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException; 
import java.text.DecimalFormat; 
import java.text.NumberFormat; 
import java.util.ArrayList; 
import java.util.Date;

import com.app.dao.ProblemMapper;
import com.app.dao.SolutionMapper;
import com.app.service.impl.SrService;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import com.code.model.Problem;
import com.code.model.ProblemExample;
import com.code.model.Solution;
import com.code.model.SolutionExample;
import com.code.model.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.MessageFormat;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)	// 不能是PowerMock等别的class，否则无法识别spring的配置文件
@ContextConfiguration("file:resource/spring/applicationContext.xml")	// 读取spring配置文件
public class test1 {
	
	
	@Autowired
	private ProblemMapper problemDao;
	
	@Autowired
	private SolutionMapper solutionDao;
	
	@Autowired
	private SrService srService;
	
	
	@Value("${BASE_URL}")
    private String BASE_URL;

    
    @Test
	public void test() throws IOException, InterruptedException
	{ 
    	long startTime = System.currentTimeMillis(); //获取开始时间
    	Runtime rn = Runtime.getRuntime();
    	String command = "ffmpeg.exe -i {0} -ac 1 -ar 16000 {1}";
    	String resource = "C:\\Users\\BF.Fat\\Desktop\\大数据挑战赛四--正式数据样本";
    	String dest = "C:\\Users\\BF.Fat\\Desktop\\音频处理";
    	
    	File resourceAudioDir = new File(resource);
    	List<Integer> audioFileList = new ArrayList<Integer>();
    	for(File file : resourceAudioDir.listFiles())
    	{
    		if(!file.getName().substring(file.getName().lastIndexOf(".")).equals(".m4a")) continue;
    		audioFileList.add(Integer.parseInt(file.getName().substring(0,file.getName().lastIndexOf("."))));
    		String execCommand = MessageFormat.format(command, file.getAbsolutePath(),dest + "\\" + file.getName().substring(0,file.getName().lastIndexOf(".")) + ".wav");
    		System.out.println("exec=" + execCommand);
    		rn.exec(execCommand);
    	}
    	Thread.sleep(5000);
    	Collections.sort(audioFileList);
    	
    	String content = getFileContent(resource + "\\正式测试样本.txt");
    	System.out.println(resource + "\\正式测试样本.txt");
    	System.out.println("content=" + content);
    	
    	Map contentDict = new HashMap<String,String>();
    	String[] contentList = content.split("\n");
    	for(int i=0;i<contentList.length;i++)
    	{
    		String txt = contentList[i];
    		System.out.println("txt="+txt);
    		String[] txtList = txt.split("、	");
    		contentDict.put(txtList[0], txtList[1]);
    	}
    	
		File dirFile = new File(dest);
		String path = "C:\\Users\\BF.Fat\\Desktop\\测试结果\\";
		List<List<Object>> objList = new ArrayList< List<Object> >();
		List<Object> zdList = new ArrayList<Object>();
		zdList.add("编号");
		zdList.add("总评分");
		
		Map<String,String> scoreMap = new HashMap<String,String>();
		
		System.out.println("list.len=" + String.valueOf(dirFile.listFiles().length));
		for(Integer audioNum : audioFileList)
		{
			String sNum = String.valueOf(audioNum);
			String filePath  = dest + "\\" + sNum + ".wav";
			String destContent = (String) contentDict.get(sNum);
			System.out.println("filename="+ sNum + ".wav\ndestContent="+destContent);
			JSONObject json = srService.recognize2(filePath, destContent, "1");
			System.out.println(json.toString()); 
			System.out.println("length=" + String.valueOf(destContent.length()));
			 
			scoreMap.put(sNum,String.valueOf( Integer.parseInt(json.getString("score"))+10 ));
			System.out.println("=================");
			

				if(scoreMap.containsKey(sNum)){
					List<Object> l = new ArrayList<Object>();
					l.add(sNum);
					l.add(scoreMap.get(String.valueOf(sNum)));
					objList.add(l);
				}

			
			CSVUtils.createCSVFile(zdList,objList,path,"68287");
			
		}
		System.out.println("测试运行完毕！！！！！！！！！！");
		long endTime = System.currentTimeMillis(); //获取结束时间
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间
        JOptionPane.showMessageDialog(null, "程序运行结束", "请注意", JOptionPane.ERROR_MESSAGE);
	}
    	

     String getFileContent(String filePath) throws IOException{
    	 File file = new File(filePath);
         BufferedReader br = new BufferedReader(new FileReader(file));
             StringBuffer sb = new StringBuffer();
        		 
             while (br.ready()) 
                 sb.append(br.readLine()+"\n");
             
         return sb.toString();
     }
        
    



}