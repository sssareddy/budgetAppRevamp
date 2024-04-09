package com.budget.budgetRevamp.service;

import com.budget.budgetRevamp.chartModel.ChartResponse;

public interface BudgetChartService {
ChartResponse getItemChartMonthly(String perticularType,String month,String chartType);

ChartResponse getItemChartYearly(String perticularType,String month,String chartType);

ChartResponse getTotalBudgetByMonth(String month,String chartType);

ChartResponse getTotalBudgetByYear(String chartType);
}
