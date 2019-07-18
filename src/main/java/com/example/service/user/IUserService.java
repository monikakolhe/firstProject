package com.example.service.user;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.entity.User;
import com.example.entityVO.UserVO;

public interface IUserService {

	public UserVO registerUser(UserVO user);

	public List<User> getAllUsersList();

	public UserVO updateUser(UserVO existuser);

	public UserVO deleteUser(String deleteUsername);

	public List<User> userSortbyDateTime();

	public UserVO loginUser(UserVO user);

	public String uploadFile(MultipartFile file, String username);

	public String resetPasswordByEmail(String email, String username);
	
	public UserVO getUserInfo(String username);
	
	public UserVO addUser(UserVO user);
	
	public String delAccountMailToAdmin(String email, String username);
	
	public String updataUserInfo(UserVO user);
}
