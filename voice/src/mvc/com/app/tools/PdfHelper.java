package com.app.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.sf.json.JSONObject;

public class PdfHelper {


	private  Font headfont ;// ���������С 
    private  Font keyfont;// ���������С 
    private  Font textfont;// ���������С 
    private int maxWidth = 520; 
    private  String fontPath = "/com/app/text/msyh.ttf";
    Document document = new Document();   
    
/*	public String Generate(userStatisticsModel model) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException
	{
		        BaseFont bfChinese; 
		        try { 

		            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED); 

		        	
		            bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED); 
		            headfont = new Font(bfChinese, 10, Font.BOLD);// ���������С 
		            keyfont = new Font(bfChinese, 8, Font.BOLD);// ���������С  
		            textfont = new Font(bfChinese, 8, Font.NORMAL);// ���������С
		            document.setPageSize(PageSize.A4);// ����ҳ���С
		            
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            String dateString = sdf.format(new Date());
		            RandomString randomString = new RandomString();
		            String ran = randomString.getRandomString(8);
		            String path = "D:\\�����\\" + dateString + "\\" + model.getUserName() + "_" + ran + ".pdf";
		            File file = new File(path);
		            if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
		            file.createNewFile(); 
		            PdfWriter.getInstance(document,new FileOutputStream(file)); 
		            document.open(); 
		            HeaderFooter footer = new HeaderFooter(new Phrase("ҳ�룺",keyfont), true);
		            footer.setBorder(Rectangle.NO_BORDER); 
		            document.setHeader(footer);
		            generatePDF(model);     
		            return path;
		        } catch (Exception e) {          
		            e.printStackTrace(); 
		        }
		        return null; 
		     
	}*/

	/*public String GenerateForAdmin(List<userStatisticsModel> models,String basePath) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException
	{
		        BaseFont bfChinese; 
		        try { 

		            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED); 

		        	
		            bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED); 
		            headfont = new Font(bfChinese, 10, Font.BOLD);// ���������С 
		            keyfont = new Font(bfChinese, 8, Font.BOLD);// ���������С  
		            textfont = new Font(bfChinese, 8, Font.NORMAL);// ���������С
		            document.setPageSize(PageSize.A4);// ����ҳ���С
		            
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            String dateString = sdf.format(new Date());
		            RandomString randomString = new RandomString();
		            String ran = randomString.getRandomString(8);
		            String path = "D:\\�����\\" + dateString + "\\����.pdf";
		            File file = new File(path);
		            if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
		            file.createNewFile();  
		            PdfWriter.getInstance(document,new FileOutputStream(file)); 
		            document.open(); 
		            HeaderFooter footer = new HeaderFooter(new Phrase("ҳ�룺",keyfont), true);
		            footer.setBorder(Rectangle.NO_BORDER); 
		            document.setHeader(footer);
		            generatePDFForAdmin(models,basePath);    
		            document.close();
		            return path;
		        } catch (Exception e) {          
		            e.printStackTrace(); 
		        }
		        return null; 
		     
	}*/


	public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align){ 
	    PdfPCell cell = new PdfPCell(); 
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);         
	    cell.setHorizontalAlignment(align);     
	    cell.setPhrase(new Phrase(value,font)); 
	   return cell; 
	} 
	  
	public PdfPCell createCell(String value,com.itextpdf.text.Font font){ 
	    PdfPCell cell = new PdfPCell(); 
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);  
	    cell.setPhrase(new Phrase(value,font)); 
	   return cell; 
	} 

	public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align,int colspan){ 
	    PdfPCell cell = new PdfPCell(); 
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
	    cell.setHorizontalAlignment(align);     
	    cell.setColspan(colspan); 
	    cell.setPhrase(new Phrase(value,font)); 
	   return cell; 
	} 
	public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align,int colspan,boolean boderFlag){ 
	    PdfPCell cell = new PdfPCell(); 
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
	    cell.setHorizontalAlignment(align);     
	    cell.setColspan(colspan); 
	    cell.setPhrase(new Phrase(value,font)); 
	    cell.setPadding(3.0f); 
	    if(!boderFlag){ 
	        cell.setBorder(0); 
	        cell.setPaddingTop(15.0f); 
	        cell.setPaddingBottom(8.0f); 
	    } 
	   return cell; 
	} 
	public PdfPTable createTable(int colNumber){ 
	   PdfPTable table = new PdfPTable(colNumber); 
	   try{ 
	       table.setTotalWidth(maxWidth); 
	       table.setLockedWidth(true); 
	       table.setHorizontalAlignment(Element.ALIGN_CENTER);      
	       table.getDefaultCell().setBorder(1); 
	   }catch(Exception e){ 
	       e.printStackTrace(); 
	   } 
	   return table; 
	} 
	public PdfPTable createTable(float[] widths){ 
	       PdfPTable table = new PdfPTable(widths); 
	       try{ 
	           table.setTotalWidth(maxWidth); 
	           table.setLockedWidth(true); 
	           table.setHorizontalAlignment(Element.ALIGN_CENTER);      
	           table.getDefaultCell().setBorder(1); 
	       }catch(Exception e){ 
	           e.printStackTrace(); 
	       } 
	       return table; 
	   } 
	  
	public PdfPTable createBlankTable(){ 
	    PdfPTable table = new PdfPTable(1); 
	    table.getDefaultCell().setBorder(0); 
	    table.addCell(createCell("", keyfont));          
	    table.setSpacingAfter(20.0f); 
	    table.setSpacingBefore(20.0f); 
	    return table; 
	} 
	
/*	public void generatePDFForAdmin(List<userStatisticsModel> models,String basePath) throws Exception{ 

		PdfPTable table = createTable(3);
		double ans  = 0;
		double ans1 = 0;
		for(userStatisticsModel model : models)
		{
			ans += model.getIncomeNow();
			ans1 += model.getNotCleanMoney();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		table.addCell(createCell("�������ڣ�" + sdf.format(new Date()) , headfont , Element.ALIGN_LEFT,3,false));
		table.addCell(createCell("���ν��������룺" + String.valueOf(ans), headfont , Element.ALIGN_LEFT,3,false));
		table.addCell(createCell("���ν����ܽ����" + String.valueOf(ans1), headfont , Element.ALIGN_LEFT,3,false));
		
		table.addCell(createCell("�û���", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell("������", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell("��ά��", keyfont, Element.ALIGN_CENTER));
		
		for(userStatisticsModel model : models)
		{
			if(model.getNotCleanMoney()<=0) continue;
			
			table.addCell(createCell(model.getUserName(), keyfont, Element.ALIGN_CENTER)); 
			table.addCell(createCell(String.valueOf(model.getNotCleanMoney()), keyfont, Element.ALIGN_CENTER));
			String path = basePath + "Page\\" + model.getUserName() + "\\QRCODE";
			File file = new File(path);

			if(file.exists() && file.listFiles().length>0)
			{
				file = file.listFiles()[0];
				Image image = Image.getInstance(file.getAbsolutePath());
				image.scaleToFit(300, 300);
				table.addCell(image);
			}
			else table.addCell(createCell("δ�ҵ���ά��", keyfont, Element.ALIGN_CENTER)); 
				
		}
		document.add(table);
	}

	    
	public void generatePDF(userStatisticsModel model) throws Exception{ 
		System.out.println("generate: " + JSONObject.fromObject(model).toString());
		
	   PdfPTable table = createTable(7); 
	   table.addCell(createCell("���ν������룺" + model.getIncomeNow() + " Ԫ", headfont,Element.ALIGN_LEFT,3,false)); 
	   table.addCell(createCell("���ν�����" + model.getNotCleanMoney()+ " Ԫ", headfont,Element.ALIGN_LEFT,4,false)); 
	   table.addCell(createCell("���ν��������ѣ�  "+ String.valueOf(model.getIncomeNow() - model.getNotCleanMoney())+ " Ԫ", headfont,Element.ALIGN_LEFT,3,false)); 
	   table.addCell(createCell("�����룺  "+ model.getIncome()+ " Ԫ", headfont,Element.ALIGN_LEFT,4,false)); 
	   table.addCell(createCell("��ˮ�˵���ϸ�� ", headfont,Element.ALIGN_LEFT,7,false)); 
	   
	   table.addCell(createCell("��ˮ��", keyfont, Element.ALIGN_CENTER)); 
	   table.addCell(createCell("�����û�", keyfont, Element.ALIGN_CENTER)); 
	   table.addCell(createCell("��Ʒ����", keyfont, Element.ALIGN_CENTER)); 
	   table.addCell(createCell("֧�����", keyfont, Element.ALIGN_CENTER)); 
	   table.addCell(createCell("����֧��ʱ��", keyfont, Element.ALIGN_CENTER));
	   table.addCell(createCell("����������", keyfont, Element.ALIGN_CENTER));
	   table.addCell(createCell("������", keyfont, Element.ALIGN_CENTER));
	   
	   List<orderInfoModel> list = model.getDetail(); 
	   for(int i=0;i<list.size();i++){ 
		   orderInfoModel order = list.get(i);
	       table.addCell(createCell(order.getId(), textfont)); 
	       table.addCell(createCell(order.getUserID(), textfont)); 
	       table.addCell(createCell(order.getGoodName(), textfont)); 
	       table.addCell(createCell(String.valueOf(order.getPrice()), textfont)); 
	       table.addCell(createCell(order.getApplyTime(), textfont)); 
	       table.addCell(createCell(order.getServerName(), textfont)); 
	       table.addCell(createCell(String.valueOf(order.getCleanPrice()), textfont)); 
	   } 
	   document.add(table); 
	      
	   document.close(); 
	}*/
}
