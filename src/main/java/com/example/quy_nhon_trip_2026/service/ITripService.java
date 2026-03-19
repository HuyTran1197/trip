package com.example.quy_nhon_trip_2026.service;


import com.example.quy_nhon_trip_2026.dto.TripDto;
import com.example.quy_nhon_trip_2026.model.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ITripService {
    Page<TripDto> search(@Param("searchName")String name,
                         @Param("searchStartTime") LocalDateTime start,
                         @Param("searchEndTime")LocalDateTime end,
                         @Param("searchCategory")String category,
                         Pageable pageable);

    boolean save(Trip quyNhonTrip);
    Trip findById(long id);
    boolean deleteById(long id);
    boolean editTrip(Trip trip);

    Long getTotalPrice(
            @Param("searchName") String searchName,
            @Param("searchStartTime") LocalDateTime searchStartTime,
            @Param("searchEndTime") LocalDateTime searchEndTime,
            @Param("searchCategory") String searchCategory
    );
}
