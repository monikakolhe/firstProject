package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.ProfileDetails;


@Repository
public interface ProfileDetailsRepository extends MongoRepository<ProfileDetails, String>{

	
	ProfileDetails findByFname(String fname);
	ProfileDetails findByLname(String lname);
//	ProfileDetails findByAddress(List<Address> addresses);
	
}

