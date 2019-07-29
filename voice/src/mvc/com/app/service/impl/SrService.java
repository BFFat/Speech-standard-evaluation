package com.app.service.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ProblemMapper;
import com.app.dao.SolutionMapper;
import com.app.dao.UserMapper;
import com.app.tools.HttpRequest;
import com.app.tools.WaveFileReader;
import com.baidu.aip.speech.AipSpeech;
import com.code.model.Problem;
import com.code.model.ProblemExample;
import com.code.model.Solution;
import com.code.model.SolutionExample;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Service
public class SrService {

	@Autowired 
	private UserMapper userDao;
	@Autowired
	private SolutionMapper solutionDao;
	@Autowired
	private ProblemMapper problemDao;
	
	
	public static final String APP_ID = "15899014";
    public static final String API_KEY = "2Vjg0GsSLCTkV0oM59v9plnC";
    public static final String SECRET_KEY = "QNpWEiXxKWSqsVg9xtECoA355ln5u3YE";
    
    
    public Problem GetRanText(int type)  //锟斤拷取锟斤拷锟斤拷谋锟�
    {
    	List<Problem> problems = problemDao.getRanRecord(type, 1);
    	if(problems.size()<=0) return null;
    	else return problems.get(0);
    }
    
    public List<Solution> GetHistoryMistake(String userName) //锟斤拷史锟斤拷锟斤拷
    {
    	List<Solution> solutions =  solutionDao.GetHistoryMistake(userName);
    	List<Solution> solutionList = new ArrayList<Solution>();
    	for(Solution s : solutions)
    	{
    		SolutionExample solutionExample = new SolutionExample();
    		SolutionExample.Criteria criteria = solutionExample.createCriteria();
    		criteria.andUserEqualTo(s.getUser());
    		criteria.andProblemIdEqualTo(s.getProblemId());
    		criteria.andScoreGreaterThanOrEqualTo(new Integer(60));
    		List<Solution> list = solutionDao.selectByExample(solutionExample);
    		if(list.size()<=0)  //杩欓杩樻病瀵�
    		{
    			s.parseTime();
    			solutionList.add(s);
    		} 
    		
    	} 
    	return solutionList;
    }
    
    public net.sf.json.JSONObject recognize(String path,String oldContent,String username) //识锟斤拷
    {
    	AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
        
        // 锟斤拷选锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟接诧拷锟斤拷
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        org.json.JSONObject json = client.asr(path, "wav", 16000, null);
        
        System.out.println(json.toString());
        if(json.has("result"))
        {
        	org.json.JSONArray result  = json.getJSONArray("result");
            
            String content = "";
            for(int i=0;i<result.length();i++)
                content += result.getString(i);
            
            System.out.println("before content=" + content ); 
            
            ProblemExample example = new ProblemExample();
            ProblemExample.Criteria criteria = example.createCriteria();
            criteria.andContentEqualTo(oldContent);
            List<Problem> problems = problemDao.selectByExample(example);
            if(problems.size()<=0)
            {
            	net.sf.json.JSONObject JSON = new net.sf.json.JSONObject();
            	JSON.put("type", 0);
            	JSON.put("msg", "后台发生错误");
            	return JSON;
            }
            System.out.println("type=" + problems.get(0).getType());
            if(problems.get(0).getType()!=3){
            	System.out.println("delete end tag");
            	content = content.replace(".","");
            	content = content.replace("。","");
            }
            System.out.println("!!before content=" + content ); 
            oldContent = FixString(oldContent);
            content = FixString(content);
            String[] s1 = new String[oldContent.toCharArray().length];
            String[] s2 = new String[content.toCharArray().length];
            System.out.println("oldContent=" + oldContent);
            System.out.println("content=" + content);
            ToPinyin(oldContent,s1);
            ToPinyin(content,s2);  
            
            int[] index = new int[s1.length];
            for(int i=0;i<index.length;i++)
            	index[i] = 0;
            if(content.isEmpty() || content.trim().equals("")){
            	net.sf.json.JSONObject JSON = new net.sf.json.JSONObject();
            	JSON.put("array", index);
                JSON.put("type", 1);
                JSON.put("msg", "您获得 0 分"); 
                JSON.put("score", 0);
                return JSON;
            }
            double rate = check(s1,s2,index)/oldContent.length() * 100;

            Solution solution = new Solution();
            solution.setDate(new Date());
            solution.setPath(path);
            solution.setProblemId(problems.get(0).getProblemId());
            solution.setResult(content);
            solution.setScore(new Integer((int)rate));
            solution.setUser(username);
            
            solutionDao.insert(solution);
            
            net.sf.json.JSONObject JSON = new net.sf.json.JSONObject();
            JSON.put("array", index);
            JSON.put("type", 1);
            JSON.put("msg", "您获得 " + String.valueOf((int)rate) + " 分");
            JSON.put("score", (int)rate);
            return JSON;
        }
        else
        {
        	String errMsg = json.getString("err_msg");
        	net.sf.json.JSONObject JSON = new net.sf.json.JSONObject();
        	JSON.put("type", 0);
        	JSON.put("msg", errMsg);
        	return JSON;
        }
    }
    
    public net.sf.json.JSONObject recognize1(String path,String oldContent,String username) //识锟斤拷
    {

        WaveFileReader wav = new WaveFileReader(path);
    	int[][] data = wav.getData();
    	String[] arr = new String[data[0].length];
    	for(int i=0;i<data[0].length;i++)
    	{
    		System.out.print(data[0][i] + " ");
    		arr[i] = String.valueOf(data[0][i]);
    	}
    	System.out.println();
        String wavs = String.join("&wavs=", arr);
        String s = HttpRequest.sendPost("http://127.0.0.1:20000", "token=qwertasd&fs=16000&wavs="+ wavs );
    	System.out.println("result:\n" + s);
    	String content = s.replace('#', ' ');

     //   if(!content.isEmpty() && !content.trim().equals(""))  
        {
        	
            ProblemExample example = new ProblemExample();
            ProblemExample.Criteria criteria = example.createCriteria();
            criteria.andContentEqualTo(oldContent);
            List<Problem> problems = problemDao.selectByExample(example);
            if(problems.size()<=0)
            {
            	net.sf.json.JSONObject JSON = new net.sf.json.JSONObject();
            	JSON.put("type", 0);
            	JSON.put("msg", "后台发生错误");
            	return JSON;
            }
            System.out.println("type=" + problems.get(0).getType());
            if(problems.get(0).getType()!=3){
            	System.out.println("delete end tag");
            	content = content.replace(".","");
            	content = content.replace("。","");
            }
            System.out.println("!!before content=" + content ); 
            oldContent = FixString(oldContent);
            content = FixString(content);
            String[] s1 = new String[oldContent.toCharArray().length];
            String[] s2 = content.split(" ");
            System.out.println("oldContent=" + oldContent);
            System.out.println("content=" + content);
            ToPinyin(oldContent,s1); 
        //    ToPinyin(content,s2);  
            
            int[] index = new int[s1.length];
            for(int i=0;i<index.length;i++)
            	index[i] = 0;
            if(content.isEmpty() || content.trim().equals("")){
            	net.sf.json.JSONObject JSON = new net.sf.json.JSONObject();
            	JSON.put("array", index);
                JSON.put("type", 1);
                JSON.put("msg", "您获得 0 分"); 
                JSON.put("score", 0);
                return JSON;
            }
            double rate = check1(s1,s2,index)/oldContent.length() * 100;

            Solution solution = new Solution();
            solution.setDate(new Date());
            solution.setPath(path);
            solution.setProblemId(problems.get(0).getProblemId());
            solution.setResult(content);
            solution.setScore(new Integer((int)rate));
            solution.setUser(username);
            
            solutionDao.insert(solution);
            
            net.sf.json.JSONObject JSON = new net.sf.json.JSONObject();
            JSON.put("array", index);
            JSON.put("type", 1);
            JSON.put("msg", "您获得 " + String.valueOf((int)rate) + " 分");
            JSON.put("score", (int)rate);
            
            return JSON;
        }

    }
     
    public net.sf.json.JSONObject recognize2(String path,String oldContent,String username)
    {

        WaveFileReader wav = new WaveFileReader(path);
    	int[][] data = wav.getData();
    	String[] arr = new String[data[0].length];
    	for(int i=0;i<data[0].length;i++)
    		arr[i] = String.valueOf(data[0][i]);

        String wavs = String.join("&wavs=", arr);
        String s = HttpRequest.sendPost("http://127.0.0.1:20000", "token=qwertasd&fs=16000&wavs="+ wavs );

        System.out.println(s);
    	String content = s.replace('#', ' ');

            
            oldContent = FixString(oldContent);
            content = FixString(content);
            String[] s1 = new String[oldContent.toCharArray().length];
            String[] s2 = content.split(" ");

            ToPinyin(oldContent,s1); 

            
            int[] index = new int[s1.length];
            for(int i=0;i<index.length;i++)
            	index[i] = 0;
            if(content.isEmpty() || content.trim().equals("")){
            	net.sf.json.JSONObject JSON = new net.sf.json.JSONObject();
            	JSON.put("array", index);
                JSON.put("type", 1);
                JSON.put("msg", "您获得 0 分"); 
                JSON.put("score", 0);
                return JSON;
            }
            double rate = check1(s1,s2,index)/oldContent.length() * 100;
            
            net.sf.json.JSONObject JSON = new net.sf.json.JSONObject();
            JSON.put("array", index);
            JSON.put("type", 1);
            JSON.put("msg", "您获得 " + String.valueOf((int)rate) + " 分");
            JSON.put("score", (int)rate);
            
            return JSON;
            

    }

    	String FixString(String s)
        {
        	s = s.replace('，',',');
        	s = s.replace('，','.');
        	s = s.replace('。', '.');
        	s = s.replace('：',',');
        	s = s.replace('“',' ');
        	s = s.replace('”',' ');
        	s = s.replace('；',',');
        	s = s.replace('、', ',');
        	s = s.replace('‘',',');
        	s = s.replace('？','?'); 
        	s = s.replace(' ',' ');
        	s = s.replace('—', ',');
        	s = s.replace('-', ',');
        	s = s.replace('—', ',');
        	s = s.replace("　", " ");
        	s = s.replace("》", ",");
        	s = s.replace("…", ",");
        	s = s.replace("—", ",");
        	s = s.replace("《", ",");
        	s = s.replace("！", ",");
        	s = s.replace("·", ",");
        	s = s.replace("|", ",");
        	s = s.replace("{", ",");
        	s = s.replace("}", ",");
        	s = s.replace("【", ",");
        	s = s.replace("】", ",");
        	s = s.replace("（", ",");
        	s = s.replace("）", ",");
        	s = s.replace("~", ",");
        	s = s.replace("#", ",");
        	s = s.replace("%", ",");
        	s = s.replace("&", ",");
        	s = s.replace("*", ",");
        	s = s.replace("/", ",");
        	s = s.replace("+", ",");
        	s = s.replace("=", ",");
        	s = s.replace("@", ",");
        	s = s.replace("￥", ",");
//        	s = s.replace("", ",");
        	return s;
        	//return s.trim();
        }
    	
    	boolean IsPunctuation(char c){
    		List<String> punctuationLis = Arrays.asList(",",":","?",".","!"," ");
    		if(punctuationLis.contains(String.valueOf(c)))
    		return true;
    		return false;
    	}
    	
    	int GetPunctuationCount(String[] s){
    		int cnt = 0;
    		for(int i=0;i<s.length;i++)
    			if(s[i].length() == 1 && IsPunctuation(s[i].charAt(0))) cnt++;
    		return cnt;
    	}
    	

    
    public static String ToPinyin(String chinese, String[] ss){          
        String pinyinStr = "";  
        char[] newChar = chinese.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
      //defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < newChar.length; i++) {  
            if (newChar[i] > 128) {  
                try {  
                    ss[i] = PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                    pinyinStr += ss[i] + " ";
                } catch (BadHanyuPinyinOutputFormatCombination e) {  
                    e.printStackTrace();  
                }  
            }else{
            	ss[i] = newChar[i] + "";
            	pinyinStr += newChar[i] + " ";    
            }     
        }
        return pinyinStr;  
    }
    
    String GetRandomText(String basePath)
    {
        int i = (int)(1+Math.random()*20); 
			String pathName = basePath + "\\data\\" + i + ".txt";
			File file = new File(pathName);
			DataInputStream in = null;
			try {
				// 一锟轿讹拷一锟斤拷锟街斤拷
				in = new DataInputStream(new FileInputStream(file));
				//   byte[] b = new byte[2048];
				int bytesRead = 0;
				int bytesToRead = 1024;
				byte[] b = new byte[bytesToRead];
				while( bytesRead < bytesToRead) {
					int result = in.read(b, bytesRead, bytesToRead - bytesRead);
					if(result == -1) {
						break;
					}
					bytesRead += result;
				}
				String str = new String(b);
				return str;
                                
    }
                        catch (IOException e) {
				e.printStackTrace();
				return null;
			}
    }
    
    public double check(String[] aa,String[] bb,int[] index){
        double score;
        int len1 = aa.length;
        System.out.println("len1=" + len1);
        int len2 = bb.length;
        String[] a = new String[len1+1];
        String[] b = new String[len2+1];
        
        for(int i=len1-1;i>=0;i--)
            a[i+1] = aa[i];
        for(int i=len2-1;i>=0;i--)
            b[i+1] = bb[i];

  
        double[][] dp = new double[len1+1][len2+1];
        
        for(int i=0;i<len1;i++)
            for(int j=0;j<len2;j++)
                dp[i][j] = 0;
         
        for(int i=1;i<=len1;i++)
            for(int j=1;j<=len2;j++)
            {

                String s1 = a[i];
                String s2 = b[j];
                if(s1.substring(0, s1.length()-1).equals(s2.substring(0, s2.length()-1)))
                {
                    if(s1.charAt(s1.length()-1) == s2.charAt(s2.length()-1))
                    	dp[i][j] = Math.max(dp[i][j] ,dp[i-1][j-1] + 1);
                    
                    else {
                    	dp[i][j] = Math.max(dp[i][j] ,dp[i-1][j-1] + 0.5); 
                    }
                }
                else dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
            }
        
        int i = len1,j=len2;
        while(dp[i][j]!=0)   //瀵绘壘鍘熸枃涓鍖归厤鐨勫瓙搴忓垪
        {
        	String s1 = a[i];
            String s2 = b[j];
            if(s1.substring(0, s1.length()-1).equals(s2.substring(0, s2.length()-1))) //鐩哥瓑
            {
            	if(s1.charAt(s1.length()-1) == s2.charAt(s2.length()-1)) index[i-1] = 2;
                else index[i-1] = 1;
            	i--;
            	j--;
            }
            else if(dp[i][j-1] > dp[i-1][j])
            j--;
            else
            i--;
        }
        
      /*  int[] index1 = new int[len1];
        for(i=1;i<=len1;i++)
        index1[i-1] = index[i];
        
        index = new int[len1];
        for(i=0;i<len1;i++)
        index[i] = index1[i];
        */
        	//dp[len1][len2] = dp[len1][len2] + 0.5*Math.abs(len1-len2);
        System.out.println("dp=" + dp[len1][len2]);
        return Math.max(dp[len1][len2],0);
        }
    
        public String GetFilePath(String path,String oldContent)
        {
        	ProblemExample example = new ProblemExample();
            ProblemExample.Criteria criteria = example.createCriteria();
            criteria.andContentEqualTo(oldContent);
            List<Problem> problems = problemDao.selectByExample(example);
            String url = "";
            if(problems.size()==1)
            { 
            	int type = (int)problems.get(0).getType();
            	if(type==1)
            		url = "http://appcdn.fanyi.baidu.com/zhdict/mp3/"+ GetPinyin(problems.get(0).getContent()) +".mp3";
            	else if(type==2)
            		url = "https://ss0.baidu.com/6KAZsjip0QIZ8tyhnq/text2audio?tex=" + problems.get(0).getContent() +"&cuid=dict&lan=ZH&ctp=1&pdt=30&vol=9&spd=4";
            	else
            	url = "https://www.rustycn.com/wav/" + problems.get(0).getProblemId().toString() + ".mp3";
            	  
            /*int id = (int)problems.get(0).getProblemId();
            System.out.println("id=" + id);
            path = path + "wav\\" + String.valueOf(id) + ".mp3";
            System.out.println("path=" + path);
            File file = new File(path); 
            if(file.exists())
            	return path;
            }*/
            return url;
            }
            return null;
        }
        
        public double check1(String[] aa,String[] bb,int[] index){
            double score;
            int len1 = aa.length;
            System.out.println("ckeck1 len1=" + len1); 
            int len2 = bb.length; 
            System.out.println("ckeck1 len2=" + len2);
            String[] a = new String[len1+1];
            String[] b = new String[len2+1];
            
            for(int i=len1-1;i>=0;i--)
                a[i+1] = aa[i];
            for(int i=len2-1;i>=0;i--)
                b[i+1] = bb[i];

      
            double[][] dp = new double[len1+1][len2+1];
            
            for(int i=0;i<len1;i++)
                for(int j=0;j<len2;j++)
                    dp[i][j] = 0;
             
            for(int i=1;i<=len1;i++)
                for(int j=1;j<=len2;j++)
                {

                    String s1 = a[i];
                    String s2 = b[j];
                    if(s1.substring(0, s1.length()-1).equals(s2.substring(0, s2.length()-1)))
                    {
                        if(s1.charAt(s1.length()-1) == s2.charAt(s2.length()-1))
                        	dp[i][j] = Math.max(dp[i][j] ,dp[i-1][j-1] + 1);
                        
                        else {
                        	dp[i][j] = Math.max(dp[i][j] ,dp[i-1][j-1] + 0.5); 
                        }
                    }
                    else dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            
            int i = len1,j=len2;
            while(dp[i][j]!=0)   //瀵绘壘鍘熸枃涓鍖归厤鐨勫瓙搴忓垪
            {
            	String s1 = a[i];
                String s2 = b[j];
                if(s1.substring(0, s1.length()-1).equals(s2.substring(0, s2.length()-1))) //鐩哥瓑
                {
                	if(s1.charAt(s1.length()-1) == s2.charAt(s2.length()-1)) index[i-1] = 2;
                    else index[i-1] = 1;
                	i--;
                	j--;
                }
                else if(dp[i][j-1] > dp[i-1][j])
                j--;
                else
                i--;
            }
            
            int pCount = GetPunctuationCount(aa);
            for(i=0;i<aa.length;i++)
            	if(aa[i].length()==1 && IsPunctuation(aa[i].charAt(0)))
            		index[i] = 2;

            System.out.println("check1 dp=" + dp[len1][len2]);
            return Math.max(dp[len1][len2] + pCount,0);
            }
       
        
        public static String GetPinyin(String chinese){          
            String pinyinStr = "";  
            char[] newChar = chinese.toCharArray();  
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
            for (int i = 0; i < newChar.length; i++) {  
                if (newChar[i] > 128) {  
                    try {  
                        pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];  
                    } catch (BadHanyuPinyinOutputFormatCombination e) {  
                        e.printStackTrace();  
                    }  
                }else{  
                    pinyinStr += newChar[i];  
                }  
            }  
            return pinyinStr;  
        }
    
}
