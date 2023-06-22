package ManyToManySet;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

//Класс в котором хранится множество отношения многие ко многим
public class MultiList {

    // Список студентов
    private StudentList _studentList;
    // Список курсов
    private CourseList _courseList;


    // Метод который инициализирует списки студентов и курса из файлов
    public void initData(String studentFilePath, String courseFilePath) throws IOException {
        // Задаем максимальное кол-во студентов и курсов
        int maxStudentCount = 30;
        int maxCourseCount = 7;
        _studentList = new StudentList(maxStudentCount);
        _courseList = new CourseList(maxCourseCount);
        // Инициализирем FileReader и Scanner и построчно заполняем список студентов, а затем курсов
        FileReader fr = new FileReader(studentFilePath);
        Scanner scan = new Scanner(fr);
        int counter = 0;
        while (scan.hasNextLine() && counter < maxStudentCount) {
            String line = scan.nextLine();
            if (line.length() > 10) continue;
            counter++;
            // Каждую строку мы преобразуем в массив char c помощью метода nameFactory, чтобы нормализовать все на 10 длину
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

    // Метод для приведения имени из строки в массив char заданной длины (10)
    private static char[] nameFactory(String str) {
        char[] resultName = new char[10];
        char[] initCharArray = str.toCharArray();
        for (int i = 0; i < initCharArray.length; i++) {
            resultName[i] = initCharArray[i];
        }
        return resultName;
    }


    // Добавляем студента на курс
    public void addStudentCourse(char[] studentName, int courseId) {
        Course course = getCourseByName(courseId);
        Student student = getStudentByName(studentName);
        if(course == null || student == null) return;
        if(isStudentOnCourse(student, course)) return;
        // Если прошли полностью до курса, значит значение не было найдено, вставляем запись вначало студента и курса
        MultiLink newLink = new MultiLink(student.link, course.link);
        course.link = newLink;
        student.link = newLink;
    }

    // Убираем студента с курса
    public void removeStudentCourse(char[] studentName, int courseId) {
        Course course = getCourseByName(courseId);
        Student student = getStudentByName(studentName);
        if(course == null || student == null) return;
        if(!isStudentOnCourse(student, course)) return;
        // Делаем обход со стороны студента, чтобы найти нужную мультссылку на студента
        // Чтобы удалить студента, нужно чтобы все элементы которые ссылается на нее, ссылались на следущую ссылку
        Link previousStudentLink = findPreviousStudentLink(student, course);
        Link previousCourseLink = findPreviousCourseLink(student, course);
        DeleteCourseLink(previousCourseLink);
        DeleteStudentLink(previousStudentLink);
    }


    // Удалить студента со всех курсов
    public void removeStudent(char[] studentName) {
        // Проверяем есть ли такой студент
        Student student = getStudentByName(studentName);
        if(student == null) return;

        // Теперь надо обойти все курсы студента и удалить у них связь с этим студентом
        Link currentLink = student.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link courseLink = multiLink.courseLink;
            // Находим конкретный курс, а не мультиссылку
            while (!courseLink.isConcrete()) {
                courseLink = ((MultiLink) courseLink).courseLink;
            }
            Link previousCourseLink = findPreviousCourseLink(student, (Course) courseLink);
            DeleteCourseLink(previousCourseLink);
            currentLink = multiLink.studentLink;
        }

        // Ссылку на студента мы просто обнуляем (ссылается сам на себя)
        student.link = student;

    }

    // Удаляем все студентов с курса
    public void removeCourse(int courseId) {
        // Проверяем есть ли такой курс
        Course course = getCourseByName(courseId);
        if(course == null) return;
        // Теперь надо обойти все курсы студента и удалить у них связь с этим студентом
        Link currentLink = course.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link studentLink = multiLink.studentLink;
            // Находим конкретного студента, а не мультиссылку
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            Link previousStudentLink = findPreviousStudentLink((Student) studentLink, course);
            DeleteStudentLink(previousStudentLink);
            currentLink = multiLink.courseLink;
        }

        // Ссылку на курс мы просто обнуляем (ссылается сам на себя)
        course.link = course;

    }

    // Выводит список студентов курса
    public void printStudentsOfCourse(int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = getCourseByName(courseId);
        if(course == null) return;
        // Если нет записей, значит нет студентов
        if (course.link.isConcrete()) {
            System.out.println("На курсе " + courseId + " нет студентов");
            return;
        }
        System.out.println("Студенты на курсе " + courseId + ": ");
        // Начинаем обход со стороны курса
        Link currentLink = course.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;

            Link studentLink = multiLink.studentLink;
            // Если ссылка на студента это мультиссылка, нужно сделать обход до тех пор пока не будет конкретный студент
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            //Выводим студента
            Student student = (Student) studentLink;
            System.out.println("\t" + student.toString());
            currentLink = multiLink.courseLink;
        }
    }

    // Выводит список курсов студента
    public void printCoursesOfStudent(char[] studentName) {
        // Проверяем есть ли такой набор значений
        Student student = getStudentByName(studentName);
        if(student == null) return;
        // Если нет записей, значит студен не записан не на один курс
        if (student.link.isConcrete()) {
            System.out.println("У студента " + student + " нет курсов");
            return;
        }
        System.out.println("Курсы студента " + student + ": ");
        // Идем до того момента пока текущая ссылка не будет конкретной
        Link currentLink = student.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link courseLink = multiLink.courseLink;
            // Если ссылка на курс это мультиссылка, нужно сделать обход до тех пор пока не будет конкретный курс
            while (!courseLink.isConcrete()) {
                courseLink = ((MultiLink) courseLink).courseLink;
            }
            // Выводим курс
            Course course = (Course) courseLink;
            System.out.println("\t" + course.id);
            currentLink = multiLink.studentLink;
        }
    }

    // Метод для поиска находится ли студент на курсе
    private boolean isStudentOnCourse(Student student, Course course) {
        if (course.link.isConcrete() || student.link.isConcrete()) {
//            System.out.println("Студент " + student + " не записан на курс " + course.id + "!");
            return false;
        }
        Link currentLink = course.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            // Проверяем, есть ли этот студент уже на этом курсе, если есть то выходим
            Link studentLink = multiLink.studentLink;
            // Если ссылка на студента это мультиссылка, нужно сделать обход до тех пор пока не будет конкретный студент
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            if (studentLink == student) {
                return true;
            }
            currentLink = multiLink.courseLink;
        }
//        System.out.println("Студент " + student + " не записан на курс " + course.id + "!");
        return false;

    }

    // Метод для получения предыдущего студента, который есть на этом курсе
    private Link findPreviousStudentLink(Student student, Course course) {
        Link previousStudentLink = student;
        Link currentStudentLink = student.link;
        while (!currentStudentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentStudentLink;
            // Проверяем, есть ли этот студент уже на этом курсе, если есть то выходим
            Link courseLink = multiLink.courseLink;
            // Если ссылка на курс это мультиссылка, нужно сделать обход до тех пор пока не будет конкретный курс
            while (!courseLink.isConcrete()) {
                courseLink = ((MultiLink) courseLink).courseLink;
            }
            if (courseLink == course) {
                break;
            }

            previousStudentLink = currentStudentLink;
            currentStudentLink = multiLink.studentLink;
        }
        return previousStudentLink;
    }

    // Метод для получения предыдущего курса, в котором есть этот студент
    private Link findPreviousCourseLink(Student student, Course course) {
        Link previousCourseLink = course;
        Link currentCourseLink = course.link;
        // Начинаем обход этого курса в поиска нашего студента
        while (!currentCourseLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentCourseLink;
            Link studentLink = multiLink.studentLink;
            // Если ссылка на студента это мультиссылка, нужно сделать обход до тех пор пока не будет конкретный студент
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            if (studentLink == student) {
                break;
            }
            previousCourseLink = currentCourseLink;
            currentCourseLink = multiLink.courseLink;
        }
        return previousCourseLink;
    }

    private static void DeleteStudentLink(Link previousStudentLink) {
        if (previousStudentLink.isConcrete()) {
            Student studentLink = (Student) previousStudentLink;
            studentLink.link = ((MultiLink)studentLink.link).studentLink;
        } else {
            MultiLink studentMultiLink = (MultiLink) previousStudentLink;
            studentMultiLink.studentLink = ((MultiLink)studentMultiLink.studentLink).studentLink;
        }
    }

    private static void DeleteCourseLink(Link previousCourseLink) {
        if (previousCourseLink.isConcrete()) {
            Course courseConcreteLink = (Course) previousCourseLink;
            courseConcreteLink.link = ((MultiLink)courseConcreteLink.link).courseLink;
        } else {
            MultiLink courseMultiLink = (MultiLink) previousCourseLink;
            courseMultiLink.courseLink = ((MultiLink)courseMultiLink.courseLink).courseLink;
        }
    }

    private Student getStudentByName(char[] studentName) {
        Student student = _studentList.get(studentName);
        if (student == null) {
            System.out.println("Cтудента + " + studentName + " не существует");
        }
        return student;
    }

    private Course getCourseByName(int courseId) {
        Course course = _courseList.get(courseId);
        if (course == null) {
            System.out.println("Курса " + courseId + " не существует");
        }
        return course;
    }
}
