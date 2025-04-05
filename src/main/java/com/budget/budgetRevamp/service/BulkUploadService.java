package com.budget.budgetRevamp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.budget.budgetRevamp.model.BudgetDTO;
import com.budget.budgetRevamp.model.BudgetEntity;

public interface BulkUploadService {
	 List<BudgetEntity> readDataFromExcel(MultipartFile file);
	 String readPerticulars(MultipartFile file);
	 String exportBudget();
	 String updateExportBudget(int rowId,BudgetEntity entity);
	 
}
