package com.budget.budgetRevamp.service;

import java.util.List;

import com.budget.budgetRevamp.model.BudgetDTO;
import com.budget.budgetRevamp.model.BudgetResponse;
import com.budget.budgetRevamp.model.GenericResponse;
import com.budget.budgetRevamp.model.PerticularsDTO;
import com.budget.budgetRevamp.model.PerticularsResponse;

public interface BudgetService {
String addItem(BudgetDTO itemRequest);
List<BudgetDTO> getItems();
BudgetResponse getItemsByMonth(String month);
String addPerticular(String type,String perticular);
PerticularsResponse getPerticulars(String type);
GenericResponse getCategoryForItem(String Item);
BudgetResponse deleteBudgetById(int id,List<BudgetDTO> searchList);
PerticularsResponse getPerticularsByType(String type);
PerticularsResponse savePerticular(PerticularsDTO perticulasDTO);
PerticularsResponse deletePerticularById(int id);
}
