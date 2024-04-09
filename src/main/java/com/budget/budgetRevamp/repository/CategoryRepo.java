package com.budget.budgetRevamp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.budget.budgetRevamp.model.CategoryEntity;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer>{

	@Query(value="select * from  category where category.CATEGORY_NAME=:name",nativeQuery = true)
	Optional<CategoryEntity> findByCategoryName(@Param(value = "name") String name);
}
