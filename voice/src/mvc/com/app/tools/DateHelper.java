package com.app.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DateHelper {

    public static List<Date> GetPreWeek() throws ParseException
    {
    	List<Date> list = new ArrayList<Date>();
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    //	while(cal.get(Calendar.DAY_OF_WEEK)!=1)
    		//cal.add(Calendar.DATE, -6);
    	
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	for(int i=0;i<7;i++)
    	{
    		date = cal.getTime();
    		date.setHours(0);
    		date.setMinutes(0);
    		date.setSeconds(0);
    		list.add(date);
    		cal.add(Calendar.DATE, -1);
    	}
    	Collections.reverse(list);
    	return list;
    }
}
