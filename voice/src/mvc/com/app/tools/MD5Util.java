package com.app.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

/**
 * 生成一个MD5码
 * 
 * @author Administrator
 *
 */
public class MD5Util {

    public static String encodePassword(String password) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(password)) {
            return password;
        }
        return getMD5(password.getBytes("utf-8"));
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            s = new String(str); // 换后的结果转换为字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public final static String MD5(String inputStr) {
        // 用于加密的字符
        char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            // 使用utf-8字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = inputStr.getBytes("utf-8");

            // 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            // MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) { // i = 0
                byte byte0 = md[i]; // 95
                str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
                str[k++] = md5String[byte0 & 0xf]; // F
            }

            // 返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            return null;
        }
    }
    
	public static String encryption(String plain) throws UnsupportedEncodingException {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plain.getBytes("utf-8"));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}
	
	public static byte[] encode2bytes(String source) {
		byte[] result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(source.getBytes("UTF-8"));
			result = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 将源字符串使用MD5加密为32位16进制数
	 * @param source
	 * @return
	 */
	public static String encode2hex(String source) {
		byte[] data = encode2bytes(source);

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(0xff & data[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			
			hexString.append(hex);
		}
		
		return hexString.toString();
	}
	
	/**
	 * 验证字符串是否匹配
	 * @param unknown 待验证的字符串
	 * @param okHex	使用MD5加密过的16进制字符串
	 * @return	匹配返回true，不匹配返回false
	 */
	public static boolean validate(String unknown , String okHex) {
		return okHex.equals(encode2hex(unknown));
	}
	
	public static String GetSign(Map<String,String> paramMap) throws UnsupportedEncodingException
	{
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String,String>>(paramMap.entrySet());
	    Collections.sort(list, new Comparator<Map.Entry<String, String>>(){
	    	
	    	public int compare(Entry<String,String> o1,Entry<String,String> o2)
	    	{
	    		return o1.getKey().compareToIgnoreCase(o2.getKey());
	    	}
	    });
	    
	    String signStr = "";
	    
	    boolean first = true;
	    for(Map.Entry<String, String> ent : list)
	    if(first)
	    {
	    signStr += ent.getKey() + "=" + ent.getValue();
	    first = false;
	    }
	    else
	    signStr += "&" +ent.getKey() + "=" + ent.getValue();
	    
	    signStr += "&5304d8ebe705a2cb06cb1cceb9c3d63052204986";
	    System.out.println("参数:" + signStr);
	    signStr = signStr.toLowerCase();
	    System.out.println("toLowerCase:" + signStr);
	    
		signStr = encodePassword(signStr).toUpperCase();
		
		System.out.println("MD5:" + signStr);
	    return signStr;
	}
}
