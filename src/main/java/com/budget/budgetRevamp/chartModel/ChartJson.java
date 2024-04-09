package com.budget.budgetRevamp.chartModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartJson {
private String type;
private ChartData data;
private ChartOptions options;
}
