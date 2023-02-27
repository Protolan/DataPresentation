package SetATD.Array;

public class Set2 {
    private class Range {
        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int start;
        public int end;
    }

    private class Position {
        public Position(int index, int bit) {
            this.index = index;
            this.bit = bit;
        }

        public int index;
        public int bit;
    }

    private Range _range; // границы
    private int[] _array;// массив, кооторый хранит информацию о доступных числах

    // Конструктор с параметрами x, y, в котором иницилизируем массив диапазона
    public Set2(int x, int y) {
        _range = new Range(x, y);
        _array = createRangeArray(_range);
    }


    //Получает общий диапазон
    // Создает диапазон с максимальной левой и максимальной правой границы
    private Range getUniteRange(Range first, Range second) {
        int x = first.start < second.start ? first.start : second.start;
        int y = first.end > second.end ? first.end : second.end;
        return new Range(x, y);
    }

    // Создает массив из диапазона
    private int[] createRangeArray(Range range) {
        int len;
        // Если диапазон положительный
        if (range.start >= 0) {
            System.out.println("Диапазон положительный");
            int add = range.start % 32 >= range.end % 32 ? 1 : 0;
            len = (range.end - range.start) / 32 + 1 + add;
        }
        // Если диапазон отрицательный
        else if (range.end < 0) {
            System.out.println("Диапазон отрицательный");
            int add = (Math.abs(range.start) - 1) % 32 <= (Math.abs(range.end) - 1) % 32 ? 1 : 0;
            len = (range.end - range.start) / 32 + 1 + add;
        }
        // Если диапазон двусторонний
        else {
            System.out.println("Диапазон двусторонний");
            len = -(range.start + 1) / 32 + range.end / 32 + 2;
        }
        System.out.println("Дина: " + len);
        return new int[len];
    }

    private Position findInArray(int value) {
        if (value < 0) {;
            return new Position(-(_range.start - value) / 32, 31 - ((Math.abs(value) - 1) % 32));
        } else {
            var add = _range.start < 0 ? (-(_range.start + 1)) / 32 + 1 : 0;
            return new Position(value / 32 + add, value % 32);
        }
    }

    private void copySet(Set2 from, Set2 to) {
        Position startPosition = to.findInArray(from._range.start);
        System.out.println("Начинаем с " + startPosition.index);
        for (int i = 0; i < from._array.length; i++) {
            to._array[startPosition.index + i] = from._array[i];
        }
    }

    private boolean haveCommonElements(Set2 Set21, Set2 Set22) {
        if (Set21._range.end < Set22._range.start || Set22._range.end < Set21._range.start) return false;
        Range unionRange = getUniteRange(Set21._range, Set22._range);
        int bitOffSet21 = (Set21._range.start - unionRange.start) % 32;
        int bitOffSet22 = (Set22._range.start - unionRange.start) % 32;
        return false;
    }


    // Возвращает множество в которым есть все неповторяющиеся элементы из двух множеств
    public Set2 union(Set2 set) {
        if (set == this) return this;
        Range unionRange = getUniteRange(_range, set._range);
        Set2 newSet2 = new Set2(unionRange.start, unionRange.end);
        newSet2.printBinary();
        copySet(this, newSet2);
        newSet2.printBinary();
        Position startPosition = newSet2.findInArray(set._range.start);
        for (int i = 0; i < set._array.length; i++) {
            newSet2._array[startPosition.index + i] |= set._array[i];
        }

        return newSet2;
    }


    // Возвращает множество которое содержит общие элементы из двух множеств
    public Set2 intersection(Set2 Set2) {
        if (Set2 == this) return this;
        Range unionRange = getUniteRange(_range, Set2._range);
        Set2 newSet2 = new Set2(unionRange.start, unionRange.end);
        if (_range.end < Set2._range.start) return newSet2;


        copySet(this, newSet2);

        Position startPosition = newSet2.findInArray(Set2._range.start);
        System.out.println(startPosition.index);
        for (int i = 0; i < startPosition.index; i++) {
            newSet2._array[i] = 0;
        }
        for (int i = startPosition.index + Set2._array.length - 1; i < _array.length; i++) {
            newSet2._array[i] = 0;
        }

        if (startPosition.bit == 0) {
            for (int i = 0; i < Set2._array.length; i++) {
                newSet2._array[startPosition.index + i] &= Set2._array[i];
            }
        } else {
            int previous = 0;
            for (int i = 0; i < Set2._array.length; i++) {
                newSet2._array[startPosition.index + i] &= (Set2._array[i] >> startPosition.bit) | previous;
                previous = Set2._array[i] << (32 - startPosition.bit);
                System.out.println();
            }
        }


        return newSet2;
    }

    // Возвращает множество которое содержит элементы которых нет в исходном множестве
    public Set2 difference(Set2 Set2) {
        if (Set2 == this) return this;
        Range unionRange = getUniteRange(_range, Set2._range);
        Set2 newSet2 = new Set2(unionRange.start, unionRange.end);

        copySet(this, newSet2);

        Position startPosition2 = newSet2.findInArray(Set2._range.start);

        if (startPosition2.bit == 0) {
            for (int i = 0; i < Set2._array.length; i++) {
                newSet2._array[startPosition2.index + i] = (newSet2._array[startPosition2.index + i] & ~Set2._array[i]);
            }
        } else {
            int previous = 0;
            for (int i = 0; i < Set2._array.length; i++) {
                newSet2._array[startPosition2.index + i] = (newSet2._array[startPosition2.index + i] & ~((Set2._array[i] >> startPosition2.bit) | previous));
                previous = Set2._array[i] << (32 - startPosition2.bit);
                System.out.println();
            }
        }


        return newSet2;
    }

    // Возвращает множество которое состоит из двух множеств, может быть выполнены если множество не имеют общих элементов
    public Set2 merge(Set2 Set2) {
        Range unionRange = getUniteRange(_range, Set2._range);
        Set2 newSet2 = new Set2(unionRange.start, unionRange.end);

        copySet(this, newSet2);

        Position startPosition = newSet2.findInArray(Set2._range.start);

        if (startPosition.bit == 0) {
            for (int i = 0; i < Set2._array.length; i++) {
                newSet2._array[startPosition.index + i] |= Set2._array[i];
            }
        } else {
            int previous = 0;
            for (int i = 0; i < Set2._array.length; i++) {
                newSet2._array[startPosition.index + i] |= (Set2._array[i] >> startPosition.bit) | previous;
                previous = Set2._array[i] << (32 - startPosition.bit);
                System.out.println();
            }
        }


        return newSet2;
    }

    // Проверяет содержит ли это множество значение
    public boolean member(int x) {
        return isMember(x);
    }


    // Возвращает множество в котором найден элемент
    public Set2 find(Set2 a, int x) {
        if (isMember(x)) return this;
        else if (a.isMember(x)) return a;
        else return null;
    }

    private boolean isMember(int x) {
        if (outRange(x)) return false;
        var position = findInArray(x);
        return (_array[position.index] & (1 << 31 - position.bit)) != 0;
    }

    // Добавляет значение в множество, если его там нет и оно попадает в диапазон множества
    public void insert(int x) {
        var position = findInArray(x);
        System.out.println("Вставляем " + x);
        _array[position.index] |= 1 << 31 - position.bit;
    }

    // Удаляет значение из множества, если оно там есть, если нет ничего не делать
    public void delete(int x) {
        if (outRange(x)) return;
        var position = findInArray(x);
        _array[position.index] &= ~(1 << 31 - position.bit);
    }

    private boolean outRange(int x) {
        return x < _range.start || x > _range.end;
    }

    // Присваивает новое множество
    public void assign(Set2 Set2) {
        _range = Set2._range;
        _array = Set2._array;
    }

    // Возвращает минимальный элемент
    public int min() {
        for (int i = 0; i < _array.length; i++) {
            if (_array[i] != 0) {
                int number = _array[i];
                int res = 0;
                while (number != 1) {
                    number >>= 1;
                    res++;
                }
                res = 32 - res;
                return _range.start + (32 * i) + res - 1;
            }
        }
        return 0;
    }

    // Возвращает максимальный элемент
    public int max() {
        for (int i = _array.length - 1; i >= 0; i--) {
            if (i != 0) {
                int number = _array[i];
                int res = 0;
                while (number % 2 == 0) {
                    number >>= 1;
                    res++;
                }
                res = 32 - res;
                return _range.start + (32 * i) + res - 1;
            }
        }
        return 0;
    }

    // Возвращает true если множества равны
    public boolean equal(Set2 Set2) {
        Range r = getUniteRange(_range, Set2._range);
        int bitOffSet21 = (_range.start - r.start) % 32;
        int bitOffSet22 = (Set2._range.start - r.start) % 32;

        return false;
    }


    public void makeNull() {
        for (int i = 0; i < _array.length; i++) {
            _array[i] = 0;
        }
    }

    //Вывод

    public void printBinary() {
        System.out.println();
        for (int i = 0; i < _array.length; i++) {
            System.out.println(toBinaryString(_array[i]));
        }
    }

    public void printSet() {
        System.out.println();
        for (int i = _range.start; i <= _range.end; i++) {
            if (isMember(i)) System.out.print(i + " ");
        }

    }

    public String toBinaryString(int value) {
        var result = new StringBuilder();
        for (int i = 0; i < 32; ++i) {
            result.append((value & 1) == 1 ? '1' : '0');
            value >>>= 1;
        }
        return result.reverse().toString();
    }


}

