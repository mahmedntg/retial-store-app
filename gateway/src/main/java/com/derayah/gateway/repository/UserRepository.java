package com.derayah.gateway.repository;

import com.derayah.gateway.authentication.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * UserRepository.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {
    @Query(value="{'username' : ?0}")
    User findByUsername(String username);
}
