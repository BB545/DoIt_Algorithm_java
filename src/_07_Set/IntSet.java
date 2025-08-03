package _07_Set;

public class IntSet {
    private int max; // 집합 최대 크기
    private int num; // 집합 요소 개수
    private int[] set;

    public int capacity() {
        return max;
    }

    public int size() {
        return num;
    }

    public int indexOf(int key) {
        for (int i = 0; i < num; i++) {
            if (set[i] == key) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(int key) {
        return indexOf(key) != -1;
    }

    public boolean add(int x) {
        if (num >= max || contains(x)) {
            return false;
        } else {
            set[num++] = x;
            return  true;
        }
    }

    public boolean remove(int x) {
        int idx = indexOf(x);
        if (num <= 0 || idx == -1) {
            return false;
        } else {
            set[idx] = set[--num];
            return true;
        }
    }
}
