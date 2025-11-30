package com.woofly.courseservice.dto;

import com.woofly.courseservice.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullCourseResponse {
    private Course course;
    private List<StudentDTO> students;
}
