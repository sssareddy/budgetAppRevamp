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
@Entity(name = "perticulars")
public class PerticularsEntity {
	@Id
	@GeneratedValue
	private int Id;
	
	@Column(name="PERTICULAR_TYPE")
	private String perticularType;
	
	@Column(name="PERTICULAR_NAME")
	private String perticularName;
}
