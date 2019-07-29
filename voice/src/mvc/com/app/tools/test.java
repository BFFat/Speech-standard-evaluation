package com.app.tools;

import static org.hamcrest.CoreMatchers.startsWith;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ProcessBuilder.Redirect;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;


import org.junit.Test;


import junit.framework.TestCase;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import com.app.tools.ExcelHelper;
import com.app.tools.CSVUtils;
import java.text.MessageFormat;

public class test extends TestCase {

	

	@Test
	public void test() throws IOException
	{ 
		
		String path = "C:\\Users\\BF.Fat\\Desktop\\aac\\测试样本数据.txt";
		System.out.println(getFileContent(path));
	}
	
	
	String getFileContent(String filePath) throws IOException{
   	 File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuffer sb = new StringBuffer();
       		 
            while (br.ready()) 
                sb.append(br.readLine());
            
        return sb.toString();
    }


		
	
}
