package com.shop.demo.controller;

import javax.naming.spi.ResolveResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.shop.demo.service.RepoService;

@RestController
public class RepoController {
	
	@Autowired
	private RepoService service;
	
	
	@GetMapping("/repos/trending")
	public ResponseEntity<?> getResult(){
		try {
			
			var data = service.getData();
			return new ResponseEntity<>(data,HttpStatus.OK);
			
		}catch(Exception ex) {
			
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	

}
