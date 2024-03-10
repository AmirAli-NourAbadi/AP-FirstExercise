package view;

import controller.Controller;
import model.Department;
import model.course.Course;
import model.user.Student;

import java.util.List;
import java.util.Scanner;

public class StudentCli {
    private Scanner scanner;
    private Controller controller;
    private Cli cli;
    private String username;

    public StudentCli(Cli cli, Controller controller, String username) {
        this.scanner = new Scanner(System.in);
        this.cli = cli;
        this.controller = controller;
        this.username = username;
        start();
    }

    public void start() {
        System.out.println("0- back \n1- my courses \n2- available courses");
        String option = scanner.nextLine();
        if (option.equals("0")) {
            cli.login();
        } else if (option.equals("1")) {
            myCourses();
        } else if (option.equals("2")) {
            showDepartments();
        } else {
            System.out.println("invalid command");
            start();
        }
    }

    private void myCourses() {
        System.out.println("My course: \n0- back");
        List<Course> myCourses = controller.getCoursesByStudent(username);
        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println(("- " + myCourses.get(i)));
        }
        System.out.println((myCourses.size() + 1) + "- remove course");
        String option = scanner.nextLine();
        if (option.equals("0")) {
            start();
        } else if (option.equals("" + (myCourses.size() + 1))) {
            removeCourse();
        } else {
            System.out.println("*invalid command*");
            myCourses();
        }
    }

    private void showDepartments() {
        List<Department> departments = controller.getDepartments();
        System.out.println("0- back");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + "- " + departments.get(i).getDepartmentName());
        }
        String departmentNumber = scanner.nextLine();
        if (checkDepartmentOptions(departmentNumber)) {
            if (departmentNumber.equals("0")) {
                start();
            } else {
                courseMenu(departmentNumber);
            }
        } else {
            System.out.println("*invalid command*");
            showDepartments();
        }
    }

    private void courseMenu(String departmentNumber) {
        String departmentName = controller.getDepartments().get(Integer.parseInt(departmentNumber) - 1).getDepartmentName();
        Department department = controller.getDepartment(departmentName);
        showCourses(department);
        String courseNumber = scanner.nextLine();
        if (checkCourseOptions(courseNumber, department)) {
            if (courseNumber.equals("0")) {
                showDepartments();
            } else if (courseNumber.equals("1")) {
                addCourseToMyCourses(departmentName);
            } else {
                System.out.println("*invalid command*");
                courseMenu(departmentNumber);
            }
        } else {
            System.out.println("invalid command");
            courseMenu(departmentNumber);
        }
    }

    private void addCourseToMyCourses(String departmentName) {
        System.out.println("0- back \nEnter course ID: ");
        String courseID = scanner.nextLine();
        Department department = Department.getDepartmentByName(departmentName);
        Student student = controller.getUserByUsername(username);
        if (checkCourseID(courseID)) {
            int courseId = Integer.parseInt(courseID);
            Course course = controller.getCourseByCourseID(courseId);
            if (courseId == 0) {
                courseMenu("" + (Department.getAllDepartments().indexOf(controller.getDepartment(departmentName)) + 1));
            } else {
                if (course != null) {
                    if (course.getDepartmentName().equals(departmentName)) {
                        if (course.getCapacity() > 0) {
                            if (student.getUnit() <= 20) {
                                if (checkConflict(student, course)) {
                                    student.getMyCourses().add(course);
                                    System.out.println("the course successfully add");
                                    courseMenu("" + (Department.getAllDepartments().indexOf(controller.getDepartment(departmentName)) + 1));
                                } else {
                                    System.out.println("*this course has conflict with others*");
                                    courseMenu("" + (Department.getAllDepartments().indexOf(controller.getDepartment(departmentName)) + 1));
                                }
                            } else {
                                System.out.println("*The roof of your unit is full*");
                                courseMenu("" + (Department.getAllDepartments().indexOf(controller.getDepartment(departmentName)) + 1));
                            }
                        } else {
                            System.out.println("*this course has no capacity*");
                            courseMenu("" + (Department.getAllDepartments().indexOf(controller.getDepartment(departmentName)) + 1));
                        }

                    } else {
                        System.out.println("this course isn't exists in this department");
                        addCourseToMyCourses(departmentName);
                    }
                } else {
                    System.out.println("this course isn't exists");
                    addCourseToMyCourses(departmentName);
                }
            }

        } else {
            System.out.println("invalid command");
            addCourseToMyCourses(departmentName);
        }
    }

    private void showCourses(Department department) {
        List<Course> courses = department.getDepartmentCourses();
        System.out.println("0- back");
        System.out.println("1- add course to my courses");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println("- " + courses.get(i));
        }
    }

    private void removeCourse() {
        List<Course> myCourses = controller.getCoursesByStudent(username);
        System.out.println("Enter course ID");
        String courseID = scanner.nextLine();
        if (checkCourseID(courseID)) {
            int courseId = Integer.parseInt(courseID);
            if (controller.getCourseByCourseID(courseId) != null) {
                if (controller.getUserByUsername(username).getMyCourses().contains(controller.getCourseByCourseID(courseId))) {
                    myCourses.remove(controller.getCourseByCourseID(courseId));
                    System.out.println("the course successfully removed");
                    myCourses();
                } else {
                    System.out.println("*The student does not have this course*");
                    removeCourse();
                }
            } else {
                System.out.println("this course isn't exists");
                removeCourse();
            }
        } else {
            System.out.println("*invalid course ID*");
            removeCourse();
        }
    }


    private boolean checkCourseOptions(String option, Department department) {
        return option.matches("\\d") && Integer.parseInt(option) >= 0 && Integer.parseInt(option) <= department.getDepartmentCourses().size();
    }

    private boolean checkDepartmentOptions(String option) {
        return option.matches("\\d") && Integer.parseInt(option) >= 0 && Integer.parseInt(option) <= controller.getDepartments().size();
    }

    private boolean checkCourseID(String option) {
        try {
            int courseID = Integer.parseInt(option);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    private boolean checkConflict(Student student, Course course) {
        for (Course myCourses : student.getMyCourses()) {
            if ((myCourses.getDay().equals(course.getDay()) && myCourses.getTime().equals(course.getTime())) || myCourses.getExamDateTime().equals(course.getExamDateTime())) {
                return false;
            }
        }
        return true;
    }
}
