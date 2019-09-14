package com.codetest.app.myretail.repository;

import com.codetest.app.myretail.response.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author nrajuri
 */
public interface UsersRepository extends MongoRepository<Users, String> {

    Users findByUsername(String username);

}
