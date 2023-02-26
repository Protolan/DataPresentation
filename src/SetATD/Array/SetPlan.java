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

    private class Position {
        public Position(int index, int bit) {
            this.index = index;
            this.bit = bit;
        }
        public int index;
        public int bit;
    }
    private Range range; // границы
    private int[] _array;// массив, кооторый хранит информацию о доступных числах
    // Конструктор с параметрами x, y, в котором иницилизируем массив диапазона
    public  SetPlan(int x, int y) {
        range = new Range(x, y);
        _array = createRangeArray(range);
    }

    private int index;

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

    private Position findInArray(int value) {
        int byteNumber = value - range.x;
        return new Position(byteNumber / 32, byteNumber % 32);
    }




    // Возвращает множество в которым есть все неповторяющиеся элементы из двух множеств
    public SetPlan union(SetPlan set) {
        if(set == this) return this;
        Range unionRange = getUniteRange(range, set.range);
        SetPlan newSet = new SetPlan(unionRange.x, unionRange.y);

        Position startPosition1 = newSet.findInArray(range.x);

        if(startPosition1.bit == 0) {
            for (int i = 0; i < _array.length; i++) {
                newSet._array[startPosition1.index + i] = _array[i];
            }
        }
        else {
            int previous = 0;
            for (int i = 0; i < _array.length; i++) {
                newSet._array[startPosition1.index + i] = (_array[i] >> startPosition1.bit) | previous;
                previous = _array[i] << (32 - startPosition1.bit);
            }
        }

        Position startPosition2 = newSet.findInArray(set.range.x);

        if(startPosition2.bit == 0) {
            for (int i = 0; i < set._array.length; i++) {
                newSet._array[startPosition2.index + i] |= set._array[i];
            }
        }
        else {
            int previous = 0;
            for (int i = 0; i <  set._array.length; i++) {
                newSet._array[startPosition2.index + i] |= (set._array[i] >> startPosition2.bit) | previous;
                previous = set._array[i] << (32 - startPosition2.bit);
                System.out.println();
            }
        }


        return newSet;
    }

    public void Print() {
        index = 0;
        for (int i = 0; i < _array.length; i++) {
            System.out.println(printNumber(_array[i]));
        }
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
            result.append(" ").append(range.x + index).append(":");
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
            result.append(range.x + index).append(" ");
            index++;
        }
        return result.toString();
    }


    // Возвращает множество которое содержит общие элементы из двух множеств
    public SetPlan intersection(SetPlan set) {
        if(set == this) return this;
        Range unionRange = getUniteRange(range, set.range);
        SetPlan newSet = new SetPlan(unionRange.x, unionRange.y);
        if(range.y < set.range.x) return newSet;


        Position startPosition1 = newSet.findInArray(range.x);

        if(startPosition1.bit == 0) {
            for (int i = 0; i < _array.length; i++) {
                newSet._array[startPosition1.index + i] = _array[i];
            }
        }
        else {
            int previous = 0;
            for (int i = 0; i < _array.length; i++) {
                newSet._array[startPosition1.index + i] = (_array[i] >> startPosition1.bit) | previous;
                previous = _array[i] << (32 - startPosition1.bit);
            }
        }

        Position startPosition2 = newSet.findInArray(set.range.x);
        System.out.println(startPosition2.index);
        for (int i = 0; i < startPosition2.index; i++) {
            newSet._array[i] = 0;
        }
        for (int i = startPosition2.index + set._array.length - 1; i < _array.length; i++) {
            newSet._array[i] = 0;
        }


        if(startPosition2.bit == 0) {
            for (int i = 0; i < set._array.length; i++) {
                newSet._array[startPosition2.index + i] &= set._array[i];
            }
        }
        else {
            int previous = 0;
            for (int i = 0; i <  set._array.length; i++) {
                newSet._array[startPosition2.index + i] &= (set._array[i] >> startPosition2.bit) | previous;
                previous = set._array[i] << (32 - startPosition2.bit);
                System.out.println();
            }
        }



        return newSet;
    }

    // Возвращает множество которое содержит элементы которых нет в исходном множестве
    public SetPlan difference(SetPlan set) {
        if(set == this) return this;
        Range unionRange = getUniteRange(range, set.range);
        SetPlan newSet = new SetPlan(unionRange.x, unionRange.y);

        Position startPosition1 = newSet.findInArray(range.x);

        if(startPosition1.bit == 0) {
            for (int i = 0; i < _array.length; i++) {
                newSet._array[startPosition1.index + i] = _array[i];
            }
        }
        else {
            int previous = 0;
            for (int i = 0; i < _array.length; i++) {
                newSet._array[startPosition1.index + i] = (_array[i] >> startPosition1.bit) | previous;
                previous = _array[i] << (32 - startPosition1.bit);
            }
        }

        Position startPosition2 = newSet.findInArray(set.range.x);

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
        return null;
    }

    // Проверяет содержит ли это множество значение
    public boolean member(int x) {
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
        return x < range.x || x > range.y;
    }

    // Присваивает новое множество
    public void assign(SetPlan set) {

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
                return range.x + (32 * i) + res - 1;
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
                while (number != 1) {
                    number >>= 1;
                    res++;
                }
                return range.x * i + res + 1;
            }
        }
        return 0;
    }

    // Возвращает true если множества равны
    public boolean equal(SetPlan set) {
        return false;
    }

    // Возвращает множество в котором найден элемент
    public SetPlan find(SetPlan a, int x) {
        return null;
    }

    public void makeNull() {
        for (int i = 0; i < _array.length; i++) {
            _array[i] = 0;
        }
    }
}
