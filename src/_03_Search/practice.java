package _03_Search;

import java.util.Arrays;

public class practice {
    public static void main(String[] args) {
        int[] array = new int[]{1, 3, 4, 0, 8, 6, 5};
        int length = array.length;

        int[] arraySentinel = new int[length + 1];
        for (int i = 0; i < length; i++) {
            arraySentinel[i] = array[i];
        }

        int[] binArray = new int[]{1, 4, 5, 5, 4, 6, 5, 8};
        int binLength = binArray.length;
        Arrays.sort(binArray);

        // Q1. 보초법을 for문으로 구현
        System.out.println(forSentinel(arraySentinel, length, 8));

        // Q2. 선형 검색 스캐닝 과정 상세 출력
        scanSearch(array, length, 9);
        System.out.println();

        // Q3. 요소 갯수 n인 배열 array에서 key와 일치하는 요소의 인덱스를 배열 idx에 순서대로 저장후
        // 일치한 요소 갯수 출력
        System.out.println(idxSize(array, length, 5));

        // Q4. 맨 앞의 요소를 찾는 이진 탐색 알고리즘 구현
        // - 검색 성공 시 해당 위치로부터 앞쪽으로 하나씩 검사 = 여러 요소 일치해도 앞쪽의 일치 인덱스 검색 가능
        // - 배열의 범위 내에서 동일한 값 반복되는 한 앞쪽으로 탐색
        System.out.println(binSearch(binArray, binLength, 5));

        // Java는 이진 검색을 하는 메서드인 Arrays.binarySearch를 표준 라이브러리로 제공
        // - 모든 자료형 배열에서 검색 가능
        // Q5. 기본 자료형 배열에서 binarySearch 메서드로 검색
        if (Arrays.binarySearch(binArray, 4) < 0) {
            System.out.println("배열에 값이 존재하지 않습니다.");
        } else {
            System.out.println("인덱스 " + Arrays.binarySearch(binArray, 4) + "에 존재합니다.");
        }
    }

    static int forSentinel(int[] arr, int len, int key) {
        int i;
        arr[len] = key;

        // 조건: arr[i] != key일 때 계속 진행
        // 본문 없음: ;만 있기 때문에 실제로는 아무 일도 하지 않음
        // 반복 자체로 목적을 달성: 조건이 만족될 때까지 i를 증가시킴
        for (i = 0; arr[i] != key; i++);

        /**
         * 이렇게도 가능
        for (i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return i == len ? -1 : i;
            }
        }
         return -1;
         */

        return i == len ? -1 : i;
    }

    static void scanSearch(int[] arr, int len, int key) {
        System.out.print("   |");
        for (int i = 0; i < len; i++) {
            System.out.printf("   %d", arr[i]);
        }

        System.out.println();
        for (int i = 0; i < 5 * len; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (int i = 0; i < len; i++) {
            System.out.print("   ");
            System.out.printf(String.format("   %%%ds*\n", (i * 4) + 1), "");
            System.out.printf(" %d |", i);
            for (int j = 0; j < len; j++) {
                System.out.printf("   %d", arr[j]);
            }

            if (arr[i] == key) {
                System.out.println();
                System.out.print("결과: " + key + "는 array[" + i + "]에 있습니다.");
                break;
            }
            System.out.println();
        }
        System.out.print("결과: " + key + "가 array 배열 안에 존재하지 않습니다.");
    }

    static int idxSize(int[] arr, int len, int key) {
        int size = 0;
        int[] idx = new int[len];

        for (int i = 0; i < len; i++) {
            if (arr[i] == key) {
                idx[size] = i;
                size++;
            }
        }
        return size;
    }

    static int binSearch(int[] arr, int len, int key) {
        // 중복값 처리 로직 필요
        int start = 0;
        int end = len - 1;

        do {
            int mid = (start + end) / 2;

            if (arr[mid] == key) {
                while (mid != 0 && arr[mid - 1] == arr[mid]) {
                    // mid가 시작점이 아니고, 이전값이 mid값과 동일하다면 mid를 앞으로 이동 (중복값 처리)
                    // 최대한 작은 인덱스 탐색
                    mid = mid - 1;
                }
                return mid;
            } else if (arr[mid] < key) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        } while (start <= end);

        return -1;
    }
}
