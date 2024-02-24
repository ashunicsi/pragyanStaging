package com.nicsi.ceda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nicsi.ceda.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>
{
	List<Category> findByStatus(int status);
	@Query(value = "SELECT category_e from Category where id = ?1 ")
	String findCategoryName(Long id);
}
