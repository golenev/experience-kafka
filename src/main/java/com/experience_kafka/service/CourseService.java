package com.experience_kafka.service;

import com.experience_kafka.model.Course;
import com.experience_kafka.model.Lesson;
import com.experience_kafka.repository.CourseRepository;
import com.experience_kafka.repository.LessonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    public Course saveCourseWithLessons(Course course) {
        Course savedCourse = courseRepository.save(course);
        course.getLessons().forEach(lesson -> {
            lesson.setCourse(savedCourse);
            lessonRepository.save(lesson);
        });
        return savedCourse;
    }

    public Course getCourseWithAllLessons(Long courseId) {
        return courseRepository.findCourseWithAllLessons(courseId);
    }

//    public Course getCourseWithLessonsByDate(Long courseId, Date date) {
//        return courseRepository.findCourseWithLessonsByDate(courseId, date);
//    }
}