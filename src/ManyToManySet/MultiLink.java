package ManyToManySet;

// Класс мультиссылки
public class MultiLink extends Link {
    // Ссылка на сторону студентов, может ссылаться на следующую мультиссылку или на обьект студента
    public Link studentLink;
    // Ссылка на сторону курсов, может ссылаться на следущю мультиссылку или на обьект курса
    public Link courseLink;

    public MultiLink(Link studentLink, Link courseLink) {
        this.studentLink = studentLink;
        this.courseLink = courseLink;
    }

    // Метод который будет использоваться для проверки класса обьекта
    @Override
    public boolean isConcrete() {
        return false;
    }
}
