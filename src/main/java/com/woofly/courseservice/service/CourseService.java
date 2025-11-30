package com.woofly.courseservice.service;

import com.woofly.courseservice.client.StudentClient;
import com.woofly.courseservice.dto.FullCourseResponse;
import com.woofly.courseservice.dto.StudentDTO;
import com.woofly.courseservice.model.Course;
import com.woofly.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentClient studentClient;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public FullCourseResponse getCourseDetails(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs tapılmadı!"));

        List<StudentDTO> students = studentClient.getStudentsByCourseId(course.getId());

        return new FullCourseResponse(course, students);
    }
}
