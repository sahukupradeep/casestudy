package com.user.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.Audit;
import com.user.repository.AuditRepository;
import com.user.util.ExcelUtils;

@Service
public class AuditService {

	private Logger logger = LoggerFactory.getLogger(AuditService.class);

	@Autowired
	private AuditRepository auditRepository;

	public Audit addAudit(Audit audit) {

		logger.info("Add audit");

		audit.setCreatedDate(LocalDateTime.now());

		Audit result = auditRepository.save(audit);
		return result;
	}

	public InputStream loadFile(String userName) {

		logger.info("loadFile " + userName);

		List<Audit> audits = auditRepository.findByUserName(userName);

		try {
			ByteArrayInputStream in = ExcelUtils.auditsToExcel(audits);
			return in;
		} catch (IOException e) {
		}

		return null;
	}

	public List<Audit> getByUserName(String userName) {

		logger.info("get audit " + userName);

		List<Audit> audits = auditRepository.findByUserName(userName);
		return audits;
	}

	public void addAudit(String userName, String activity, String msg) {
		Audit audit = new Audit();
		audit.setActivity(activity);
		audit.setMessage(msg);
		audit.setUserName(userName);
		audit.setCreatedDate(LocalDateTime.now());
		
		auditRepository.save(audit);
	}

}
