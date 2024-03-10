package model.course;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class GeneralCourse extends Course {
    public GeneralCourse(String courseName, int courseID, String departmentName, String instructor, int capacity, int module,
                         String courseType, LocalDateTime examDateTime, String day, LocalTime time){
        super(courseName, courseID,departmentName, instructor, capacity, module, courseType, examDateTime, day ,time);
        Course.getAllCourses().add(this);
    }
}
