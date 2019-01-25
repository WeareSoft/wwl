## Chapter 1. 첫 번째 애플리케이션
### 1. 사용할 프로그램
- 이번 장에서 필요한 것은 브라우저와 텍스트 에디터뿐이다.
  - 브라우저: `Chrome`
  - 텍스트 에디터: `Visual Studio Code`

### 2. 주석
- 자바스크립트 주석
  - 인라인 주석과 블록 주석 두 가지가 있다.
    - 인라인 주석
      - 슬래시 두 개(//)로 시작해서 행 끝에서 끝난다.
        ~~~javascript
        console.log('echo') // 콘솔에 'echo'를 출력한다.
        ~~~
    - 블록 주석
      - /* 로 시작해서 */로 끝나며 여러 줄에 걸쳐 쓸 수 있다.
        ~~~javascript
        /*
          이 텍스트는 블록 주석이며 자바스크립트는 주석을 무시한다.
          가독성을 생각해서 들여 썼지만 꼭 들여 써야 하는 건 아니다.
        */
        ~~~
- HTML, CSS 주석
  - 인라인 주석은 없고, 블록 주석만 있다.
    - HTML 블록 주석
      ~~~html
      <!-- 주석 -->
      ~~~~
    - CSS 블록 주석
      ~~~css
      /* 주석 */
      ~~~

### 3. 시작하기
1. `main.js` 파일 만들기
    - `console.log('main.js loaded')` 행을 추가한다.
2. `main.css` 파일 만들기
    - 주석 한 줄만 써서 빈 파일이 되지 않게 만든다.
3. `index.html` 파일 만들기
    ~~~html
    <!doctype html>
    <html>
      <head>
        <link rel="stylesheet" href="main.css">
      </head>
      <body>
        <h1>My first application</h1>
        <p>Welcome to <i>Learning JavaScript, 3rd Edition</i>.</p>
        <script src="main.js"></script>
      </body>
    </html>
    ~~~

- HTML 문서는 크게 헤드와 바디로 나뉜다.
  - `<head>`
    - 브라우저에 표시되는 내용과 관련이 있긴 하지만, 브라우저에 직접 표시되지는 않는 정보가 들어있다.
  - `<body>`
    - 브라우저에 렌더링될 페이지 콘텐츠가 들어있다.
- `<link rel="stylesheet" href="main.css">`
  - 현재 비어있는 CSS 파일을 문서로 불러오는 링크
- `<script src="main.js"></script>`
  - 자바스크립트 파일을 문서로 불러오는 링크
- **CSS는 `<head>`에, JavaScript는 `<body>`최하단에 두는 이유**
  - 페이지 로딩속도를 높이기 위해서다.
  - HTML은 최상단에서부터 코드가 실행되기 때문에 `<head>`가 다 실행되고서야 `<body>`가 실행된다.
  - 즉, `<head>`가 다 불러지지 않으면 사용자 쪽에선 백지 화면만 노출된다.
  - 특히 JavaScript의 용량이 크면 클수록 `<body>` 부분의 실행이 늦어지기 때문에 JavaScript는 `<body>` 하단에 두어 화면이 다 그려진 뒤에 호출하는것이 좋다.
  - 반면, CSS는 화면을 그리는 역할을 하기 때문에 `<head>`에서 불러오는것이 좋다.

### 4. 자바스크립트 콘솔
- 콘솔은 프로그램을 진단할 때 사용하는 텍스트 전용 도구이다.
- 프로그램의 출력 결과를 보는 것 외에도 자바스크립트 코드를 콘솔에 직접 입력할 수 있다.
- 이를 통해 간단한 테스트를 하거나 자바스크립트 기능을 공부할 수 있고, 프로그램을 임시로 수정하는 것도 가능하다.
- 맥용 크롬 콘솔 단축키: `command` + `option` + `i`

### 5. 제이쿼리 맛보기
- 제이쿼리는 거의 어디에서나 쓰이는 라이브러리이며 웹 코드를 작성할 때 거의 항상 가장 먼저 불러온다.
- 바디 마지막, `main.js`를 불러온 행 바로 앞에 제이쿼리 링크를 삽입한다.
  ~~~html
  <script  src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
  ~~~
- 제이쿼리의 장점을 활용하도록 `main.js` 파일 수정
  ~~~javascript
  $(document).ready(function() {
    console.log('main.js loaded')
  })
  ~~~
  - 자바스크립트 코드를 실행하기 전에 브라우저가 HTML을 전부 불러왔는지 확인한다.

---
**Reference**
> - [https://jojoldu.tistory.com/255?category=635883](https://jojoldu.tistory.com/255?category=635883)
