package com.budget.budgetRevamp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.budgetRevamp.model.BudgetResponse;
import com.budget.budgetRevamp.model.MultiSelectRequest;
import com.budget.budgetRevamp.model.ReportResponse;
import com.budget.budgetRevamp.service.SearchBudgetService;

@RestController
@RequestMapping("/searchBudget")
@CrossOrigin(origins = "*")
public class SearchBudgetController {
	@Autowired
	SearchBudgetService searchBudgetService;

	@GetMapping("/searchByMonth/{month}")
	public ResponseEntity<BudgetResponse> searchItemsByMonth(@PathVariable String month) {

		BudgetResponse responseBody = searchBudgetService.getBudgetByMonth(month.toUpperCase());
		responseBody.setMessage("Success");
		responseBody.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<BudgetResponse>(responseBody, HttpStatus.OK);

	}
	
	@GetMapping("/searchByMonth/{perticularType}/{perticularValue}/{from}/{to}")
	public ResponseEntity<BudgetResponse> searchBudget(@PathVariable String perticularType,@PathVariable String perticularValue,@PathVariable String from,@PathVariable String to) {

		BudgetResponse responseBody = searchBudgetService.getBudgetByType(perticularType, perticularValue, from, to);
		responseBody.setMessage("Success");
		responseBody.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<BudgetResponse>(responseBody, HttpStatus.OK);

	}
	
	@GetMapping("/getSummaryByMonth/{month}")
	public ResponseEntity<ReportResponse> getSummary(@PathVariable String month) {

		ReportResponse responseBody = searchBudgetService.getSummaryByMonth(month);
		responseBody.setMessage("Success");
		responseBody.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<ReportResponse>(responseBody, HttpStatus.OK);

	}
	
	@PostMapping("/getSummaryForMultiMonth")
	public ResponseEntity<ReportResponse> getSummaryForMultiMonth(@RequestBody List<String> months) {

		ReportResponse responseBody = searchBudgetService.getSummaryByMultipleMonths(months);
		responseBody.setMessage("Success");
		responseBody.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<ReportResponse>(responseBody, HttpStatus.OK);

	}
	@PostMapping("/getBudgetByMonthMultiSelect")
	public ResponseEntity<BudgetResponse> getBudgetByMonthMultiSelect(@RequestBody MultiSelectRequest multiSelectRequest) {

		BudgetResponse responseBody = searchBudgetService.getBudgetByMonthMultiSelect(multiSelectRequest);
		responseBody.setMessage("Success");
		responseBody.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<BudgetResponse>(responseBody, HttpStatus.OK);

	}
	
}
