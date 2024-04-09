package com.budget.budgetRevamp.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "budget")
public class BudgetEntity {
	@Id
	@GeneratedValue
	private int Id;
	@Column(name = "ITEM_NAME")
	private String itemName;
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	@Column(name = "ITEM_PRICE")
	private double price;
	@Column(name = "PURCHASE_DATE")
	private LocalDate purchaseDate;
	@Column(name = "PURCHASE_MODE")
	private String purchaseMode;
	@Column(name = "PAYMENT_MODE")
	private String paymentMode;
	@Column(name="EXPORT_FLAG")
	private String exportFlag;
	@Column(name="ROW_ID")
	private int rowId;
}
