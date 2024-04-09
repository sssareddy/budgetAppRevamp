package com.budget.budgetRevamp.chartModel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartDataSet {
private String label;
List<Double> data;
@JsonProperty("backgroundColor")
List<String> backgroundColor;
}
