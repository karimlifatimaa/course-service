package com.woofly.courseservice.controller;

import com.woofly.courseservice.client.StudentClient;
import com.woofly.courseservice.dto.FullCourseResponse;
import com.woofly.courseservice.dto.StudentDTO;
import com.woofly.courseservice.model.Course;
import com.woofly.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        // 1. Kursu tapırıq (Burada heç bir tələbə məlumatı yoxdur)
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kurs tapılmadı!"));

        // 2. Feign vasitəsilə Tələbə servisinə deyirik:
        // "Mənim ID-m (course.getId()) filandır, bu ID-yə bağlı tələbələri ver"
        List<StudentDTO> students = studentClient.getStudentsByCourseId(course.getId());

        // 3. Kursu və gələn siyahını birləşdirib qaytarırıq
        return new FullCourseResponse(course, students);
    }
}