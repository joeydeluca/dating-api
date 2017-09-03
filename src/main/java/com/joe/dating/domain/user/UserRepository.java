package com.joe.dating.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {
    List<User> findByUsername(String username);
    List<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
