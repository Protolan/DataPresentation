package ManyToManySet;

public class Student extends Link {

    public final char[] name;
    public Link link;

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




    @Override
    public boolean isConcrete() {
        return true;
    }
}
