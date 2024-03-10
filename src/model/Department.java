package model;

import model.course.Course;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private final static List<Department> allDepartments = new ArrayList<>();
    private final String departmentName;
    private final ArrayList<Course> departmentCourses;

    public Department(String departmentName) {
        this.departmentName = departmentName;
        allDepartments.add(this);
        departmentCourses = new ArrayList<>();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public static List<Department> getAllDepartments() {
        return allDepartments;
    }

    public static Department getDepartmentByName(String departmentName) {
        for (Department department : allDepartments) {
            if (department.departmentName.equals(departmentName)) {
                return department;
            }
        }
        return null;
    }

    public Course getCourseByName(String courseName) {
        for (Course course : this.departmentCourses) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    public static Course getCourseByCourseID(int courseID) {
        for (Course course : Course.getAllCourses()) {
            if (course.getCourseID() == courseID) {
                return course;
            }
        }
        return null;
    }

    public ArrayList<Course> getDepartmentCourses() {
        return departmentCourses;
    }
}
