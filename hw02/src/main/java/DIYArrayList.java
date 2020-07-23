import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class DIYArrayList<T> implements List<T> {

    private static final int EXTRA_CAPACITY = 5;
    private int size = 0;
    private Object[] elements;

    public <T> DIYArrayList() {
        elements = new Object[size];
    }

    public <T> DIYArrayList(int initSize) {
        if (initSize < 1) {
            throw new IllegalArgumentException("initSize must be > 0");
        }
        elements = new Object[initSize];
        size = initSize;
    }

    public <T> DIYArrayList(Collection<T> collection) {
        if (collection == null) {
            throw new IllegalArgumentException();
        }
        elements = collection.toArray();
        size = collection.size();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a == null) {
            throw new IllegalArgumentException();
        }
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(elements, size, a.getClass());
        } else {
            System.arraycopy(elements, 0, a, 0, size);
            Arrays.fill(a, size, a.length, null);
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        grow();
        elements[size] = t;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        if (c.isEmpty()) {
            return false;
        }
        for (T element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        size = 0;
        elements = new Object[0];
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }
        return (T) elements[index];
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException();
        }
        if (index == size) {
            add(element);
        } else {
            grow();
            System.arraycopy(elements, index + 1, elements, index, elements.length - index);
            elements[index] = element;
            size++;
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }
        T removedElement = (T) elements[index];
        if (size - 1 > index) {
            System.arraycopy(elements, index + 1, elements, index, elements.length - index);
        }
        size -= -1;
        elements[size] = null;
        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DIYListIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new DIYListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private void grow() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length + EXTRA_CAPACITY);
        }
    }

    private class Itr implements Iterator<T> {

        int current = 0;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return size != current;
        }

        @Override
        public T next() {
            if (current >= size) {
                throw new NoSuchElementException();
            }
            lastRet = current++;
            return (T) elements[lastRet];
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            DIYArrayList.this.remove(lastRet);
            current = lastRet;
            lastRet = -1;
        }
    }

    private class DIYListIterator extends Itr implements ListIterator<T> {

        public DIYListIterator(int index) {
            super();
            current = index;
        }

        @Override
        public boolean hasPrevious() {
            return current != 0;
        }

        @Override
        public T previous() {
            if (current - 1 < 0) {
                throw new NoSuchElementException();
            }
            current -= 1;
            return (T) elements[lastRet = current];
        }

        @Override
        public int nextIndex() {
            return current;
        }

        @Override
        public int previousIndex() {
            return current - 1;
        }

        @Override
        public void set(T t) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            elements[lastRet] = t;
        }

        @Override
        public void add(T t) {
            DIYArrayList.this.add(current, t);
            current++;
            lastRet = -1;
        }
    }
}
