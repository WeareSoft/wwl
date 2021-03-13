# 아이템9 - try-finally 보다는 try-with-resources를 사용하라

자바에는 ```close()``` 메소드를 호출해 사용한 자원을 직접 닫아줘야 하는 경우가 있음
- ```InputStream```
- ```OutputStream```
- ```java.sql.Connection```
- 기타 등등

일반적으로 사용한 자원이 제대로 닫혔음을 보장하는 수단으로 ```try-finally```를 사용해왔으나, JAVA 7에서 추가된 ```try-with-resources```를 사용하는 것이 더 좋은 방법

자원을 직접 닫아줘야 하는 클래스들은 대부분 ```AutoCloseable``` 인터페이스를 구현했는데, 이 클래스의 객체들을 사용할 경우 ```try-finally``` 대신 ```try-with-resources``` 구문을 활용하여 코드 개선 가능

<br />

### AutoCloseable
**이 인터페이스를 구현하면 ```try-with-resources```에서 ```close()``` 메소드를 명시적으로 작성하지 않아도 자동으로 자원을 닫아줌**
```JAVA
public interface AutoCloseable {
  void close() throws Exception;
}
```

<br />

### try-finally와 try-with-resources 비교
```JAVA
// try-finally
static String firstLineOfFile(String path) throws IOException {
  BufferedReader br = new BufferedReader(new FileReader(path));

  try {
    return br.readLine();
  } finally {
    br.close();
  }
}
```
- ```finally``` 블록에서도 예외가 발생할 수 있으며, ```try``` 블록의 ```readLine()``` 메소드에서 예외 발생 시 ```finally``` 블록의 ```close()``` 메소드에서도 예외가 발생하기 때문에 결론적으로 예외가 덮어 씌워지고, 근본적인 문제 원인을 알지 못하게 되어 디버깅이 어려워짐

<br />

```JAVA
// try-with-resources
static String firstLineOfFile(String path) throws IOException {
  try (BufferedReader br = new BufferedReader(new FileReader(path))) {
    return br.readLine();
  }
}
```
```JAVA
// try-with-resources (자원 여러 개 사용 시)
static void copy(String src, Strign dst) throws IOException {
  try (InputStream in = new FileInputStream(src);
       OutputStream out = new FileOutputStream(dst)) {
    byte[] buf = new byte[BUFFER_SIZE];
    int n;
    while ((n = in.read(buf)) >= 0) {
      out.write(buf, 0, n);
    }
  }
}
```
- ```try-finally```에 비해 코드 양 감소
- 첫번째 예시 코드를 보면 ```readLine()``` 메소드와 코드에는 드러나지 않은 ```close()``` 메소드 양쪽에서 예외 발생 시 ```close()```에서 발생한 예외는 숨겨지고 ```readLine()```에서 발생한 예외가 기록
