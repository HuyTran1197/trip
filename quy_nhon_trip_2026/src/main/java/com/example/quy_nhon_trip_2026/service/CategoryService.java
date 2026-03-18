package com.example.quy_nhon_trip_2026.service;

import com.example.quy_nhon_trip_2026.model.Category;
import com.example.quy_nhon_trip_2026.repo.ICategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CategoryService implements ICategoryService{
    @Autowired
    private ICategoryRepo categoryRepo;

    @Override
    public List<Category> getList() {
        return categoryRepo.findAll();
    }

    @Override
    public Category findById(int id) {
        return categoryRepo.findById(id).orElse(null);
    }

    @Override
    public boolean save(Category category) {
        try {
            categoryRepo.save(category);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }
}
