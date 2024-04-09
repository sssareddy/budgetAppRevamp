package com.budget.budgetRevamp.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.budget.budgetRevamp.model.BudgetResponse;
import com.budget.budgetRevamp.model.MultiSelectRequest;
import com.budget.budgetRevamp.model.ReportResponse;

public interface SearchBudgetService {
public BudgetResponse getBudgetByMonth(String month);
public BudgetResponse getBudgetByDate(String from,String to);
public BudgetResponse getBudgetByType(String perticularType, String perticularValue,String from, String to);
public ReportResponse getSummaryByMonth(String month);
public ReportResponse getSummaryByYear(String month);
public ReportResponse getSummaryByMultipleMonths(List<String> months);
public BudgetResponse getBudgetByMonthMultiSelect(MultiSelectRequest multiSelectRequest);
}
