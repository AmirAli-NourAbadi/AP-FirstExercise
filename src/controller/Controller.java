package controller;

import model.Department;
import model.course.Course;
import model.course.GeneralCourse;
import model.course.SpecialCourse;
import model.user.Student;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Controller {

    public Controller() {
        Department computerEngineering = new Department("Computer engineering");
        computerEngineering.getDepartmentCourses().add(new GeneralCourse("Basic Programming", 2010, "Computer engineering", "dr.Fazli", 150, 3, "General", LocalDateTime.of(2024, 7, 5, 9, 0), "Monday", LocalTime.of(8, 0)));
        computerEngineering.getDepartmentCourses().add(new SpecialCourse("Data Base", 2011, "Computer engineering", "dr.Abam", 150, 3, "Special", LocalDateTime.of(2024, 7, 5, 12, 0), "Sunday", LocalTime.of(8, 0)));
        computerEngineering.getDepartmentCourses().add(new SpecialCourse("Machine Learning", 2012, "Computer engineering", "dr.ZarrabiZadeh", 150, 3, "Special", LocalDateTime.of(2024, 6, 5, 9, 0), "Monday", LocalTime.of(9, 0)));

        Department mathematics = new Department("Mathematics");
        mathematics.getDepartmentCourses().add(new GeneralCourse("Calculus 1", 2004, "Mathematics", "dr.Mir Sadeghi", 150, 4, "General", LocalDateTime.of(2024, 12, 5, 9, 0), "Tuesday", LocalTime.of(8, 0)));
        mathematics.getDepartmentCourses().add(new SpecialCourse("Advanced Programming", 2005, "Mathematics", "dr.Boomari", 100, 4, "Special", LocalDateTime.of(2025, 7, 5, 9, 0), "Monday", LocalTime.of(10, 0)));
        mathematics.getDepartmentCourses().add(new SpecialCourse("Linear Algebra", 2006, "Mathematics", "dr.Akbari", 80, 4, "Special", LocalDateTime.of(2024, 7, 5, 11, 0), "Monday", LocalTime.of(8, 0)));

        Department physics = new Department("Physics");
        physics.getDepartmentCourses().add(new GeneralCourse("Physics 1", 2007, "Physics", "dr.Naseri Taheri", 150, 3, "General", LocalDateTime.of(2024, 7, 5, 9, 0), "Monday", LocalTime.of(8, 0)));
        physics.getDepartmentCourses().add(new SpecialCourse("Physics 3 ", 2008, "Physics", "dr.BahmanAbadi", 60, 3, "Special", LocalDateTime.of(2024, 7, 16, 9, 0), "Saturday", LocalTime.of(8, 0)));
        physics.getDepartmentCourses().add(new SpecialCourse("Physics 4", 2009, "Physics", "dr.Abolhassani", 150, 3, "Special", LocalDateTime.of(2024, 7, 5, 9, 0), "Monday", LocalTime.of(17, 0)));

        Department chemistry = new Department("Chemistry");
        chemistry.getDepartmentCourses().add(new GeneralCourse("General Chemistry", 2013, "Chemistry", "dr.Hosseini", 150, 3, "General", LocalDateTime.of(2024, 7, 5, 9, 0), "Monday", LocalTime.of(8, 0)));
        chemistry.getDepartmentCourses().add(new SpecialCourse("Organic Chemistry", 2014, "Chemistry", "dr.Ahmadi", 80, 3, "Special", LocalDateTime.of(2024, 7, 5, 12, 30), "Monday", LocalTime.of(8, 30)));
        chemistry.getDepartmentCourses().add(new SpecialCourse("Organic Chemistry", 2015, "Chemistry", "dr.Safari", 30, 3, "Special", LocalDateTime.of(2024, 7, 5, 12, 30), "Monday", LocalTime.of(14, 0)));
        addStudentToCourse(Department.getAllDepartments().get(0).getDepartmentCourses().get(0), new Student("401100263", "2111063584"));
    }

    public boolean processSignup(String username, String password) {
        Student student = Student.getUserByUsername(username);
        if (student == null) {
            Student.addStudent(username, password);
            return true;
        }
        return false;
    }

    public Student getUserByUsername(String username) {
        return Student.getUserByUsername(username);
    }

    public boolean checkPassword(Student student, String password) {
        return student.getPassword().equals(password);
    }

    public List<Department> getDepartments() {
        return Department.getAllDepartments();
    }

    public Department getDepartment(String departmentName) {
        return Department.getDepartmentByName(departmentName);
    }

    public List<Course> getCoursesByStudent(String studentId) {
        return Student.getUserByUsername(studentId).getMyCourses();
    }

    public Course getCourseByCourseID(int courseID) {
        return Department.getCourseByCourseID(courseID);
    }

    public void addStudentToCourse(Course course, Student student) {
        course.getStudents().add(student);
        student.getMyCourses().add(course);
        course.setCapacity(course.getCapacity() - 1);
    }

    public void removeStudentToCourse(Course course, Student student) {
        course.getStudents().remove(student);
        student.getMyCourses().remove(course);
        course.setCapacity(course.getCapacity() + 1);
    }

}
