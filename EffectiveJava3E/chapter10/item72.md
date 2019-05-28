# [item 72. 표준 예외를 사용하라]
### 정리
- API가 다른 사람이 익히고 사용하고 이를 사용한 코드도 읽기 쉬워진다.
- 예외 클래스가 적을수록 메모리 사용량도 줄고 클래스를 적재하는 시간도 적게 걸린다.

#### 대표적으로 재사용되는 예외
- `IllefalArgumentException`: 허용하지 않은 값이 인수로 건네졌을 때
  - `NullpointerException`: 위와 비슷한 상황에서 사용 가능. `null`이 인수로 건네졌을 때
  - `IndexOutOfBoundsException`: 위와 비슷한 상황에서 사용 가능. 어떤 시퀀스의 허용범위를 넘는 값이 인수로 건네졌을 때
- `ConcurrentModificationException`: 단일 스레드에서 사용하려고 설계한 객체를 여러 스레드가 동시에 수정하려 할 때
- `UnsuppertedOperationExcpetion`: 클라이언트가 요청한 동작을 대상 객체가 지원하지 않을 때

#### 기타 재사용 예외
`ArithmeticException`, `NumberFormatException`

#### 주의
`Exception`, `RuntimeException`, `Throwable`, `Error`는 직접 재사용하지 말자.

### 스터디 요약
#### 이슈
이이템 후반부에 이런 내용이 있다.
> 표준 예외를 확장해도 좋다. 다만 예외도 직렬화가 가능하니 되도록 하지 않는게 좋다. (직렬화에는 많은 부담이 따르기 때문)

- 직렬화를 한다함은 클래스내의 필드를 특정 데이터로 변환한다는 의미가 아닌가?
- 그렇다면 이 예외 자체를 필드로 두는 일이 있다는 것인가?
- 그렇다면 그런 상황이 흔할까?

결론을 내지 못한 체 아직 미결된 문제이다.