package com.user.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

	List<Audit> findByUserName(String userName);

	List<Audit> findByUserNameOrderByCreatedDateDesc(String userName);

	@Query("SELECT a FROM Audit a WHERE (:userName is null or a.userName = :userName) and "
			+ "(:firstName is null or a.firstName=:firstName) and (:lastName is null or a.lastName=:lastName) and "
			+ "(:startDate is null or (a.createdDate>=:startDate and a.createdDate<:endDate ))")
	List<Audit> search(@Param("userName") String userName, @Param("firstName") String firstName,
			@Param("lastName") String lastName, @Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

}
