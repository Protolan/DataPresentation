package SetATD.Array;

public class SetPlan {
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
    public  SetPlan(int x, int y) {
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
        int arrayCount = (range.end - range.start) / 32 + 1;
        return new int[arrayCount];
    }

    private Position findInArray(int value) {
        int byteNumber = value - _range.start;
        return new Position(byteNumber / 32, byteNumber % 32);
    }

    private void copySet(SetPlan from, SetPlan to) {
        Position startPosition = to.findInArray(_range.start);

        if(startPosition.bit == 0) {
            for (int i = 0; i < from._array.length; i++) {
                to._array[startPosition.index + i] = from._array[i];
            }
        }
        else {
            int previous = 0;
            for (int i = 0; i < from._array.length; i++) {
                to._array[startPosition.index + i] = (from._array[i] >> startPosition.bit) | previous;
                previous = from._array[i] << (32 - startPosition.bit);
            }
        }
    }



    // Возвращает множество в которым есть все неповторяющиеся элементы из двух множеств
    public SetPlan union(SetPlan set) {
        if(set == this) return this;
        Range unionRange = getUniteRange(_range, set._range);
        SetPlan newSet = new SetPlan(unionRange.start, unionRange.end);

        copySet(this, newSet);

        Position startPosition = newSet.findInArray(set._range.start);

        if(startPosition.bit == 0) {
            for (int i = 0; i < set._array.length; i++) {
                newSet._array[startPosition.index + i] |= set._array[i];
            }
        }
        else {
            int previous = 0;
            for (int i = 0; i <  set._array.length; i++) {
                newSet._array[startPosition.index + i] |= (set._array[i] >> startPosition.bit) | previous;
                previous = set._array[i] << (32 - startPosition.bit);
                System.out.println();
            }
        }


        return newSet;
    }




    // Возвращает множество которое содержит общие элементы из двух множеств
    public SetPlan intersection(SetPlan set) {
        if(set == this) return this;
        Range unionRange = getUniteRange(_range, set._range);
        SetPlan newSet = new SetPlan(unionRange.start, unionRange.end);
        if(_range.end < set._range.start) return newSet;


        copySet(this, newSet);

        Position startPosition = newSet.findInArray(set._range.start);
        System.out.println(startPosition.index);
        for (int i = 0; i < startPosition.index; i++) {
            newSet._array[i] = 0;
        }
        for (int i = startPosition.index + set._array.length - 1; i < _array.length; i++) {
            newSet._array[i] = 0;
        }

        if(startPosition.bit == 0) {
            for (int i = 0; i < set._array.length; i++) {
                newSet._array[startPosition.index + i] &= set._array[i];
            }
        }
        else {
            int previous = 0;
            for (int i = 0; i <  set._array.length; i++) {
                newSet._array[startPosition.index + i] &= (set._array[i] >> startPosition.bit) | previous;
                previous = set._array[i] << (32 - startPosition.bit);
                System.out.println();
            }
        }



        return newSet;
    }

    // Возвращает множество которое содержит элементы которых нет в исходном множестве
    public SetPlan difference(SetPlan set) {
        if(set == this) return this;
        Range unionRange = getUniteRange(_range, set._range);
        SetPlan newSet = new SetPlan(unionRange.start, unionRange.end);

        copySet(this, newSet);

        Position startPosition2 = newSet.findInArray(set._range.start);

        if(startPosition2.bit == 0) {
            for (int i = 0; i < set._array.length; i++) {
                newSet._array[startPosition2.index + i] = (newSet._array[startPosition2.index + i] & ~set._array[i]);
            }
        }
        else {
            int previous = 0;
            for (int i = 0; i <  set._array.length; i++) {
                newSet._array[startPosition2.index + i] = (newSet._array[startPosition2.index + i] & ~((set._array[i] >> startPosition2.bit) | previous));
                previous = set._array[i] << (32 - startPosition2.bit);
                System.out.println();
            }
        }


        return newSet;
    }

    // Возвращает множество которое состоит из двух множеств, может быть выполнены если множество не имеют общих элементов
    public SetPlan merge(SetPlan set) {
        Range unionRange = getUniteRange(_range, set._range);
        SetPlan newSet = new SetPlan(unionRange.start, unionRange.end);

        copySet(this, newSet);

        Position startPosition = newSet.findInArray(set._range.start);

        if(startPosition.bit == 0) {
            for (int i = 0; i < set._array.length; i++) {
                newSet._array[startPosition.index + i] |= set._array[i];
            }
        }
        else {
            int previous = 0;
            for (int i = 0; i <  set._array.length; i++) {
                newSet._array[startPosition.index + i] |= (set._array[i] >> startPosition.bit) | previous;
                previous = set._array[i] << (32 - startPosition.bit);
                System.out.println();
            }
        }


        return newSet;
    }

    // Проверяет содержит ли это множество значение
    public boolean member(int x) {
        return isMember(x);
    }


    // Возвращает множество в котором найден элемент
    public SetPlan find(SetPlan a, int x) {
        if(isMember(x)) return this;
        else if(a.isMember(x)) return a;
        else return null;
    }

    private boolean isMember(int x) {
        if(outRange(x)) return false;
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
        if(outRange(x)) return;
        var position = findInArray(x);
        _array[position.index] &= ~(1 << 31 - position.bit);
    }
    private boolean outRange(int x) {
        return x < _range.start || x > _range.end;
    }

    // Присваивает новое множество
    public void assign(SetPlan set) {
        _range = set._range;
        _array = set._array;
    }

    // Возвращает минимальный элемент
    public int min() {
        for (int i = 0; i < _array.length; i++) {
            if(_array[i] != 0) {
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
            if(i != 0) {
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
    public boolean equal(SetPlan set) {
        Range r = getUniteRange(_range, set._range);
        int bitOffset1 = (_range.start - r.start) % 32;
        int bitOffset2 = (set._range.start - r.start) % 32;
        
        return false;
    }


    public void makeNull() {
        for (int i = 0; i < _array.length; i++) {
            _array[i] = 0;
        }
    }

//Вывод
    private int index;
    public void print() {
        index = 0;
        for (int i = 0; i < _array.length; i++) {
            System.out.print(printNumber(_array[i]) + " ");
        }
        System.out.println();
    }

    public String toBinaryString(int value) {
        var result = new StringBuilder();
        for(int i = 0; i < 32; ++i) {
            result.append((value & 1) == 1 ? '1' : '0');
            value >>>= 1;
        }
        return result.reverse().toString();
    }
    public String toStringto(int value) {
        var result = new StringBuilder();
        for(int i = 0; i < 32; ++i) {
            result.append((value & 1) == 1 ? '1' : '0');
            value >>>= 1;
        }
        var r =  result.reverse();
        result = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            result.append(" ").append(_range.start + index).append(":");
            result.append(r.charAt(i));
            index++;
        }
        return result.toString();
    }
    public String printNumber(int value) {
        var result = new StringBuilder();
        for(int i = 0; i < 32; ++i) {
            result.append((value & 1) == 1 ? '1' : '0');
            value >>>= 1;
        }
        var r =  result.reverse();
        result = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if(r.charAt(i) == '0') {
                index++;
                continue;
            }
            result.append(_range.start + index).append(" ");
            index++;
        }
        return result.toString();
    }
}
