package com.example.IntegrationService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.IntegrationService.model.IntegrationConfig;

import jakarta.transaction.Transactional;

@Repository
public interface FetchData extends JpaRepository<IntegrationConfig, Integer>  {
	
	List<IntegrationConfig> findByStatus(String status);
	
	@Transactional
	@Modifying
	@Query("Update IntegrationConfig ic set ic.status = '02' where ic.id =:id")
	void updateStatusById(@Param("id") Integer id);
	
}
