package com.vti.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vti.service.IFileService;
import com.vti.utils.FileManager;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/files")
@Validated
@Slf4j
public class FileController {
	
	//private static Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private IFileService fileService;

	@PostMapping(value = "/image")
	public ResponseEntity<?> upLoadImage(@RequestParam(name = "image") MultipartFile image) throws IOException {

		if (!new FileManager().isTypeFileImage(image)) {
			return new ResponseEntity<>("File must be image!", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(fileService.uploadImage(image), HttpStatus.OK);
	}
	
	@PostMapping(value = "/product")
	public ResponseEntity<?> upLoadProduct(@RequestParam(name = "image") MultipartFile image, 
			@RequestParam(name = "name") String name) {

		if (!new FileManager().isTypeFileImage(image)) {
			return new ResponseEntity<>("File must be image!", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		String imageLink = "";
		try {
			imageLink = fileService.uploadImage(image);
		}
		catch (IOException io) {
			log.error(io.toString());			
		}
		
		log.info("Name " + name + "Image Link " + imageLink);
		
		log.info("debug enabled: {}", log.isDebugEnabled());
	    log.trace("trace");
	    log.debug("debug");
	    log.info("info");
	    log.warn("warn");
	    log.error("error");
		
		return new ResponseEntity<>(imageLink, HttpStatus.OK);				
	}
}
