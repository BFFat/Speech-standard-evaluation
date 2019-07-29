package com.app.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.UserMapper;
import com.app.tools.MD5Util;
import com.app.tools.RandomString;
import com.app.tools.SendEmail;
import com.code.model.User;
import com.code.model.UserExample;


@Service 
public class UserService {

	@Autowired
	private UserMapper userDao;
	
	public int Login(String username,String password)
	{
	     UserExample example = new UserExample();
	     UserExample.Criteria criteria = example.createCriteria();
	     criteria.andUsernameEqualTo(username);
	     List<User> users = userDao.selectByExample(example);
	     if(users.size()<=0)
	    	 return -2;
	     criteria.andPasswordEqualTo(password);
	     
	     users = userDao.selectByExample(example);
	     if(users.size()<=0)
	    	 return -1;
	     else return users.get(0).getLevel();
	}
	
	public int Register(String userName,String passWord)
	{
		UserExample example = new UserExample();
	    UserExample.Criteria criteria = example.createCriteria();
	    criteria.andUsernameEqualTo(userName);
	    List<User> users = userDao.selectByExample(example);
	    if(users.size()>0)
	    	return 0;
	    User user = new User();
	    user.setUsername(userName);
	    user.setPassword(passWord);
	    user.setLevel(0);
	    userDao.insert(user);
	    return 1;
	}


}
