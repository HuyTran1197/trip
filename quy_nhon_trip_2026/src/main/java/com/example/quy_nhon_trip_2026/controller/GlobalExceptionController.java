package com.example.quy_nhon_trip_2026.controller;

import com.example.quy_nhon_trip_2026.exception.TripException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(TripException.class)
    public String handleBookingException(TripException e, Model model){
        model.addAttribute("errorTrip", e.getMessage());
        return "/error-trip";
    }

}
