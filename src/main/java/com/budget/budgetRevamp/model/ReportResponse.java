package com.budget.budgetRevamp.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ReportResponse extends GenericResponse {
	Map<String, Double> itemMap;
	Map<String, Double> categoryMap;
	Map<String, Double> purchaseModeMap;
	Map<String, Double> paymentModeMap;
	Map<LocalDate, Double>  purchaseDateMap;

}
