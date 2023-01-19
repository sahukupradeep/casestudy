package com.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.User;
import com.user.payload.response.SearchUserResponse;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByUserName(String userName);

	Boolean existsByEmail(String email);

	Boolean existsByPhone(String phone);

	Optional<User> findByUserName(String userName);

	Optional<User> findByUserNameAndPassword(String userName, String password);

	@Query("SELECT new com.user.payload.response.SearchUserResponse(u.userName,u.firstName,u.lastName,u.address) FROM User u WHERE (:userName is null or u.userName = :userName) and "
			+ "(:firstName is null or u.firstName=:firstName) and (:lastName is null or u.lastName=:lastName) and "
			+ "(:dob is null or u.dob=:dob)")
	List<SearchUserResponse> search(@Param("userName") String userName, @Param("firstName") String firstName,
			@Param("lastName") String lastName, @Param("dob") String dob);

	Optional<User> findByUserNameAndEmailAndPhoneAndDob(String userName, String email, String phone, String dob);

}
