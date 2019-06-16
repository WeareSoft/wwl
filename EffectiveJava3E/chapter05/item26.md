# [item 26] Raw 타입은 사용하지 말라

### 사전 조사
- [Type Eraser in Java Doc](https://docs.oracle.com/javase/tutorial/java/generics/erasure.html)
    - 호환성을 위해 존재
    - Compile 시점에 진행하여 RunTime에는 Overhead가 존재하지 않음
    - 그렇기 때문에 Instance of에는 Type 정보가 없어서 예외로 처리.

#### Type Erase - linked list 예제
```java
public class Node<T> {

    private T data;
    private Node<T> next;

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() { return data; }
    // ...
}
```

- the type parameter T is unbounded, the Java compiler replaces it with Object:

```java
public class Node {

    private Object data; // <-
    private Node next;

    public Node(Object data, Node next) {
        this.data = data;
        this.next = next;
    }

    public Object getData() { return data; }
    // ...
}
```

- the generic Node class uses a bounded type parameter:

```java
public class Node<T extends Comparable<T>> { // Bounded Type

    private T data;
    private Node<T> next;

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() { return data; }
    // ...
}
```

- The Java compiler replaces the bounded type parameter T with the first bound class, Comparable:

```java
public class Node {

    private Comparable data; // <-
    private Node next;

    public Node(Comparable data, Node next) {
        this.data = data;
        this.next = next;
    }

    public Comparable getData() { return data; }
    // ...
}
```

## Reference
- <https://docs.oracle.com/javase/tutorial/extra/generics/index.html>
- <https://medium.com/@joongwon/java-java%EC%9D%98-generics-604b562530b3>


### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

