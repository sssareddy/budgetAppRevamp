package com.budget.budgetRevamp.chartModel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartData {
List<String> labels;
List<ChartDataSet>datasets;
}
