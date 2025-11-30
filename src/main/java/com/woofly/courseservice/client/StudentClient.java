package com.woofly.courseservice.client;

import com.woofly.courseservice.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// name: İstənilən ad
// url: Sorğu atılacaq servisin ünvanı (Student Service)
@FeignClient(name = "student-service", url = "http://localhost:8081/api")
public interface StudentClient {

    @GetMapping("/students/course/{courseId}")
    List<StudentDTO> getStudentsByCourseId(@PathVariable("courseId") Long courseId);
}