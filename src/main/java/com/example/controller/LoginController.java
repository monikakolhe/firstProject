
package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entityVO.UserVO;
import com.example.service.user.impl.UserServiceImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	/**
	 * list of User
	 **/
	@RequestMapping(value="/user/adminSecure/list",method=RequestMethod.GET)
	public ResponseEntity<?> login() {
		return new ResponseEntity<> (this.userServiceImpl.getAllUsersList(),HttpStatus.OK);
	}
	
	/**
	 * User information
	 **/
	@RequestMapping(value="/user/userInfo",method=RequestMethod.GET)
	public ResponseEntity<?> userInfo(@RequestParam("username") String username) {
		return new ResponseEntity<> (this.userServiceImpl.getUserInfo(username),HttpStatus.OK);
	}
	
	/**
	 * User registration
	 **/
	@RequestMapping(value="/user/save", method=RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody UserVO user ) {
		return new ResponseEntity<>(this.userServiceImpl.registerUser(user),HttpStatus.OK);
	}
	
	/**
	 * User sign_in
	 **/
	@RequestMapping(value="/user/sign_in", method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UserVO user) {
		return new ResponseEntity<>(this.userServiceImpl.loginUser(user),HttpStatus.OK);
	}	
	
	
	/**
	 * header impl 
	 **/
	@RequestMapping(value="/user/info", method=RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody UserVO user) {
		return new ResponseEntity<>(this.userServiceImpl.addUser(user),HttpStatus.OK);
	}
	
	/**
	 * Delete user from Admin Side
	 **/
	@RequestMapping(value="/user/delete",method=RequestMethod.DELETE)
	public ResponseEntity<?> userDelete(@RequestParam("username")String username ) {
		return new ResponseEntity<> (this.userServiceImpl.deleteUser(username),HttpStatus.OK);
	}
	
	/**
	 * Update password User
	 **/
	@RequestMapping(value="/user/update",method=RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody UserVO user) {	
		return new ResponseEntity<> (this.userServiceImpl.updateUser(user),HttpStatus.OK);
	}
	
	/**
	 * Sort User by data and time
	 **/
	@RequestMapping(value="/user/sort",method=RequestMethod.GET)
	public ResponseEntity<?> sortByDateTime() {
		return new ResponseEntity<> (this.userServiceImpl.userSortbyDateTime(),HttpStatus.OK);
	}
	
	/**
	 * Upload File
	 **/
	@RequestMapping(value="/user/uploadFile",method=RequestMethod.POST)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
	 
		return new ResponseEntity<>(userServiceImpl.uploadFile(file, username),HttpStatus.OK);
	}	
	
	/**
	 * Send mail for password Reset
	 **/
	@RequestMapping(value="/setPasswordFromMail",method=RequestMethod.POST)
	public ResponseEntity<?> forgetPassword(@RequestParam("email")String email, @RequestParam("username") String username) {
	 
		return new ResponseEntity<>(userServiceImpl.resetPasswordByEmail(email, username),HttpStatus.OK);
	}	
	
	/**
	 * Send mail for Delete user from adminSide
	 **/
	@RequestMapping(value="/unsubscribeUser",method=RequestMethod.POST)
	public ResponseEntity<?> unsubscribeUser(@RequestParam("email")String email, @RequestParam("username") String username) {
	 
		return new ResponseEntity<>(userServiceImpl.delAccountMailToAdmin(email, username),HttpStatus.OK);
	}	
	
	/**
	 * Update UserInfo
	 **/
	@RequestMapping(value="/updateUserInfo",method=RequestMethod.PUT)
	public ResponseEntity<?> updateInfo (@RequestBody UserVO user) {
	 
		return new ResponseEntity<>(userServiceImpl.updataUserInfo(user),HttpStatus.OK);
	}	
}
