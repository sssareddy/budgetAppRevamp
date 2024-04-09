package com.budget.budgetRevamp.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MultiSelectRequest {
private List<String> perticularsList;
private String perticularType;
String fromDate;
String toDate;
}
