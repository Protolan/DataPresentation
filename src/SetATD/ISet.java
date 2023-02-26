package SetATD;

import SetATD.Array.SetPlan;

public interface ISet {
    // Возвращает множество в которым есть все неповторяющиеся элементы из двух множеств
    SetPlan union(SetPlan set);
    // Возвращает множество которое содержит общие элементы из двух множеств
    SetPlan intersection(SetPlan set);
    // Возвращает множество которое содержит элементы которых нет в исходном множестве
    SetPlan difference(SetPlan set);
    // Возвращает множество которое состоит из двух множеств, может быть выполнены если множество не имеют общих элементов
    SetPlan merge(SetPlan set);
    // Проверяет содержит ли это множество значение
    boolean member(int x);
    // Добавляет значение в множество, если его там нет и оно попадает в диапазон множества
    void insert(int x);
    // Удаляет значение из множества, если оно там есть, если нет ничего не делать
    void delete(int x);
    // Присваивает новое множество
    void assign(SetPlan set);
    // Возвращает минимальный элемент
    int min();
    // Возвращает максимальный элемент
    int max();
    // Возвращает true если множества равны
    boolean equal(SetPlan set);
    // Возвращает множество в котором найден элемент
    SetPlan find(SetPlan a, int x);
    void makeNull();
}
