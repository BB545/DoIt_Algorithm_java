package _07_Set._LinkedHashSet;

public class LinkedHashSet<E> implements Set<E> {
    // 최소 기본 용량 - 비트 연산으로 초기화
    // 1 << 4 -> 1의 2진수 = 0001 -> 왼쪽으로 4칸 이동 = 10000 = 16
    private final static int DEFAULT_CAPACITY = 1 << 4;

    // 3/4 이상 채워질 경우 resize하기 위한 임계값
    private static final float LOAD_FACTOR = 0.75f;

    Node<E>[] table;
    private int size;

    private Node<E> head;
    private Node<E> tail;

    public LinkedHashSet() {
        table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
        head = null;
        tail = null;
    }

    private static final int hash(Object key) {
        int hash;
        if (key == null) {
            return 0;
        } else {
            // Math.abs() = 해시코드가 음수일 수도 있으므로 절댓값으로 변환
            // hash >>> 16
            // - 부호 없는 오른쪽 시프트 (Unsigned right shift)
            // - 해시 값의 상위 16비트를 하위 쪽으로 끌어내림
            // - 해시 버킷 크기가 2의 거듭제곱일 때, 인덱스는 보통 하위 비트로 결정되는데, 상위 비트가 버려지는 것을 막기 위해
            return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
        }
    }

    private void linkLastNode(Node<E> o) {
        Node<E> last = tail;
        // tail을 새로운 노드 o가 가리키도록 갱신
        tail = o;

        // if 마지막 노드가 null이면 노드에 저장된 데이터가 없음을 의미
        // 즉, head도 null인 상태 = head도 o가 가리기도록 설정
        if (last == null) {
            head = o;
        } else {
            // 새로운 노드에 요소 추가
            o.prevLink = last;
            last.nextLink = o;
        }
    }

    @Override
    public boolean add(E e) {
        return add(hash(e), e) == null;
    }

    private E add(int hash, E key) {
        // int idx = hash % table.length;
        // 인덱싱은 음수가 되면 안 되므로
        int idx = hash & (table.length - 1);

        Node<E> newNode = new Node<E>(hash, key, null); // 새로운 노드

        if (table[idx] == null) {
            table[idx] = newNode;
        } else {
            Node<E> temp = table[idx];
            Node<E> prev = null;

            // 해당 인덱스 마지막 체인 노드까지 탐색
            while (temp != null) {
                // 동일 객체면 저장 X, key 반환
                if ((temp.hash == hash) && (temp.key == key || temp.key.equals(key))) {
                    return key;
                }
                prev = temp;
                temp = temp.next;
            }

            prev.next = newNode;
        }
        size++;

        // table에 저장 끝나면 해당 노드 연결
        linkLastNode(newNode);

        // 데이터의 개수가 현재 table 용적의 75%을 넘어가는 경우 용적을 늘려준다.
        if (size >= LOAD_FACTOR * table.length) {
            resize();
        }
        return null;
    }

    private void resize() {
        int newCapacity = table.length * 2;

        final Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];

        for (int i = 0; i < table.length; i++) {
            // 각 인덱스 첫번째 노드 head
            Node<E> value = table[i];

            // 해당 값 없을 경우 다음으로 넘어감
            if (value == null) {
                continue;
            }

            table[i] = null;

            Node<E> nextNode;

            // 현재 인덱스에 연결된 노드 순회
            while(value != null) {
                int idx = value.hash % newCapacity;

                if (newTable[idx] != null) {
                    Node<E> tail = newTable[idx];

                    // 가장 마지막 노드로 감
                    while (tail.next != null) {
                        tail = tail.next;
                    }

                    nextNode = value.next;
                    value.next = null;
                    tail.next = value;
                } else { // 충돌되지 않는다면 (= 빈공간이라면) 해당 노드 추가
                    // 반드시 value가 참조하고 있는 다음 노드와의 연결 끊어줘야함
                    // 안하면 각 인덱스의 tail도 다른 노드를 참조하게 되어 잘못된 참조 발생가능
                    nextNode = value.next;
                    value.next = null;
                    newTable[idx] = value;
                }

                value = nextNode; // 다음 노드로 이동
            }
        }

        table = null;
        table = newTable;
    }

    private void unlinkNode(Node<E> o) {
        Node<E> prevNode = o.prevLink;
        Node<E> nextNode = o.nextLink;

        // if prevNode가 null이면 삭제된 노드가 head였음을 의미 -> nextNode가 head가 됨
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.nextLink = nextNode;
            o.prevLink = null;
        }

        // if nextNode가 null이면 삭제된 노드가 tail이였음을 의미 -> 이전노드가 tail이 됨
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prevLink = prevNode;
            o.nextLink = null;
        }
    }

    @Override
    public boolean remove(Object o) {
        // null이 아니라는 것은 노드가 삭제되었다는 의미
        return remove(hash(o), o) != null;
    }

    private Object remove(int hash, Object key) {

        int idx = hash % table.length;

        Node<E> node = table[idx];
        Node<E> removedNode = null;
        Node<E> prev = null;

        if (node == null) {
            return null;
        }

        while (node != null) {
            // 같은 노드를 찾았다면
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {

                removedNode = node; // 삭제되는 노드를 반환하기 위함

                // 해당노드의 이전 노드가 존재하지 않는 경우 (= table에 첫번째 체인 노드인 경우)
                if (prev == null) {
                    table[idx] = node.next;
                } else { // 그 외엔 이전 노드의 next를 삭제할 노드의 다음노드와 연결
                    prev.next = node.next;
                }

                // table의 체인을 끊었으니 다음으로 순서를 유지하는 link를 끊는다.
                unlinkNode(node);
                node = null;

                size--;
                break;	// table에서 삭제되었으니 탐색 종료
            }
            prev = node;
            node = node.next;
        }

        return removedNode;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int idx = hash(o) % table.length;
        Node<E> temp = table[idx];


        /*
         *  같은 객체 내용이어도 hash값은 다를 수 있음
         *  하지만, 내용이 같은지를 알아보고 싶을 때 쓰는 메소드이기에
         *  hash값은 따로 비교 안해주어도 큰 지장 없음
         *  단, o가 null인지는 확인해야함
         */
        while (temp != null) {
            if (o == temp.key || (o != null && temp.key.equals(o))) {
                return true;
            }
            temp = temp.next;
        }

        return false;
    }


    @Override
    public void clear() {
        if (table != null && size > 0) {
            for (int i = 0; i < table.length; i++) {
                table[i] = null;
            }
            size = 0;
        }
        head = tail = null;	// 마지막에 반드시 head와 tail을 끊어주어야 gc처리 됨
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {

        // 만약 파라미터 객체가 현재 객체와 동일한 객체라면 true
        if(o == this) {
            return true;
        }
        // 만약 o 객체가 LinkedHashSet 계열이 아닌경우 false
        if(!(o instanceof LinkedHashSet)) {
            return false;
        }

        LinkedHashSet<E> oSet;

        /*
         *  Object로부터 LinkedHashSet<E>로 캐스팅 되어야 비교가 가능하기 때문에
         *  만약 캐스팅이 불가능할 경우 ClassCastException 이 발생
         *  이 경우 false를 return 하도록 try-catch문을 사용
         */
        try {

            // LinkedHashSet 타입으로 캐스팅
            oSet = (LinkedHashSet<E>) o;
            // 사이즈가 다르다는 것은 명백히 다른 객체
            if(oSet.size() != size) {
                return false;
            }

            for(int i = 0; i < oSet.table.length; i++) {
                Node<E> oTable = oSet.table[i];

                while(oTable != null) {
                    /*
                     * 서로 Capacity가 다를 수 있기 때문에 index에 연결 된 원소를을
                     * 비교하는 것이 아닌 contains로 원소의 존재 여부를 확인
                     */
                    // contains는 키를 받도록 설계되어 있기 때문에 oTable.key를 넘겨야 함
                    if(!contains(oTable.key)) {
                        return false;
                    }
                    oTable = oTable.next;
                }
            }

        } catch(ClassCastException e) {
            return false;
        }

        return true;
    }
}
