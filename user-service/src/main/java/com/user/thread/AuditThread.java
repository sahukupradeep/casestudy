package com.user.thread;

import com.user.entity.Audit;
import com.user.service.AuditService;

public class AuditThread implements Runnable {

	private AuditService auditService;

	private Audit audit;

	public AuditThread(AuditService auditService, Audit audit) {
		this.auditService = auditService;
		this.audit = audit;
	}

	@Override
	public void run() {
		
		this.auditService.addAudit(this.audit);

	}

}
