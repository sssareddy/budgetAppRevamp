package com.budget.budgetRevamp.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.budget.budgetRevamp.model.BudgetDTO;
import com.budget.budgetRevamp.model.BudgetEntity;
import com.budget.budgetRevamp.model.BudgetResponse;
import com.budget.budgetRevamp.model.GenericResponse;
import com.budget.budgetRevamp.model.PerticularsDTO;
import com.budget.budgetRevamp.model.PerticularsEntity;
import com.budget.budgetRevamp.model.PerticularsResponse;
import com.budget.budgetRevamp.repository.BudgetRepo;
import com.budget.budgetRevamp.repository.CategoryRepo;
import com.budget.budgetRevamp.repository.ItemRepo;
import com.budget.budgetRevamp.repository.PerticularsRepo;
import com.budget.budgetRevamp.util.CommonUtill;

@Service
public class BudgetServiceImpl implements BudgetService {
	
	@Autowired
	BudgetRepo budgetRepo;
	 
	@Autowired
	ItemRepo itemRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	PerticularsRepo perticularsRepo;
	
	

	@Override
	public String addItem(BudgetDTO itemRequest) {
		// TODO Auto-generated method stub
		System.out.println(itemRequest);
		if(0!=itemRequest.getId()) {
			Optional<BudgetEntity> exisitng=budgetRepo.findById(itemRequest.getId());
			if(exisitng.isPresent()) {
				BudgetEntity entity=exisitng.get();
				if("Y".equals(entity.getExportFlag())) {
					
				}
			}
		}
		budgetRepo.save(CommonUtill.mapToEntity(itemRequest));
		return "Item "+itemRequest.getItemName()+" Added Successfully";
	}
	
	@Override
	public List<BudgetDTO> getItems() {
		// TODO Auto-generated method stub
		List<BudgetEntity> itemList=budgetRepo.findAll();
		List<BudgetDTO> itemResponse=itemList.stream()
		.map(responseEntity->CommonUtill.mapToDto(responseEntity))
		.collect(Collectors.toList());
		return itemResponse;
	}

	@Override
	public BudgetResponse getItemsByMonth(String month) {
		// TODO Auto-generated method stub
		List<BudgetDTO> itemsResponseList = null;
		BudgetResponse itemResponse = new BudgetResponse();
		double totalPrice = 0.0;
		if ("today".equals(month)) {
			LocalDate from = LocalDate.now();
			List<BudgetEntity> itemList = budgetRepo.findAllItemsByMonth(from, from);
			itemsResponseList = itemList.stream().map(responseEntity -> CommonUtill.mapToDto(responseEntity))
					.collect(Collectors.toList());
			totalPrice = itemList.stream().mapToDouble(item -> item.getPrice()).sum();
			itemResponse.setItemsList(itemsResponseList);
			itemResponse.setTotalSum(totalPrice);

		} else {
			LocalDate from = LocalDate.now().withDayOfMonth(1);
			LocalDate to = YearMonth.from(LocalDate.now()).atEndOfMonth();
			List<BudgetEntity> itemList = budgetRepo.findAllItemsByMonth(from, to);
			itemsResponseList = itemList.stream().map(responseEntity -> CommonUtill.mapToDto(responseEntity))
					.collect(Collectors.toList());
			totalPrice = itemList.stream().mapToDouble(item -> item.getPrice()).sum();
			itemResponse.setItemsList(itemsResponseList);
			itemResponse.setTotalSum(totalPrice);
		}
		if ("today".equals(month)) {
			LocalDate from = LocalDate.now();
			List<BudgetEntity> itemList = budgetRepo.findAllItemsByMonth(from, from);
			itemsResponseList = itemList.stream().map(responseEntity -> CommonUtill.mapToDto(responseEntity))
					.collect(Collectors.toList());
			totalPrice = itemList.stream().mapToDouble(item -> item.getPrice()).sum();
			itemResponse.setItemsList(itemsResponseList);
			itemResponse.setTotalSum(totalPrice);

		}
		return itemResponse;
	}

	
	@Override
	public String addPerticular(String type, String perticular) {
		PerticularsEntity pEntity = new PerticularsEntity();
		Optional<List<String>> pList = perticularsRepo.findByPerticularType(type);
		if (pList.isPresent()) {
			if(pList.get().contains(perticular)) {
				return type+" Already Exists";
			}
		}
		pEntity.setPerticularType(type);
		pEntity.setPerticularName(perticular);
		perticularsRepo.save(pEntity);
		return type + " " + perticular + " Added Successfully";
	}

	@Override
	public PerticularsResponse getPerticulars(String type) {
		PerticularsResponse allPerticulars = new PerticularsResponse();
		Optional<List<String>> pList = perticularsRepo.findByPerticularType(type);
		List<PerticularsEntity> allList = null;
		if ("category".equals(type)) {
			pList = perticularsRepo.findByPerticularType("item");
		} else if ("All".equals(type)) {
			allList = perticularsRepo.findAll(Sort.by(Sort.Direction.ASC, "perticularName"));
		}
		else {
			pList = perticularsRepo.findByPerticularType(type);
		}
		List<String> perticularList = new ArrayList<String>();

		if (pList.isPresent()) {
			switch (type) {
			case "item": {
				perticularList = pList.get().stream().map(item -> {
					String[] items = item.split(",");
					return items[0];
				}).distinct().sorted().collect(Collectors.toList());
				allPerticulars.setItemsList(perticularList);
				break;
			}
			case "category": {
				perticularList = pList.get().stream().map(item -> {
					String[] categories = item.split(",");
					return categories[1];
				}).distinct().sorted().collect(Collectors.toList());
				allPerticulars.setCategoryList(perticularList);
				break;
			}
			case "purchaseMode": {
				perticularList = pList.get().stream().sorted().collect(Collectors.toList());
				allPerticulars.setPurchaseModeList(perticularList);
				break;
			}
			case "paymentMode": {
				perticularList = pList.get().stream().sorted().collect(Collectors.toList());
				allPerticulars.setPaymentModeList(perticularList);
				break;
			}
			default: {
				Map<String, List<String>> response = allList.stream()
						.collect(Collectors.groupingBy(PerticularsEntity::getPerticularType, // Group by category
								Collectors.mapping(PerticularsEntity::getPerticularName, Collectors.toList())));

				allPerticulars.setItemsList(response.getOrDefault("item", Collections.emptyList()));
				allPerticulars.setPurchaseModeList(response.getOrDefault("purchaseMode", Collections.emptyList()));
				allPerticulars.setPaymentModeList(response.getOrDefault("paymentMode", Collections.emptyList()));
				List<String> itemsList = allPerticulars.getItemsList().stream().map(item -> {
					String[] items = item.split(",");
					return items[0];
				}).distinct().sorted().collect(Collectors.toList());
				List<String> categoryList = allPerticulars.getItemsList().stream().map(item -> {
					String[] categories = item.split(",");
					return categories[1];
				}).distinct().sorted().collect(Collectors.toList());
				allPerticulars.setItemsList(itemsList);
				allPerticulars.setCategoryList(categoryList);
			}
			}
		}
		return allPerticulars;
	}

	@Override
	public GenericResponse getCategoryForItem(String Item) {
		Optional<List<String>> pList = perticularsRepo.findCategoryforItem(Item);
		GenericResponse category=new GenericResponse();
		List<String> getItemCatList=null;
		if(pList.isPresent()) {
			getItemCatList=pList.get();
		}
		if(getItemCatList.size()==1) {
			String[] categoryRes=getItemCatList.get(0).split(",");
			category.setMessage(categoryRes[1]);
		} else {
			Map<String, String> itemCatMap = getItemCatList.stream()
	                .map(item -> item.split(","))
	                .filter(parts -> parts.length == 2)
	                .collect(Collectors.toMap(
	                        parts -> parts[0].trim(),
	                        parts -> parts[1].trim() ,(existing, replacement) -> {
	                            return existing;
	                        }  
	                ));
			category.setMessage(itemCatMap.get(Item));
		}
		return category;
	}

	@Override
	public BudgetResponse deleteBudgetById(int id,List<BudgetDTO> searchList) {
		// TODO Auto-generated method stub
		BudgetResponse response=new BudgetResponse();
		List<BudgetDTO> updatedList=null;
		double totalsum=0;
		budgetRepo.deleteById(id);
		updatedList=CommonUtill.updateList(id, searchList);
		totalsum=updatedList.stream().mapToDouble(item -> item.getPrice()).sum();
		response.setItemsList(updatedList);
		response.setTotalSum(totalsum);
		response.setMessage("Item Deleted Successfully");
		return response;
	}

	@Override
	public PerticularsResponse getPerticularsByType(String type) {
		PerticularsResponse allPerticulars = new PerticularsResponse();
		allPerticulars.setPerticulasList(perticularsRepo.findByType(type));
		return allPerticulars;
	}

	@Override
	public PerticularsResponse savePerticular(PerticularsDTO perticularsDTO) {
		PerticularsResponse response = new PerticularsResponse();
		PerticularsEntity pe=PerticularsEntity.builder().build();
		String type=pe.getPerticularType();
		pe.setId(perticularsDTO.getId());
		pe.setPerticularName(perticularsDTO.getPerticularName());
		pe.setPerticularType(perticularsDTO.getPerticularType());
		pe=perticularsRepo.save(pe);
		response.setPerticulasList(perticularsRepo.findByType(type));
		response.setMessage(perticularsDTO.getPerticularName()+" "+" Updated  Successfylly");
		return response;
	}

	@Override
	public PerticularsResponse deletePerticularById(int id) {
		PerticularsEntity pe=perticularsRepo.findPerticularById(id);
	    String type=pe.getPerticularType();
	    perticularsRepo.delete(pe);
	    PerticularsResponse allPerticulars = new PerticularsResponse();
		allPerticulars.setPerticulasList(perticularsRepo.findByType(type));
		return allPerticulars;
	}

}
