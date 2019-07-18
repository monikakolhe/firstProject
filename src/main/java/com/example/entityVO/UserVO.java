package com.example.entityVO;

import com.example.entity.MessageDetails;
import com.example.entity.ProfileDetails;

public class UserVO {

	
		private String id;
		private String username;
		private String password;
		private MessageDetails details;
		private ProfileDetails profileDetails;
		private String jwtToken;
		private String fileData;
		private String email;
		private String role;
		private String status;
		

		/**
		 * @return the jwtToken
		 */
		public String getJwtToken() {
			return jwtToken;
		}

		/**
		 * @param jwtToken the jwtToken to set
		 */
		public void setJwtToken(String jwtToken) {
			this.jwtToken = jwtToken;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * @return the details
		 */
		public MessageDetails getDetails() {
			return details;
		}

		/**
		 * @param details the details to set
		 */
		public void setDetails(MessageDetails details) {
			this.details = details;
		}

		/**
		 * @return the profileDetails
		 */
		public ProfileDetails getProfileDetails() {
			return profileDetails;
		}

		/**
		 * @param profileDetails the profileDetails to set
		 */
		public void setProfileDetails(ProfileDetails profileDetails) {
			this.profileDetails = profileDetails;
		}

		/**
		 * @return the fileData
		 */
		public String getFileData() {
			return fileData;
		}

		/**
		 * @param fileData the fileData to set
		 */
		public void setFileData(String fileData) {
			this.fileData = fileData;
		}

		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * @param email the email to set
		 */
		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * @return the role
		 */
		public String getRole() {
			return role;
		}

		/**
		 * @param role the role to set
		 */
		public void setRole(String role) {
			this.role = role;
		}

		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
		
		
}
