package com.budget.budgetRevamp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "item")
public class ItemEntity {
	@Id
	@GeneratedValue
	private int Id;
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@ManyToOne
    @JoinColumn(name = "category_id")
	private CategoryEntity category;
}
