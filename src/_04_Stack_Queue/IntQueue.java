package _04_Stack_Queue;

public class IntQueue {
    private int max; // 큐 용량
    private int front; // 첫번째 요소 포인터
    private int rear; // 마지막 요소 포인터
    private int num; // 현재 데이터 수
    private int[] que; // 큐

    public IntQueue(int capacity) {
        num = front = rear = 0;
        max = capacity;
        que = new int[max];
    }

    public class EmptyIntQueueException extends RuntimeException {
        public EmptyIntQueueException() {
        }
    }

    public class OverflowIntQueueException extends RuntimeException {
        public OverflowIntQueueException() {
        }
    }

    // 1. 데이터 삽입
    public int enque(int x) {
        if (num >= max) throw new OverflowIntQueueException();

        que[rear++] = x;
        num++;
        if (rear == max) rear = 0;
        return x;
    }

    // 2. 데이터 삭제
    public int deque() {
        if (num <= 0) throw new EmptyIntQueueException();

        int x = que[front++];
        num--;
        if (front == max) front = 0;
        return x;
    }

    // 3. 맨앞 요소 반환
    public int peek() {
        if (num <= 0) throw new EmptyIntQueueException();
        return que[front];
    }

    // 4. 인덱스 반환
    public int indexOf(int key) {
        for (int i = 0; i < num; i++) {
            // idx는 front부터 시작해야함, ex. 원형큐 - 배열 끝에 도달 시 처음으로 돌아가게함.
            // 단순한 for문의 i = front로 하는 것은 원형큐에선 rear가 front 보다 앞에 있을 수 있으므로 불가
            int idx = (i + front) % max;
            if (que[idx] == key) return idx;
        }
        return -1;
    }

    // 6. 데이터 모두 삭제
    public void clear() {
        num = front = rear = 0;
    }

    // 7. 큐 용량 반환
    public int capacity() {
        return max;
    }

    // 8. 큐에 있는 데이터 갯수 반환
    public int size() {
        return num;
    }

    // 9. 큐가 비었있음
    public boolean isEmpty() {
        return num <= 0;
    }

    // 10. 큐가 가득참
    public boolean isFull() {
        return num >= max;
    }

    // 11. 큐에 있는 모든 데이터 front -> rear 순으로 출력
    public void dump() {
        if (num <= 0) {
            System.out.println("큐가 비어 있습니다.");
        } else {
            for (int i = 0; i < num; i++) {
                System.out.println(que[(i + front) % max]);
            }
        }
    }

    // Q2. 임의의 데이터 검색하는 메서드 구현
    public int search(int x) {
        for (int i = 0; i < num; i++) {
            int idx = (i + front) % max;
            if (que[idx] == x) {
                return i + 1; // 앞에서부터 몇번째에 있는지 위치 반환
            }
        }
        return 0;
    }
}
