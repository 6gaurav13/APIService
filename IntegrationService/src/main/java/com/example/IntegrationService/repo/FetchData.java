package com.example.IntegrationService.repo;

import java.lang.annotation.Native;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.IntegrationService.model.RequestQueue;

import jakarta.transaction.Transactional;

@Repository
public interface FetchData extends JpaRepository<RequestQueue, Integer>  {
	
	List<RequestQueue> findByStatus(String status);
	
	@Transactional
	@Modifying
	@Query("Update RequestQueue ic set ic.status = '02' where ic.id =:id")
	void updateStatusById(@Param("id") Integer id);

	@Query(value = "SELECT c FROM RequestQueue c WHERE c.status = '01'")
	List<RequestQueue> findTop10ByStatus(Pageable pageable);

	@Transactional
	@Modifying
	@Query(value = "Update request_queue Set status = '04', comment = 'Processed', processed_time = :processedTime where api_config_id = :id and id = :itemID", nativeQuery = true)
	int updateStatusAndComment(@Param("id") Integer serviceID, @Param("processedTime") LocalDateTime processedTime, @Param("itemID") Integer itemId);
}
