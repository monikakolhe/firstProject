package com.example.repository;

import java.util.List;
import com.example.entity.User;

public interface IUserRepositoryCustom {


	public List<User> findUserByCreatedDate(); 
}
