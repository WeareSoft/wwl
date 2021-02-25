# Null Safety

스프링 5에 추가된 기능

목적 : 컴파일 시점에 최대한 NullPointerException을 방지할 수 있게 함

### @NonNull, @Nullable
```JAVA
@Service
public class EventService {

  @NonNull
  public String createEvent(@NonNull String name) {
    return "hello " + name;
  }
}
```
- 패키지는 ```org.springframework.lang```
- 메소드 위 애노테이션은 리턴값 null 미허용
- 파라미터 옆 애노테이션은 매개값 null 미허용

클라이언트 코드
```JAVA
@Component
public class AppRunner implements ApplicationRunner {

  @Autowired
  EventService eventService;

  @Override
  public void run(ApplicationArguments args) {

  }
}
```
- 문제는 위와 같이 코딩 했을 때 컴파일 에러가 발생하지 않음 => IDE 추가 설정 필요
- 인텔리제이에서 Preference-Compiler-Configure Annotations 설정에 ```Nullable과``` ```NonNull``` 애노테이션 추가 후 재시작하면 워닝으로 표시

이 기능은 Spring Data, Reactor에서 사용 중

### @NonNullApi, @NonNullFields
패키지 레벨에 설정하는 애노테이션

package-info.java 파일 생성 후 해당 패키지 라인 윗줄에 애노테이션 적용 시 해당 패키지 내 모든 자바 파일에 NonNull 설정
