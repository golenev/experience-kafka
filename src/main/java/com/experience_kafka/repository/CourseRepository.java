package com.experience_kafka.repository;


import com.experience_kafka.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c LEFT JOIN c.lessons l WHERE c.id = :courseId")
    Course findCourseWithAllLessons(@Param("courseId") Long courseId);

//    @Query("SELECT c FROM Course c RIGHT JOIN c.lessons l WHERE c.id = :courseId AND l.date = :date")
//    Course findCourseWithLessonsByDate(@Param("courseId") Long courseId, @Param("date") Date date);
}