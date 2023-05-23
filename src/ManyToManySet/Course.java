package ManyToManySet;

// Класс обьекта курса, хранит номер и ссылку на мультиссылку
public class Course extends Link {
    public final int id;
    public MultiLink link;

    public Course(int number) {
        this.id = number;
        link = null;
    }

    @Override
    public boolean isConcrete() {
        return true;
    }
}
