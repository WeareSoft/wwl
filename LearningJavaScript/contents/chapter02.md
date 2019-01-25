## Chapter 2. 자바스크립트 개발 도구
- `깃(Git)`: 프로젝트가 커져도 쉽게 관리할 수 있고 다른 개발자와 협력할 수 있게 돕는 버전 컨트롤 도구
- `노드(Node)`: 브라우저 밖에서 자바스크립트를 실행할 수 있게 하는 도구, 노드와 함께 설치되는 `npm`은 이 리스트의 다른 도구를 설치할 때 필요하다.
- `걸프(Gulp)`: 반복적인 개발 작업을 자동화하는 빌드 도구, `그런트(Grunt)`도 널리 쓰인다.
- `바벨(Babel)`: ES6 코드를 ES5 코드로 변환하는 트랜스컴파일러
- `ES린트(ESLint)`: 코드 컨벤션을 사용자가 정의하고 그 컨벤션을 지키도록 하는 프로그램

### 1. ES(ECMAScript) 란?
- 자바스크립트(JavaScript) 란?
  - 우리에게 친숙한 자바스크립트는 웹브라우저(클라이언트)에서 서버에 데이터를 보내기 전, 데이터 유효성 검사 등을 처리하는 스크립트 언어였다.
  - 이 자바스크립트는 1995년 넷스케이프(Netscape)의 Brendan Eich에 의해 개발된 언어이고, Mocha → LiveScript → JavaScript의 네이밍 변천사를 갖고있다.
  - 그리고 우리가 알던 자바스크립트는 **ECMAScript + BOM(Browser Object Model) + DOM(Document Object Model)** 라는 1개의 코어와 2개의 모델로 이루어져있다.
- **ES(ECMAScript) 란?**
  - ES는 **자바스크립트를 이루는 코어(Core)스크립트 언어**로써, 다양한 환경에서 운용될 수 있게 확장성을 갖고 있기때문에 웹 환경으로 국한되어있지 않다.
  - 즉, 위에서 말한 우리가 아는 자바스크립트는 웹브라우저에서 돌아갈 수 있도록 BOM 과 DOM을 함께 사용하는 확장성이다.
- **ES 버전**
  - ES는 다음과 같은 버전 히스토리를 갖고있다.
    - ES3 → ES5 → ES6(ES2015) → ES7(ES2016)
  - **ES3 (1999)**
    - 대중적으로 알고있는 그냥 자바스크립트라고 보면 된다.
    - 함수 단위의 스코프, 호이스팅, 클로저, 프로토타입 등 익히 알고있는 자바스크립트의 기본적인 특징들을 갖고있다.
    - 대부분의 브라우저에서 지원하며, IE8까지 크로스브라우징을 지원하는 환경이라면 ES3을 쓰고 있다고 보면 된다.
  - **ES5 (2009)**
    - ES4는 너무 시대의 흐름을 앞서갔는지 거절되고, 그 후에 점진적인 개선을 목표로 ES5가 나왔다.
    - 아무리 그래도 10년만에 버전업이라니 너무한것 같지만 많은 편리한 기능이 추가되었다.
      - 배열 배열과 관련하여 편리한 메서드들이 다수 생겼다. 
        - `forEach`, `map`, `reduce`, `filter`, `some`, `every`와 같은 순환 메서드들이 생겼다.
        - 이 메서드들은 개발 시 불필요한 중복 코드를 줄여주어서 가독성은 높이고 버그율은 낮추는 효과가 있다.
      - 객체 프로퍼티에 대한 설정을 할 수 있게 되었다.
        - 객체를 생성, 수정, 복사하는 표준 메서드 `Object.Create()`, `Object.defineProperty()`, `Object.freeze()`, `Object.assign()` 등 과 getter, setter 등이 추가되었으며, `Object.keys()` 메서드를 이용하면 `for in` 메서드도 대체할 수 있게 되었다.
      - 문법을 좀 더 깐깐하게 체크하는 모드인 `strict 모드`가 생겼다. 
        - 너무 자유분방하였던 기존 ES를 안전하고, 개발자가 인지할 수 있는 범위 안에서 개발할 수 있도록 사용하기 위해 등장했다.
        - [Strict mode - JavaScript | MDN](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Strict_mode) 에서 자세한 특징을 확인 할 수 있다.
      - `bind()` 메서드
        - this를 강제로 바인딩 시켜주는 메서드이다.
        - 좀 더 명확하게 this 스코프를 지정 할 수 있게 되었다.
  - **ES6 (ES2015)**
    - ES6 Harmony라고도 불린다.
    - ES6에서 다음과 같은 기능이 추가되었다.
      - 호이스팅이 사라진 것 같은 효과
      - 함수 단위 스코프에서 블록 단위 스코프로 변경
      - this를 동적으로 바인딩하지 않는 화살표 함수
      - 모듈화 지원
      - 콜백 지옥에서 구원해줄 `Promise`
      - `Default`, `Rest` 파라미터
      - 해체 할당, `Spread` 연산자
      - 템플릿 리터럴
      - 클래스 이 외에도 추가된점이 너무 많아서 이부분에서 사람들이 진입장벽을 느끼는 것 같다.
      - 브라우저(특히 MS 계열)에서 지원해주지 않는 경우가 많아 바벨(Babel)이라는 트랜스파일러를 써야하는데 이 바벨은 웹브라우저가 아닌 Node.js 위에서 돌아가고, Node.js를 설치하려면 npm을 알아야하고, 또 모듈화를 사용하려면 웹팩(WebPack)같은 모듈 번들러를 알아야하고.. 
      - 이런 다양한 장벽 때문에 사람들이 ES2015를 쉽게 접근하지 못하는 경향이 있는 것 같다.
  - **ES7 (ES2016)**
    - ES2015때처럼 큰 변화는 없었다.
      - `제곱 연산자(**)` 등장
      - `Array.includes` 배열에 해당 요소가 존재하는지 확인하는 메서드 등장
  - **ES8 (ES2017)**
    - ES2017에서는 `Promise` 급의 중대한 변화인 `async`, `await`등이 발표되었다.
      - `async`, `await`
      - 객체의 좀더 심화된 메서드가 등장했다. 
        - `Object.keys()`에 대응되는 메서드인 `Object.values()`, `Object.keys()`와 `Object.values()`를 합쳐 놓은 `Object.entries()`, `Object.getOwnPropertyDescriptor`의 복수 형태인 `Object.getOwnPropertDescriptors()`로써 상속받지 않은 속성들의 설명만 보여준다.
      - 문자열 단순 편의기능이 추가되었다.
        - 문자열 앞부분에 공백을 넣어 자리수를 맞춰주는 `String.padStart()`, 문자열 뒷부분에 공백을 넣어 자리수를 맞춰주는 `String.padEnd()`
      - 매개변수 마지막에 콤마를 붙이는걸 허용

### 2. 빌드 도구

### 3. 트랜스컴파일러

### 4. 린트

---
**Reference**
> - [https://luckydavekim.github.io/web/2018/02/07/what-is-the-ecmascript/](https://luckydavekim.github.io/web/2018/02/07/what-is-the-ecmascript/)