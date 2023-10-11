package com.example.Swiggato.repository;

import com.example.Swiggato.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<FoodItem,Integer> {
}
