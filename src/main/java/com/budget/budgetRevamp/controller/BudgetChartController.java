package com.budget.budgetRevamp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.budgetRevamp.chartModel.ChartData;
import com.budget.budgetRevamp.chartModel.ChartDataSet;
import com.budget.budgetRevamp.chartModel.ChartJson;
import com.budget.budgetRevamp.chartModel.ChartOptions;
import com.budget.budgetRevamp.chartModel.ChartResponse;
import com.budget.budgetRevamp.chartModel.Legend;
import com.budget.budgetRevamp.chartModel.Scales;
import com.budget.budgetRevamp.chartModel.Ticks;
import com.budget.budgetRevamp.chartModel.YAxis;
import com.budget.budgetRevamp.model.ReportResponse;
import com.budget.budgetRevamp.service.BudgetChartService;


@RestController
@RequestMapping("/charts")
@CrossOrigin(origins = "http://localhost:4200")
public class BudgetChartController {

	@Autowired
	BudgetChartService budgetChartService;

	@GetMapping("/getChartOptions")
	public ChartJson getChartOptions() {
		ChartJson chartJson = ChartJson.builder().build();
		ChartOptions chartOptions = ChartOptions.builder().build();
		Scales scales = Scales.builder().build();
		YAxis y = YAxis.builder().build();
		Ticks ticks = Ticks.builder().build();
		ChartData chartData = ChartData.builder().build();
		ChartDataSet chartDataSet = ChartDataSet.builder().build();
		Legend legend = Legend.builder().build();
		List<String> labels = new ArrayList<>();
		List<String> backgroundColor = new ArrayList<>();
		backgroundColor.add("rgba(255, 99, 132, 0.2)");
		backgroundColor.add("rgba(153, 102, 255, 0.2)");
		labels.add("January");
		labels.add("Febrauary");
		List<YAxis> yaxisList = new ArrayList<>();
		List<Double> data = new ArrayList<>();
		List<ChartDataSet> datasets = new ArrayList<>();
		data.add(600.00);
		data.add(40.00);
		ticks.setMin(1);
		ticks.setMax(1000);
		y.setTicks(ticks);
		yaxisList.add(y);
		scales.setYAxes(yaxisList);
		chartOptions.setScales(scales);
		chartOptions.setResponsive(true);
		chartOptions.setScaleShowVerticalLines(true);
		legend.setDisplay(true);
		legend.setPosition("bottom");
		chartOptions.setLegend(legend);
		chartJson.setType("bar");
		chartData.setLabels(labels);
		chartDataSet.setLabel("item");
		chartDataSet.setData(data);
		chartDataSet.setBackgroundColor(backgroundColor);
		chartJson.setData(chartData);
		chartJson.setOptions(chartOptions);
		datasets.add(chartDataSet);
		chartData.setDatasets(datasets);
		return chartJson;
	}
	
	@GetMapping("/getBudgetChartMonth/{perticularType}/{month}/{chartType}")
	public ResponseEntity<ChartResponse> getBudgetChartMonth(@PathVariable String perticularType,
			@PathVariable String month,@PathVariable String chartType) {
		ChartResponse response = budgetChartService.getItemChartMonthly(perticularType, month,chartType);
		response.setMessage("Success");
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<ChartResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/getBudgetChartByYear/{perticularType}/{year}/{chartType}")
	public ResponseEntity<ChartResponse> getBudgetChartByYear(@PathVariable String perticularType,
			@PathVariable String year,@PathVariable String chartType) {
		ChartResponse response = budgetChartService.getItemChartYearly(perticularType, year,chartType);
		response.setMessage("Success");
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<ChartResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/getTotalBudgetByMonth/{year}/{chartType}")
	public ResponseEntity<ChartResponse> getTotalBudgetByMonth(@PathVariable String year,@PathVariable String chartType) {
		ChartResponse response = budgetChartService.getTotalBudgetByMonth(year,chartType);
		response.setMessage("Success");
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<ChartResponse>(response, HttpStatus.OK);
	}
	@GetMapping("/getTotalBudgetByYear/{chartType}")
	public ResponseEntity<ChartResponse> getTotalBudgetByYear(@PathVariable String chartType) {
		ChartResponse response = budgetChartService.getTotalBudgetByYear(chartType);
		response.setMessage("Success");
		response.setStatusCode(HttpStatus.OK);
		return new ResponseEntity<ChartResponse>(response, HttpStatus.OK);
	}
}
