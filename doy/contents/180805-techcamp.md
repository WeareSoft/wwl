## 우아한테크캠프

### [배민찬 서비스](https://github.com/doooyeon/baeminchan) 설계 및 구현
#### 2단계 - 카테고리
    * 관리자 권한을 가진 사용자가 카테고리를 관리
        * tree 구조로 구현
        * Spring interceptor 이용
        * 특정 url(/admin) 으로 시작하는 페이지는 관리자 권한을 가진 사용자만 접근하도록 구현

#### 3단계 - 프로모션
    * css transition 전환효과
        * opacity: 1;
        * transform: translateX(0);
        * transition: 0.5s;
        * transition-timing-function: ease-in;

    * javascrip dom class 추가/삭제
        * $(".img" + (currentNo)).classList.remove("current-item");
        * $(".img" + (currentNo)).classList.add("prev-item");
        * $(".on-item").classList.toggle("on-item");

    * Spring Profile을 적용한 각 배포 환경별 설정
        * spring.profiles.active 설정을 통해 profile을 지정할 수 있음
            
        * application.properties
            * spring.profiles.active = local

        * application-local.properties
            * spring.jpa.hibernate.ddl-auto = create
            * logging.config = classpath:logback-local.xml
                * 로그레벨 : DEBUG

        * application-prod.properties
            * spring.jpa.hibernate.ddl-auto = validate
            * logging.config = classpath:logback-prod.xml
                * 로그레벨 : INFO

#### 4단계 - 베스트 반찬
    * 

#### Acceptance Test, Unit Test
    *

---

### 네트워크
#### tcp/ip
    * 
#### http
    *