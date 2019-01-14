## CSS Selector
- HTML의 요소를 tag, id, class, html 태그 속성등을 통해 쉽게 찾아주는 방법

## id, class, tag selector
### Tag
```css
span {
    color : red;
}
```

### id
- Javascript와는 달리 같은 ID에 모두 적용된다.

```css
#spantag {
    color : red;
}

span#spantag {
    color : red;
}
```
### class
```css
.spanclass {
    color : red;
}
```


## 그룹 선택
```css
h1, span, div { color : red}
```


## 하위 요소 선택(공백)
- 모든 자식들에게 적용.
```css
<div id="jisu">
    <div>
        <span> span tag </span> //적용
    </div>
    <span> span tag </span> //적용
</div>

#jisu span { color : red}
```

## 자식 선택(>)
- 바로 아래의 자식만 적용.
```css
<div id="jisu">
    <div>
        <span> span tag </span> //적용
    </div>
    <span> span tag </span> //미적용
</div>

#jisu > span { color : red}
```

## n번째 자식요소를 선택
```css
<div id="jisu">
    <h2>단락</h2>
    <p>1</p>
    <p>2</p>
    <p>3</p>
    <p>4</p>
</div>

#jisu > p:nth-child(2) { color : red}
```

## pseudo-class인 nth-child 와 nth-of-type의 차이점은 무엇일까요?
- nth-child :  내형제 (같은 태그들)남의 형제 (다른 태그들)할것 없이 다 포함해서 순서를 센다.
- nth-of-type :  남의 형제는 아예 세지도 않는다.



## font 색상 변경
- color : red;
- color : rgba(255, 0, 0, 0.5);
- color : #ff0000;   //16진수 표기법으로 가장 많이 사용되는 방법이죠.


## font 사이즈 변경
- font-size : 16px;
- font-size : 1em; // 상대적인 값. 기본 값은 16px // 부모의 기본값이 바뀌면 상대적인 값도 바뀜.


## 배경색
- background-color : #ff0;
- background-image, position, repeat 등의 속성이 있습니다.
- background : #0000ff url(“.../gif”) no-repeat center top; //한 줄로 정의도 가능


## 글씨체/글꼴
- font-family:"Gulim";
- font-family : monospace;


## 엘리먼트가 배치되는 방식
- 엘리먼트를 화면에 배치하는 것을 Layout , Rendering
- Element는 위에서 아래로 순서대로 블럭을 이루며 배치되는 것이 기본
    - 하지만 다양하게 표현 가능해야 하기 때문에 다양한 방식으로 배치하는 전략이 필요
    - display(block, inline, inline-block)
    - position(static, absolute, relative, fixed)
    - float(left, right)

### display:block
- 대부분 블록 형태로 표현
### display:inline
- 우측으로 꽉차면 오른쪽 아래로 이동.

### position
- 상대적/절대적으로 어떤 위치에 엘리먼트를 배치하는 것이 수월합니다.
- static
    - 순서대로 기본값
- absolute
    -기준점에 따라 특별한 위치에 위치
    - 기준점은 무엇인가?
        - 상위 엘리먼트들 중에 static이 아닌 position이 기준점(relative등등)
    - top, left값을 주는게 좋다.
- relative
    - 원래 자신이 위치해야 할 곳을 기준으로 이동합니다.
- fixed
    - 전체 화면 기준 position

## Box Model
- margin, padding, border, outline으로 생성되는 것
- box-shadow속성도 box-modle에 포함지어 설명할 수 있습니다.
- box-sizing
    - content-box : padding값에 따라 증가할 수 있음
    - border-box : 박스 고정(padding값에 상관없이 유지하려고 하나 값이 크면 증가가됨)