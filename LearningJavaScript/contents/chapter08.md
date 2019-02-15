## Chapter 8. 배열과 배열 처리
### 1. 배열의 기초
- 배열의 기본적인 사항
    - 배열은 객체와 달리 본질에서 순서가 있는 데이터 집합이며, 0으로 시작하는 숫자형 인덱스를 사용한다.
    - 자바스크립트의 배열은 *비균질적*이다. 즉, **한 배열의 요소가 모두 같은 타입일 필요는 없다.** 배열은 다른 배열이나 객체도 포함할 수 있다.
    - 배열 리터럴은 대괄호로 만들고, 배열 요소에 인덱스로 접근할 때도 대괄호를 사용한다.
    - 모든 배열에는 요소가 몇 개 있는지 나타내는 `length` 프로퍼티가 있다.
    - 배열에 배열 길이보다 큰 인덱스를 사용해서 요소를 할당하면 배열은 자동으로 그 인덱스에 맞게 늘어나며, 빈 자리는 `undefiend`로 채워진다.
    - `Array` 생성자를 써서 배열을 만들 수도 있지만 그렇게 해야 하는 경우는 별로 없다.
    ```javascript
    // 배열 리터럴
    const arr1 = [1, 2, 3]                // 숫자로 구성된 배열
    const arr2 = ['one', 2, 'three']      // 비균질적 배열
    const arr3 = [[1, 2, 3], ['one', 2, 'three']] // 배열을 포함한 배열
    const arr4 = [                        // 비균질적 배열
        { name: 'doy', type: 'object', luckyNumbers = [5, 7, 13] },
        [
            { name: 'hee', type: 'object' },
            { name: 'delf', type: 'object' },
        ],
        1,
        function() { return 'arrays can contain functions too' },
        'three',
    ]

    // 배열 요소에 접근하기
    arr1[0]     // 1
    arr1[2]     // 3
    arr3[1]     // ['one', 2, 'three']
    arr4[1][0]  // { name; 'hee', type: 'object' }

    // 배열 길이
    arr1.length     // 3
    arr4.length     // 5
    arr4[1].length  // 2

    // 배열 길이 늘리기
    arr1[4] = 5
    arr1            // [1, 2, 3, undefined, 5]
    arr1.length     // 5

    // 배열의 현재 길이보다 큰 인덱스에 접근하는 것만으로 배열의 길이가 늘어나지는 않는다.
    arr2[10]        // undefined
    arr2.length     // 3

    // Array 생성자(거의 사용하지 않는다)
    const arr5 = new Array()        // 빈 배열
    const arr6 = new Array(1, 2, 3) // [1, 2, 3]
    const arr7 = new Array(2)       // 길이가 2인 배열. 요소는 모두 undefined
    const arr8 = new Array('2')     // ['2']
    ```

**Reference**
> - [MDN web docs - Array](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array)
> - [MDN web docs - Array.length](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/length)

### 2. 배열 요소 조작
- 배열 메서드 중 일부는 배열 '자체를' 수정하며, 다른 일부는 새 배열을 반환한다.
    - 예를 들어 `push`는 배열 자체를 수정하며, `concat`은 새 배열을 반환한다.
    - 메서드 이름에 이런 차이점에 대한 힌트가 전혀 없으므로 프로그래머가 전부 기억해야 한다.

#### 1. 배열의 처음이나 끝에서 요소 하나를 추가하거나 제거하기
- `push`와 `pop`은 각각 배열의 끝에 요소를 추가하거나 제거한다.
- `shift`와 `unshift`는 각각 배열의 처음에 요소를 추가하거나 제거한다.
- `push`와 `unshift`는 새 요소를 추가해서 늘어난 길이를 반환한다.
- `pop`과 `shift`는 제거된 요소를 반환한다.
    ```javascript
    const arr = ['b', 'c', 'd']
    arr.push('e')       // 반환: 4,   arr: ['b', 'c', 'd', 'e']
    arr.pop()           // 반환: 'e', arr: ['b', 'c', 'd']
    arr.unshift('a')    // 반환: 4,   arr: ['a', 'b', 'c', 'd']
    arr.shift()         // 반환: 'a', arr: ['b', 'c', 'd']
    ```

**Reference**
> - [MDN web docs - Array.prototype.push()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/push)
> - [MDN web docs - Array.prototype.pop()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/pop)
> - [MDN web docs - Array.prototype.shift()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/shift)
> - [MDN web docs - Array.prototype.unshift()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/unshift)

#### 2. 배열의 끝에 여러 요소 추가하기
- `concat` 메서드는 배열의 끝에 여러 요소를 추가한 사본을 반환한다.
- `concat`에 배열을 넘기면 이 메서드는 배열을 분해해서 원래 배열에 추가한 사본을 반환한다.
    ```javascript
    const arr = [1, 2, 3]
    arr.concat(4, 5, 6)     // [1, 2, 3, 4, 5, 6]. arr은 바뀌지 않음
    arr.concat([4, 5, 6])   // [1, 2, 3, 4, 5, 6]. arr은 바뀌지 않음
    arr.concat([4, 5], 6)   // [1, 2, 3, 4, 5, 6]. arr은 바뀌지 않음
    arr.concat([4, [5, 6]]) // [1, 2, 3, 4, [5, 6]]. arr은 바뀌지 않음
    ```
- `concat`은 제공받은 배열을 *한 번만* 분해한다. 배열 안에 있는 배열을 다시 분해하지는 않는다.

**Reference**
> - [MDN web docs - Array.prototype.concat()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/concat)

#### 3. 배열 일부 가져오기
- `slice` 메서드는 매개변수 두 개를 받는다.
    - 첫 번째 매개변수는 어디서부터 가져올지를, 두 번째 매개변수는 어디까지 가져올지를(바로 앞 인덱스까지 가져온다) 지정한다.
    - 두 번째 매개변수를 생략하면 배열의 마지막까지 반환한다.
- 이 메서드에서는 음수 인덱스를 쓸 수 있고, 음수 인덱스를 쓰면 배열의 끝에서부터 요소를 센다.
    ```javascript
    const arr = [1, 2, 3, 4, 5]
    arr.slice(3)        // [4, 5]. arr은 바뀌지 않음
    arr.slice(2, 4)     // [3, 4]. arr은 바뀌지 않음
    arr.slice(-2)       // [4, 5]. arr은 바뀌지 않음
    arr.slice(1, -2)    // [2, 3]. arr은 바뀌지 않음
    arr.slice(-2, -1)   // [4]. arr은 바뀌지 않음
    ```

**Reference**
> - [MDN web docs - Array.prototype.slice()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/slice)

#### 4. 임의의 위치에 요소 추가하거나 제거하기
- `splice`는 배열을 자유롭게 수정할 수 있다.
    - 첫 번째 매개변수는 수정을 시작할 인덱스이고, 두 번째 매개변수는 제거할 요소 숫자이다.
    - 아무 요소도 제거하지 않을 떄는 0을 넘긴다.
    - 나머지 매개변수는 배열에 추가될 요소이다.
    ```javascript
    const arr = [1, 5, 7]
    arr.splice(1, 0, 2, 3, 4)   // []. arr: [1, 2, 3, 4, 5, 7]
    arr.splice(5, 0, 6)         // []. arr: [1, 2, 3, 4, 5, 6, 7]
    arr.splice(1, 2)            // [2, 3]. arr: [1, 4, 5, 6, 7]
    arr.splice(2, 1, 'a', 'b')  // [5]. arr: [1, 4, 'a', 'b', 6, 7]
    ```

**Reference**
> - [MDN web docs - Array.prototype.splice()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/splice)

#### 5. 배열 안에서 요소 교체하기
- `copyWithin`은 ES6에서 도입한 새 메서드이다.
- 이 메서드는 배열 요소를 복사해서 다른 위치에 붙여넣고, 기존의 요소를 덮어쓴다.
    - 첫 번째 매개변수는 복사한 요소를 붙여넣을 위치이고, 두 번째 매개변수는 복사를 시작할 위치이고, 세 번째 매개변수는 복사를 끝낼 위치이다(생략할 수 있다).
    - `slice`와 마찬가지로, 음수 인덱스를 사용하면 배열의 끝에서부터 센다.
    ```javascript
    const arr = [1, 2, 3, 4]
    arr.copyWithin(1, 2)        // arr: [1, 3, 4, 4]
    arr.copyWithin(2, 0, 2)     // arr: [1, 3, 1, 3]
    arr.copyWithin(0, -3, -1)   // arr: [3, 1, 1, 3]
    ```

**Reference**
> - [MDN web docs - Array.prototype.copyWithin()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/copyWithin)

#### 6. 특정 값으로 배열 채우기
- `fill`은 ES6에서 도입한 새 메서드이다.
- 이 메서드는 정해진 값으로 배열을 채운다.
- 크기를 지정해서 배열을 생성하는 `Array` 생성자와 잘 어울린다.
- 배열의 일부만 채우려 할 때는 시작 인덱스와 끝 인덱스를 지정하면 된다.
    ```javascript
    const arr = new Array(5).fill(1) // arr이 [1, 1, 1, 1, 1]로 초기화
    arr.fill('a')       // arr: ['a', 'a', 'a', 'a', 'a']
    arr.fill('b', 1)    // arr: ['a', 'b', 'b', 'b', 'b']
    arr.fill('c', 2, 4) // arr: ['a', 'b', 'c', 'c', 'b']
    arr.fill(5.5, -4)   // arr: ['a', 5.5, 5.5, 5.5, 5.5]
    arr.fill(0, -3, -1) // arr: ['a', 5.5, 0, 0, 5.5]
    ```

**Reference**
> - [MDN web docs - Array.prototype.fill()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/fill)

#### 7. 배열 정렬과 역순 정렬
- `reverse`는 이름 그래도 배열 요소의 순서를 반대로 바꾼다.
    ```javascript
    const arr = [1, 2, 3, 4, 5]
    arr.reverse()   // arr: [5, 4, 3, 2, 1]
    ```
- `sort`는 배열 요소의 순서를 정렬한다.
    ```javascript
    const arr = [5, 3, 2, 4, 1]
    arr.sort()      // arr: [1, 2, 3, 4, 5]
    ```
- `sort`는 *정렬 함수*를 받을 수 있다. 예를 들어 일반적으로는 객체가 들어있는 배열은 정렬할 수 없지만, 정렬 함수를 사용하면 가능하다.
    ```javascript
    const arr = [{ name: 'doy' }, { name: 'hee' }, { name: 'delf' }, { name: 'nesoy' }]
    arr.sort()  // arr은 바뀌지 않음  
    arr.sort((a,b) => a.name > b.name)
    // arr은 name 프로퍼티의 알파벳 순으로 정렬됨
    // 
    arr.sort((a, b) => a.name[1] < b.name[1])
    // arr은 name 프로퍼티의 두 번째 글자의 알파벳 역순으로 정렬됨
    // 
    ```

**Reference**
> - [MDN web docs - Array.prototype.reverse()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/reverse)
> - [MDN web docs - Array.prototype.sort()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/sort)

### 3. 배열 검색
#### 1. indexOf
- `indexOf`는 찾고자 하는 것과 정확히 일치(===)하는 첫 번째 요소의 인덱스를 반환한다.
- `indexOf`의 짝인 `lastIndexOf`는 배열의 끝에서부터 검색한다.
- 배열의 일부분만 검색하려면 시작 인덱스를 지정할 수 있다.
- 일치하는 것을 찾지 못하면 -1을 반환한다.
    ```javascript
    const o = { name: 'doy' }
    const arr = [1, 5, 'a', o, true, 5, [1, 2], '9']
    arr.indexOf(5)                  // 1
    arr.lastIndexOf(5)              // 5
    arr.indexOf('a')                // 2
    arr.lastIndexOf('a')            // 2
    arr.indexOf({ name: 'doy' })    // -1
    arr.indexOf(o)                  // 3
    arr.indexOf([1, 2])             // -1
    arr.indexOf('9')                // 7
    arr.indexOf(9)                  // -1
    arr.indexOf('a', 5)             // -1
    arr.indexOf(5, 5)               // 5
    arr.lastIndexOf(5, 4)           // 1
    arr.lastIndexOf(true, 3)        // -1
    ```

**Reference**
> - [MDN web docs - Array.prototype.indexOf()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/indexOf)

#### 2. findIndex
- `findIndex`는 일치하는 것을 찾지 못했을 때 -1을 반환한다는 점에서는 `indexOf`와 비슷하지만, 보조 함수를 써서 검색 조건을 지정할 수 있으므로 `indexOf`보다 더 다양한 상황에서 활용할 수 있다.
- `findIndex`는 검색을 시작할 인덱스를 지정할 수 없고, 뒤에서부터 찾는 `findLastIndex` 같은 짝도 없다.
    ```javascript
    const arr = [{ id: 5, name: 'doy' }, { id: 7, name; 'hee' }]
    arr.findIndex(o => o.id === 5)          // 0
    arr.findIndex(o => o.name === 'hee')    // 1
    arr.findIndex(o => o === 3)             // -1
    arr.findIndex(o => o.id === 17)         // -1
    ```

**Reference**
> - [MDN web docs - Array.prototype.findIndex()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/findIndex)

#### 3. find
- `indexOf`와 `findIndex`는 조건에 맞는 요소의 인덱스를 찾을 때 알맞지만, 조건에 맞는 요소의 인덱스가 아니라 요소 자체를 원할 때는 `find`를 사용한다.
- `find`는 `findIndex`와 마찬가지로 검색 조건을 함수로 전달할 수 있다.
- 조건에 맞는 요소가 없을 때는 `undefined`를 반환한다.
    ```javascript
    const arr = [{ id: 5, name: 'doy' }, { id: 7, name; 'hee' }]
    arr.find(o => o.id === 5)   // 객체 { id: 5, name: 'doy' }
    arr.find(o => o.id === 2)   // undefined
    ```
- `find`와 `findIndex`에 전달하는 함수는 배열의 각 요소를 첫 번째 매개변수로 받고, 현재 요소의 인덱스와 배열 자체도 매개변수로 받는다. 이런 점을 다양하게 응용할 수 있다.
    - 예를 들어, 특정 인덱스보다 뒤에 있는 제곱수를 찾아야 한다고 하자.
        ```javascript
        const arr = [1, 17, 16, 5, 4, 16, 10, 3, 49]
        arr.find((x, i) => i > 2 && Number.isInteger(Math.sqrt(x)))  // 4
        ```
- `find`와 `findIndex`에 전달하는 함수의 `this`도 수정할 수 있다. 이를 이용해서 함수가 객체의 메서드인 것처럼 호출할 수 있다.
    - ID를 조건으로 Person 객체를 검색하는 예제를 보자. 두 방법의 결과는 같다.
        ```javascript
        class Person {
            constructor(name) {
                this.name = name
                this.id = Person.nextId++
            }
        }
        Person.nextId = 0
        const doy = new Person('doy'),
            hee = new Person('hee'),
            delf = new Person('delf'),
            nesoy = new Person('nesoy')
        const arr = [doy, hee, delf, nesoy]

        // 옵션 1: ID를 직접 비교하는 방법
        arr.find(p => p.id === hee.id)  // hee 객체

        // 옵션 2: 'this' 매개변수를 이용하는 방법
        arr.find(function (p) {
            return p.id === this.id
        }, hee)                         // hee 객체
        ```
    - 이렇게 간단한 예제에서는 `find`와 `findIndex`에서 `this` 값을 바꾸는 의미가 별로 없지만, 나중에 이 방법이 더 유용하게 쓰이는 경우를 보게 될 것이다.

**Reference**
> - [MDN web docs - Array.prototype.find()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/find)

#### 4. some과 every
- 간혹 조건을 만족하는 요소의 인덱스도, 요소 자체도 필요 없고, 조건을 만족하는 요소가 있는지 없는지만 알면 충분할 때가 있다.
    - 물론 앞의 함수를 사용하고 -1이나 `null`이 반환되는지 확인해도 되지만, 자바스크립트는 이럴 때 쓰라고 만든 `some`과 `every` 메서드가 있다.
- `some`은 조건에 맞는 요소를 찾으면 즉시 검색을 멈추고 `true`를 반환하며, 조건에 맞는 요소를 찾지 못하면 `false`를 반환한다.
    ```javascript
    const arr = [5, 7, 12, 15, 17]
    arr.some(x => x%2===0)  // true; 12는 짝수
    arr.some(x => Number.isInteger(Math.sqrt(x)))  // false; 제곱수가 없음
    ```
- `every`는 배열의 모든 요소가 조건에 맞아야 `true`를 반환하며 그렇지 않다면 `false`를 반환한다.
    - `every`는 조건에 맞지 않는 요소를 찾아야만 검색을 멈추고 `false`를 반환한다.
    - 조건에 맞지 않는 요소를 찾지 못하면 배열 전체를 검색한다.
    ```javascript
    const arr = [4, 6, 16, 36]
    arr.every(x => x%2===0)  // true; 홀수가 없음
    arr.every(x => Number.isInteger(Math.sqrt(x)))  // false; 6은 제곱수가 아님
    ```
- 이 장에서 소개하는 메서드 중 콜백 함수를 받는 모든 메서드가 그렇듯, `some`과 `every`도 콜백 함수를 호출할 때 `this`로 사용할 값을 두 번째 매개변수로 받을 수 있다.

**Reference**
> - [MDN web docs - Array.prototype.some()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/some)
> - [MDN web docs - Array.prototype.every()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/every)

### 4. map과 filter
- `map`과 `filter`는 배열 메서드 중 가장 유용한 메서드이다.
- `map`은 배열 요소를 *변형*한다.
    - 무엇으로 변형할까? 뭐든 가능하다.
    - 숫자가 들어있는 객체가 있는데, 필요한 건 숫자일 때, 프라미스가 필요할 때 등
    - *일정한 형식의 배열을 다른 형식으로 바꿔야 한다면* `map`을 사용하자.
    - `map`과 `filter`는 모두 사본을 반환하며 원래 배열은 바뀌지 않는다.
    ```javascript
    const cart = [{ name: 'widget', price: 9.95}, {name: 'gadget', price: 22.95 }]
    const names = cart.map(x => x.name)             // ['widget', 'gadget']
    const prices = cart.map(x => x.price)           // [9.95, 22.95]
    const discountPrices = prices.map(x => x*0.8)   // [7.96, 18.36]
    ```
    ```javascript
    const items = ['widget', 'gadget']
    const prices = [9.95, 22.95]
    const cart = items.map((x, i) => ({ name: x, price: prices[i]}))
    // cart: [{ name: 'widget', price: 9.95}, {name: 'gadget', price: 22.95 }]
    ```
- 이 예제는 더 복잡하지만 `map` 함수의 가능성을 잘 표현한다.
- 여기서 요소 자체(x)만 사용하지 않고 인덱스(i)도 사용한 이유는 `items`의 요소와 `prices`의 요소를 인덱스에 따라 겹하기 위해서다.
- 여기서 `map`은 다른 배열에서 정보를 가져와서 문자열로 이루어진 배열을 객체 배열로 변형했다.
- 객체를 괄호로 감싼 이유는, 이렇게 하지 않으면 화살표 표기법에서 객체 리터럴의 중괄호를 블록으로 판단하기 때문이다.
- `filter`는 이름이 암시하듯 배열에서 필요한 것들만 남길 목적으로 만들어졌다.
    - `filter`는 `map`과 마찬가지로 사본을 반환하며 새 배열에는 필요한 요소만 남는다.
    - 어떤 요소를 남길지 판단할 함수를 매개변수로 넘긴다.
    ```javascript
    // 카드 덱을 만든다.
    const cards = []
    for(let suit of ['H', 'C', 'D', 'S']) // 하트, 클로버, 다이아몬드, 스페이드
        for(let value=1; value<=13; value++)
            cards.push({ suit, value })

    // value가 2인 카드
    cards.filter(c => c.value === 2)
    // 다이아몬드
    cards.filter(c => c.suit === 'D')   // length: 13
    // 킹, 퀸, 주니어
    cards.filter(c => c.value > 10)     // length: 12
    // 하트의 킹, 퀸, 주니어
    cards.filter(c => c.value > 10 && c.suit === 'H') // length: 3
    ```
- `map`과 `filter`를 결합하면 정말 다양한 일을 할 수 있다.
    - 예를 들어 앞에서 만든 카드 덱을 짧은 문자열로 표현하고 싶다고 하자.
    - 카드 그림(하트, 클로버, 다이아몬드, 스페이드)에는 유니코드 코드 포인트를 쓰고 에이스와 킹, 퀸, 주니어는 숫자 대신 각각 A, K, Q, J를 쓰겠다.
    ```javascript
    function cardToString(c) {
        const suits = { 'H': '\u2665', 'C': '\u2663', 'D': '\u2666', 'S': '\u2660' }
        const values = { 1: 'A', 11: 'J', 12: 'Q', 13: 'K' }
        // cardToString을 호출할 때마다 매번 값을 만드는 건 그리 효율적인 방법은 아니다.
        for(let i=2; i<=10; i++)
            values[i] = i
        return values[c.value] + suits[c.suit]
    }

    // value가 2인 카드
    cards.filter(c => c.value === 2)
        .map(cardToString)

    // 하트의 킹, 퀸, 주니어
    cards.filter(c => c.value > 10 && c.suit === 'H' )
        .map(cardToString)
    ```

**Reference**
> - [MDN web docs - Array.prototype.map()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/map)
> - [MDN web docs - Array.prototype.filter()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/filter)

### 5. 배열의 마법 reduce
- `map`이 배열의 각 요소를 변형한다면 `reduce`는 *배열 자체*를 변형한다.
- `recude`라는 이름은 이 메서드가 보통 배열을 값 하나로 줄이는 데 쓰이기 때문에 붙었다.
- `recude`가 반환하는 *값 하나*는 객체일 수도 있고, 다른 배열일 수도 있다.
- `reduce`는 `map`과 `filter`를 비롯해 여태까지 설명한 배열 메서드의 동작을 대부분 대신할 수 있다.
- `reduce`는 `map`이나 `filter`와 마찬가지로 콜백 함수를 받는다.
    - 여태까지 설명한 콜백에서 첫 번째 매개변수는 항상 현재 배열 요소였지만, `reduce`가 받는 첫 번째 매개변수는 배열이 줄어드는 대상인 *어큐뮬레이터*이다.
    - 두 번째 매개변수부터는 여태까지 설명한 콜백의 순서대로 현재 배열 요소, 현재 인덱스, 배열 자체이다.
- `reduce`는 초깃값도 옵션으로 받을 수 있다. 배열의 숫자를 더하는 단순한 예제를 보자.
    ```javascript
    const arr = [5, 7, 2, 4]
    const sum = arr.reduce((a, x) => a += x, 0)
    ```
    - `reduce`의 콜백 함수는 매개변수로 누적값 `a`와 현재 배열 요소 `x`를 받았다.
    - 이 예제에서 누적값은 0으로 시작한다.
    - 이 예제의 진행순서는 다음과 같다.
        - 첫 번째 배열 요소 5에서 (익명) 함수를 호출한다. `a`의 초깃값은 0이고 `x`의 값은 5이다. 함수는 `a`와 `x`(5)의 합을 반환한다. 이 값은 다음 단계에서 `a`의 값이 된다.
        - 두 번째 배열 요소 7에서 함수를 호출한다. `a`의 초깃값은 이전 단계에서 전달한 5이고, `x`의 값은 7이다. 함수는 `a`와 `x`의 합 12를 반환한다. 이 값은 다음 단계에서 `a`의 값이 된다.
        - 세 번째 배열 요소 2에서 함수를 호출한다. 이 단계에서 `a`는 12고 `x`는 2이다. 함수는 `a`와 `x`의 합인 14를 반환한다.
        - 네 번째이자 마지막 배열 요소인 4에서 함수를 호출한다. `a`는 14이고 `x`는 4이다. 함수는 `a`와 `x`의 합인 18을 반환하며 이 값은 `reduce`의 값이고 `sum`에 할당되는 값이다.
    - 사실 이 예제에서는 `a`에 값을 할당할 필요는 없다. 화살표 함수에서 명시적인 `return` 문이 필요하지 않았던 것처럼, 함수에서 중요한 건 무엇을 반환하는가 이므로 그냥 `a + x`를 반환해도 됐을 것이다.
    - 하지만 `recude`를 더 잘 활용하려면 누적값이 어떻게 변하는지 생각하는 습관을 기르는 게 좋다.
- 누적값이 `undefined`로 시작한다면 어떻게 될까?
    - 누적값이 제공되지 않으면 `reduce`는 첫 번째 배열 요소를 초깃값으로 보고 두 번째 요소에서부터 함수를 호출한다.
    ```javascript
    const arr = [5, 7, 2, 4]
    const sum = arr.reduce((a, x) => a += x)
    ```
    - 이 예제의 진행순서는 다음과 같다.
        - 두 번째 배열 요소 7에서 함수가 호출된다. `a`의 초깃값은 첫 번째 배열 요소인 5이고, `x`의 값은 7이다. 함수는 `a`와 `x`의 합 12를 반환하고 이 값이 다음 단계에서 `a`의 값이다.
        - 세 번째 배열 요소 2에서 함수를 호출한다. `a`의 초깃값은 12고 `x`의 값은 2이다. 함수는 `a`와 `x`의 합인 14를 반환한다.
        - 네 번째이자 마지막 배열 요소인 4에서 함수를 호출한다. `a`는 14이고 `x`는 4이다. 함수는 `a`와 `x`의 합인 18을 반환하며 이 값은 `reduce`의 값이고 `sum`에 할당되는 값이다.
    - 단계는 하나 줄었지만 결과는 같다. 이 예제를 포함해, 배열의 첫 번째 요소가 그대로 초깃값이 될 수 있을 때는 초깃값을 생략해도 된다.
- `reduce`는 보통 숫자나 문자열 같은 원시 값을 누적값으로 사용하지만, 객체 또한 누적값이 될 수 있고 이를 통해 아주 다양하게 활용할 수 있다.
    - 예를 들어 영단어로 이루어진 배열이 있고 각 단어를 첫 글자에 따라 묶는다고 하면 `reduce`와 함께 객체를 쓸 수 있다.
    ```javascript
    const words = ['Beachball', 'Rodeo', 'Angel', 'Aardvark', 'Xylophone', 'November', 'Chocolate', 'Papaya', 'Uniform', 'Jocker', 'Clover', 'Bali']
    const alphabetical = words.reduce((a, x) => {
        if(!a[x[0]]) a[x[0]] = []
        a[x[0]].push(x)
        return a
    }, {})
    ```
    - 배열의 모든 요소에서 콜백 함수는 전 단계의 결과에 이어 이 단어의 첫 번째 글자인 프로퍼티가 있는지 확인한다.
    - 그런 프로퍼티가 없다면 빈 배열을 추가한다.
        - 즉, 'Beachball'을 만나면 `a.B` 프로퍼티를 확인하는데 그런 프로퍼티는 없으므로 빈 배열을 만든다. 그리고 그 단어를 적절한 프로퍼티에 추가한다.
        - 'Beachball'은 `a.B` 프로퍼티가 없었으므로 빈 배열에 추가되고, 마지막으로 `{B: [Beachball]}`인 `a`를 반환한다.
- `reduce`는 통계에도 사용할 수 있다.
    - 예를 들어 데이터 셋의 평균과 분산을 계산한다고 해보자
    ```javascript
    const data = [3.3, 5, 7.2, 12, 4, 6, 10.3]
    const stats = data.reduce((a, x) => {
        a.N++
        let delta = x - a.mean
        a.mean += delta/a.N
        a.M2 += delta*(x - a.mean)
        return a
    }, { N: 0, mean: 0, M2: 0})
    if(stats.N > 2) {
        stats.variance = stats.M2 / (stats.N - 1)
        stats.stdev = Math.sqrt(stats.variance)
    }
    ```
    - 변수 여러 개, 특히 `mean`과 `M2`를 사용해야 하므로 객체를 누적값으로 썼다. `N` 대신 인덱스에서 1을 뺀 값을 써도 된다.
- `reduce`의 유연성을 알아보기 위해 한 가지 예제를 더 살펴보자.
    - 예제 자체는 매우 조악하지만, 이번에는 문자열을 누적값으로 사용한다.
    ```javascript
    const words = ['Beachball', 'Rodeo', 'Angel', 'Aardvark', 'Xylophone', 'November', 'Chocolate', 'Papaya', 'Uniform', 'Jocker', 'Clover', 'Bali']
    const longWords = words.reduce((a, w) => w.length>6 ? a+' '+w : a, '').trim()
    // longwords: 'Beachball Aardvark Xylophone November Chocolate Uniform'
    ```
    - 이 예제는 문자열 누적값을 써서 6글자가 넘는 단어를 모아 문자열 하나로 만들었다.
    - `reduce` 대신 `filter`와 `join`을 써서 같은 결과를 얻을 수 있다.

**Reference**
> - [MDN web docs - Array.prototype.reduce()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/Reduce)

### 6. 삭제되거나 정의되지 않은 요소들
- `Array` 메서드는 삭제되거나 정의되지 않은 요소들을 다룰 때 당혹스럽게 동작하곤 한다.
- `map`과 `filter`, `reduce`는 *삭제되거나 정의되지 않은 요소들에서 콜백 함수를 호출하지 않는다.*
    - 예를 들어 ES5에서 배열을 초기화할 때 다음과 같은 방법을 시도해 보고 실망했던 사례도 있을 것이다.
        ```javascript
        const arr = Array(10).map(function(x) { return 5})
        ```
    - `arr`의 요소는 전부 `undefined`이다. 이와 비슷하게, 배열 중간의 요소를 삭제하고 `map`을 호출하면 배열 가운데 '구멍'이 생긴다.
         ```javascript
        const arr = [1, 2, 3, 4, 5]
        delete arr[2]
        arr.map(x => 0)     // [0, 0, undefined, 0, 0]
        ```
    - 일반적으로 배열을 다룰 때는 모든 요소가 명시적으로 정의된 배열을 다루고, 의도적으로 빈 부분을 만든다 하더라도 배열에 `delete`를 쓰지 않을 테니 현실적으로 이런 동작이 문제를 일으킬 가능성은 거의 없다.

### 7. 문자열 병합
- 배열의 문자열 요소들을 몇몇 구분자로 합치려 할 때가 있다.
- `Array.prototype.join`은 매개변수르 구분자 하나를 받고 요소들을 하나로 합친 문자열을 반환한다.
    - 이 매개변수가 생략됏을 때의 기본값은 쉼표이며, 문자열 요소를 합칠 때 정의되지 않은 요소, 삭제된 요소, `null`, `undefiend`는 모두 빈 문자열로 취급한다.
    ```javascript
    const arr = [1, null, 'hello', 'world', true, undefined]
    delete arr[3]
    arr.join()        // '1,,hello,,true,'
    arr.join('')      // '1hellotrue'
    arr.join(' -- ')  // '1 -- -- hello -- -- true --'
    ```
- 문자열 병합과 `Array.prototype.join`을 함께 쓰면 HTML `<ul>` 리스트 같은 것도 만들 수 있다. 이때 빈 배열에 사용하려면 빈 `<li>` 요소 하나만 나올 것이다.
    ```javascript
    const attributes = ['Nimble', 'Perceptive', 'Generous']
    const html = '<ul><li>' + attributes.join('</li><li>') + '</li></ul>'
    // html: '<ul><li>Nimble</li><li>Perceptive</li><li>Generous</li></ul>
    ```

**Reference**
> - [MDN web docs - Array.prototype.join()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/join)
