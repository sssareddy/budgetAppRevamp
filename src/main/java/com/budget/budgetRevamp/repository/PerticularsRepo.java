package com.budget.budgetRevamp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.budget.budgetRevamp.model.PerticularsEntity;

public interface PerticularsRepo extends JpaRepository<PerticularsEntity, Integer> {
	@Query(value="select PERTICULAR_NAME from  perticulars p where p.PERTICULAR_TYPE=:type",nativeQuery = true)
	Optional<List<String>> findByPerticularType(@Param(value = "type") String type);
	
	@Query(value="select PERTICULAR_NAME from  perticulars p where p.PERTICULAR_NAME like %:Item%",nativeQuery = true)
	Optional<List<String>> findCategoryforItem(@Param(value = "Item") String Item);
	
	@Query("From perticulars p where p.perticularType=:perticularType order by p.perticularName")
	List<PerticularsEntity> findByType(@Param("perticularType") String perticularType);
	
	@Query("From perticulars p where p.id=:id")
	PerticularsEntity findPerticularById(@Param("id") int id);
	
	
	//Optional<List<PerticularsEntity>> findByPerticularNameContaining(@Param(value = "Item") String Item);
}

