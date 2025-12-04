package com.woofly.courseservice.controller;

import com.woofly.courseservice.dto.FullCourseResponse;
import com.woofly.courseservice.model.Course;
import com.woofly.courseservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        log.info("Received request to create course: {}", course);
        return courseService.createCourse(course);
    }

    @GetMapping("/{id}")
    public FullCourseResponse getCourseDetails(@PathVariable Long id) {
        log.info("Received request to get course details for ID: {}", id);
        return courseService.getCourseDetails(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        log.info("Received request to delete course with ID: {}", id);
        courseService.deleteCourse(id);
    }
}