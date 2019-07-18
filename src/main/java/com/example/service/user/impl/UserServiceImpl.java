package com.example.service.user.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.customException.ApplicationException;
import com.example.customException.PasswordNotMatchException;
import com.example.customException.UserNotPresentException;
import com.example.entity.MessageDetails;
import com.example.entity.User;
import com.example.entityVO.UserVO;
import com.example.repository.ProfileDetailsRepository;
import com.example.repository.UserRepository;
import com.example.security.GenerateJWTToken;
import com.example.service.user.IUserService;

@Service
@PropertySource("classpath:global.properties")
@ConfigurationProperties
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GenerateJWTToken generateJWTToken;

	@Autowired
	private ProfileDetailsRepository profileDetailsRepository;

	@Autowired
	Environment environment;

	/*
	 * @Autowired private EmailServiceImpl emailServiceImpl;
	 */
	@Autowired
	private JavaMailSender mailSender;

	@Value("${mail.from.email}")
	private String mail_from_email;

	private boolean flag = false;
	private static String UPLOAD_FOLDER = "E:/monika/";

	@Override
	public List<User> getAllUsersList() {

		return userRepository.findAll();
	}

	 final static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Override
	public UserVO registerUser(UserVO newUser) {

		User registerUser = null;
		MessageDetails messageDetails = null;
		if (!StringUtils.isEmpty(newUser.getUsername())) {
			User existingUser = userRepository.findByUsername(newUser.getUsername());
			if (null != existingUser) {
				// logger.error(environment.getProperty("user_presentDB"));
				System.out.println(environment.getProperty("user_presentDB"));
				throw new UserNotPresentException(environment.getProperty("user_presentDB"));
			} else {
				if (StringUtils.isEmpty(newUser.getPassword())) {
					throw new UserNotPresentException(environment.getProperty("invalid_password"));
				}
				registerUser = new User();
				registerUser.setUsername(newUser.getUsername());
				registerUser.setPassword(newUser.getPassword());
				registerUser.setCreatedDate(new Date());
				registerUser.setProfileDetails(newUser.getProfileDetails());
				registerUser.setEmail(newUser.getEmail());
				if(StringUtils.isEmpty(newUser.getRole())) {
				registerUser.setRole("user");
				}else
				{
					registerUser.setRole(newUser.getRole());
				}
				registerUser.setStatus("subcribe");
				System.out.println(newUser.getProfileDetails().getAddress().toString());
				this.profileDetailsRepository.save(newUser.getProfileDetails());
				this.userRepository.save(registerUser);

				messageDetails = new MessageDetails();
				messageDetails.setDetails(environment.getProperty("save_user"));
				// logger.info("globalProperties.save_user" +
				// newUser.getUsername());
				newUser.setDetails(messageDetails);
			}
		} else {
			// logger.error(environment.getProperty("invalid_username"));

			throw new UserNotPresentException(environment.getProperty("invalid_username"));
		}
		return this.transformUserIntoUserVO(registerUser);
	}

	private UserVO transformUserIntoUserVO(User user) {
		UserVO userVO = new UserVO();
		userVO.setId(user.getId());
		userVO.setProfileDetails(user.getProfileDetails());
		userVO.setUsername(user.getUsername());
		userVO.setDetails(userVO.getDetails());
		userVO.setEmail(userVO.getEmail());
		userVO.setRole(userVO.getRole());
		return userVO;
	}

	@Override
	public UserVO updateUser(UserVO updateUser) {

		MessageDetails messageDetails = null;
		if (!StringUtils.isEmpty(updateUser.getUsername())) {
			User dbuser = userRepository.findByUsername(updateUser.getUsername());
			if (null != dbuser) {
				if (!StringUtils.isEmpty(updateUser.getPassword())) {
					if (!(dbuser.getPassword().equals(updateUser.getPassword()))) {
						dbuser.setPassword(updateUser.getPassword());
						userRepository.save(dbuser);
						messageDetails = new MessageDetails();
						messageDetails.setDetails(environment.getProperty("update_password"));
						updateUser.setDetails(messageDetails);
					} else {
						// logger.debug(environment.getProperty("check_password"));
						throw new PasswordNotMatchException(environment.getProperty("check_password"));
					}
				} else {
					throw new UserNotPresentException(environment.getProperty("invalid_password"));
				}
			} else {
				throw new UserNotPresentException(environment.getProperty("username_not_present"));
			}

		} else {
			throw new UserNotPresentException(environment.getProperty("invalid_username"));
		}
		return updateUser;
	}

	@Override
	public UserVO deleteUser(String deleteUsername) {
		
		UserVO userVO = new UserVO();
		MessageDetails messageDetails = null;
		if (!StringUtils.isEmpty(deleteUsername)) {
			User deleteUser = userRepository.findByUsername(deleteUsername);
			if (null != deleteUser) {
				this.profileDetailsRepository.delete(deleteUser.getProfileDetails().getId());
				this.userRepository.delete(deleteUser);
				messageDetails = new MessageDetails();
				messageDetails.setDetails(environment.getProperty("delete_user"));
				userVO.setUsername(deleteUser.getUsername());
				userVO.setEmail(deleteUser.getEmail());
				userVO.setProfileDetails(deleteUser.getProfileDetails());
				userVO.setDetails(messageDetails);
			} else {
				throw new UserNotPresentException(environment.getProperty("user_not_present"));
			}
		} else {
			throw new UserNotPresentException(environment.getProperty("invalid_username"));
		}
		return userVO;
	}

	@Override
	public List<User> userSortbyDateTime() {
		return userRepository.findUserByCreatedDate();
	}

	@Override
	public UserVO loginUser(UserVO loginUser) {

		String jwtToken;
		MessageDetails messageDetails = null;
		if (!StringUtils.isEmpty(loginUser.getUsername())) {
			User databaseUser = userRepository.findByUsername(loginUser.getUsername());
			if (null != databaseUser) {
				if (!StringUtils.isEmpty(loginUser.getPassword())) {
					messageDetails = new MessageDetails();
					if ((loginUser.getPassword().equals(databaseUser.getPassword()))) {
						if (flag == true) {
							updateUser(loginUser);
						}
						jwtToken = generateJWTToken.getToken(databaseUser);
						System.out.println(jwtToken);
						loginUser.setJwtToken(jwtToken);
						// logger.info("globalProperties.save_user" + " " +
						// databaseUser.getUsername());
						messageDetails.setDetails(databaseUser.getUsername() + " " + environment.getProperty("login_user"));
						loginUser.setDetails(messageDetails);
						loginUser.setRole(databaseUser.getRole());
						loginUser.setStatus(databaseUser.getStatus());
						System.out.println(databaseUser.getStatus());
						System.out.println("login successfully");
					} else {
						throw new UserNotPresentException(environment.getProperty("pass_not_match"), HttpStatus.NOT_FOUND.value());
					}
				} else {
					throw new UserNotPresentException(environment.getProperty("invalid_password"), HttpStatus.NOT_FOUND.value());
				}
			} else {
				throw new UserNotPresentException(environment.getProperty("user_not_present"), HttpStatus.NOT_FOUND.value());
			}
		} else {
			throw new UserNotPresentException(environment.getProperty("invalid_username"), HttpStatus.NOT_FOUND.value());
		}
		return loginUser;
	}

	@Override
	public String uploadFile(MultipartFile file, String username) {

		User databaseUser = new User();
		String fileLine;
		String saveFileInDatabase = null;

		byte[] bytes;
		if (file.isEmpty()) {
			throw new ApplicationException(environment.getProperty("file_not_found"), 404);
		} else {
			databaseUser = userRepository.findByUsername(username);
			if (databaseUser != null) {
				try {
					bytes = file.getBytes();
					Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
					Files.write(path, bytes);

					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(UPLOAD_FOLDER + file.getOriginalFilename()));

					while ((fileLine = br.readLine()) != null) {
						saveFileInDatabase = fileLine;
					}
					databaseUser.setFileData(saveFileInDatabase);
					userRepository.save(databaseUser);
				} catch (IOException e) {
					throw new ApplicationException("not get the path", HttpStatus.NOT_FOUND.value());
				}
				return (environment.getProperty("File_uploaded"));
			} else {
				throw new UserNotPresentException(environment.getProperty("user_not_present" + " can not upload the file"));
			}
		}
	}

	@Override
	public String resetPasswordByEmail(String email, String username) {
		// TODO Auto-generated method stub

		User databaseUser = new User();

		if (email.isEmpty()) {
			throw new ApplicationException(environment.getProperty("email_not_found"), 404);
		} else {
			databaseUser = userRepository.findByUsername(username);
			if (databaseUser != null) {

				databaseUser.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0, 8));
				userRepository.save(databaseUser);
				flag = true;

				SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
				passwordResetEmail.setFrom(mail_from_email);
				passwordResetEmail.setTo(databaseUser.getEmail());
				passwordResetEmail.setSubject(environment.getProperty("Reset_password"));
				passwordResetEmail.setText("Your reset password = " + databaseUser.getPassword());

				mailSender.send(passwordResetEmail);
			} else {
				throw new UserNotPresentException(environment.getProperty("user_not_present" + " can not send the email"));
			}
		}
		return "Email sends";
	}

	@Override
	public UserVO getUserInfo(String username) {
		
		UserVO user = new UserVO();
			User dbuser = userRepository.findByUsername(username);
			if (null != dbuser) {
				if(dbuser.getUsername().equals(username))	
				{
					userRepository.findOne(dbuser.getId());
				user.setEmail(dbuser.getEmail());
				user.setUsername(dbuser.getUsername());
				user.setProfileDetails(dbuser.getProfileDetails());
				user.setRole(dbuser.getRole());
				user.setStatus(dbuser.getStatus());
				}
			} else {
				throw new UserNotPresentException(environment.getProperty("username_not_present"));
			}
		return user;
	}

	public UserVO addUser(UserVO newUser) {
	

		User registerUser = null;
		MessageDetails messageDetails = null;
		if (!StringUtils.isEmpty(newUser.getUsername())) {
			User existingUser = userRepository.findByUsername(newUser.getUsername());
			if (null != existingUser) {
				// logger.error(environment.getProperty("user_presentDB"));
				System.out.println(environment.getProperty("user_presentDB"));
				throw new UserNotPresentException(environment.getProperty("user_presentDB"));
			} else {
				if (StringUtils.isEmpty(newUser.getPassword())) {
					throw new UserNotPresentException(environment.getProperty("invalid_password"));
				}
				registerUser = new User();
				registerUser.setUsername(newUser.getUsername());
				registerUser.setPassword(newUser.getPassword());
				registerUser.setCreatedDate(new Date());
				registerUser.setProfileDetails(newUser.getProfileDetails());
				registerUser.setEmail(newUser.getEmail());
				registerUser.setRole(newUser.getRole());
				System.out.println(newUser.getProfileDetails().getAddress().toString());
				this.profileDetailsRepository.save(newUser.getProfileDetails());
				this.userRepository.save(registerUser);

				messageDetails = new MessageDetails();
				messageDetails.setDetails(environment.getProperty("save_user"));
				// logger.info("globalProperties.save_user" +
				// newUser.getUsername());
				newUser.setDetails(messageDetails);
			}
		} else {
			// logger.error(environment.getProperty("invalid_username"));

			throw new UserNotPresentException(environment.getProperty("invalid_username"));
		}
		return this.transformUserIntoUserVO(registerUser);
	}

	public String delAccountMailToAdmin(String email, String username) {
	
		User databaseUser = new User();

		if (email.isEmpty()) {
			throw new ApplicationException(environment.getProperty("email_not_found"), 404);
		} else {
			databaseUser = userRepository.findByUsername(username);
			if (databaseUser != null) {

//				databaseUser.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0, 8));
//				userRepository.save(databaseUser);
				databaseUser.setStatus("Unsubcribed");
				userRepository.save(databaseUser);
				SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
				passwordResetEmail.setFrom(mail_from_email);
				passwordResetEmail.setTo(databaseUser.getEmail());
				passwordResetEmail.setSubject(environment.getProperty("Delete_Account"));
				passwordResetEmail.setText("Please Delete my account = " + databaseUser.getUsername());

				mailSender.send(passwordResetEmail);
				
			} else {
				throw new UserNotPresentException(environment.getProperty("user_not_present" + " can not send the email"));
			}
		}
		return "Email sends";
	}

	@Override
	public String updataUserInfo(UserVO user) {

		
		MessageDetails messageDetails = null;
		if (!StringUtils.isEmpty(user.getUsername())) {
			User existingUser = userRepository.findByUsername(user.getUsername());
			if (null != existingUser) {
				existingUser.setProfileDetails(user.getProfileDetails());
				existingUser.setEmail(user.getEmail());
				System.out.println(user.getProfileDetails().getAddress().toString());
				this.profileDetailsRepository.save(user.getProfileDetails());
				this.userRepository.save(existingUser);

				messageDetails = new MessageDetails();
				messageDetails.setDetails(environment.getProperty("save_user"));
				// logger.info("globalProperties.save_user" +
				
				user.setDetails(messageDetails);
			}
			else {
				throw new UserNotPresentException(environment.getProperty("user_not_present"));
			}
		} else {
			// logger.error(environment.getProperty("invalid_username"));

			throw new UserNotPresentException(environment.getProperty("invalid_username"));
		}
		return "update user info";
	}

}