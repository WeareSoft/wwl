## equals 메소드를 오버라이딩 할 때는 보편적 계약을 따르자.
### equals()을 상속받은 그대로 사용하는 것이 더 좋은 경우
- 클래스의 각 인스턴스가 본래부터 유일한 경우
    - Thread
    - 논리적인 비교는 의미가 없는 경우
- 두 인스턴스가 논리적으로 같은지 검사하지 않아도 되는 클래스인 경우
- 수퍼 클래스에서 equals() 메소드를 이미 오버라이딩 했고, 그 메소드를 그대로 사용해도 좋은 경우
- private이나 패키지 전용 클래스라서 이 클래스의 equals() 메소드가 절대 호출되지 않을 경우.


### 그렇다면 언제 equals()를 Override해야 할까?
- 인스턴스가 갖는 값을 비교하여 논리적으로 같은지 판단할 필요가 있는 경우
- 일반적으로 Value Class가 여기에 해당한다.
    - Integer, Long
- Map의 Key, Set의 요소로 객체를 저장하고 사용할 수 있게 하려면 equals()의 Override는 필수다.


### equals()을 사용하기 위한 규칙
- 재귀적이다.(Reflexive)
    - x.equals(x) == true
- 대칭적이다.(Symmetric)
    - y.equals(x) == true, x.equals(y) == true
- 이행적이다.(Transitive)
    - x.equals(y) == true, y.equals(z) == true 이면 x.equals(z)도 반드시 true여야 한다.
- 일관적이다.(Consistent)
    - x.equals(y)를 여러 번 호출하더라도 일관적으로 값을 가져와야 한다.

-----------


## equals 메소드를 오버라이드 할 때는 hashCode 메소드도 항상 같이 오버라이드 하자.
- 동일한 객체들은 같은 해시 코드 값을 가져야 한다는 것이다.
- hashCode()가 규칙을 어기게 되면 Hash Collection(HashMap, HashTable, HashSet)에서 List형태로 데이터를 저장하여 시간복잡도 증가하게 된다.
- 31의 좋은 점은 비트 이동과 뺄셈으로 곱셈을 대체할 수 있어서 성능을 향상시킬 수 있다는 것이다.
    - Java VM들은 이런 부류의 최적화를 자동적으로 수행한다.
- immutable이면서 hashCode 연산 비용이 중요한 클래스라면 해시 값이 매번 계산하는 것보다 내부에 저장하는 것이 좋을 수 있다.
    - 그렇지 않다면 hashCode 메소드가 호출될 때 Lazy initialization을 할 수 있다.

### 주의할 점.
- hashCode()를 생성할 때 성능 향상을 이유로 객체의 중요 부분을 제외시키게 되면 key collision이 발생하게 되어 성능 저하가 발생하게 된다.

### Reference
- <https://nesoy.github.io/articles/2018-06/Java-equals-hashcode>



-----------

## toString 메소드는 항상 오버라이드 하자.
- toString() 메소드를 Override하여 잘 구현해 놓으면 assert, 디버거 출력 등의 여러 경우에서 편하게 사용할 수 있다.
- 객체의 모든 중요한 정보를 반환해야 한다.
    - 하지만 객체가 너무 크거나 많은 경우에 요약 정보만 반환하는 것도 좋은 방법이다.
- 표현 형식의 규정 여부와는 무관하게 의도를 명쾌하게 문서화해야 한다.
    - 이런 정보가 없다면 생산성 저하와 불필요한 일을 가중시키는 것 외에도 에러를 유발하기 쉬우며, 문자열의 형식을 변경할 경우 문제가 생길 수 있다.

-----------