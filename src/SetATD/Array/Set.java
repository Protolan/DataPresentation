package SetATD.Array;

public class Set {
    // Нужен для хранения диапазона
    private class Range {
        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int start;
        public int end;
    }

    // Нужен для передачи позиции индекса и бита числа
    private class Position {
        public Position(int index, int bit) {
            this.index = index;
            this.bit = bit;
        }

        public int index;
        public int bit;

    }

    // диапазон, который может содержает отрицательные и положительные целые числа
    // диапазон задается вначале, и меняется если только мы назначем новое множество
    private Range _range;

    // массив целых чисел, где каждый бит числа указывает на наличие числа в множестве
    // количество элементов зависит от диапазона
    private int[] _array;

    // Конструктор с параметрами x, y, в котором иницилизируем массив диапазона
    public Set(int x, int y) {
        _range = new Range(x, y);
        _array = new int[getArrayElementCount(_range)];
    }

    // Конструктор с диапазоном и длиной массива, нужен что бы не вычиислять количество элементов массива
    private Set(Range range, int arrayLength) {
        _range = range;
        _array = new int[arrayLength];
    }


    // Возвращает множество в которым есть все неповторяющиеся элементы из двух множеств
    public Set union(Set set) {
        // Если обьединяем это же множество возвращаем его
        if (set == this) return this;
        // Вычисляем общий диапазон
        int x = Math.min(_range.start, set._range.start);
        int y = Math.max(_range.end, set._range.end);
        // Создаем пустое множество с общим диапазоном
        Set unionSet = new Set(x, y);
        // Находим смещение индексов с первым множеством и копируем значения оттуда значения
        int startIndex1 = unionSet.findIndex(_range.start);
        for (int i = 0; i < _array.length; i++) {
            unionSet._array[startIndex1 + i] = _array[i];
        }
        // Находим смещение индексов с вторым множеством и побитово применяем операцию обьединения
        int startIndex2 = unionSet.findIndex(set._range.start);
        for (int i = 0; i < set._array.length; i++) {
            unionSet._array[startIndex2 + i] |= set._array[i];
        }
        return unionSet;
    }


    // Возвращает множество которое содержит общие элементы из двух множеств
    public Set intersection(Set set) {
        // При пересечение такого же множество возвращаем его
        if (set == this) return this;
        // Если диапазоны не пересекаются то возвращаем пустое множество с минимальным диапазоном
        if (rangeNotInter(_range, set._range)) {
            int len = _range.start - _range.end;
            int len2 = set._range.start - set._range.end;
            if (len < len2)
                return new Set(new Range(_range.start, _range.end), _array.length);
            else
                return new Set(new Range(set._range.start, set._range.end), set._array.length);
        }
        // Находим диапазон пересечения
        Range interRange = new Range(Math.max(_range.start, set._range.start), Math.min(_range.end, set._range.end));
        // Создаем новое множество с этим диапазоном
        Set interSet = new Set(interRange.start, interRange.end);


        // Вычисляем смещения индексов
        int startIndex1 = findIndex(interRange.start);
        int startIndex2 = set.findIndex(interRange.start);

        // Выполняем побитовое умножение
        for (int i = 0; i < interSet._array.length; i++) {
            interSet._array[0] = _array[startIndex1 + i] & set._array[startIndex2 + i];
        }
        return interSet;
    }

    // Возвращает множество которое содержит элементы которых нет в исходном множестве
    public Set difference(Set set) {
        // Если множества не пересекаются, то возвращаем это же множество
        if (rangeNotInter(_range, set._range)) return this;
        // Если это же множество, то возвращаем пустое множество с этим же диапазоном
        Set differenceSet = new Set(new Range(_range.start, _range.end), _array.length);
        if (set == this) return differenceSet;

        // Копируем значения первого массива
        for (int i = 0; i < _array.length; i++) {
            differenceSet._array[i] = _array[i];
        }

        //Вычисляем индекс массива для выполнение побитовой операции
        int startIndex = findIndex(set._range.start);
        int endIndex = Math.min(set._array.length, _array.length);

        // Находим те биты которых нет во втором множестве
        for (int i = 0; i < endIndex; i++) {
            differenceSet._array[startIndex + i] = (_array[startIndex + i] & ~set._array[i]);
        }
        return differenceSet;
    }

    // Возвращает множество которое состоит из двух множеств, может быть выполнены если множество не имеют общих элементов
    public Set merge(Set set) {
        // Мы не можем соединить одно и тоже множество
        if (set == this) return this;
        // Мы не может соединить перескающиеся множества
        if (haveCommonElements(set)) return this;
        // Вычисляем общий диапазон
        int x = Math.min(_range.start, set._range.start);
        int y = Math.max(_range.end, set._range.end);
        // Создаем пустое множество с общим диапазоном
        Set mergeSet = new Set(x, y);
        // Находим смещение индексов с первым множеством и копируем значения оттуда значения
        int startIndex1 = mergeSet.findIndex(_range.start);
        for (int i = 0; i < _array.length; i++) {
            mergeSet._array[startIndex1 + i] = _array[i];
        }
        // Находим смещение индексов с вторым множеством и копируем значения оттуда значения
        int startIndex2 = mergeSet.findIndex(set._range.start);
        for (int i = 0; i < set._array.length; i++) {
            mergeSet._array[startIndex2 + i] = set._array[i];
        }
        return mergeSet;
    }

    // Проверяет содержит ли это множество значение
    public boolean member(int x) {
        return isMember(x);
    }


    // Возвращает множество в котором найден элемент
    public Set find(Set a, int x) {
        if (isMember(x)) return this;
        else if (a.isMember(x)) return a;
        else return null;
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



    // Присваивает новое множество
    public void assign(Set set) {
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
                int bit = 0;
                while (number != 1) {
                    number >>= 1;
                    bit++;
                }
                bit = 32 - bit;
                return getNumberFromPosition(i, bit);
            }
        }
        return 0;
    }

    // Возвращает максимальный элемент
    public int max() {
        for (int i = _array.length - 1; i >= 0; i--) {
            if (_array[i] != 0) {
                int number = _array[i];
                int bit = 0;
                while (number % 2 == 0) {
                    number >>= 1;
                    bit++;
                }
                bit = 32 - bit;
                return getNumberFromPosition(i, bit);
            }
        }
        return 0;
    }

    // Возвращает true если множества равны
    public boolean equal(Set set) {
        if(rangeNotInter(_range, set._range)) return false;
        // Находим диапазон пересечения
        Range interRange = new Range(Math.max(_range.start, set._range.start), Math.min(_range.end, set._range.end));
        // Вычисляем смещения индексов
        int startIndex1 = findIndex(interRange.start);
        int startIndex2 = set.findIndex(interRange.start);
        int rangeLength = getArrayElementCount(interRange);

        // Проверяем на ноль не пересекающиеся промежутки
        for (int i = 0; i < startIndex1; i++) {
            if(_array[i] != 0) return false;
        }
        for (int i = startIndex1 + rangeLength; i < _array.length; i++) {
            if(_array[i] != 0) return false;
        }
        for (int i = 0; i < startIndex2; i++) {
            if(set._array[i] != 0) return false;
        }
        for (int i = startIndex2 + rangeLength; i < set._array.length; i++) {
            if(set._array[i] != 0) return false;
        }

        // Проверяем на неравенство на пересекающихся промежутках
        for (int i = 0; i < rangeLength; i++) {
            if((_array[startIndex1 + i] != set._array[startIndex2 + i])) return false;
        }
        return true;
    }


    public void makeNull() {
        for (int i = 0; i < _array.length; i++) {
            _array[i] = 0;
        }
    }

    // Создает массив из диапазона
    private int getArrayElementCount(Range range) {
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
        return len;
    }

    // Находит индекс элемента массива и бит в котором находится число
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

    // Находит индекс элемента массива в котором находится число
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


    //Проверяет пересекаются ли диапазоны
    private boolean rangeNotInter(Range first, Range second) {
        return first.end < second.start || second.end < first.start;
    }

    // Проверяет не выходит ли число за диапазон
    private boolean outRange(int x) {
        return x < _range.start || x > _range.end;
    }


    // Проверяет есть ли такое число в множестве
    private boolean isMember(int x) {
        if (outRange(x)) return false;
        var position = findInArray(x);
        return (_array[position.index] & (1 << 31 - position.bit)) != 0;
    }

    private boolean haveCommonElements(Set set) {
        if(rangeNotInter(_range, set._range)) return false;
        // Находим диапазон пересечения
        Range interRange = new Range(Math.max(_range.start, set._range.start), Math.min(_range.end, set._range.end));
        // Вычисляем смещения индексов
        int startIndex1 = findIndex(interRange.start);
        int startIndex2 = set.findIndex(interRange.start);

        int rangeLength = getArrayElementCount(interRange);

        // Выполняем побитовое умножение
        for (int i = 0; i < rangeLength; i++) {
            if((_array[startIndex1 + i] & set._array[startIndex2 + i]) != 0) return true;
        }
        return false;
    }

    private int getNumberFromPosition(int index, int bit) {
        int startValue = _range.start < 0 ? -((-_range.start + 31) & ~31) : ((_range.start + 31) & ~31) - 32;
        return startValue  + 32*index + bit - 1;
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
        System.out.print("{");
        for (int i = _range.start; i <= _range.end; i++) {
            if (isMember(i)) {
                System.out.print(i + ", ");
            }
        }
        System.out.print("}");
        System.out.println("\n");
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

