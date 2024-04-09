package com.budget.budgetRevamp.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.budget.budgetRevamp.model.BudgetEntity;

public interface BulkUploadService {
	 List<BudgetEntity> readDataFromExcel(MultipartFile file);
	 String readPerticulars(MultipartFile file);
	 String exportBudget();
}
