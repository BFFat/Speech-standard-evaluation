package com.lxm;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;


import com.app.tools.PdfHelper;
import com.app.tools.SendEmail;


public class MyListener implements ServletContextListener{

	private MyThread mythread;

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// TODO Auto-generated method stub
		if(mythread!=null && mythread.isInterrupted())
		{
			mythread.interrupt();
		}
	}
	@Override
	public void contextInitialized(ServletContextEvent e) {
		// TODO Auto-generated method stub
		System.out.println("[Listener] contextInitialized!");
		
		   String path = e.getServletContext().getRealPath("");
		
		mythread = new MyThread();

		mythread.setPath(path);
		mythread.start();
	}
}

class MyThread extends Thread{

	private String path;
	
	
	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}






	public void run()
	{
		
	}
}
