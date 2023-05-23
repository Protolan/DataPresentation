package ManyToManySet;

public class Student extends Link {

    public final char[] name;
    public MultiLink link;

    public Student(char[] name) {
        this.name = name;
        link = null;
    }


    @Override
    public boolean isConcrete() {
        return true;
    }
}
