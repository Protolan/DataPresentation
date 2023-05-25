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
        if (course == null) {
            System.out.println("Курса " + courseId + " не существует");
            return;
        }
        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) {
            System.out.println("Cтудента + " + studentName + " не существует");
            return;
        }
        // Если нет ни одной записи у студента или курса, значит этого студента нет на курсе
        // Создаем новую ссылку
        if (course.link.isConcrete() || student.link.isConcrete()) {
            MultiLink newLink = new MultiLink(student.link, course.link);
            course.link = newLink;
            student.link = newLink;
            return;
        }
        // Начинаем обход со стороны курса
        // Идем до того момента пока текущая ссылка не будет конкретной, то есть до момента когда мы вернемся к этому же курсу
        Link currentLink = course.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            // Проверяем, есть ли этот студент уже на этом курсе, если есть то выходим
            Link studentLink = multiLink.studentLink;
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            if (studentLink == student) {
                return;
            }
            currentLink = multiLink.courseLink;
        }

        // Если прошли полностью до курса, значит значение не было найдено, вставляем запись вначало студента и курса
        MultiLink newLink = new MultiLink(student.link, course.link);
        course.link = newLink;
        student.link = newLink;
    }

    // Убираем студента с курса
    public void removeStudentCourse(String studentName, int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if (course == null) {
            System.out.println("Курса " + courseId + " не существует");
            return;
        }

        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) {
            System.out.println("Cтудента + " + studentName + " не существует");
            return;
        }
        // Если нет ни одной записи у студента или курса, значит этого студента нет на курсе
        if (course.link.isConcrete() || student.link.isConcrete()) {
            System.out.println("Студент " + studentName + " не записан на курс " + courseId + "!");
            return;
        }
        // Делаем обход со стороны студента, чтобы найти нужную мультссылку на студента
        // Чтобы удалить студента, нужно чтобы все элементы которые ссылается на нее, ссылались на следущую ссылку
        Link previousStudentLink = student;
        Link currentStudentLink = student.link;
        while (!currentStudentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentStudentLink;
            // Проверяем, есть ли этот студент уже на этом курсе, если есть то выходим
            Link courseLink = multiLink.courseLink;
            while (!courseLink.isConcrete()) {
                System.out.println("Идет работа на курсом");
                courseLink = ((MultiLink) courseLink).courseLink;
            }
            if (courseLink == course) {
                System.out.println("Курс нашелся!");
                break;
            }

            previousStudentLink = currentStudentLink;
            currentStudentLink = multiLink.studentLink;
        }
        // Если не нашелся у этого курса студента, значит нечего удалять
        if (currentStudentLink.isConcrete()) {
            System.out.println("Студент " + studentName + " не записан на курс " + courseId + "!");
            return;
        }
        Link previousCourseLink = course;
        MultiLink currentCourseLink = (MultiLink) course.link;
        // Теперь ищем предыдущий узел со стороны курс, теперь мы знаем, что он точно есть проверять не нужно
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

        if (previousCourseLink.isConcrete()) {
            course.link = currentCourseLink.courseLink;
        } else {
            ((MultiLink) previousCourseLink).courseLink = currentCourseLink.courseLink;
        }
        if (previousStudentLink.isConcrete()) {
            student.link = ((MultiLink) currentStudentLink).studentLink;
        } else {
            ((MultiLink) previousStudentLink).studentLink = ((MultiLink) currentStudentLink).studentLink;
        }

    }

    // Удалить студента со всех курсов
    public void removeStudent(String studentName) {
        // Проверяем есть ли такой набор значений
        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) {
            System.out.println("Cтудента + " + studentName + " не существует");
            return;
        }
        // Идем со стороны курса, ищем курсы и ищем предыдущий и удаляем их
        Link currentLink = student.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            // Как только находим мультиссылку с сылкой на курс, нужно элемент который на нее ссылается
            // Мы идем с курса, так как он образует кольцо
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

            if (previousCourseLink.isConcrete()) {
                ((Course) previousCourseLink).link = ((MultiLink) currentCourseLink).courseLink;
            } else {
                ((MultiLink) previousCourseLink).courseLink = ((MultiLink) currentCourseLink).courseLink;
            }

            currentLink = multiLink.studentLink;
        }

        student.link = student;

    }

    // Удаляем все студентов с курса
    public void removeCourse(int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if (course == null) {
            System.out.println("Курса " + courseId + " не существует");
            return;
        }
        // Идем со стороны студента, ищем курсы и ищем предыдущий и удаляем их
        Link currentLink = course.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            // Как только находим мультиссылку с сылкой на курс, нужно элемент который на нее ссылается
            // Мы идем с курса, так как он образует кольцо

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

            if (previousStudentLink.isConcrete()) {
                ((Student) previousStudentLink).link = ((MultiLink) currentStudentLink).studentLink;
            } else {
                ((MultiLink) previousStudentLink).studentLink = ((MultiLink) currentStudentLink).studentLink;
            }

            currentLink = multiLink.courseLink;
        }

        course.link = course;

    }

    // Выводит список студентов курса
    public void printStudentsOfCourse(int courseId) {
        // Проверяем есть ли такой набор значений
        Course course = _courseList.get(courseId);
        if (course == null) {
            System.out.println("Курса " + courseId + " не существует");
            return;
        }
        // Если нет записей, значит нет студентов
        if (course.link.isConcrete()) {
            System.out.println("На курсе " + courseId + " нет студентов");
            return;
        }
        System.out.println("Студенты на курсе " + courseId + ": ");
        // Начинаем обход со стороны курса
        // Идем до того момента пока текущая ссылка не будет конкретной
        // То есть до момента когда мы вернемся к этому же курсу
        Link currentLink = course.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            //Выводим студента
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
        // Проверяем есть ли такой набор значений
        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) {
            System.out.println("Cтудента + " + studentName + " не существует");
            return;
        }
        // Если нет записей, значит студен не записан не на один курс
        if (student.link.isConcrete()) {
            System.out.println("У студента " + studentName + " нет курсов");
            return;
        }
        System.out.println("Курсы студента " + studentName + ": ");
        // Идем до того момента пока текущая ссылка не будет конкретной
        // То есть до момента когда мы вернемся к этому же студенту
        Link currentLink = student.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link courseLink = multiLink.courseLink;
            while (!courseLink.isConcrete()) {
                courseLink = ((MultiLink) courseLink).courseLink;
            }
            // Выводим курс
            Course course = (Course) courseLink;
            System.out.println("\t" + course.id);
            currentLink = multiLink.studentLink;
        }
    }


}
