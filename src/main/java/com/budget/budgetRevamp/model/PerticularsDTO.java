package com.budget.budgetRevamp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerticularsDTO {
	private int Id;
	private String perticularType;
	private String perticularName;
}
