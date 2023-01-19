package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByUserName(String userName);

	Boolean existsByEmail(String email);

	Boolean existsByPhone(String phone);

	Optional<User> findByUserName(String userName);

	Optional<User> findByUserNameAndPassword(String userName, String password);

}
