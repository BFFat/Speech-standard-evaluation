package com.app.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
 
import javax.imageio.ImageIO;
 

 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.app.tools.RandomString;
 

public class QrCodeUtil {
 
	private String fileContextPath;
 
	/**
	 * 生成二维码
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param content 二维码的内容
	 * @param filePath临时文件的路径
	 */
	public void encodeQrCode(String content, String filePath) {
		try {
			//需要创建一个临时文件，为了防止出现并发问题，现把二维码文件名用16为的UUID来命名
			RandomString ran = new RandomString();
			String fileName = ran.getRandomString(16) + ".png";
			int width = 300; //二维码图像宽度  
			int height = 300; // 二维码图像高度  
			String format = "png";// 图像类型  
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵  
			Path path = FileSystems.getDefault().getPath(filePath, fileName);
			//由于生成二维码的方法没有返回值，现将二维码临时路径进行保存
			this.setFileContextPath(path.toString());
			MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像  
			//System.out.println("输出成功.");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/** 
	 * 解析二维码 
	 */
	public void decodeQrCode(String filePath) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码  
			System.out.println("图片中内容：  ");
			System.out.println("author： " + result.getText());
			System.out.println("图片中格式：  ");
			System.out.println("encode： " + result.getBarcodeFormat());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getFileContextPath() {
		return fileContextPath;
	}
	public void setFileContextPath(String fileContextPath) {
		this.fileContextPath = fileContextPath;
	}
}
