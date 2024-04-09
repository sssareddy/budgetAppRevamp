package com.budget.budgetRevamp.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.budgetRevamp.chartModel.ChartResponse;
import com.budget.budgetRevamp.model.ReportResponse;
import com.budget.budgetRevamp.repository.BudgetRepo;
import com.budget.budgetRevamp.util.CommonUtill;

@Service
public class BudgetChartServiceImpl implements BudgetChartService {
	
	@Autowired
	private SearchBudgetService searchBudgetService;
	
	@Autowired
	private BudgetRepo budgetRepo;

	@Override
	public ChartResponse getItemChartMonthly(String perticularType,String month,String chartType) {
		ChartResponse response=new ChartResponse();
		ReportResponse res=searchBudgetService.getSummaryByMonth(month);
		Map<String, Double> itemMap = new LinkedHashMap<>();
		switch (perticularType) {
		case "item": {
			itemMap=res.getItemMap();
			 break;
		}
		case "category": {
			itemMap=res.getCategoryMap();
			 break;
		}
		case "purchaseMode": {
			itemMap=res.getPurchaseModeMap();
			 break;
		}
		case "paymentMode": {
			itemMap=res.getPaymentModeMap();
			 break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + perticularType);
		}
		 List<String> keysList = itemMap.keySet().stream().collect(Collectors.toList());
		 List<Double> valuesList = itemMap.values().stream().collect(Collectors.toList());
		 List<Double> valuesListSort= valuesList.stream()
	                .filter(val -> val != null)
	                .sorted()
	                .collect(Collectors.toList());
			 response=CommonUtill.prepareCharts(keysList, valuesList,valuesListSort.get(0).intValue(),valuesListSort.get(valuesListSort.size()-1).intValue(),chartType);
		return response;
	}
	
	@Override
	public ChartResponse getItemChartYearly(String perticularType, String year,String chartType) {
		ChartResponse response=new ChartResponse();
		ReportResponse res=searchBudgetService.getSummaryByYear(year);
		Map<String, Double> itemMap = new LinkedHashMap<>();
		switch (perticularType) {
		case "item": {
			itemMap=res.getItemMap();
			 break;
		}
		case "category": {
			itemMap=res.getCategoryMap();
			 break;
		}
		case "purchaseMode": {
			itemMap=res.getPurchaseModeMap();
			 break;
		}
		case "paymentMode": {
			itemMap=res.getPaymentModeMap();
			 break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + perticularType);
		}
		 List<String> keysList = itemMap.keySet().stream().collect(Collectors.toList());
		 List<Double> valuesList = itemMap.values().stream().collect(Collectors.toList());
		 List<Double> valuesListSort= valuesList.stream()
	                .filter(val -> val != null)
	                .sorted()
	                .collect(Collectors.toList());
			 response=CommonUtill.prepareCharts(keysList, valuesList,valuesListSort.get(0).intValue(),valuesListSort.get(valuesListSort.size()-1).intValue(),chartType);
		return response;
	}

	@Override
	public ChartResponse getTotalBudgetByMonth(String year,String chartType) {
		Map<String, Double> itemMap = new LinkedHashMap<>();
		ChartResponse response = new ChartResponse();
		for (String month : CommonUtill.getMonthsList()) {
			YearMonth yearMonth = YearMonth.of(Year.parse(year).getValue(), Month.valueOf(month.toUpperCase()));
			LocalDate start = yearMonth.atDay(1);
			LocalDate end = yearMonth.atEndOfMonth();
			Double totalBudget = budgetRepo.getTotalExpense(start, end);
			itemMap.put(month, totalBudget);
		}
		List<String> keysList = itemMap.keySet().stream().collect(Collectors.toList());
		List<Double> valuesList = itemMap.values().stream().collect(Collectors.toList());
		List<Double> valuesListSort= valuesList.stream()
                .filter(val -> val != null)
                .sorted()
                .collect(Collectors.toList());
		 response=CommonUtill.prepareCharts(keysList, valuesList,valuesListSort.get(0).intValue(),valuesListSort.get(valuesListSort.size()-1).intValue(),chartType);
		return response;
	}

	@Override
	public ChartResponse getTotalBudgetByYear(String chartType) {
		Map<String, Double> yearMap = new LinkedHashMap<>();
		ChartResponse response = new ChartResponse();
		int year=Year.now().getValue()+1;
		for(int i=0;i<5;i++) {
			year=year-1;
			Year currYear=Year.parse(Integer.toString(year));
			LocalDate firstDayOfYear = currYear.atDay(1);
			LocalDate lastDayOfYear=null;
			if(!currYear.isLeap()) {
			 lastDayOfYear = currYear.atDay(365);
			} else {
				 lastDayOfYear = currYear.atDay(366);
			}
			Double totalBudget = budgetRepo.getTotalExpense(firstDayOfYear, lastDayOfYear);
			yearMap.put(Integer.toString(year), totalBudget);
		}
		List<String> keysList = yearMap.keySet().stream().collect(Collectors.toList());
		List<Double> valuesList = yearMap.values().stream().collect(Collectors.toList());
		List<Double> valuesListSort= valuesList.stream()
                .filter(val -> val != null)
                .sorted()
                .collect(Collectors.toList());
		 response=CommonUtill.prepareCharts(keysList, valuesList,50000,valuesListSort.get(valuesListSort.size()-1).intValue(),chartType);
		return response;
	}

}
