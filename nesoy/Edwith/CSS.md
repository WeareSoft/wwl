## CSS 우선순위
- inline > internal > external
- id > class > element
- css specificity
## CSS 상속
- padding과 배치와 같은 속성은 상속되지 않는다.
- box-model이라고 불리는 속성들(width, height, margin, padding, border)과 같이 크기와 배치 관련된 속성들은 하위엘리먼트로 상속이 되지 않습니다.

## 선언방식에 따른 차이
- 같은 선택자(selector)라면 나중에 선언한 것이 반영됩니다.
- 선택자의 표현이 구체적인 것이 있다면 먼저 적용됩니다.
- body > span (O)
- span (X)
