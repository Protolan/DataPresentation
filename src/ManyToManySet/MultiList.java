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



    // Добавляем студента на курс
    public void addStudentCourse(String studentName, int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if (course == null) return;
        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) return;
        // Если нет ни одной записи, создаем новую
        if (course.link == null) {
            MultiLink newLink = new MultiLink(student, course);
            newLink.courseLink = course;
            return;
        }
        // Начинаем обход со стороны курса
        // Идем до того момента пока текущая ссылка не будет конкретной, то есть до момента когда мы вернемся к этому же курсу
        MultiLink currentLink = course.link;
        while (!currentLink.isConcrete()) {
            // Проверяем, есть ли этот студент уже на этом курсе, если есть то выходим
            if (currentLink.studentLink.isConcrete() && currentLink.studentLink == student) {
                return;
            }
            currentLink = (MultiLink) currentLink.courseLink;
        }
        // Если прошли полностью до курса, значит значение не было найдено, вставляем запись вначало
        MultiLink newLink = new MultiLink(student, course.link);
        course.link = newLink;
    }

    // Убираем студента с курса
    public void removeStudentCourse(String studentName, int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if (course == null) return;
        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) return;
        // Если нет записей у студента или курса значит студен нет на этом курсе
        if(course.link == null || student.link == null) return;
        // Чтобы удалить регистрационную запись, нам нужно найти тех кто на нее ссылается
        MultiLink studentPreviousLink = null;
        MultiLink studentCurrentLink = student.link;
        while(true) {
            // Проверяем нашли ли мы нужный нам курс, если наши значит выходим
            if(studentCurrentLink.courseLink.isConcrete() && studentCurrentLink.courseLink == course) {
                break;
            }
            if(studentCurrentLink.studentLink.isConcrete()) return;
            studentPreviousLink = studentCurrentLink;
            studentCurrentLink = (MultiLink) studentCurrentLink.studentLink;
        }
        MultiLink coursePreviousLink = null;
        MultiLink courseCurrentLink = course.link;
        while(true) {
            // Проверяем нашли ли мы нужный нам курс, если наши значит выходим
            if(courseCurrentLink.studentLink.isConcrete() && courseCurrentLink.studentLink == student) {
                break;
            }
            if(courseCurrentLink.courseLink.isConcrete()) return;
            coursePreviousLink = courseCurrentLink;
            courseCurrentLink = (MultiLink) courseCurrentLink.courseLink;
        }
        // Теперь переназначаем ссылки
        if(studentPreviousLink == null) {
            student.link = null;
        }
        else {
            studentPreviousLink.studentLink = studentCurrentLink.studentLink;
        }
        if(coursePreviousLink == null) {
            course.link = null;
        }
        else {
            coursePreviousLink.courseLink = courseCurrentLink.courseLink;
        }
    }

    // Удалить студента со всех курсов
    public void removeStudent(String studentName) {
        // Проверяем есть ли такой набор значений
        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) return;
    }

    // Удаляем все студентов с курса
    public void removeCourse(int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if (course == null) return;
    }

    // Выводит список студентов курса
    public void printStudentsOfCourse(int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if (course == null) return;
        if(course.link == null) return;
    }

    // Выводит список курсов студента
    public void printCoursesOfStudent(String studentName) {
        // Проверяем есть ли такой набор значений
        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) return;
    }


}
