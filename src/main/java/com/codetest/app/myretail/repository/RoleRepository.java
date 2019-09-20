package com.codetest.app.myretail.repository;

import com.codetest.app.myretail.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * @author nrajuri
 */
public interface RoleRepository extends MongoRepository<Role, String> {

}
