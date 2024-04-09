package com.budget.budgetRevamp.model;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class BudgetResponse extends GenericResponse{
private String message;
private HttpStatusCode statusCode;
List<BudgetDTO> itemsList;
double totalSum;
}
