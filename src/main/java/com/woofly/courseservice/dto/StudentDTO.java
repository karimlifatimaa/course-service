package com.woofly.courseservice.dto;

import lombok.Data;

@Data
public class StudentDTO {
    // Student Service-dən gələn sahələrlə eyni olmalıdır
    private Long id;
    private String name;
    private String surname;
    private String email;
}
