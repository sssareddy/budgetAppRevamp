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
public class Scales {
	 @JsonProperty("yAxes")
private List<YAxis> yAxes;
}
