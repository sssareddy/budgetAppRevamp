package com.budget.budgetRevamp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetDTO {

	private int Id;

	private String itemName;

	private String categoryName;

	private double price;

	private String purchaseDate;

	private String purchaseMode;

	private String paymentMode;
	
	private double totalSum;
	
	private String exportFlag;
	
	private int rowId;
}
