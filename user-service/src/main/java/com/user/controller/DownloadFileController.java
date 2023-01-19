package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.FileServices;

@RestController
public class DownloadFileController {

	@Autowired
	FileServices fileServices;

    /*
     * Download Files
     */
	@GetMapping("/file")
	public ResponseEntity<InputStreamResource> downloadFile() {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Audit.xlsx");
		
		return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(fileServices.loadFile()));	
	}
}