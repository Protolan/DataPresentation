package ManyToManySet;


import java.io.IOException;

public class Test {
    private static final int LAMBDA = -1;

    private static final String STUDENTS_FILE = "src/ManyToManySet/students.txt";
    private static final String COURSES_FILE = "src/ManyToManySet/courses.txt";

    public static void main(String[] args) throws IOException {
        MultiList multiList = new MultiList();
        multiList.initData(STUDENTS_FILE, COURSES_FILE);
        multiList.addStudentCourse("Alan", 25);
        multiList.addStudentCourse("Ekaterina", 25);
        multiList.removeStudentCourse("Alan", 25);
        multiList.addStudentCourse("Alan", 25);
        multiList.printCoursesOfStudent("Alan");
        multiList.printStudentsOfCourse(25);
    }



}
