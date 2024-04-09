package com.budget.budgetRevamp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.budget.budgetRevamp.model.BudgetResponse;
import com.budget.budgetRevamp.model.GenericResponse;
import com.budget.budgetRevamp.service.BulkUploadService;

@RestController
@RequestMapping("/bulkUpload")
@CrossOrigin(origins = "http://localhost:4200")
public class BulkUploadController {

	@Autowired
	BulkUploadService bulkUploadService;
	
	@PostMapping("/bulkUploadItems")
	public ResponseEntity<BudgetResponse> addItem(@RequestParam("file") MultipartFile file) {
		bulkUploadService.readDataFromExcel(file);
		BudgetResponse response=new BudgetResponse();
		response.setMessage("File Uploaded Successfully");
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<BudgetResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping("/uploadPerticulars")
	public ResponseEntity<GenericResponse> addPerticular(@RequestParam("file") MultipartFile file) {
		String result=bulkUploadService.readPerticulars(file);
		GenericResponse response=new GenericResponse();
		response.setMessage(result);
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<GenericResponse>(response,HttpStatus.OK);
		
	}
	@PostMapping("/exportBudget")
	public ResponseEntity<GenericResponse> exportBudget() { 
		GenericResponse res=new GenericResponse();
		res.setMessage(bulkUploadService.exportBudget());
		res.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<GenericResponse>(res,HttpStatus.OK);
	}

}
