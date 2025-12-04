package com.woofly.courseservice.service;

import com.woofly.courseservice.client.StudentClient;
import com.woofly.courseservice.dto.FullCourseResponse;
import com.woofly.courseservice.dto.StudentDTO;
import com.woofly.courseservice.model.Course;
import com.woofly.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private static final Logger log = LoggerFactory.getLogger(CourseService.class);
    private final CourseRepository courseRepository;
    private final StudentClient studentClient;
    private final EmailService emailService;

    public Course createCourse(Course course) {
        Course savedCourse = courseRepository.save(course);
        log.info("New course created with ID: {}", savedCourse.getId());
        emailService.sendEmail("adilkerimli001@gmail.com", "New Course Created", "A new course has been created: " + savedCourse.getTitle());

        return savedCourse;
    }

    public FullCourseResponse getCourseDetails(Long courseId) {
        log.info("Fetching course details for course ID: {}", courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs tapılmadı!"));

        List<StudentDTO> students = studentClient.getStudentsByCourseId(course.getId());
        log.info("Found {} students for course ID: {}", students.size(), courseId);

        return new FullCourseResponse(course, students);
    }

    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs tapılmadı!"));
        courseRepository.deleteById(courseId);
        log.info("Course with ID: {} has been deleted.", courseId);
        emailService.sendEmail("recipient-email@example.com", "Course Deleted", "The course \"" + course.getTitle() + "\" has been deleted.");
    }
}
