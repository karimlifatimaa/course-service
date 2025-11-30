package com.woofly.courseservice.client;

import com.woofly.courseservice.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// name: İstənilən ad
// url: Sorğu atılacaq servisin ünvanı (Student Service)
@FeignClient(name = "student-service", url = "http://localhost:8081")
public interface StudentClient {

    // Student Service-dəki endpoint: /students/{id}
    @GetMapping("/students/{id}")
    StudentDTO getStudentById(@PathVariable("id") Long id);
}