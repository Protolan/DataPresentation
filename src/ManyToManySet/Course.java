package ManyToManySet;

// Класс обьекта курса, хранит номер и ссылку на мультиссылку
public class Course extends Link {
    // Id курса
    public final int id;

    // Хранит ссылку на мультиссылку, или если нет связей сам на себя
    public Link link;

    //По умолчанию запись ссылается на саму себя
    public Course(int number) {
        this.id = number;
        link = this;
    }

    // Метод который будет использоваться для проверки класса обьекта
    @Override
    public boolean isConcrete() {
        return true;
    }
}
