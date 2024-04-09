package com.budget.budgetRevamp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.budget.budgetRevamp.model.BudgetEntity;

public interface BudgetRepo extends JpaRepository<BudgetEntity, Integer> {
	
@Query(value="select b.* from Budget b where b.purchase_date between :from and :to",nativeQuery=true)
List<BudgetEntity> findAllItemsByMonth(@Param("from") LocalDate from,@Param("to") LocalDate to);

@Query(value="select b.* from Budget b where b.item_name=:itemName and b.purchase_date between :from and :to",nativeQuery=true)
List<BudgetEntity> findAllItemsByItem(@Param("itemName") String itemName,@Param("from") LocalDate from,@Param("to") LocalDate to);

@Query(value="select b.* from Budget b where b.category_name=:categoryName and b.purchase_date between :from and :to",nativeQuery=true)
List<BudgetEntity> findAllItemsByCategory(@Param("categoryName") String categoryName,@Param("from") LocalDate from,@Param("to") LocalDate to);

@Query(value="select b.* from Budget b where b.purchase_mode=:purchaseMode and b.purchase_date between :from and :to",nativeQuery=true)
List<BudgetEntity> findAllItemsBypurchaseMode(@Param("purchaseMode") String purchaseMode,@Param("from") LocalDate from,@Param("to") LocalDate to);


@Query(value="select b.* from Budget b where b.payment_mode=:paymentMode and b.purchase_date between :from and :to",nativeQuery=true)
List<BudgetEntity> findAllItemsBypaymentMode(@Param("paymentMode") String paymentMode,@Param("from") LocalDate from,@Param("to") LocalDate to);


List<BudgetEntity> findAllByexportFlag(String exportFlag);

@Query("select sum(be.price) from budget be where be.purchaseDate between :from and :to")
Double getTotalExpense(@Param("from") LocalDate from,@Param("to") LocalDate to);
}
