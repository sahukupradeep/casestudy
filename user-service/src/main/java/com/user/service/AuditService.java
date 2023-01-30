package com.user.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.Audit;
import com.user.entity.User;
import com.user.repository.AuditRepository;
import com.user.repository.UserRepository;
import com.user.util.ExcelUtils;

@Service
public class AuditService {

	private Logger logger = LoggerFactory.getLogger(AuditService.class);

	@Autowired
	private AuditRepository auditRepository;

	@Autowired
	private UserRepository userRepository;

	
	@Autowired
	private ExcelUtils excelUtils;

	public Audit addAudit(Audit audit) {

		logger.info("Add audit");

		try {
			audit.setCreatedDate(LocalDateTime.now());

			Audit result = auditRepository.save(audit);
			return result;
		} catch (Exception e) {
			logger.error("addAudit() : Exception occured, message={}", e.getMessage(), e);
			return null;
		}

	}

	public InputStream loadFile(String userName) {

		logger.info("loadFile " + userName);

		try {
			List<Audit> audits = auditRepository.findByUserNameOrderByCreatedDateDesc(userName);
			ByteArrayInputStream in = excelUtils.auditsToExcel(audits);
			return in;
		} catch (Exception e) {
			logger.error("loadFile() : Exception occured, message={}", e.getMessage(), e);
		}

		return null;
	}

	public List<Audit> getByUserName(String userName) {

		logger.info("get audit " + userName);
		try {
			List<Audit> audits = auditRepository.findByUserNameOrderByCreatedDateDesc(userName);
			return audits;
		} catch (Exception e) {
			logger.error("getByUserName() : Exception occured, message={}", e.getMessage(), e);
			return null;
		}

	}

	public Audit addAudit(String userName, String activity, String msg) {
		logger.info("add audit string");
		try {

			Optional<User> optional = userRepository.findByUserName(userName);

			Audit audit = new Audit();

			if (optional.isPresent()) {
				audit.setFirstName(optional.get().getFirstName());
				audit.setLastName(optional.get().getLastName());
			}
			audit.setActivity(activity);
			audit.setMessage(msg);
			audit.setUserName(userName);
			audit.setCreatedDate(LocalDateTime.now());

			Audit result = auditRepository.save(audit);
			return result;
		} catch (Exception e) {
			logger.error("addAudit() : Exception occured, message={}", e.getMessage(), e);
			return null;
		}

	}

	public InputStream searchDownload(String userName, String firstName, String lastName,String auditDate) {

		logger.info("loadFile " + userName);

		try {
			LocalDateTime startDate=null;
			LocalDateTime endDate=null;
			if(auditDate!=null && auditDate.trim()!="") {
				LocalDate localDate = LocalDate.parse(auditDate);
				startDate = localDate.atStartOfDay();
				endDate=startDate.plusDays(1);

			}
			List<Audit> audits = auditRepository.search(userName,firstName,lastName,startDate,endDate);
			ByteArrayInputStream in = excelUtils.auditsToExcel(audits);
			return in;
		} catch (Exception e) {
			logger.error("loadFile() : Exception occured, message={}", e.getMessage(), e);
		}

		return null;
	}

	public InputStream searchDownloadV1(String userName, String firstName, String lastName, String from, String to) {
		logger.info("searchDownloadV1 " + userName);

		try {
			LocalDateTime startDate=null;
			LocalDateTime endDate=null;
			if((from!=null && from.trim()!="") && (to!=null && to.trim()!="")) {
				LocalDate fromDate = LocalDate.parse(from);
				LocalDate toDate = LocalDate.parse(to);
				startDate = fromDate.atStartOfDay();
				endDate=toDate.atStartOfDay().plusDays(1);

			}
			List<Audit> audits = auditRepository.search(userName,firstName,lastName,startDate,endDate);
			ByteArrayInputStream in = excelUtils.auditsToExcel(audits);
			return in;
		} catch (Exception e) {
			logger.error("loadFile() : Exception occured, message={}", e.getMessage(), e);
		}

		return null;
	}

}
