package com.user.service;

import java.io.ByteArrayInputStream;
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

	@Autowired
	private ExcelUtils excelUtils;

	public Audit addAudit(Audit audit) {

		logger.info("Add audit");

		try {
			audit.setCreatedDate(LocalDateTime.now());

			Audit result = auditRepository.save(audit);
			return result;
		} catch (Exception e) {
			logger.error("addAudit() Failed isertion");
			return audit;
		}

	}

	public InputStream loadFile(String userName) {

		logger.info("loadFile " + userName);

		try {
			List<Audit> audits = auditRepository.findByUserName(userName);
			ByteArrayInputStream in = excelUtils.auditsToExcel(audits);
			return in;
		} catch (Exception e) {
			logger.error(" ");
		}

		return null;
	}

	public List<Audit> getByUserName(String userName) {

		logger.info("get audit " + userName);
		try {
			List<Audit> audits = auditRepository.findByUserName(userName);
			return audits;
		} catch (Exception e) {
			return null;
		}

	}

	public Audit addAudit(String userName, String activity, String msg) {
		try {
			Audit audit = new Audit();
			audit.setActivity(activity);
			audit.setMessage(msg);
			audit.setUserName(userName);
			audit.setCreatedDate(LocalDateTime.now());

			Audit result=auditRepository.save(audit);
			return result;
		} catch (Exception e) {
			return null;
		}

	}

}
