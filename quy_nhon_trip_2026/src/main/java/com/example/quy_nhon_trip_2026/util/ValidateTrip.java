package com.example.quy_nhon_trip_2026.util;

import com.example.quy_nhon_trip_2026.model.Trip;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ValidateTrip implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Trip dto = (Trip) target;

        if (dto.getName()==null||dto.getName().equals("")){
            errors.rejectValue("name",null,"Tên không được trống");
        } else if (!dto.getName().matches("^\\p{L}+(\\s\\p{L}+)*$")) {
            errors.rejectValue("name", null, "Không dùng kí tự đặc biệt ");
        }


        if (dto.getStartTime()==null){
            errors.rejectValue("startTime",null,"ngày không được để trống");
        } else if (dto.getStartTime().isBefore(java.time.LocalDateTime.now())) {
            errors.rejectValue("startTime", null, "nhập thời điểm hiện tại");
        } else if (dto.getStartTime().isAfter(dto.getEndTime())) {
            errors.rejectValue("startTime", null, "không được lớn hơn thời gian kết thúc");
        }

        if (dto.getEndTime()==null){
            errors.rejectValue("endTime",null,"ngày không được để trống");
        } else if (dto.getEndTime().isBefore(dto.getStartTime().plusMinutes(30))) {
            errors.rejectValue("endTime", null, "thời điểm sau phải " +
                    "lớn hơn thời điểm bắt đầu tối thiểu 30 phút");
        }

        if (!errors.hasFieldErrors("price")){
            if (dto.getPrice()==null){
                errors.rejectValue("price",null,"Vui lòng nhập giá hoặc nhập 0");
            } else if (dto.getPrice()<0) {
                errors.rejectValue("price",null,"Giá không được nhỏ hơn 0");
            }
        }

        if (dto.getLocation()==null||dto.getLocation().equals("")){
            errors.rejectValue("location",null,"địa chỉ không được trống");
        } else if (!dto.getLocation().matches("^[\\w\\p{L}]+(\\s[\\w\\p{L}]+)*$")) {
            errors.rejectValue("location", null, "Không chứa kí tự đặc biệt");
        }

        if (!errors.hasFieldErrors("category")) {
            if (dto.getCategory() == null) {
                errors.rejectValue("category", null, "chọn thể loại");
            }
        }

    }
}
