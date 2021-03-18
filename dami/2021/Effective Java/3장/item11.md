# 아이템11 - equals를 재정의하려거든 hashCode도 재정의하라

**```equals```를 재정의한 클래스 모두에서 ```hashCode```도 재정의할 것**

### 이유
같이 재정의하지 않으면 ```hashCode``` 일반 규약을 어기게 되어 해당 클래스의 인스턴스를 ```HashMap```의 키나 ```HashSet```의 원소로 사용할 때 문제 발생

### hashCode 규약
1. ```equals``` 비교에 사용되는 정보가 변경되지 않았다면, 애플리케이션이 실행되는 동안 그 객체의 ```hashCode``` 메소드는 몇 번을 호출해도 항상 같은 값 반환(애플리케이션이 다시 실행되면 바뀌어도 상관없음)
2. ```equals(Object)```가 두 객체를 같다고 판단했다면, 두 객체의 ```hashCode```는 똑같은 값을 반환
3. ```equals(Object)```가 두 객체를 다르다고 판단했어도 두 객체의 ```hashCode```가 서로 다른 값을 반환할 필요는 없지만, 다른 객체에 대해서는 다른 값을 반환해야 해시테이블 성능이 향상

아이템10의 ```equals```를 재정의하면 물리적으로 다른 객체가 논리적으로 같다고 할 수 있지만, ```Object```의 ```hashCode```는 다르다고 판단하기 때문에 규약 2에 어긋나므로 ```hashCode```도 재정의 필요

```JAVA
public final class PhoneNumber {
  private final short areaCode, prefix, lineNum;

  // . . .

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof PhoneNumber)) {
      return false;
    }

    PhoneNumber pn = (PhoneNumber) o;
    return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
  }
}
```
```JAVA
Map<PhoneNumber, String> m = new HashMap<>();
m.put(new PhoneNumber(707, 867, 5309), "dami");

m.get(new PhoneNumber(707, 867, 5309)); // null 반환
```
- 두 객체는 논리적 동치이지만 ```hashCode```가 재정의되어있지 않아 다른 해시코드를 반환하므로 값을 찾는 것이 불가능
- 특히 ```HashMap```은 해시코드가 다른 엔트리끼리는 동치성 비교를 시도조차 하지 않도록 최적화된 상태

모든 객체가 똑같은 해시코드를 반환하게끔 구현하면 모든 객체가 해시테이블의 버킷 하나에 담겨 연결 리스트처럼 동작하기 때문에 해시테이블의 탐색 시간이 O(n)으로 느려지는 문제 발생

### hashCode 재정의 단계 요약
1. ```int``` 변수 ```result```를 선언 후 값 ```c```로 초기화
    - ```c```는 객체의 첫 번째 핵심 필드(equals에 사용하는)를 해시코드화한 것
2. 해당 객체의 나머지 핵심 필드(```f```)도 해시코드화하여 ```result``` 갱신
3. ```result``` 반환

재정의 후 ```equals```와 마찬가지로 테스트 코드를 작성하여 검증하거나 AutoValue 또는 IDE 자동완성 기능으로 코드 생성하는 것이 간편

### 주의할 점
- 다른 필드로부터 계산해낼 수 있는 파생 필드는 해시코드 계산에서 제외 가능
- ```equals```에서 비교하지 않은 필드는 반드시 제외
- 해시 충돌이 더욱 적은 방법을 꼭 써야 한다면 구아바의 ```com.google.common.hash.Hashing```을 참고
- 클래스가 불변이고 해시코드 계산 비용이 크다면, 캐싱 방식을 고려하기
- 성능을 높이겠다는 이유로 핵심 필드를 생략하는 것은 지양
- 해시코드 생성 규칙을 API 사용자에게 자세히 알리는 것은 지양
