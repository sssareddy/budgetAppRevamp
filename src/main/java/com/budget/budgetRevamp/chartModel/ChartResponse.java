package com.budget.budgetRevamp.chartModel;

import org.springframework.http.HttpStatusCode;

import com.budget.budgetRevamp.model.GenericResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ChartResponse extends GenericResponse{
private ChartJson chartJson;
}
