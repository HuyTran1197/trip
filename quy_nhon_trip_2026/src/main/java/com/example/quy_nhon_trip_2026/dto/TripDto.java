package com.example.quy_nhon_trip_2026.dto;



import java.time.LocalDateTime;


public interface TripDto {

    Long getId();
    String getName();
    Double getPrice();
    String getLocation();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
    String getCategory();
}
