package com.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

	List<Audit> findByUserName(String userName);

}
