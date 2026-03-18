package com.example.quy_nhon_trip_2026.repo;

import com.example.quy_nhon_trip_2026.dto.TripDto;
import com.example.quy_nhon_trip_2026.model.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ITripRepo extends JpaRepository<Trip,Long> {
    @Query(value = "SELECT t.id AS id, t.name AS name, t.price AS price, " +
            "t.location AS location, t.start_time AS startTime, " +
            "t.end_time AS endTime, c.name AS category " +
            "FROM trip t " +
            "JOIN category c ON t.category_id = c.id " +
            "WHERE (:searchName IS NULL OR t.name LIKE :searchName) " +
            "and (:searchStartTime is null or t.end_time > :searchStartTime) " +
            "and (:searchEndTime is null or t.start_time < :searchEndTime) " +
            "AND (:searchCategory IS NULL OR c.name LIKE :searchCategory)",
            nativeQuery = true)
    Page<TripDto> search(@Param("searchName") String name,
                         @Param("searchStartTime") LocalDateTime start,
                         @Param("searchEndTime") LocalDateTime end,
                         @Param("searchCategory") String category,
                         Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM trip WHERE LOWER(name) = LOWER(:name)", nativeQuery = true)
    int existsByName(@Param("name") String name);

    @Query(value = """
    SELECT COUNT(*) 
    FROM trip 
    WHERE start_time < :end 
    AND end_time > :start
""", nativeQuery = true)
    int existsTimeOverlap(@Param("start") LocalDateTime start,
                          @Param("end") LocalDateTime end);

//    @Query(value = "insert into trip(name,price,location,start_time,end_time,category_id) " +
//            "values (:name,:price,:location,:start,:end,:category)",nativeQuery = true)
//    boolean editTrip(@Param(("name"))String name,
//                     @Param("price")Double price,
//                     @Param("location")String location,
//                     @Param("start")LocalDateTime start,
//                     @Param("end")LocalDateTime end,
//                     @Param("category")Integer categoryId);
}
