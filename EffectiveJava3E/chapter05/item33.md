# [item 33] 타입 안전 이종 컨테이너를 고려하라

### 사전 조사
#### 타입 안전 이종 컨테이너, `DatabaseRow` 예
- Column<T>
``` java
public class Column<T> {
 
    private final Class<T> type;
 
    public Column(Class<T> type) {
        this.type = type;
    }
 
    public T cast(Object obj) {
        return obj == null ? null : type.cast(obj);
    }
}
```
- DatabaseRow
``` java
import java.util.HashMap;
import java.util.Map;
 
public class DatabaseRow {
 
 
    private Map<Column<?>, Object> columns =
            new HashMap<Column<?>, Object>();
 
    public <T> void putColumn(Column<T> type, T instance) {
        if (type == null)
              throw new NullPointerException("Type is null");
 
        // Achieving runtime type safety with a dynamic cast
        // Same trick can be found in the following implementations
        // checkedSet, checkedList, checkedMap, and so forth.
        columns.put(type, instance.getClass().cast(instance));
 
    }
 
    public <T> T getColumn(Column<T> type) {
 
        return type.cast(columns.get(type));
    }
 
}
```
- DatabaseClient
```java
public class DatabaseClient {
 
    public static void main(String[] args) {
        Column<Integer> integerColumn = new Column<Integer>(Integer.class);
        Column<String> stringColumn = new Column<String>(String.class);
 
        DatabaseRow databaseRow = new DatabaseRow();
        databaseRow.putColumn(integerColumn, 3);
        databaseRow.putColumn(stringColumn, "3");
 
        System.out.println("The integer + 1: "+ (databaseRow.getColumn(integerColumn)+1));
        System.out.println("The string: "+ databaseRow.getColumn(stringColumn));
 
    }
}
```

- output
```
The integer: 4
The string: 3
```

#### AnnotationElement
- [AnnotationElement.java](./AnnotationElement.java)

## Reference
- DatabaseRow
  - https://gerardnico.com/code/design_pattern/typesafe_heterogeneous_container
- 슈퍼타입 토큰
  - https://homoefficio.github.io/2016/11/30/클래스-리터럴-타입-토큰-수퍼-타입-토큰/
  - http://gafter.blogspot.com/2006/12/super-type-tokens.html
  - http://wonwoo.ml/index.php/post/1807
- AnnotationType
  - https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/AnnotatedElement.html 

---

#### 클래스 리터럴 타입
- 클래스 리터럴(Class Literal)
    - String.class, Integer.class 등 
    - String.class의 타입은 Class<String>, Integer.class의 타입은 Class<Integer>다.
    
#### 타입 토큰 
- - 타입 토큰(Type Token) 개념
    - 컴파일 타임 타입 정보와 런타임 타입 정보를 알아내기 위해 메서드들이 주고 받는 class 리터럴 
    - 쉽게 말해 타입을 나타내는 토큰이며, 클래스 리터럴이 타입 토큰으로서 사용된다.
    - `myMethod(Class<?> clazz)` 와 같은 메서드는 타입 토큰을 인자로 받는 메서드이며, method(String.class)로 호출하면, String.class라는 클래스 리터럴을 타입 토큰 파라미터로 myMethod에 전달한다.
- 사용 사례 
    - `MyLittleTelevision mlt = objectMapper.readValue(jsonString, MyLittleTelevision.class);`
    - 범용적으로 말하자면 타입 토큰은 타입 안전성이 필요한 곳에 사용된다.
    - 정해진 특정 타입이 아니라 다양한 타입을 지원해야 하는 Heterogeneous Map이 필요하다면 타입 안전성을 확보하기 위해 다른 방법이 필요하다. 이럴 때 타입 토큰을 이용할 수 있다.

#### 수퍼 타입 토큰 - List<String>.class 타입 구하기
- Class<List<String>>라는 타입은 List<String>.class 같은 클래스 리터럴로 쉽게 구할 수 없다는 점이 다르다. 
    - 하지만 어떻게든 Class<List<String>>라는 타입을 구할 수 있다면, 우리는 타입 안전성을 확보할 수 있다.
    - Class에 들어있는 `public Type getGenericSuperclass()` 이용 
- [ParameterizedType 참고](https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/ParameterizedType.html)
    - [제네릭스 정보 얻기 참고](https://m.blog.naver.com/PostView.nhn?blogId=javaking75&logNo=220727784474&proxyReferer=https%3A%2F%2Fwww.google.com%2F)


## Reference
- [클래스 리터럴, 타입 토큰, 수퍼 타입 토큰](https://homoefficio.github.io/2016/11/30/%ED%81%B4%EB%9E%98%EC%8A%A4-%EB%A6%AC%ED%84%B0%EB%9F%B4-%ED%83%80%EC%9E%85-%ED%86%A0%ED%81%B0-%EC%88%98%ED%8D%BC-%ED%83%80%EC%9E%85-%ED%86%A0%ED%81%B0/)


### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)


