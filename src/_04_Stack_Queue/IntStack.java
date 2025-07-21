package _04_Stack_Queue;

public class IntStack {
    private int max; // 스택 용량
    private int ptr; // 스택 포인터
    private int[] stack; // 스택

    // 내부 클래스 = 중첩 클래스
    // - 외부에서는 IntStack.EmptyIntStackException 로 참조 가능
    // - 스택이 비어 있는데 pop()을 호출하면 EmptyIntStackException이 발생
    // 실행 시 예외 : 스택 비어있음
    public class EmptyIntStackException extends RuntimeException {
        // 기본 생성자
        public EmptyIntStackException() {}
    }

    // 실행 시 예외 : 스택 가득 참
    public class OverflowIntStackException extends RuntimeException {
        public OverflowIntStackException() {}
    }

    // 생성자
    public IntStack(int capacity) {
        ptr = 0;
        max = capacity;
        try {
            stack = new int[max];
        } catch (OutOfMemoryError e) {
            max = 0;
        }
    }

    // 1. 데이터 삽입
    public int push(int x) throws OverflowIntStackException {
        if (ptr == max) {
            throw new OverflowIntStackException();
        }
        return stack[ptr++] = x;
    }

    // 2. 데이터 추출
    public int pop() throws EmptyIntStackException {
        if (ptr == 0) {
            throw new EmptyIntStackException();
        }
        return stack[ptr--];
    }

    // 3. 스택 마지막 요소 반환 - 스택에 영향 x
    public int peek() throws EmptyIntStackException {
        if (ptr == 0) {
            throw new EmptyIntStackException();
        }
        return stack[ptr - 1];
    }

    // 4. 인덱스 반환
    public int indexOf(int key) {
        for (int i = ptr - 1; i >= 0; i--) {
            if (stack[i] == key) {
                return i;
            }
        }
        return -1;
    }

    // 5. 모든 요소 삭제 - 스택 포인터 = 0
    public void clear() {
        ptr = 0;
    }

    // 6. 스택 용량 반환
    public int capacity() {
        return max;
    }

    // 7. 스택 크기 반환
    public int size() {
        return ptr;
    }

    // 8. 스택이 비었는지
    public boolean isEmpty() {
        return ptr <= 0;
    }

    // 9. 스택이 가득 찼는지
    public boolean isFull() {
        return ptr >= max;
    }

    // 10. 스택 모든 요소를 bottom -> top으로 출력
    public void dump() {
        if (ptr <= 0) {
            System.out.println("스택이 비어있습니다.");
        } else {
            for (int i = 0; i < ptr; i++) {
                System.out.println(stack[i]);
            }
        }
    }
}
