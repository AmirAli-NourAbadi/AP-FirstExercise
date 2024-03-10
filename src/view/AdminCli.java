package view;

import controller.Controller;
import model.Department;
import model.course.Course;
import model.user.Student;

import java.util.List;
import java.util.Scanner;

public class AdminCli {
    private Scanner scanner;
    private Controller controller;
    private Cli cli;

    AdminCli(Cli cli, Controller controller) {
        this.controller = controller;
        scanner = new Scanner(System.in);
        this.cli = cli;
        start();
    }

    public void start() {
        showDepartments();
        System.out.println("choose your department:");
        String departmentNumber = scanner.nextLine();
        if (checkDepartmentOptions(departmentNumber)) {
            if (departmentNumber.equals("0")) {
                cli.login();
            } else {
                courseMenu(departmentNumber);
            }
        } else {
            System.out.println("*invalid command*");
            start();
        }
    }

    private void showDepartments() {
        List<Department> departments = controller.getDepartments();
        System.out.println("0- back");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + "- " + departments.get(i).getDepartmentName());
        }
    }

    private void courseMenu(String departmentNumber) {
        String departmentName = controller.getDepartments().get(Integer.parseInt(departmentNumber) - 1).getDepartmentName();
        Department department = controller.getDepartment(departmentName);
        showCourses(department);
        String courseNumber = scanner.nextLine();
        if (checkCourseOptions(courseNumber, department)) {
            if (courseNumber.equals("0")) {
                start();
            } else {
                String courseName = department.getDepartmentCourses().get(Integer.parseInt(courseNumber) - 1).getCourseName();
                Course course = department.getCourseByName(courseName);
                courseOption(course);
            }
        } else {
            System.out.println("*invalid command*");
            courseMenu(departmentNumber);
        }
    }

    private void removeCourse(Course course) {
        Department department = controller.getDepartment(course.getDepartmentName());
        department.getDepartmentCourses().remove(course);
        String departmentIndex = "" + (controller.getDepartments().indexOf(department) + 1);
        System.out.println("the course successfully removed");
        courseMenu(departmentIndex);
    }


    private void courseOption(Course course) {
        List<Student> students = course.getStudents();
        System.out.println("0- back");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + "- " + students.get(i));
        }
        System.out.println((students.size() + 1) + "- add student");
        System.out.println((students.size() + 2) + "- remove student");
        System.out.println((students.size() + 3) + "- increase capacity");
        System.out.println((students.size() + 4) + "- remove course");
        String courseOption = scanner.nextLine();
        if (courseOption.equals("0")) {
            courseMenu("" + (Department.getAllDepartments().indexOf(controller.getDepartment(course.getDepartmentName())) + 1));
        } else if (courseOption.equals("" + (students.size() + 1))) {
            addStudent(course);
        } else if (courseOption.equals("" + (students.size() + 2))) {
            removeStudent(course);
        } else if (courseOption.equals("" + (students.size() + 3))) {
            increaseCapacity(course);
        } else if (courseOption.equals("" + (students.size() + 4))) {
            removeCourse(course);
        } else {
            System.out.println("*invalid command*");
            courseOption(course);
        }

    }

    private void increaseCapacity(Course course) {
        System.out.println("0- back \nEnter capacity:");
        String number = scanner.nextLine();
        int capacity = 0;
        try {
            capacity = Integer.parseInt(number);
        } catch (Exception exception) {
            System.out.println("*invalid capacity*");
            increaseCapacity(course);
        }
        if (number.equals("0")) {
            courseOption(course);
        } else {
            course.setCapacity(course.getCapacity() + capacity);
            System.out.println("The capacity was increased");
            courseOption(course);
        }
    }

    private void addStudent(Course course) {
        System.out.println("0- back \nEnter Student ID:");
        String studentID = scanner.nextLine();
        Student student = controller.getUserByUsername(studentID);
        if (studentID.equals("0")) {
            courseOption(course);
        } else if (student != null) {
            if (course.getCapacity() > 0) {
                if (student.getUnit() <= 20) {
                    if (checkConflict(student, course)) {
                        controller.addStudentToCourse(course, student);
                        System.out.println("*the student add successfully*");
                        courseOption(course);
                    } else {
                        System.out.println("*this course has conflict with others*");
                        courseOption(course);
                    }
                } else {
                    System.out.println("*The roof of this student's unit is full*");
                    addStudent(course);
                }

            } else {
                System.out.println("*this course has no capacity*");
                showCourses(controller.getDepartment(course.getDepartmentName()));
            }
        } else {
            System.out.println("*invalid student Id* \nEnter correct one");
            addStudent(course);
        }

    }

    private boolean checkConflict(Student student, Course course) {
        for (Course myCourses : student.getMyCourses()) {
            if ((myCourses.getDay().equals(course.getDay()) && myCourses.getTime().equals(course.getTime())) || myCourses.getExamDateTime().equals(course.getExamDateTime())) {
                return false;
            }
        }
        return true;
    }

    private void removeStudent(Course course) {
        System.out.println("Enter Student ID:");
        String studentID = scanner.nextLine();
        if (studentID.equals("0")) {
            courseOption(course);
        } else if (controller.getUserByUsername(studentID) != null) {
            if (controller.getUserByUsername(studentID).getMyCourses().contains(course)) {
                controller.removeStudentToCourse(course, controller.getUserByUsername(studentID));
                System.out.println("the student remove successfully:)");
                courseOption(course);
            } else {
                System.out.println("*The student does not have this course*\n");
                courseOption(course);
            }

        } else {
            System.out.println("*invalid student Id* \nEnter correct one");
            removeStudent(course);
        }
    }

    private boolean checkDepartmentOptions(String option) {
        return option.matches("\\d") && Integer.parseInt(option) >= 0 && Integer.parseInt(option) <= controller.getDepartments().size();
    }

    private boolean checkCourseOptions(String option, Department department) {
        return option.matches("\\d") && Integer.parseInt(option) >= 0 && Integer.parseInt(option) <= department.getDepartmentCourses().size() + 1;
    }


    private void showCourses(Department department) {
        List<Course> courses = department.getDepartmentCourses();
        System.out.println("0- back");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + "- " + courses.get(i));
        }
    }

}
