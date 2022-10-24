package ListATD.Array;

public class Position {
    public int value;

    public Position(int number) {
        value = number;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position Data = (Position) o;
        return (this.value == Data.value);
    }

}
