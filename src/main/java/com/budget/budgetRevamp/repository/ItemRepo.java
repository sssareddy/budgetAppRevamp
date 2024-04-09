package com.budget.budgetRevamp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budget.budgetRevamp.model.ItemEntity;

public interface ItemRepo extends JpaRepository<ItemEntity, Integer> {

}
