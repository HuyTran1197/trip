package com.example.quy_nhon_trip_2026.service;

import com.example.quy_nhon_trip_2026.dto.TripDto;
import com.example.quy_nhon_trip_2026.exception.TripException;
import com.example.quy_nhon_trip_2026.model.Trip;
import com.example.quy_nhon_trip_2026.repo.ICategoryRepo;
import com.example.quy_nhon_trip_2026.repo.ITripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class TripService implements ITripService {
    @Autowired
    private ITripRepo tripRepo;


    @Override
    public Page<TripDto> search(String name, LocalDateTime start, LocalDateTime end, String category, Pageable pageable) {
        return tripRepo.search("%"+name+"%", start, end,"%"+category+"%", pageable);
    }



    @Override
    public boolean save(Trip trip) {
       if (tripRepo.existsByName(trip.getName().trim())>0){
           throw new TripException("Món/địa danh này đã được đặt trước đó !! " +
                   "Vui lòng thay bằng món/địa danh khác ^^");
       }
       if (tripRepo.existsTimeOverlap(
               trip.getStartTime(), trip.getEndTime())>0) {
           throw new TripException("Lịch đặt đã bị trùng, vui lòng kiểm tra lại " +
                   "và đặt lịch khác");
       }

        tripRepo.save(trip);
        return true;
    }

    @Override
    public Trip findById(long id) {
        return tripRepo.findById(id).orElse(null);
    }

    @Override
    public boolean deleteById(long id) {
        try {
            tripRepo.deleteById(id);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean editTrip(Trip trip) {
        try {
            tripRepo.save(trip);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }


}
