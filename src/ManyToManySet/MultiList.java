package ManyToManySet;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

//Класс в котором хранится множество отношения многие ко многим
public class MultiList {

    private StudentList _studentList;
    private CourseList _courseList;


    // Метод которые инициализирует списки студентов и курса из файлов
    public void initData(String studentFilePath, String courseFilePath) throws IOException {
        int maxStudentCount = 30;
        int maxCourseCount = 7;
        _studentList = new StudentList(maxStudentCount);
        _courseList = new CourseList(maxCourseCount);
        // Инициализирем FileReader и Scanner и построчно заполняем список Стдентов
        // Каждую строку мы преоборазуем в массива char c помощью метода nameFactory, чтобы нормализовать все на 10 длину
        FileReader fr = new FileReader(studentFilePath);
        Scanner scan = new Scanner(fr);
        int counter = 0;
        while (scan.hasNextLine() && counter < maxStudentCount) {
            String line = scan.nextLine();
            if (line.length() > 10) continue;
            counter++;
            _studentList.insert(nameFactory(line));
        }
        fr.close();
        fr = new FileReader(courseFilePath);
        scan = new Scanner(fr);
        counter = 0;
        while (scan.hasNextLine() && counter < maxStudentCount) {
            int id = Integer.parseInt(scan.nextLine());
            counter++;
            _courseList.insert(id);
        }
        fr.close();
    }

    // Метод для приведения имени из строки в массив char заданной длины
    private static char[] nameFactory(String str) {
        char[] resultName = new char[10];
        char[] initCharArray = str.toCharArray();
        for (int i = 0; i < initCharArray.length; i++) {
            resultName[i] = initCharArray[i];
        }
        return resultName;
    }

    // Метод для сравнения имен, рассчитываем на то что передаются имена одной длины
    private boolean compareNames(char[] name1, char[] name2) {
        for (int i = 0; i < name1.length; i++) {
            if (name1[i] != name2[i]) return false;
        }
        return true;
    }


    public void addStudentCourse(String studentName, int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if(course == null) return;
        Student student = _studentList.get(nameFactory(studentName));
        if(student == null) return;
        // Вначале заходим со стороны курса
        // Если нет ни одной записи, просто создаем
        if(course.link == null) {
            MultiLink newLink = new MultiLink(student, course);
            newLink.courseLink = course;
            return;
        }
        // Вместо предыдущего вставлять первый элемент
        // Идем до того момента пока next узла не будет начального курса и проверяем есть ли этот студент уже на этом курсе
        MultiLink previousLink = course.link;
        MultiLink currentLink = course.link;
        while(!currentLink.isConcrete()) {
            if(currentLink.studentLink.isConcrete() && currentLink.studentLink == student) {
                return;
            }
            previousLink = currentLink;
            currentLink = (MultiLink) currentLink.courseLink;
        }
        // Если прошли полностью до курса, значим можем вставлять
        // Назначаем предыдущей
        MultiLink newLink = new MultiLink(student, course);
        previousLink.courseLink = newLink;
    }

    public void removeStudentCourse(String studentName, int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if(course == null) return;
        Student student = _studentList.get(nameFactory(studentName));
        if(student == null) return;
    }

    public void removeStudent(String studentName) {
        // Проверяем есть ли такой набор значений
        Student student = _studentList.get(nameFactory(studentName));
        if(student == null) return;
    }

    public void removeCourse(int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if(course == null) return;
    }

    public void printStudentsOfCourse(int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if(course == null) return;
    }

    public void printCoursesOfStudent(String studentName) {
        // Проверяем есть ли такой набор значений
        Student student = _studentList.get(nameFactory(studentName));
        if(student == null) return;
    }


}
