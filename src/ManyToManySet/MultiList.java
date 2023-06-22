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
            // Если ссылка на студента это мультиссылка, нужно сделать обход до тех пор пока не будет конкретный студент
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
        // Проверяем есть ли вообще такой курс и студент
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
        // Если не нашелся у этого курса студента, значит нечего удалять
        if (currentStudentLink.isConcrete()) {
            System.out.println("Студент " + studentName + " не записан на курс " + courseId + "!");
            return;
        }
        Link previousCourseLink = course;
        MultiLink currentCourseLink = (MultiLink) course.link;
        // Теперь ищем предыдущий узел со стороны курса
        while (!currentCourseLink.isConcrete()) {
            Link studentLink = currentCourseLink.studentLink;
            // Если ссылка на студента это мультиссылка, нужно сделать обход до тех пор пока не будет конкретный студент
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            if (studentLink == student) {
                break;
            }
            previousCourseLink = currentCourseLink;
            currentCourseLink = (MultiLink) currentCourseLink.courseLink;
        }

        // Если все нашлось, теперь мы можем удалить студента курса со стороны курса, так и со стороны студента
        // Важно проверить, мы удаляем ссылку у конкретного класса или мультиссылки?
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
        // Проверяем есть ли такой студент
        Student student = _studentList.get(nameFactory(studentName));
        if (student == null) {
            System.out.println("Cтудента + " + studentName + " не существует");
            return;
        }

        // Теперь надо обойти все курсы студента и удалить у них связь с этим студентом
        Link currentLink = student.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link courseLink = multiLink.courseLink;
            // Находим конкретный курс, а не мультиссылку
            while (!courseLink.isConcrete()) {
                courseLink = ((MultiLink) courseLink).courseLink;
            }
            Link previousCourseLink = courseLink;
            Link currentCourseLink = ((Course) courseLink).link;
            // Начинаем обход этого курса в поиска нашего студента
            while (!currentCourseLink.isConcrete()) {
                MultiLink courseMultiLink = (MultiLink) currentCourseLink;
                Link studentLink = courseMultiLink.studentLink;
                // Если ссылка на студента это мультиссылка, нужно сделать обход до тех пор пока не будет конкретный студент
                while (!studentLink.isConcrete()) {
                    studentLink = ((MultiLink) studentLink).studentLink;
                }
                if (studentLink == student) {
                    break;
                }
                previousCourseLink = currentCourseLink;
                currentCourseLink = courseMultiLink.courseLink;
            }

            // Если все нашлось, теперь мы можем удалить студента со стороны курса
            // Важно проверить, мы удаляем ссылку у курса или мультиссылки?
            if (previousCourseLink.isConcrete()) {
                ((Course) previousCourseLink).link = ((MultiLink) currentCourseLink).courseLink;
            } else {
                ((MultiLink) previousCourseLink).courseLink = ((MultiLink) currentCourseLink).courseLink;
            }

            currentLink = multiLink.studentLink;
        }

        // Ссылку на студента мы просто обнуляем (ссылается сам на себя)
        student.link = student;

    }

    // Удаляем все студентов с курса
    public void removeCourse(int courseId) {
        // Проверяем есть ли такой курс
        Course course = _courseList.get(courseId);
        if (course == null) {
            System.out.println("Курса " + courseId + " не существует");
            return;
        }
        // Теперь надо обойти все курсы студента и удалить у них связь с этим студентом
        Link currentLink = course.link;
        while (!currentLink.isConcrete()) {
            MultiLink multiLink = (MultiLink) currentLink;
            Link studentLink = multiLink.studentLink;
            // Находим конкретного студента, а не мультиссылку
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            Link previousStudentLink = studentLink;
            Link currentStudentLink = ((Student) studentLink).link;
            // Начинаем обход этого студента в поиска нашего курса
            while (!currentStudentLink.isConcrete()) {
                MultiLink studentMultiLink = (MultiLink) currentStudentLink;
                Link courseLink = studentMultiLink.courseLink;
                // Если ссылка на курс это мультиссылка, нужно сделать обход до тех пор пока не будет конкретный курс
                while (!courseLink.isConcrete()) {
                    courseLink = ((MultiLink) courseLink).courseLink;
                }
                if (courseLink == course) {
                    break;
                }
                previousStudentLink = currentStudentLink;
                currentStudentLink = studentMultiLink.studentLink;
            }

            // Если все нашлось, теперь мы можем удалить курса со стороны студента
            // Важно проверить, мы удаляем ссылку студента или мультиссылки?
            if (previousStudentLink.isConcrete()) {
                ((Student) previousStudentLink).link = ((MultiLink) currentStudentLink).studentLink;
            } else {
                ((MultiLink) previousStudentLink).studentLink = ((MultiLink) currentStudentLink).studentLink;
            }

            currentLink = multiLink.courseLink;
        }

        // Ссылку на курс мы просто обнуляем (ссылается сам на себя)
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
            System.out.println("Студент " + student.toString() + " не записан на курс " + course.id + "!");
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
        return false;

    }

    // Метод для получения предыдущего студента, который есть на этом курсе
    private void findPreviousStudentOfCourse(Student student, Course course) {
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

    }

    // Метод для получения предыдущего курса, в котором есть этот студент
    private Link findPreviousCourseOfStudent(Student student, Course course) {
        Link previousCourseLink = course;
        MultiLink currentCourseLink = (MultiLink) course.link;
        // Теперь ищем предыдущий узел со стороны курса
        while (!currentCourseLink.isConcrete()) {
            Link studentLink = currentCourseLink.studentLink;
            // Если ссылка на студента это мультиссылка, нужно сделать обход до тех пор пока не будет конкретный студент
            while (!studentLink.isConcrete()) {
                studentLink = ((MultiLink) studentLink).studentLink;
            }
            if (studentLink == student) {
                break;
            }
            previousCourseLink = currentCourseLink;
            currentCourseLink = (MultiLink) currentCourseLink.courseLink;
        }
        return previousCourseLink;
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
