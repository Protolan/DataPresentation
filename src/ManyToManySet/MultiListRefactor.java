package ManyToManySet;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// Класс, в котором хранится множество отношений многие ко многим
public class MultiListRefactor {

    // Список студентов
    private StudentList _studentList;
    // Список курсов
    private CourseList _courseList;

    // Метод, который инициализирует списки студентов и курса из файлов
    public void initData(String studentFilePath, String courseFilePath) throws IOException {
        int maxStudentCount = 30;
        int maxCourseCount = 7;
        _studentList = new StudentList(maxStudentCount);
        _courseList = new CourseList(maxCourseCount);

        initStudentList(studentFilePath, maxStudentCount);
        initCourseList(courseFilePath, maxCourseCount);
    }


    // Добавляем студента на курс
    public void addStudentCourse(String studentName, int courseId) {
        Course course = _courseList.get(courseId);
        if (course == null) {
            System.out.println("Курса " + courseId + " не существует");
            return;
        }

        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) {
            System.out.println("Студента " + studentName + " не существует");
            return;
        }

        if (!isStudentEnrolledInCourse(student, course)) {
            MultiLink newLink = new MultiLink(student.link, course.link);
            course.link = newLink;
            student.link = newLink;
        }
    }
    // Убираем студента с курса
    public void removeStudentCourse(String studentName, int courseId) {
        Course course = _courseList.get(courseId);
        if (course == null) {
            System.out.println("Курса " + courseId + " не существует");
            return;
        }

        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) {
            System.out.println("Студента " + studentName + " не существует");
            return;
        }

        if (isStudentEnrolledInCourse(student, course)) {
            removeStudentFromCourse(student, course);
        } else {
            System.out.println("Студент " + studentName + " не записан на курс " + courseId + "!");
        }
    }
    // Удаляет студента со всех курсов
    public void removeStudent(String studentName) {
        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) {
            System.out.println("Студента " + studentName + " не существует");
            return;
        }

        Link currentLink = student.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link courseLink = multiLink.courseLink;
            while (!courseLink.isConcrete()) {
                courseLink = ((MultiLink) courseLink).courseLink;
            }
            Link previousCourseLink = courseLink;
            Link currentCourseLink = ((Course) courseLink).link;
            while (!currentCourseLink.isConcrete()) {
                MultiLink courseMultiLink = (MultiLink) currentCourseLink;
                Link studentLink = courseMultiLink.studentLink;
                while (!studentLink.isConcrete()) {
                    studentLink = ((MultiLink) studentLink).studentLink;
                }
                if (studentLink == student) {
                    break;
                }
                previousCourseLink = currentCourseLink;
                currentCourseLink = courseMultiLink.courseLink;
            }

            if (currentCourseLink.isConcrete()) {
                System.out.println("Студент " + studentName + " не записан на курс!");
                return;
            }

            updateCourseLink(previousCourseLink, (MultiLink) currentCourseLink);
            currentLink = multiLink.studentLink;
        }

        student.link = student;
    }
    // Удаляет все студентов с курса
    public void removeCourse(int courseId) {
        Course course = _courseList.get(courseId);
        if (course == null) {
            System.out.println("Курса " + courseId + " не существует");
            return;
        }

        Link currentLink = course.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link studentLink = multiLink.studentLink;
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            Link previousStudentLink = studentLink;
            Link currentStudentLink = ((Student) studentLink).link;
            while (!currentStudentLink.isConcrete()) {
                MultiLink studentMultiLink = (MultiLink) currentStudentLink;
                Link courseLink = studentMultiLink.courseLink;
                while (!courseLink.isConcrete()) {
                    courseLink = ((MultiLink) courseLink).courseLink;
                }
                if (courseLink == course) {
                    break;
                }
                previousStudentLink = currentStudentLink;
                currentStudentLink = studentMultiLink.studentLink;
            }

            if (currentStudentLink.isConcrete()) {
                System.out.println("Курс " + courseId + " не содержит студентов!");
                return;
            }

            updateStudentLink(previousStudentLink, currentStudentLink);
            currentLink = multiLink.courseLink;
        }

        course.link = course;
    }
    // Выводит список студентов курса
    public void printStudentsOfCourse(int courseId) {
        Course course = _courseList.get(courseId);
        if (course == null) {
            System.out.println("Курса " + courseId + " не существует");
            return;
        }

        if (course.link.isConcrete()) {
            System.out.println("На курсе " + courseId + " нет студентов");
            return;
        }

        System.out.println("Студенты на курсе " + courseId + ": ");
        Link currentLink = course.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link studentLink = multiLink.studentLink;
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            Student student = (Student) studentLink;
            System.out.println("\t" + student.toString());
            currentLink = multiLink.courseLink;
        }
    }
    // Выводит список курсов студента
    public void printCoursesOfStudent(String studentName) {
        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) {
            System.out.println("Студента " + studentName + " не существует");
            return;
        }

        if (student.link.isConcrete()) {
            System.out.println("У студента " + studentName + " нет курсов");
            return;
        }

        System.out.println("Курсы студента " + studentName + ": ");
        Link currentLink = student.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link courseLink = multiLink.courseLink;
            while (!courseLink.isConcrete()) {
                courseLink = ((MultiLink) courseLink).courseLink;
            }
            Course course = (Course) courseLink;
            System.out.println("\t" + course.id);
            currentLink = multiLink.studentLink;
        }
    }


    // Проверяет, записан ли студент на курс
    private boolean isStudentEnrolledInCourse(Student student, Course course) {
        Link currentLink = course.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link studentLink = multiLink.studentLink;
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            if (studentLink == student) {
                return true;
            }
            currentLink = multiLink.courseLink;
        }
        return false;
    }



    // Удаляет студента с курса
    private void removeStudentFromCourse(Student student, Course course) {
        Link previousStudentLink = student;
        Link currentStudentLink = student.link;
        while (!currentStudentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentStudentLink;
            Link courseLink = multiLink.courseLink;
            while (!courseLink.isConcrete()) {
                courseLink = ((MultiLink) courseLink).courseLink;
            }
            if (courseLink == course) {
                break;
            }
            previousStudentLink = currentStudentLink;
            currentStudentLink = multiLink.studentLink;
        }

        Link previousCourseLink = course;
        MultiLink currentCourseLink = (MultiLink) course.link;
        while (!currentCourseLink.isConcrete()) {
            Link studentLink = currentCourseLink.studentLink;
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            if (studentLink == student) {
                break;
            }
            previousCourseLink = currentCourseLink;
            currentCourseLink = (MultiLink) currentCourseLink.courseLink;
        }

        updateCourseLink(previousCourseLink, currentCourseLink);
        updateStudentLink(previousStudentLink, currentStudentLink);
    }

    // Обновляет ссылку на курс
    private void updateCourseLink(Link previousCourseLink, MultiLink currentCourseLink) {
        if (previousCourseLink.isConcrete()) {
            ((Course) previousCourseLink).link = currentCourseLink.courseLink;
        } else {
            ((MultiLink) previousCourseLink).courseLink = currentCourseLink.courseLink;
        }
    }

    // Обновляет ссылку на студента
    private void updateStudentLink(Link previousStudentLink, Link currentStudentLink) {
        if (previousStudentLink.isConcrete()) {
            ((Student) previousStudentLink).link = ((MultiLink) currentStudentLink).studentLink;
        } else {
            ((MultiLink) previousStudentLink).studentLink = ((MultiLink) currentStudentLink).studentLink;
        }
    }




    // Метод для инициализации списка студентов
    private void initStudentList(String studentFilePath, int maxStudentCount) throws IOException {
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
    }

    // Метод для инициализации списка курсов
    private void initCourseList(String courseFilePath, int maxCourseCount) throws IOException {
        FileReader fr = new FileReader(courseFilePath);
        Scanner scan = new Scanner(fr);

        int counter = 0;
        while (scan.hasNextLine() && counter < maxCourseCount) {
            int id = Integer.parseInt(scan.nextLine());
            counter++;
            _courseList.insert(id);
        }

        fr.close();
    }

    // Метод для приведения имени из строки в массив char заданной длины (10)
    private static char[] nameFactory(String str) {
        char[] resultName = new char[10];
        char[] initCharArray = str.toCharArray();
        for (int i = 0; i < initCharArray.length; i++) {
            resultName[i] = initCharArray[i];
        }
        return resultName;
    }

}
