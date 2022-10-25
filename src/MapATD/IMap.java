package MapATD;

public interface IMap<T1, T2> {
    void makeNull();
    void assign(T1 d, T2 r);
    boolean compute(T1 d, T2 r);

}
