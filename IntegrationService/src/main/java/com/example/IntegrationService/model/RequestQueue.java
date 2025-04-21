package com.example.IntegrationService.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_queue")
public class RequestQueue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "api_config_id")
	private ApiConfig apiConfig;


	// --01->pending, --02->In Progress, --03->Failed, --04->Processed
	private String status; // 01, 02, 03, 04

	private String comment;

	@Column(name = "created_time")
	private LocalDateTime createdTime = LocalDateTime.now();

	@Column(name = "processed_time")
	private LocalDateTime processedTime;

	public ApiConfig getApiConfig() {
		return apiConfig;
	}

	public void setApiConfig(ApiConfig apiConfig) {
		this.apiConfig = apiConfig;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getProcessedTime() {
		return processedTime;
	}

	public void setProcessedTime(LocalDateTime processedTime) {
		this.processedTime = processedTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
