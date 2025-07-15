package _03_Search;

public class linearSearch {
    public static void main(String[] args) {
        int[] array = new int[]{-1, 3, 4, 0, 8, 10, -3, 5};
        int length = array.length;

        int[] arraySentinel = new int[length + 1]; // 배열에 새로운 값 추가하기 위한 새로운 배열 생성
        for (int i = 0; i < length; i++) {
            arraySentinel[i] = array[i]; // 배열 복사
        }

        System.out.println(whileSearch(array, length, 1));
        System.out.println(forSearch(array, 5));
        System.out.println(sentinelMethod(arraySentinel, length, 3));
    }

    // while 문으로 선형검색 구현
    static int whileSearch(int[] arr, int len, int key) {
        int i = 0;

        while(true) {
            if (i == len) {
                return -1;
            } // 검색 실패

            if (arr[i] == key) {
                return i;
            } //검색 성공

            i++;
        }
    }

    // for 문으로 선형검색 구현
    static int forSearch(int[] arr, int key) {
        for(int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return i;
            } // 검색 성공
        }
        return -1; // 검색 실패
    }

    // 보초법
    static int sentinelMethod(int[] arr, int n, int key) {
        int i = 0;
        arr[n] = key; // 보초 추가

        while (true) { // 첫번쨰 종료 조건 필요 X
            if (arr[i] == key) {
                break; // 검색 성공
            }
            i++;
        }

        return i == n ? -1 : i;
    }
}