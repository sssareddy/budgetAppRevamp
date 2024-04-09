package com.budget.budgetRevamp.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.budgetRevamp.model.BudgetDTO;
import com.budget.budgetRevamp.model.BudgetEntity;
import com.budget.budgetRevamp.model.BudgetResponse;
import com.budget.budgetRevamp.model.MultiSelectRequest;
import com.budget.budgetRevamp.model.ReportResponse;
import com.budget.budgetRevamp.repository.BudgetRepo;
import com.budget.budgetRevamp.util.CommonUtill;

import jakarta.persistence.EntityManager;

@Service
public class SearchBudgetServiceImpl implements SearchBudgetService {
	
	@Autowired
	BudgetRepo budgetRepo;
	
	@Autowired
	EntityManager em;

	@Override
	public BudgetResponse getBudgetByMonth(String month) {
		List<BudgetDTO> itemsResponseList = null;
		BudgetResponse budgetResponse = new BudgetResponse();
		double totalPrice = 0.0;
		YearMonth yearMonth= YearMonth.of(Year.now().getValue(),Month.valueOf(month));
		LocalDate from = yearMonth.atDay(1);
        LocalDate to = yearMonth.atEndOfMonth();
        List<BudgetEntity> itemList=budgetRepo.findAllItemsByMonth(from, to);
        itemsResponseList = itemList.stream().map(responseEntity -> CommonUtill.mapToDto(responseEntity))
				.collect(Collectors.toList());
		totalPrice = itemList.stream().mapToDouble(item -> item.getPrice()).sum();
		budgetResponse.setItemsList(itemsResponseList);
		budgetResponse.setTotalSum(totalPrice);
		return budgetResponse;
	}

	@Override
	public BudgetResponse getBudgetByDate(String from, String to) {
		// TODO Auto-generated method stub
		List<BudgetDTO> itemsResponseList = null;
		BudgetResponse budgetResponse = new BudgetResponse();
		  List<BudgetEntity> itemList=budgetRepo.findAllItemsByMonth(CommonUtill.dateConverter(from), CommonUtill.dateConverter(to));
		return null;
	}

	@Override
	public BudgetResponse getBudgetByType(String perticularType, String perticularValue,String from, String to) {
		List<BudgetDTO> itemsResponseList = null;
		BudgetResponse budgetResponse = new BudgetResponse();
		List<BudgetEntity> itemList;
		double totalPrice = 0.0;
		switch (perticularType) {
		case "Date": {
			itemList = budgetRepo.findAllItemsByMonth(CommonUtill.dateConverter(from), CommonUtill.dateConverter(to));
			break;
		}
		case "Month": {
			YearMonth yearMonth = YearMonth.of(Year.now().getValue(), Month.valueOf(perticularValue.toUpperCase()));
			LocalDate start = yearMonth.atDay(1);
			LocalDate end = yearMonth.atEndOfMonth();
			itemList = budgetRepo.findAllItemsByMonth(start, end);
			break;
		}
		case "item": {
			itemList = budgetRepo.findAllItemsByItem(perticularValue, CommonUtill.dateConverter(from),
					CommonUtill.dateConverter(to));
			break;
		}
		case "category": {
			itemList = budgetRepo.findAllItemsByCategory(perticularValue, CommonUtill.dateConverter(from),
					CommonUtill.dateConverter(to));
			break;
		}
		case "purchaseMode": {
			itemList = budgetRepo.findAllItemsBypurchaseMode(perticularValue, CommonUtill.dateConverter(from),
					CommonUtill.dateConverter(to));
			break;
		}
		case "paymentMode": {
			itemList = budgetRepo.findAllItemsBypaymentMode(perticularValue, CommonUtill.dateConverter(from),
					CommonUtill.dateConverter(to));
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + perticularType);
		}
		itemsResponseList = itemList.stream().map(responseEntity -> CommonUtill.mapToDto(responseEntity))
				.collect(Collectors.toList());
		totalPrice = itemList.stream().mapToDouble(item -> item.getPrice()).sum();
		budgetResponse.setItemsList(itemsResponseList);
		budgetResponse.setTotalSum(totalPrice);
		return budgetResponse;
	}

	@Override
	public ReportResponse getSummaryByMonth(String month) {
		// TODO Auto-generated method stub
		ReportResponse response=new ReportResponse();
		YearMonth yearMonth = YearMonth.of(Year.now().getValue(), Month.valueOf(month.toUpperCase()));
		LocalDate start = yearMonth.atDay(1);
		LocalDate end = yearMonth.atEndOfMonth();
		List<BudgetEntity> itemList = budgetRepo.findAllItemsByMonth(start, end);
		Map<String, Double> itemMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getItemName, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<String, Double> categoryMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getCategoryName, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<String, Double>  purchaseModeMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getPurchaseMode, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<String, Double>  paymentModeMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getPaymentMode, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<LocalDate, Double>  purchaseDateMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getPurchaseDate, Collectors.summingDouble(BudgetEntity::getPrice)));
		response.setItemMap(itemMap);
		response.setCategoryMap(categoryMap);
		response.setPurchaseModeMap(purchaseModeMap);
		response.setPaymentModeMap(paymentModeMap);
		response.setPurchaseDateMap(purchaseDateMap);
		return response;
	}
	
	@Override
	public ReportResponse getSummaryByYear(String year) {
		ReportResponse response=new ReportResponse();
		Year currYear=Year.parse(year);
		LocalDate firstDayOfYear = currYear.atDay(1);
		LocalDate lastDayOfYear=null;
		if(!currYear.isLeap()) {
		 lastDayOfYear = Year.parse(year).atDay(365);
		} else {
			 lastDayOfYear = Year.parse(year).atDay(366);
		}
		List<BudgetEntity> itemList = budgetRepo.findAllItemsByMonth(firstDayOfYear, lastDayOfYear);
		Map<String, Double> itemMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getItemName, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<String, Double> categoryMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getCategoryName, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<String, Double>  purchaseModeMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getPurchaseMode, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<String, Double>  paymentModeMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getPaymentMode, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<LocalDate, Double>  purchaseDateMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getPurchaseDate, Collectors.summingDouble(BudgetEntity::getPrice)));
		response.setItemMap(itemMap);
		response.setCategoryMap(categoryMap);
		response.setPurchaseModeMap(purchaseModeMap);
		response.setPaymentModeMap(paymentModeMap);
		response.setPurchaseDateMap(purchaseDateMap);
		return response;
	}
	

	@Override
	public BudgetResponse getBudgetByMonthMultiSelect(MultiSelectRequest multiSelectRequest) {
		List<BudgetDTO> itemsResponseList = null;
		double totalPrice=0.0;
		BudgetResponse budgetResponse = new BudgetResponse();
		YearMonth yearMonth = null;
		LocalDate start = null;
		LocalDate end =null;
		List<BudgetEntity> itemList=new ArrayList<>();
		List<BudgetEntity> accumulatedList = new ArrayList<>();
		List<String> perticularsList=multiSelectRequest.getPerticularsList();
		String perticularType=multiSelectRequest.getPerticularType();
		String from=multiSelectRequest.getFromDate();
		String to=multiSelectRequest.getToDate();
		switch (perticularType) {
		case "month": {
			for(String month:perticularsList) {
				yearMonth = YearMonth.of(Year.now().getValue(), Month.valueOf(month.toUpperCase()));
				start = yearMonth.atDay(1);
				end = yearMonth.atEndOfMonth();
			itemList = budgetRepo.findAllItemsByMonth(start, end);
			totalPrice = totalPrice+itemList.stream().mapToDouble(item -> item.getPrice()).sum();
			itemList.addAll(accumulatedList);
			accumulatedList.addAll(itemList);
			}
			}
			break;
		case "item": {
			for(String items:perticularsList) {
				itemList = budgetRepo.findAllItemsByItem(items, CommonUtill.dateConverter(from),
						CommonUtill.dateConverter(to));
				totalPrice = totalPrice+itemList.stream().mapToDouble(item -> item.getPrice()).sum();
				itemList.addAll(accumulatedList);
				accumulatedList.addAll(itemList);
			}
			break;
		}
		case "Category": {
			for(String categories:perticularsList) {
				itemList = budgetRepo.findAllItemsByCategory(categories, CommonUtill.dateConverter(from),
						CommonUtill.dateConverter(to));
				totalPrice = totalPrice+itemList.stream().mapToDouble(category -> category.getPrice()).sum();
				itemList.addAll(accumulatedList);
				accumulatedList.addAll(itemList);
			}
			break;
		}
		case "purchaseMode": {
			for(String purchaseModes:perticularsList) {
				itemList = budgetRepo.findAllItemsBypurchaseMode(purchaseModes, CommonUtill.dateConverter(from),
						CommonUtill.dateConverter(to));
				totalPrice = totalPrice+itemList.stream().mapToDouble(purchaseMode -> purchaseMode.getPrice()).sum();
				itemList.addAll(accumulatedList);
				accumulatedList.addAll(itemList);
			}
			break;
		}
		case "paymentMode": {
			for(String paymentModes:perticularsList) {
				itemList = budgetRepo.findAllItemsBypaymentMode(paymentModes, CommonUtill.dateConverter(from),
						CommonUtill.dateConverter(to));
				totalPrice = totalPrice+itemList.stream().mapToDouble(paymentMode -> paymentMode.getPrice()).sum();
				itemList.addAll(accumulatedList);
				accumulatedList.addAll(itemList);
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + perticularType);
		} 
		itemsResponseList = itemList.stream().map(responseEntity -> CommonUtill.mapToDto(responseEntity))
				.collect(Collectors.toList());
		budgetResponse.setItemsList(itemsResponseList);
		budgetResponse.setTotalSum(totalPrice);
		return budgetResponse;
	}

	@Override
	public ReportResponse getSummaryByMultipleMonths(List<String> months) {
		ReportResponse response=new ReportResponse();
		months=CommonUtill.sortMonths(months);
		YearMonth startmonth = YearMonth.of(Year.now().getValue(), Month.valueOf(months.get(0).toUpperCase()));
		YearMonth endmonth = YearMonth.of(Year.now().getValue(), Month.valueOf(months.get(months.size()-1).toUpperCase()));
		LocalDate start = startmonth.atDay(1);
		LocalDate end = endmonth.atEndOfMonth();
		List<BudgetEntity> itemList = budgetRepo.findAllItemsByMonth(start, end);
		Map<String, Double> itemMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getItemName, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<String, Double> categoryMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getCategoryName, Collectors.summingDouble(BudgetEntity::getPrice))).entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
		Map<String, Double>  purchaseModeMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getPurchaseMode, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<String, Double>  paymentModeMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getPaymentMode, Collectors.summingDouble(BudgetEntity::getPrice)));
		Map<LocalDate, Double>  purchaseDateMap = itemList.stream()
	            .collect(Collectors.groupingBy(BudgetEntity::getPurchaseDate, Collectors.summingDouble(BudgetEntity::getPrice)));
		response.setItemMap(itemMap);
		response.setCategoryMap(categoryMap);
		response.setPurchaseModeMap(purchaseModeMap);
		response.setPaymentModeMap(paymentModeMap);
		response.setPurchaseDateMap(purchaseDateMap);
		return response;
	}
	

}
