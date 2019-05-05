# [item 57] 지역변수의 범위를 최소화하라
### 사전 조사
#### 지역변수 범위에 대한 다른 예제
##### 1. 루프 밖에서 list에서 꺼내서 담을 변수 선언
```java
static int countOfIncluded(List list, String str){
    int count = 0;
    String element;
    for (int i=0,n=list.size(); i<n; i++){
        element = (String) list.get(i);
        if (element.indexOf(str)!= -1 ) count++;             
    }
    return count;
}
```

##### 2. 루프 안에서 list에서 꺼내서 담을 변수 선언
```java
static int countOfIncluded(List list, String str){
    int count = 0;

    for (int i=0,n=list.size(); i<n;  i++){
        String element = (String) list.get(i);
        if (element.indexOf(str)!= -1 ) count++;             
    }
    return count;
}
```

- 많은 사람들이 1번과 같이 코드를 작성하고 있고, 1번이 성능이 좋다는 '믿음'을 가지고 있다.
  - 크기가 10000개인 리스트를 생성해서 100번씩 반복해서 실행시간을 찍어보자.
    ```
    test1: 밖에 선언 2243
    test2: 안에 선언 2153
    test2: 안에 선언 2184
    test1: 밖에 선언 2253
    test1: 밖에 선언 2213
    test2: 안에 선언 2123
    ```
  - 거의 차이가 없거나 오히려 안에 선언한 쪽이 미묘하게 빠르기도 하다.
  - 결국 **"루프 안에서 반복되는 변수 선언을 밖으로 빼는 것은 성능상에 아무런 이점이 없고 소스에서 변수의 유효범위만 늘어나게 한다."** 는 것이다.

#### Recommend reference
- [List와 반복문(loop), 그리고 변수 선언 위치에 대해서](https://hwan1402.tistory.com/48)

---

### 스터디 요약 
- 

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

