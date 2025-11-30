package com.woofly.courseservice.controller;

import com.woofly.courseservice.client.StudentClient;
import com.woofly.courseservice.dto.FullCourseResponse;
import com.woofly.courseservice.dto.StudentDTO;
import com.woofly.courseservice.model.Course;
import com.woofly.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;
    private final StudentClient studentClient;

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @GetMapping("/{id}")
    public FullCourseResponse getCourseDetails(@PathVariable Long id) {
        // 1. Bazadan kursu tapırıq
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kurs tapılmadı!"));

        // 2. Feign ilə tələbəni gətiririk
        StudentDTO student = studentClient.getStudentById(course.getStudentId());

        // 3. Birləşdirib qaytarırıq (Import etdiyimiz DTO-nu işlədirik)
        return new FullCourseResponse(course, student);
    }
}