package com.woofly.courseservice.service;

import com.woofly.courseservice.client.StudentClient;
import com.woofly.courseservice.dto.FullCourseResponse;
import com.woofly.courseservice.dto.StudentDTO;
import com.woofly.courseservice.model.Course;
import com.woofly.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private static final Logger log = LoggerFactory.getLogger(CourseService.class);
    private final CourseRepository courseRepository;
    private final StudentClient studentClient;
    private final EmailService emailService;
    // For caching
    private static final String COURSE_CACHE_NAME = "courseData";

    @CacheEvict(value = COURSE_CACHE_NAME, allEntries = true)
    public Course createCourse(Course course) {
        Course savedCourse = courseRepository.save(course);
        log.info("New course created with ID: {}", savedCourse.getId());
        emailService.sendEmail("adilkerimli001@gmail.com", "New Course Created Notification", "New Course Created");

        return savedCourse;
    }

    @Cacheable(value = COURSE_CACHE_NAME)
    public List<Course> getAllCourses() {
        log.info("Fetching all courses from DB");
        return courseRepository.findAll();
    }

    /**
     * Kurs məlumatlarını keşləyir.
     * Keşdə varsa: Vb-yə və StudentClient-ə sorğu getmir, Redis-dən gəlir.
     * Keşdə yoxdursa: Metod icra olunur, nəticə (FullCourseResponse) Redis-də saxlanılır.
     */
    @Cacheable(value = COURSE_CACHE_NAME, key = "'course:' + #courseId")
    public Course getCourseById(Long courseId) {
        log.info("Fetching course with id: {}", courseId);
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));
    }
    
    /**
     * Kurs məlumatlarını keşləyir.
     * Keşdə varsa: Vb-yə və StudentClient-ə sorğu getmir, Redis-dən gəlir.
     * Keşdə yoxdursa: Metod icra olunur, nəticə (FullCourseResponse) Redis-də saxlanılır.
     */
    @Cacheable(value = COURSE_CACHE_NAME, key = "#courseId")
    public FullCourseResponse getCourseDetails(Long courseId) {
        log.info("Fetching course details for course ID: {}", courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs tapılmadı!"));

        List<StudentDTO> students = studentClient.getStudentsByCourseId(course.getId());
        log.info("Found {} students for course ID: {}", students.size(), courseId);

        return new FullCourseResponse(course, students);
    }


    public Course updateCourse(Long courseId, Course courseDetails) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs tapılmadı!"));

        existingCourse.setTitle(courseDetails.getTitle());
        existingCourse.setPrice(courseDetails.getPrice());

        Course updatedCourse = courseRepository.save(existingCourse);
        log.info("Course with ID: {} has been updated.", courseId);

        emailService.sendHtmlEmail("adilkerimli001@gmail.com", "Course Updated Notification", "Course Updated");

        return updatedCourse;
    }

    @CacheEvict(value = COURSE_CACHE_NAME, key="#courseId")
    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs tapılmadı!"));
        courseRepository.deleteById(courseId);
        log.info("Course with ID: {} has been deleted.", courseId);
        emailService.sendHtmlEmail("adilkerimli001@gmail.com", "Course Deleted Notification", "Course Deleted : "+ course);
    }

    @CacheEvict(value = COURSE_CACHE_NAME, allEntries = true)
    public void clearAllCaches() {
        log.info("All caches related to courses have been cleared.");
    }
}
