package com.woofly.courseservice.controller;

import com.woofly.courseservice.dto.FullCourseResponse;
import com.woofly.courseservice.model.Course;
import com.woofly.courseservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @GetMapping("/{id}")
    public FullCourseResponse getCourseDetails(@PathVariable Long id) {
        return courseService.getCourseDetails(id);
    }
}