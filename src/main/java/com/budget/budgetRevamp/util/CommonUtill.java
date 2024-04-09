package com.budget.budgetRevamp.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.budget.budgetRevamp.chartModel.ChartData;
import com.budget.budgetRevamp.chartModel.ChartDataSet;
import com.budget.budgetRevamp.chartModel.ChartJson;
import com.budget.budgetRevamp.chartModel.ChartOptions;
import com.budget.budgetRevamp.chartModel.ChartResponse;
import com.budget.budgetRevamp.chartModel.Legend;
import com.budget.budgetRevamp.chartModel.Scales;
import com.budget.budgetRevamp.chartModel.Ticks;
import com.budget.budgetRevamp.chartModel.YAxis;
import com.budget.budgetRevamp.model.BudgetDTO;
import com.budget.budgetRevamp.model.BudgetEntity;

public class CommonUtill {
	
	
	
	public static BudgetEntity mapToEntity(BudgetDTO itemRequest) {
		BudgetEntity itemEntity = new BudgetEntity();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		LocalDate purchaseDate = LocalDate.parse(itemRequest.getPurchaseDate(), dateFormatter);
		if(0!=itemRequest.getId()) {
			itemEntity.setId(itemRequest.getId());
		}
		itemEntity.setItemName(itemRequest.getItemName());
		itemEntity.setCategoryName(itemRequest.getCategoryName());
		itemEntity.setPrice(itemRequest.getPrice());
		itemEntity.setPurchaseDate(purchaseDate);
		itemEntity.setPurchaseMode(itemRequest.getPurchaseMode());
		itemEntity.setPaymentMode(itemRequest.getPaymentMode());
		return itemEntity;
	}

	public static BudgetDTO mapToDto(BudgetEntity itemResponse) {
		BudgetDTO responseDto = new BudgetDTO();
		LocalDate localDate = LocalDate.parse(itemResponse.getPurchaseDate().toString());
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String purchaseDate = localDate.format(outputFormatter);
		responseDto.setId(itemResponse.getId());
		responseDto.setItemName(itemResponse.getItemName());
		responseDto.setCategoryName(itemResponse.getCategoryName());
		responseDto.setPrice(itemResponse.getPrice());
		responseDto.setPurchaseDate(purchaseDate);
		responseDto.setPurchaseMode(itemResponse.getPurchaseMode());
		responseDto.setPaymentMode(itemResponse.getPaymentMode());
		return responseDto;
	}
	
	public static LocalDate dateConverter(String dateString) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		return LocalDate.parse(dateString, dateFormatter);
	}
	
	public static List<BudgetDTO>  updateList(int id,List<BudgetDTO> budgetList) {
		budgetList=budgetList.stream().filter(i->i.getId()!=id).collect(Collectors.toList());
		return budgetList;
		
	
	}
	
	public static String  dateFormattor(LocalDate date) {
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        String formattedDate = date.format(outputFormatter);
		return formattedDate;
	
	}
	
	public static List<String> sortMonths(List<String> monthNames) {
        // Use a Comparator to define the order based on Month enum values
        List<Month> sortedMonths = monthNames.stream()
        		.map((month)->month.toUpperCase())
                .map(Month::valueOf)
                .sorted()
                .collect(Collectors.toList());

        // Convert back to string representation
        return sortedMonths.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
	
	public static ChartResponse prepareCharts( List<String> keysList, List<Double> valuesList,int min,int max,String chartType ) {
		ChartResponse response=new ChartResponse();
		 ChartJson chartJson=ChartJson.builder().build();
			ChartOptions chartOptions=ChartOptions.builder().build();
			Scales scales=Scales.builder().build();
			YAxis y=YAxis.builder().build();
			Ticks ticks=Ticks.builder().build();
			ChartData chartData=ChartData.builder().build();
			ChartDataSet chartDataSet=ChartDataSet.builder().build();
			Legend legend=Legend.builder().build();
			List<String> labels=new ArrayList<>();
			List<String> backgroundColor=new ArrayList<>();
			backgroundColor.add("rgba(255, 99, 132, 0.2)");
			backgroundColor.add("rgba(153, 102, 255, 0.2)");
			labels.addAll(keysList);
			List<YAxis> yaxisList=new ArrayList<>();
			List<Double> data=new ArrayList<>();
			List<ChartDataSet> datasets=new ArrayList<>();
			data.addAll(valuesList);
			ticks.setMin(min);
			ticks.setMax(max);
			y.setTicks(ticks);
			yaxisList.add(y);
			scales.setYAxes(yaxisList);
			chartOptions.setScales(scales);
			chartOptions.setResponsive(true);
			chartOptions.setScaleShowVerticalLines(true);
			legend.setDisplay(true);
			legend.setPosition("bottom");
			chartOptions.setLegend(legend);
			chartJson.setType(chartType);
			chartData.setLabels(labels);
			chartDataSet.setLabel("item");
			chartDataSet.setData(data);
			chartDataSet.setBackgroundColor(backgroundColor);
			chartJson.setData(chartData);
			chartJson.setOptions(chartOptions);
			datasets.add(chartDataSet);  
			chartData.setDatasets(datasets);
			response.setChartJson(chartJson);
		return response;
	}
	
	public static List<String> getMonthsList() {
		return Arrays.asList(new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
	}
}
