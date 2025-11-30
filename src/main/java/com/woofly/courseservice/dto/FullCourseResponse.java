package com.woofly.courseservice.dto;

import com.woofly.courseservice.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullCourseResponse {
    private Course course;
    private StudentDTO student;
}
