package com.budget.budgetRevamp.chartModel;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartOptions {
private boolean scaleShowVerticalLines;
private boolean responsive;
private Legend legend;
private Scales scales;
}
