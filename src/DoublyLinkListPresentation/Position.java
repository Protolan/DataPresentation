package DoublyLinkListPresentation;


public class Position {
    public Node node;
    public Position(Node d){
        node = d;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position Data = (Position) o;
        return (this.node == Data.node);
    }
}
