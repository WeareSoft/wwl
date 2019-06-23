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



### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)


