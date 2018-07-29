## 우아한테크캠프

### [배민찬 서비스](https://github.com/doooyeon/baeminchan) 설계 및 구현
* 도메인 설계
  * 회원가입, 로그인
    * Member
    * Role
    * MemberJoinDto
    * MemberLoginDto
  * 카테고리
    * Category
    * Product
  * 프로모션
    * Promotion
  * 베스트 반찬
    * BestCategory
    
* API 설계
  * 회원가입
    * POST /api/members
  * 로그인
    * POST /api/members/login
    
* 프로젝트 환경 설정
  * spring boot
  * spring data jpa
  * mysql
  * lombok
  * slf4j logging

* 회원가입, 로그인
  * 값을 서버에 전송해야 하는 기능은 AJAX로 구현
  * 서버측에서 유효성 체크
  * 비밀번호는 BCrypt 알고리즘을 적용해 암호화
  
* Acceptance Test, Unit Test