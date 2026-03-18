package com.example.quy_nhon_trip_2026.service;

import com.example.quy_nhon_trip_2026.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getList();
    Category findById(int id);
    boolean save(Category category);
}
