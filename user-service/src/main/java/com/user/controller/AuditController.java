package com.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.Audit;
import com.user.payload.response.MessageResponse;
import com.user.service.AuditService;

@RestController
@RequestMapping("/api/audit/")
public class AuditController {

	private Logger logger = LoggerFactory.getLogger(AuditController.class);

	@Autowired
	private AuditService auditService;

	@PostMapping("add")
	public ResponseEntity<?> addAudit(@RequestBody Audit audit) {

		logger.info("Audit");

		Audit result = auditService.addAudit(audit);

		if (result == null) {
			logger.error("Audit create failed!");
			return ResponseEntity.internalServerError().body(new MessageResponse("Audit create failed!"));
		} else {
			logger.info("Audit created successfully!");
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Audit created successfullY!"));
		}

	}

	@GetMapping("download")
	public ResponseEntity<InputStreamResource> downloadExcel(@RequestParam String userName) {

		logger.info("download audit " + userName);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=Audit.xlsx");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(auditService.loadFile(userName)));

	}
	@GetMapping("get/userName")
	public ResponseEntity<List<Audit>> getByUserName(@RequestParam String userName) {

		logger.info("get audit " + userName);
		
		List<Audit> audits=auditService.getByUserName(userName);

		return ResponseEntity.ok().body(audits);
	}
}
