package _07_Set._LinkedHashSet;

public interface Set<E> {
    // 요소가 set에 없는 경우 요소 추가
    boolean add(E e);

    // 요소가 set에 있을 경우 해당 요소 삭제
    boolean remove(Object o);

    // 특정 요소 현재 set에 포함되었는지 여부 반환
    boolean contains(Object o);

    // 지정된 객체가 현재 집합과 같은지 여부 반환
    boolean equals(Object o);

    // 현재 집합 비었는지 여부 반환
    boolean isEmpty();

    // 현재 집합 요소 개수 반환
    int size();

    // 집합 모든 요소 제거
    void clear();
}
