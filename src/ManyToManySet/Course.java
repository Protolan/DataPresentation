package ManyToManySet;

// Класс обьекта курса, хранит номер и ссылку на мультиссылку
public class Course extends Link {
    public final int id;
    public Link link;

    //По умолчанию запись ссылаеться на саму себя
    public Course(int number) {
        this.id = number;
        link = this;
    }

    @Override
    public boolean isConcrete() {
        return true;
    }
}
