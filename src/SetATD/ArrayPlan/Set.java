package SetATD.ArrayPlan;

// Множество на двоичных векторах
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
        // Создаем пустое множество с общим диапазоном
        // Находим смещение индексов c помощью findIndex со первым множеством и копируем значения оттуда значения
        // Находим смещение индексов  c помощью findIndex со вторым множеством и побитово применяем операцию обьединения
        // unionSet._array[startIndex2 + i] |= set._array[i];
        return null;
    }


    // Возвращает множество которое содержит общие элементы из двух множеств
    public Set intersection(Set set) {
        // При пересечение такого же множество возвращаем его
        if (set == this) return this;
        // Если диапазоны не пересекаются то возвращаем пустое множество с минимальным диапазоном
        // Находим диапазон пересечения
        // Создаем новое множество с этим диапазоном
        // Вычисляем смещение индексов c помощью findIndex
        // Выполняем побитовое умножение, учитывая смещение индексов
        // interSet._array[0] = _array[startIndex1 + i] & set._array[startIndex2 + i];
        return null;
    }

    // Возвращает множество которое содержит элементы которых нет в исходном множестве
    public Set difference(Set set) {
        // Если множества не пересекаются, то возвращаем это же множество
        // Если это же множество, то возвращаем пустое множество с этим же диапазоном
        // Создаем новый массив с таким же диапазоном как исходное множество
        // Копируем значения первого массива в новый массив
        // Вычисляем индекс массива для выполнение побитовой операции
        // Находим те биты которых нет во втором множестве
        // differenceSet._array[startIndex + i] = (_array[startIndex + i] & ~set._array[i]);
        return null;
    }

    // Возвращает множество которое состоит из двух множеств, может быть выполнены если множество не имеют общих элементов
    public Set merge(Set set) {
        // Мы не можем соединить одно и тоже множество
        if (set == this) return this;
        // Мы не может соединить перескающиеся множества
        // Вычисляем общий диапазон
        // Создаем пустое множество с общим диапазоном
        // Находим смещение индексов с первым множеством и копируем значения оттуда значения
        // Находим смещение индексов с вторым множеством и копируем значения оттуда значения

        return null;
    }

    // Проверяет содержит ли это множество значение
    public boolean member(int x) {
        return isMember(x);
    }


    // Возвращает множество в котором найден элемент
    public Set find(Set a, int x) {
        // Используем isMember для проверки каждого отдельного множества
        if (isMember(x)) return this;
        else if (a.isMember(x)) return a;
        else return null;
    }



    // Добавляет значение в множество, если его там нет и оно попадает в диапазон множества
    public void insert(int x) {
        // Используем метод findInArray, чтобы определить индекс и бит числа в массиве
        var position = findInArray(x);
        // Используем побитовую операцию, чтобы заполнить нужной бит
        _array[position.index] |= 1 << 31 - position.bit;
    }

    // Удаляет значение из множества, если оно там есть, если нет ничего не делать
    public void delete(int x) {
        // Если вне диапазон, то неч
        if (outRange(x)) return;
        // Используем метод findInArray, чтобы определить индекс и бит числа в массиве
        var position = findInArray(x);
        // Используем побитовую операцию, чтобы обнулить нужной бит
        _array[position.index] &= ~(1 << 31 - position.bit);
    }



    // Присваивает новое множество
    public void assign(Set set) {
        // Создаем новый диапазон и копируем его
        // Создаем новый массив и копируем
    }

    // Возвращает минимальный элемент
    public int min() {
        // Ищем индекс и бит самого левого единичного бита
        // Использем метод getNumberFromPosition, чтобы вернуть число
        return 0;
    }

    // Возвращает максимальный элемент
    public int max() {
        // Ищем индекс и бит самого правого единичноого бита
        // Использем метод getNumberFromPosition, чтобы вернуть число
        return 0;
    }

    // Возвращает true если множества равны
    public boolean equal(Set set) {
        // Если диапазоны не перескаются, множества не равны
        if(rangeNotInter(_range, set._range)) return false;
        // Находим диапазон пересечения
        // Вычисляем смещения индексов
        // Проверяем на ноль не пересекающиеся промежутки
        // Проверяем на неравенство на пересекающихся промежутках
        return true;
    }


    public void makeNull() {
        // Просто обнуляем массив
        for (int i = 0; i < _array.length; i++) {
            _array[i] = 0;
        }
    }

    // Создает массив из диапазона
    private int getArrayElementCount(Range range) {
        int len;
        // Если диапазон положительный, делим каждую отдельную границу на 32 и + 1
        if (range.start >= 0) {
            len = range.end / 32 - range.start / 32 + 1;
        }
        // Если диапазон отрицательный, делим каждую отдельную границу на 32
        // для каждой + 1 для решения несимметричност + 1
        else if (range.end < 0) {
            len = (range.end + 1) / 32 - (range.start + 1) / 32 + 1;
        }
        // Если диапазон двусторонний, просто делим на 32 диапазон конца и диапазон (начала + 1) + 2
        // + 2 потому что минимальное кол-во элементов это 2 для двустороннего
        else {
            len = range.end / 32 - (range.start + 1) / 32 + 2;
        }
        return len;
    }

    // Находит индекс элемента массива и бит в котором находится число
    private Position findInArray(int value) {
        // Чтобы определить индекс отрицательного числа просто разница начала диапазон и числа, +1 для симметрии
        // И деленное на 32 по отдельности
        // Для отрицательного числа мы должные инвертировать бит,
        // Потому что отрицательное число по модулю это первый бит числа
        if (value < 0) {
            return new Position((value + 1) / 32 - (_range.start + 1) / 32, 31 - ((-value- 1) % 32));
        } else {
            // Если число положительное, то бит
            // Если диапазон двусторонний, тогда нужно учесть отрицательные числа с их симетрии и добавить + 1
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
        // Тоже самое, что и наверху только чисто для индекса
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
        // Сразу false, если вне диапазона
        if (outRange(x)) return false;
        var position = findInArray(x);
        // Испоьзуем побитовую операцию на нужном индексе, чтобы определить есть ли 1 в нужном бите
        return (_array[position.index] & (1 << 31 - position.bit)) != 0;
    }

    // Метод для проверки общих элементов
    public boolean haveCommonElements(Set set) {
        // Если диапазонны не перескаются, то не имеет общих элементов
        if(rangeNotInter(_range, set._range)) return false;
        // Находим диапазон пересечения
        // Вычисляем смещения индексов
        // Выполняем побитовое умножение и проверяем на неравнество нулю
        // if((_array[startIndex1 + i] & set._array[startIndex2 + i]) != 0) return true;
        return false;
    }

    // Метод для определния числа по индексу и биту
    private int getNumberFromPosition(int index, int bit) {
        // Находим число которое находится число откуда начинать просчет, округляя в меньщую сторону
        // Например если -40, то -65. Если 40, то 32
        int startValue = _range.start < 0 ? -((-_range.start + 31) & ~31) : ((_range.start + 31) & ~31) - 32;
        // Используем вычисленное число  + прибавляем номер бита и индекс массива умноженное на 32
        return startValue  + 32*index + bit;
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

