package com.budget.budgetRevamp.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "category")
public class CategoryEntity {
	@Id
	@GeneratedValue
	private int Id;
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	@OneToMany(mappedBy = "category")
    List < ItemEntity > items;
}
