package model.course;

import model.user.Student;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public abstract class Course {
    private final static List<Course> allCourses = new ArrayList<>();
    private String courseName;
    private int courseID;
    String departmentName;
    List<Student> students;
    private String instructor;
    private int capacity;
    private int module;
    private String courseType;
    private LocalDateTime examDateTime;
    private String day;
    private LocalTime time;

    public Course(String courseName, int courseID, String departmentName, String instructor, int capacity, int module
            , String courseType, LocalDateTime examDateTime, String day, LocalTime time) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.instructor = instructor;
        this.capacity = capacity;
        this.module = module;
        this.departmentName = departmentName;
        this.courseType = courseType;
        students = new ArrayList<>();
        this.examDateTime = examDateTime;
        this.day = day;
        this.time = time;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public int getCourseID() {
        return courseID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public static List<Course> getAllCourses() {
        return allCourses;
    }

    public int getModule() {
        return module;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getCourseType() {
        return courseType;
    }

    public LocalDateTime getExamDateTime() {
        return examDateTime;
    }

    public String getDay() {
        return day;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return
                "courseName: " + courseName +
                        ", courseID: " + courseID +
                        ", instructor: " + instructor +
                        ", capacity: " + capacity +
                        ", module: " + module +
                        ", courseType: " + courseType +
                        ", examDateTime: " + examDateTime +
                        ", day: " + day +
                        ", time: " + time;
    }
}
