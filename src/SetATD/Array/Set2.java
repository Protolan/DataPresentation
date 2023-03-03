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

    // Конструктор с диапазоном и длиной массива, нужен что бы не вычиислять количество элементов массива
    private Set2(Range range, int arrayLength) {
        _range = range;
        _array = new int[arrayLength];
    }


    //Получает общий диапазон
    // Создает диапазон с максимальной левой и максимальной правой границы
    private Range getUniteRange(Range first, Range second) {
        int x = Math.min(first.start, second.start);
        int y = Math.max(first.end, second.end);
        return new Range(x, y);
    }

    //Проверяет пересекаются ли диапазоны
    private boolean rangeNotInter(Range first, Range second) {
        return first.end < second.start || second.end < first.start;
    }

    // Создает массив из диапазона
    private int[] createRangeArray(Range range) {
        int len;
        // Если диапазон положительный
        if (range.start >= 0) {
            len = range.end / 32 - range.start / 32 + 1;
        }
        // Если диапазон отрицательный
        else if (range.end < 0) {
            len = (range.end + 1) / 32 - (range.start + 1) / 32 + 1;
        }
        // Если диапазон двусторонний
        else {
            len = range.end / 32 - (range.start + 1) / 32 + 2;
        }
        return new int[len];
    }

    private Position findInArray(int value) {
        if (value < 0) {
            return new Position((value + 1) / 32 - (_range.start + 1) / 32, 31 - ((-value- 1) % 32));
        } else {
            if(_range.start < 0) {
                return new Position(value / 32 - (_range.start + 1) / 32 + 1, value % 32);
            }
            else {
                return new Position(value / 32 - _range.start / 32, value % 32);
            }
        }
    }

    private int findIndex(int value) {
        if (value < 0) {
            return (value + 1) / 32 - (_range.start + 1) / 32;
        } else {
            if(_range.start < 0) {
                return value / 32 - (_range.start + 1) / 32 + 1;
            }
            else {
                return value / 32 - _range.start / 32;
            }
        }
    }


    // Возвращает множество в которым есть все неповторяющиеся элементы из двух множеств
    public Set2 union(Set2 set) {
        if (set == this) return this;
        System.out.println("Union Start");
        Range unionRange = getUniteRange(_range, set._range);
        Set2 unionSet = new Set2(unionRange.start, unionRange.end);
        int startIndex1 = unionSet.findIndex(_range.start);
        for (int i = 0; i < _array.length; i++) {
            unionSet._array[startIndex1 + i] = _array[i];
        }
        printBinary();
        unionSet.printBinary();
        int startIndex2 = unionSet.findIndex(set._range.start);
        for (int i = 0; i < set._array.length; i++) {
            unionSet._array[startIndex2 + i] |= set._array[i];
        }
        set.printBinary();
        unionSet.printBinary();
        System.out.println("Union End");
        return unionSet;
    }


    // Возвращает множество которое содержит общие элементы из двух множеств
    public Set2 intersection(Set2 set) {
        // Если этот же сет возвращаем его
        if (set == this) return this;
        // Если диапазоны не пересекаются то возвращаем пустое множество с минимальным диапазоном
        if (rangeNotInter(_range, set._range)) {
            int len = _range.start - _range.end;
            int len2 = set._range.start - set._range.end;
            if (len < len2)
                return new Set2(new Range(_range.start, _range.end), _array.length);
            else
                return new Set2(new Range(set._range.start, set._range.end), set._array.length);
        }
        // Находи интервал пересечения
        Range interRange = new Range(Math.max(_range.start, set._range.start), Math.min(_range.end, set._range.end));
        // Создаем новое множество с этим диапазонм
        Set2 interSet = new Set2(interRange.start, interRange.end);


        int startIndex1 = findIndex(interRange.start);
        int startIndex2 = set.findIndex(interRange.start);


        for (int i = 0; i < interSet._array.length; i++) {
            interSet._array[0] = _array[startIndex1 + i] & set._array[startIndex2 + i];
        }


        return interSet;
    }

    // Возвращает множество которое содержит элементы которых нет в исходном множестве
    public Set2 difference(Set2 set) {
        // Если множества не пересекаются, то возвращаем это же множество
        if (rangeNotInter(_range, set._range)) return this;
        // Если это же множество, то возвращаем пустое множество с этим же диапазоном
        Set2 differenceSet = new Set2(new Range(_range.start, _range.end), _array.length);
        if (set == this) return differenceSet;

        // Копируем значения первого массива
        for (int i = 0; i < _array.length; i++) {
            differenceSet._array[i] = _array[i];
        }

        //Вычисляем индекс массива для выполнение побитовой операции
        int startIndex = findIndex(set._range.start);
        int endIndex = Math.min(set._array.length, _array.length);

        for (int i = 0; i < endIndex; i++) {
            differenceSet._array[startIndex + i] = (_array[startIndex + i] & ~set._array[i]);
        }
        return differenceSet;
    }

    // Возвращает множество которое состоит из двух множеств, может быть выполнены если множество не имеют общих элементов
    public Set2 merge(Set2 set) {
        return union(set);
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
    public void assign(Set2 set) {
        // Создаем новый диапазон и копируем его
        _range = new Range(set._range.start, set._range.end);
        // Создаем новый массив и копируем
        _array = new int[set._array.length];
        for (int i = 0; i < _array.length; i++) {
            set._array[i] = _array[i];
        }
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
    public boolean equal(Set2 set) {
        if (rangeNotInter(_range, set._range)) {
            return false;
        }

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
        int count = 0;
        for (int i = _range.start; i <= _range.end; i++) {
            if (isMember(i)) {
                count++;
                System.out.print(i + " ");
            }
        }
        if (count == 0) System.out.println("Множество пустое");
        System.out.println();
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

