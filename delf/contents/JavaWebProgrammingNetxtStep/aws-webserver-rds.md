# 요구사항
## AWS를 이용한 웹 사이트 구축
### 내용: cloud computing 관련 신문 기사, 기술 정보등을 제공하는 웹사이트
- 첫 화면은 기사 목록이 나타나야 함 
- 기사를 클릭하면 기사를 볼 수 있도록
- 구성은 자유롭게

### Apache Web Server + RDS 를 이용한 구축
- /var/www/html = document root
- index.html = 초기 화면
- 신문 기사, 기술 정보 문서를 DB에 보관 - 자동으로
    - 초기 화면에 기사, 문서를 DB에 등록하는 메뉴가 필요

# 발표 자료 작성 발표
- 자신의 웹 사이트 구조 - 1~2 pages
- 구축 과정 - 3~5 pages
- cloud computing에 대한 뉴스 또는 정보 2개 설명 - 2 pages

# 시연
- 자신의 웹 사이트 접속
- 몇몇 기사, 문서 보여주기

---

# 구성
- 하나의 VPC 내에 두 개의 subnet 생성
    - 웹 호스팅을 담당할 public subnet
    - RDS DB를 담당할 private subnet

# 과정
- VPN 생성
    - 퍼블릿 서브넷, 프라이빗 서브넷 생성
    - RDS DB를 위한 프라이빗 서브넷 하나 더 생성
    - 두개의 프라이빗 서브넷의 라우팅 테이블을 통일
- DB 서브넷 생성
- VPC에는 DB 인스턴스에 대한 액세스를 허용하는 VPC 보안 그룹이 있어야 합니다.

## 참고
- [자습서: Amazon RDS DB 인스턴스에 사용할 Amazon VPC 생성](https://docs.aws.amazon.com/ko_kr/AmazonRDS/latest/UserGuide/CHAP_Tutorials.WebServerDB.CreateVPC.html)
- [시나리오 2: 퍼블릭 서브넷과 프라이빗 서브넷이 있는 VPC(NAT)](https://docs.aws.amazon.com/ko_kr/vpc/latest/userguide/VPC_Scenario2.html)

AWS를 이용한 웹 사이트 구축
내용: cloud computing 관련 신문 기사, 기술 정보등을 제공하는 웹사이트
첫 화면은 기사 목록이 나타나야 함 - 기사를 클릭하면 기사를 볼 수 있도록
구성은 자유롭게
