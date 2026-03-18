package com.example.quy_nhon_trip_2026.repo;

import com.example.quy_nhon_trip_2026.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepo extends JpaRepository<Category,Integer> {
}
