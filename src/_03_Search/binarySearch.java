package _03_Search;

import java.util.Arrays;

public class binarySearch {
    // 이진탐색은 반드시 배열 정렬 후 진행
    public static void main(String[] args) {
        // do-while 문 : 조건을 나중에 검사
        // - 무조건 한번은 실행됨
        // - 그 후 다음 조건을 평가해 반복 여부 결정

        int[] array = new int[]{-1, 3, 4, 0, 8, 10, -3, 5};
        Arrays.sort(array);
        int length = array.length;

        for(int i = 0; i < length; i++) {
            System.out.print(array[i] + " ");
        } // 정렬된 array 출력 확인용
        System.out.println();
        System.out.println(binSearch(array, length, 4));
    }

    static int binSearch(int[] arr, int n, int key) {
        int start = 0;
        int end = n - 1;

        do {
            int mid = (start + end) / 2; // 중앙
            if (arr[mid] == key) {
                return mid; // 검색 성공
            } else if (arr[mid] > key) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        } while (start <= end); // 실행 조건

        return  -1;
    }
}
