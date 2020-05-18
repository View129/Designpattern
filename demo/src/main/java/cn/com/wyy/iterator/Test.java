package cn.com.wyy.iterator;

public class Test {
    //iterator
    public static void main(String[] args) {
        ArrayList a1 = new ArrayList();
        for (int i = 0; i < 15; i++) {
            a1.add(new Cat(i));
        }

        System.out.println(a1.size());

        Iterator it = a1.iterator();
        while (it.hasNext()) {
            Cat cat = (Cat) it.next();
            System.out.println(cat);
        }
    }
}
