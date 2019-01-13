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
