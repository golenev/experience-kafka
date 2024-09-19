package com.experience_kafka.controller;

import com.experience_kafka.model.Course;
import com.experience_kafka.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses")
    public Course saveCourseWithLessons(@RequestBody Course course) {
        return courseService.saveCourseWithLessons(course);
    }

    @GetMapping("/{courseId}/lessons")
    public Course getCourseWithAllLessons(@PathVariable Long courseId) {
        return courseService.getCourseWithAllLessons(courseId);
    }

}
