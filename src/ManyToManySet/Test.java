package ManyToManySet;


import java.io.IOException;

public class Test {
    private static final String STUDENTS_FILE = "src/ManyToManySet/students.txt";
    private static final String COURSES_FILE = "src/ManyToManySet/courses.txt";

    public static void main(String[] args) throws IOException {
        MultiList multiList = new MultiList();
        multiList.initData(STUDENTS_FILE, COURSES_FILE);
        multiList.addStudentCourse(nameFactory("Alan"), 25);
        multiList.addStudentCourse(nameFactory("Ekaterina"), 25);
        multiList.addStudentCourse(nameFactory("Rustam"),25);
        multiList.addStudentCourse(nameFactory("Rustam"),12);
        multiList.addStudentCourse(nameFactory("Ekaterina"), 12);
        multiList.addStudentCourse(nameFactory("Ekaterina"), 15);
//        multiList.removeStudent(nameFactory("Ekaterina"));
        multiList.printCoursesOfStudent(nameFactory("Alan"));
        multiList.printCoursesOfStudent(nameFactory("Ekaterina"));
        multiList.printStudentsOfCourse(12);
    }

    private static char[] nameFactory(String str) {
        char[] resultName = new char[10];
        char[] initCharArray = str.toCharArray();
        for (int i = 0; i < initCharArray.length; i++) {
            resultName[i] = initCharArray[i];
        }
        return resultName;
    }




}
