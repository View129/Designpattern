package cn.com.wyy.iterator;

public class ArrayList implements Collection {
    Object[] objects = new Object[10];
    int index = 0;

    public void add(Object o) {
        if (index == objects.length) {
            Object[] newObjects = new Object[objects.length * 2];
            System.arraycopy(objects, 0, newObjects, 0, objects.length);
            objects = newObjects;
        }
        objects[index] = o;
        index++;
    }

    public int size() {
        return index;
    }

    @Override
    public Iterator iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator {
        int itIndex = 0;

        @Override
        public Object next() {
            Object o = objects[itIndex];
            itIndex++;
            return o;
        }

        @Override
        public Boolean hasNext() {
            if (itIndex >= index) {
                return false;
            } else {
                return true;
            }
        }
    }
}
