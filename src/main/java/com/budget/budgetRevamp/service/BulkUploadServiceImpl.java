package com.budget.budgetRevamp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.budget.budgetRevamp.model.BudgetDTO;
import com.budget.budgetRevamp.model.BudgetEntity;
import com.budget.budgetRevamp.model.PerticularsEntity;
import com.budget.budgetRevamp.repository.BudgetRepo;
import com.budget.budgetRevamp.repository.PerticularsRepo;
import com.budget.budgetRevamp.util.CommonUtill;

@Service
public class BulkUploadServiceImpl implements BulkUploadService {
	
	@Autowired
	BudgetRepo budgetRepo;
	
	@Autowired
	PerticularsRepo perticularsRepo;

	@Override
	public List<BudgetEntity> readDataFromExcel(MultipartFile file) {
		String filePath = "D:\\BudgetFiles\\exportBudget.xlsx";
        List<BudgetEntity> data = new ArrayList<>();
        List<PerticularsEntity> perticulars = new ArrayList<>();
        Set<String> uniquePerticulars=new LinkedHashSet<String>();
        Optional<List<String>> existingPerticulars=perticularsRepo.findByPerticularType("item");
        List<String> existingPerticularList=new ArrayList<>();
        if(existingPerticulars.isPresent()) {
        	existingPerticularList=existingPerticulars.get();
        }
        XSSFWorkbook workbook = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
		try {

				workbook = new XSSFWorkbook(file.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=1;i<worksheet.getPhysicalNumberOfRows();i++) {
            XSSFRow row = worksheet.getRow(i);
            BudgetEntity budgetEntity=new BudgetEntity();
            	if(0.0!=row.getCell(0).getNumericCellValue()) {
            	budgetEntity.setItemName(row.getCell(1).getStringCellValue());
            	budgetEntity.setCategoryName(row.getCell(2).getStringCellValue());
            	budgetEntity.setPrice(row.getCell(3).getNumericCellValue());
            	budgetEntity.setPurchaseDate(LocalDate.parse(row.getCell(4).getStringCellValue(), dateFormatter));
            	budgetEntity.setPurchaseMode(row.getCell(5).getStringCellValue());
            	budgetEntity.setPaymentMode(row.getCell(6).getStringCellValue());
            	budgetEntity.setExportFlag("N");
            	String perticular=row.getCell(1).getStringCellValue()+","+row.getCell(2).getStringCellValue();
            	if(!existingPerticularList.contains(perticular)) {
            	uniquePerticulars.add(perticular);
            	}
            }
            data.add(budgetEntity);
        }  
        try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        data=budgetRepo.saveAll(data);
        for(String perticular:uniquePerticulars) {
        	PerticularsEntity perticularsEntity=new PerticularsEntity();
        	perticularsEntity.setPerticularType("item");
        	perticularsEntity.setPerticularName(perticular);
        	perticulars.add(perticularsEntity);
        	
        }
        perticularsRepo.saveAll(perticulars);
        return data;
	}

	@Override
	public String readPerticulars(MultipartFile file) {
		String filePath = "D:\\BudgetFiles\\Perticulers.xlsx";
        List<PerticularsEntity> perticulars = new ArrayList<>();
        
        XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        XSSFSheet worksheet = workbook.getSheetAt(1);

        for(int i=1;i<worksheet.getPhysicalNumberOfRows();i++) {
            XSSFRow row = worksheet.getRow(i);
            for(int j=0;j<row.getLastCellNum();j++) {
            	 PerticularsEntity perticularsEntity=new PerticularsEntity();
            	 perticularsEntity.setPerticularType(worksheet.getSheetName());
            	 perticularsEntity.setPerticularName(row.getCell(0).getStringCellValue());
            	 perticulars.add(perticularsEntity);
            }

        } 
        XSSFSheet worksheet2 = workbook.getSheetAt(2);

        for(int i=1;i<worksheet2.getPhysicalNumberOfRows();i++) {
            XSSFRow row = worksheet2.getRow(i);
            for(int j=0;j<row.getLastCellNum();j++) {
            	 PerticularsEntity perticularsEntity=new PerticularsEntity();
            	 perticularsEntity.setPerticularType(worksheet2.getSheetName());
            	 perticularsEntity.setPerticularName(row.getCell(0).getStringCellValue());
            	 perticulars.add(perticularsEntity);
            }

        } 
        try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        perticularsRepo.saveAll(perticulars);
		return "success";
	}

	@Override
	public String exportBudget() {
		List<BudgetEntity> entityList=new ArrayList<>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("\\BudgetFiles\\exportBudget.xlsx"));
			int currYear=Year.now().getValue();
	    	XSSFSheet sheet = workbook.getSheet(Integer.toString(currYear));
	    	List<BudgetEntity> updatList=new ArrayList<>();
	    	entityList=budgetRepo.findAllByexportFlag("N");
	    	for(BudgetEntity entity:entityList) {
	    	Row dataRow = sheet.createRow(sheet.getLastRowNum()+1);
	    	dataRow.createCell(0).setCellValue(entity.getId());
	    	dataRow.createCell(1).setCellValue(entity.getItemName());
	    	dataRow.createCell(2).setCellValue(entity.getCategoryName());
	    	dataRow.createCell(3).setCellValue(entity.getPrice());
	    	dataRow.createCell(4).setCellValue(CommonUtill.dateFormattor(entity.getPurchaseDate()));
	    	dataRow.createCell(5).setCellValue(entity.getPurchaseMode());
	    	dataRow.createCell(6).setCellValue(entity.getPaymentMode());
	    	entity.setExportFlag("Y");
	    	entity.setRowId(dataRow.getRowNum());
	    	updatList.add(entity);
	    	}
	      FileOutputStream out =  new FileOutputStream(new File("\\BudgetFiles\\exportBudget.xlsx"));
   		  workbook.write(out);
   		  out.close();
   		  workbook.close();
	    	budgetRepo.saveAll(updatList);
	    	workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return "Budget Exported Successfully,Total No Of Rows "+entityList.size();
	}

	@Override
	public String updateExportBudget(int rowId,BudgetEntity entity) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("\\BudgetFiles\\exportBudget.xlsx"));
			int currYear = Year.now().getValue();
			XSSFSheet sheet = workbook.getSheet(Integer.toString(currYear));
			Row dataRow = sheet.createRow(rowId);
			dataRow.createCell(0).setCellValue(entity.getId());
			dataRow.createCell(1).setCellValue(entity.getItemName());
			dataRow.createCell(2).setCellValue(entity.getCategoryName());
			dataRow.createCell(3).setCellValue(entity.getPrice());
			dataRow.createCell(4).setCellValue(CommonUtill.dateFormattor(entity.getPurchaseDate()));
			dataRow.createCell(5).setCellValue(entity.getPurchaseMode());
			dataRow.createCell(6).setCellValue(entity.getPaymentMode());
			FileOutputStream out = new FileOutputStream(new File("\\BudgetFiles\\exportBudget.xlsx"));
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return null;
	}
  
}
