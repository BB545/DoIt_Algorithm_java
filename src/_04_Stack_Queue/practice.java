package _04_Stack_Queue;

import java.util.Scanner;

public class practice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IntStack s = new IntStack(64);

        // Q1. IntStack에 있는 메서드 모두 사용하는 프로그램 구현
        while (true) {
            System.out.println("현재 데이터 수: " + s.size() +" / " + s.capacity());
            System.out.println("(1) 푸시  (2) 팝  (3) 피크  (4) 덤프  (0) 종료 ");
            System.out.println("(5) 클리어 (6) 용량 (7) 사이즈 (8) empty (9) full");
            int menu = sc.nextInt();

            if (menu == 0) return;
            switch (menu) {
                case 1:
                    System.out.println("데이터: ");
                    int x = sc.nextInt();
                    try {
                        s.push(x);
                    } catch (IntStack.OverflowIntStackException e) {
                        System.out.println("스택이 가득 찼습니다.");
                    }
                    break;
                case 2:
                    try {
                        x = s.pop();
                        System.out.println("팝한 데이터는 " + x + "입니다.");
                    } catch (IntStack.EmptyIntStackException e) {
                        System.out.println("스택이 비어 있습니다.");
                    }
                    break;
                case 3:
                    try {
                        x = s.peek();
                        System.out.println("피크한 데이터는 " + x + "입니다.");
                    } catch (IntStack.EmptyIntStackException e) {
                        System.out.println("스택이 비어 있습니다.");
                    }
                    break;
                case 4:
                    s.dump();
                    break;
                case 5:
                    s.clear();
                    break;
                case 6:
                    System.out.println("스택의 용량은 " + s.capacity() + "입니다.");
                    break;
                case 7:
                    System.out.println("스택의 사이즈는 " + s.size()+ "입니다.");
                    break;
                case 8:
                    if (s.isEmpty())
                        System.out.println("네, 스택은 비어있습니다.");
                    System.out.println("아니오, 스택은 비어있지 않습니다.");
                    break;
                case 9:
                    if (s.isFull())
                        System.out.println("네, 스택이 꽉 찼습니다.");
                    System.out.println("아니오, 스택이 꽉 차지 않았습니다.");
                    break;
            }
        }

        // Q2. 임의의 데이터 검색하는 메서드 구현
        // - IntQueue에 구현함
    }
}
