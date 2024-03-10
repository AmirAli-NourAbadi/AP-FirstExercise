package model.user;

import model.course.Course;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private ArrayList<Course> myCourses;
    private int unit;
    private static final List<Student> allStudents = new ArrayList<>();
    private String username;
    private String password;

    public Student(String username , String password) {
        this.password = password;
        this.username = username;
        myCourses = new ArrayList<>();
        allStudents.add(this);
        for (Course myCourse : myCourses) {
            unit += myCourse.getModule();
        }
    }
    public static Student getUserByUsername(String username){
        for (Student student : allStudents) {
            if (student.getUserName().equals(username)){
                return student;
            }
        }
        return null;
    }

    public static void addStudent(String username, String password) {
        new Student(username,password);
    }
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return username;
    }

    public ArrayList<Course> getMyCourses() {
        return myCourses;
    }

    public int getUnit() {
        return unit;
    }

    public static List<Student> getAllStudents() {
        return allStudents;
    }

    @Override
    public String toString() {
        return "studentID: " + this.username;
    }
}
