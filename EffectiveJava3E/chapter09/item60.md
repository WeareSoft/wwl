# [item 60] 정확한 답이 필요하다면 float와 double은 피하라.
### 사전 조사
#### 기본 자료형 
- float와 double
    - 자바의 실수를 표현하기 위한 자료형
    - [이진 부동소수점](https://ko.wikipedia.org/wiki/%EB%B6%80%EB%8F%99%EC%86%8C%EC%88%98%EC%A0%90#32%EB%B9%84%ED%8A%B8_%EC%BB%B4%ED%93%A8%ED%84%B0%EC%97%90%EC%84%9C%EC%9D%98_%EB%B6%80%EB%8F%99%EC%86%8C%EC%88%98%EC%A0%90_%EB%B0%A9%EC%8B%9D)연산에 사용되며, 넓은 범위의 수를 빠르게 정밀한 **"근사치"**로 계산하도록 설계되었다.
    - 즉, 금융 관련 계산과 같은 정확한 결과가 필요할 때는 사용하지 않도록 주의하자.
    - float과 double의 차이는 표현할 수 있는 실수가 얼마나 정밀한가의 차이 
    - float
        - 32bits
        - 소수점 이하 7자리 
        -  7개의 유효 숫자를 가지며, 
            - -3.4E+38의 근사값 ~ 3.4E+38의 근사값 
    - double
        - 64bits
        - 소수점 이하 15자리
            - 1.7E+/-308(15개의 자릿수)
        -  15개의 유효 숫자를 가지며, 
            - -1.7E+308의 근사값 ~ 1.7E+308의 근사값
- int
    - 32bits
    - -2^31 ~ 2^31-1 (-2,147,483,648 ~ 2,147,483,647)
    - 대략 20억
    - 10자리 수
    - 12! < int 범위 < 13!
- long
    - 64bits
    - -2^63 ~ 2^63-1 (-9,223,372,036,854,775,808 ~ 9,223,372,036,854,775,807)
    - 대략 900경
    - 19자리 수
- BigInteger, BigDecimal
    - 크기 제한 없음(무한대)
    - 일반적인 operator 연산 불가
        - + : add()
        - - : subtract()
        - x : multiply()
        - / : divide()
        - % : mod()
        - == : compareTo()
    - BigInteger
        - 무한한 크기의 정수형 숫자
    - BigDecimal
        - 무한한 크기의 부동소수점 숫자
        - 8가지 반올림 모드가 존재한다. 이를 통해 반올림을 완벽히 제어할 수 있다.

#### 개선 
- float, double -> BigDecimal
    - 단점: 1) 코딩 시 불편, 2) 성능 저하 
    - 사용: 법으로 정해진 반올림을 수행해야 하는 비즈니스 계산에서 편리함 
    - double 대신 BigDecimal을 사용하는 경우, 문자열을 받는 생성자를 사용하라. 
        - double 생성자를 사용하면 정확한 값이 나오지 않으니 String 생성자를 사용하라는 것
        - [https://en.m.wikipedia.org/wiki/Single-precision_floating-point_format](https://en.m.wikipedia.org/wiki/Single-precision_floating-point_format)
- BigDecimal -> int, long
    - 사용: 성능이 중요하고 소수점을 직접 추적할 수 있고 숫자가 너무 크지 않은 경우 
    - 숫자를 아홉 자리 십진수로 표현할 수 있다면 int
    - 숫자를 열여덟 자리 십진수로 표현할 수 있다면 long
    - 열여덟 자리 초과면 BigDecimal

## Reference
- [](http://gdthink.blogspot.com/2006/05/%ED%8E%8C-float%ED%98%95%EA%B3%BC-double%EC%9D%98-%ED%91%9C%ED%98%84-%EB%B2%94%EC%9C%84.html)
- http://sunychoi.github.io/java/2015/04/12/java-bigdecimal.html

---

### 스터디 요약
-

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

