package com.example.quy_nhon_trip_2026.controller;

import com.example.quy_nhon_trip_2026.dto.TripDto;
import com.example.quy_nhon_trip_2026.model.Category;
import com.example.quy_nhon_trip_2026.model.Trip;
import com.example.quy_nhon_trip_2026.service.ICategoryService;
import com.example.quy_nhon_trip_2026.service.ITripService;
import com.example.quy_nhon_trip_2026.util.ValidateTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;


@Controller
@RequestMapping("/trips")
public class TripController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ITripService tripService;



    @GetMapping
    public String showList(@RequestParam(name = "page",defaultValue = "0")int page,
                           @RequestParam(name = "searchName",defaultValue = "")String searchName,
                           @RequestParam(value = "searchStartTime", required = false)
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                               LocalDateTime searchStartTime,

                           @RequestParam(value = "searchEndTime", required = false)
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                               LocalDateTime searchEndTime,
                           @RequestParam(name = "searchCategory",defaultValue = "")String searchCategory,
                           Model model){
        Pageable pageable = PageRequest.of(page,3,Sort.by("start_time").descending());

        Page<TripDto> tripDtoPage = tripService.search(searchName,searchStartTime,searchEndTime,searchCategory,pageable);

        model.addAttribute("tripDtoPage",tripDtoPage);
        model.addAttribute("categoryList",categoryService.getList());
        model.addAttribute("searchName",searchName);
        model.addAttribute("searchStartTime",searchStartTime);
        model.addAttribute("searchEndTime",searchEndTime);
        model.addAttribute("searchCategory",searchCategory);
        return "trip/list";
    }

    @GetMapping("/add")
    public String showAdd(Model model){
        model.addAttribute("trip",new Trip());
        model.addAttribute("categoryList",categoryService.getList());
        return "trip/save";
    }
    @PostMapping("/add")
    public String save(@ModelAttribute Trip trip,
                       Model model,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes){
        new ValidateTrip().validate(trip,bindingResult);
        if (bindingResult.hasFieldErrors()){
            model.addAttribute("categoryList",categoryService.getList());
            return "trip/save";
        }
        Category category = categoryService.findById(trip.getCategory().getId());
        trip.setCategory(category);
        boolean isSuccess = tripService.save(trip);
        redirectAttributes.addFlashAttribute("mess",isSuccess?"Tạo mới thành công":"Tạo mới không thành công");
        return "redirect:/trips";
    }

    @GetMapping("{id}/update")
    public String showUpdate(@PathVariable long id,
                             Model model){
        model.addAttribute("trip",tripService.findById(id));
        model.addAttribute("categoryList",categoryService.getList());
        return "trip/update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute Trip trip,
                         Model model,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        new ValidateTrip().validate(trip,bindingResult);
        if (bindingResult.hasFieldErrors()){
            model.addAttribute("categoryList",categoryService.getList());
            return "trip/update";
        }
        Category category = categoryService.findById(trip.getCategory().getId());
        trip.setCategory(category);
        boolean isSuccess = tripService.editTrip(trip);
        redirectAttributes.addFlashAttribute("mess",isSuccess?"Chỉnh sửa thành công":"Chỉnh sửa không thành công");
        return "redirect:/trips";

    }

    @PostMapping("/delete")
    public String deleteById(@ModelAttribute Trip trip,
                             RedirectAttributes redirectAttributes){
        boolean isSuccess = tripService.deleteById(trip.getId());
        redirectAttributes.addFlashAttribute("mess",isSuccess?"Xóa thành công":"Xóa không thành công");
        return "redirect:/trips";
    }

    @GetMapping("{id}/detail")
    public String detail(@PathVariable long id,
                         Model model){
        model.addAttribute("trip",tripService.findById(id));
        return "trip/view";
    }
}
