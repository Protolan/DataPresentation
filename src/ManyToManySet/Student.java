package ManyToManySet;

public class Student extends Link {

    // Имя студента, длина 10 символов
    public final char[] name;
    // Хранит ссылку на мультиссылку, или если нет связей сам на себя
    public Link link;

    //По умолчанию запись ссылается на саму себя
    public Student(char[] name) {
        this.name = name;
        link = this;
    }

    @Override
    public String toString() {
        //Убираем лишние нули в конце(у нас все имена длиной 10 символов)
        int counter = 0;
        for (int i = 0; i < name.length; i++) {
            if (name[i] == 0) {
                break;
            }
            counter++;
        }
        char[] clear = new char[counter];
        for (int i = 0; i < counter; i++) {
            clear[i] = name[i];
        }
        return new String(clear);
    }

    // Метод который будет использоваться для проверки класса обьекта
    @Override
    public boolean isConcrete() {
        return true;
    }
}
