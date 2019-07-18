package com.example.repository.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;

import com.example.customException.UserNotPresentException;
import com.example.entity.User;
import com.example.repository.IUserRepositoryCustom;


public class UserRepositoryImpl implements IUserRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public List<User> findUserByCreatedDate()  {
		// TODO Auto-generated method stub
		
			List<User> users = null;
		
		
		Query query = new Query().with(new Sort(new Order(Direction.DESC, "createdDate")));
		users =  mongoTemplate.find(query, User.class);
		try
		{
			if(!users.isEmpty())
			{
				return users;
		}
		}catch (NullPointerException e) {
			// TODO: handle exception
			new UserNotPresentException("Users not present in Database",e,  HttpStatus.NOT_FOUND.value());
		}
		return users;
	}

}
