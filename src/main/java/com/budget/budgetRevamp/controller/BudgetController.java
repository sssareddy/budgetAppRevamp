package com.budget.budgetRevamp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.budget.budgetRevamp.model.BudgetDTO;
import com.budget.budgetRevamp.model.BudgetResponse;
import com.budget.budgetRevamp.model.GenericResponse;
import com.budget.budgetRevamp.model.PerticularsDTO;
import com.budget.budgetRevamp.model.PerticularsEntity;
import com.budget.budgetRevamp.model.PerticularsResponse;
import com.budget.budgetRevamp.service.BudgetService;

@RestController
@RequestMapping("/budget")
@CrossOrigin(origins = "http://localhost:4200")
public class BudgetController {
	@Autowired
	BudgetService budgetService;
	@PostMapping("/addItem")
	public ResponseEntity<BudgetResponse> addItem(@RequestBody BudgetDTO itemRequest) {
		String responseBody = budgetService.addItem(itemRequest);
		BudgetResponse response=new BudgetResponse();
		response.setMessage(responseBody);
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<BudgetResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getItems")
	public ResponseEntity<List<BudgetDTO>> getItems() {
		List<BudgetDTO> responseBody=budgetService.getItems();
		return new ResponseEntity<List<BudgetDTO>>(responseBody, HttpStatus.OK);
	}
	@GetMapping("/getItemsByMonth")
	public ResponseEntity<BudgetResponse> getItemsByMonth(@RequestParam("month") String month) {
		BudgetResponse responseBody=budgetService.getItemsByMonth(month);
		responseBody.setMessage("Success");
		responseBody.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<BudgetResponse>(responseBody, HttpStatus.OK);
	}
	
	@PostMapping("/{type}/{perticular}")
	public ResponseEntity<GenericResponse> addPerticular(@PathVariable String type,@PathVariable String perticular) {
		String result=budgetService.addPerticular(type, perticular);
		GenericResponse response=new GenericResponse();
		response.setMessage(result);
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<GenericResponse>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/{type}")
	public ResponseEntity<PerticularsResponse> getPerticular(@PathVariable String type) {
		PerticularsResponse response=budgetService.getPerticulars(type);
		return new ResponseEntity<PerticularsResponse>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("perticularsMgmt/{type}")
	public ResponseEntity<PerticularsResponse> getPerticularByType(@PathVariable String type) {
		PerticularsResponse response=budgetService.getPerticularsByType(type);
		return new ResponseEntity<PerticularsResponse>(response,HttpStatus.OK);
		
	}
	
	@PostMapping("savePerticular")
	public ResponseEntity<PerticularsResponse> editPerticular(@RequestBody PerticularsDTO perticularsDTO) {
		PerticularsResponse response=budgetService.savePerticular(perticularsDTO);
		return new ResponseEntity<PerticularsResponse>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("getCategory/{item}")
	public ResponseEntity<GenericResponse> getCategoryForItem(@PathVariable String item) {
		GenericResponse response=budgetService.getCategoryForItem(item);
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<GenericResponse>(response,HttpStatus.OK);
		
	}
	
	@PostMapping("deleteById/{id}")
	public ResponseEntity<BudgetResponse> deleteBudgetById(@PathVariable int id,@RequestBody List<BudgetDTO> searchList) {
		BudgetResponse response=budgetService.deleteBudgetById(id,searchList);
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<BudgetResponse>(response,HttpStatus.OK);
		
	}
	@PostMapping("deletePerticular/{id}")
	public ResponseEntity<PerticularsResponse> deletePerticularById(@PathVariable int id) {
		PerticularsResponse response=budgetService.deletePerticularById(id);
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<PerticularsResponse>(response,HttpStatus.OK);
		
	}
}
