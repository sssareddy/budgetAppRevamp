package com.budget.budgetRevamp.model;

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
@Entity(name = "purchase_mode")
public class purchaseModeEntity {
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "PURCHASE_MODE_NAME")
	private String purchaseModeName;
}
