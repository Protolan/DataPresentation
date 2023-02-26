package SetATD.Array;

public class SetPlan {
    private class Range {
        public Range(int start, int end) {
            x = start;
            y = end;
        }
        public int x;
        public int y;
    }
    private Range range; // границы
    private int[] _array;// массив, кооторый хранит информацию о доступных числах
    // Конструктор с параметрами x, y, в котором иницилизируем массив диапазона
    public  SetPlan(int x, int y) {
        // Вычисляем нужную длину массива согласно дипазонам
        // Если y < x некоректный диапазон, выбросить исключение
        // Если x * y <= 0 -x;y, тогда длина массив -x/32 + y/32 + 2
        // Иначе (y - x) / 32 + 1
        // Инициализируем массив нулями
        _array = createRangeArray(new Range(x, y));
    }


    // Констрктор по умолчанию, создает пустое множество без выделения памяти на массив
    public  SetPlan() {
    }

    //Получает общий диапазон
    // Создает диапазон с максимальной левой и максимальной правой границы
    private Range getUniteRange(Range first, Range second) {
        int x = first.x < second.x ? first.x: second.x;
        int y = first.y > second.y ? first.y: second.y;
        return new Range(x, y);
    }

    // Создает массив из диапазона
    private int[] createRangeArray(Range range) {
        int arrayCount = (range.y - range.x) / 32 + 1;
        return new int[arrayCount];
    }

    private int getOffsetFromRange(Range range) {
        if(range.x * range.y <= 0) return 0;
        else if (range.x + range.y > 0) return range.x;
        else return range.y;
    }



    // Возвращает множество в которым есть все неповторяющиеся элементы из двух множеств
    SetPlan union(SetPlan set) {
        // Создаем новый массив общего диапазона
        // Выставляем значения диапазона из исходного списка
        // Делаем побитовое или согласно диапазонам второго
        if(set == this) return this;
        Range unionRange = getUniteRange(range, set.range);
        SetPlan setPlan = new SetPlan(unionRange.x, unionRange.y);
        // Привести к общему дипазону
        // Пробегаемся по векторам двух множества и побитово | и возвращаем новое множество
        return null;
    }

    // Возвращает множество которое содержит общие элементы из двух множеств
    SetPlan intersection(SetPlan set) {
        if(set == this) return this;
        return null;
    }

    // Возвращает множество которое содержит элементы которых нет в исходном множестве
    SetPlan difference(SetPlan set) {
        if(set == this) return new SetPlan(0,0);
        return null;
    }

    // Возвращает множество которое состоит из двух множеств, может быть выполнены если множество не имеют общих элементов
    SetPlan merge(SetPlan set) {
        return null;
    }

    // Проверяет содержит ли это множество значение
    boolean member(int x) {
        return false;
    }

    // Добавляет значение в множество, если его там нет и оно попадает в диапазон множества
    void insert(int x) {

    }

    // Удаляет значение из множества, если оно там есть, если нет ничего не делать
    void delete(int x) {

    }

    // Присваивает новое множество
    void assign(SetPlan set) {

    }

    // Возвращает минимальный элемент
    int min() {
        return 0;
    }

    // Возвращает максимальный элемент
    int max() {
        return 0;
    }

    // Возвращает true если множества равны
    boolean equal(SetPlan set) {
        return  false;
    }

    // Возвращает множество в котором найден элемент
    SetPlan find(SetPlan a, int x) {
        return null;
    }

    void makeNull() {
    }
}
