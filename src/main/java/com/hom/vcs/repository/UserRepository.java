package com.hom.vcs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hom.vcs.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	
	
}
