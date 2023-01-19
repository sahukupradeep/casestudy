package com.user.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.Audit;
import com.user.repository.AuditRepository;
import com.user.util.ExcelUtils;

@Service
public class FileServices {

	@Autowired
	AuditRepository auditRepository;

	// Load Data to Excel File
	public ByteArrayInputStream loadFile() {
		List<Audit> audits = auditRepository.findAll();

		try {
			ByteArrayInputStream in = ExcelUtils.auditsToExcel(audits);
			return in;
		} catch (IOException e) {
		}

		return null;
	}
}
