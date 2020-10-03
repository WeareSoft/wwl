## Chapter 7. 스코프
### 1. 스코프와 존재
- 스코프란
  - 변수와 상수, 매개변수가 언제 어디서 정의되는지 결정하는 것
  ```javascript
  function f(x) {
    return x + 3
  }
  f(5)  // 8
  x     // ReferenceError: x is not defined
  ```
  - `x`가 아주 잠시나마 존재했었으니 `x + 3`을 계산할 수 있었다.
  - 하지만 함수 바디를 벗어나면 `x`는 존재하지 않는 것처럼 보인다.
  - 따라서 `x`의 스코프는 함수 `f`이다.
- 스코프와 존재
  - 변수가 존재하지 않으면 그 변수는 스코프 안에 '있지 않음'을 알 수 있다.
    - 즉, 아직 선언하지 않은 변수나 함수가 종료되면서 **존재하지 않게 된 변수**는 분명 **스코프 안에 '있지 않다'**.
  - 그렇다면 변수가 스코프 안에 있지 않다면, 그 변수는 존재하지 않는다는 말일까?
    - 꼭 그런 건 아니다.
    - *스코프*와 *존재*를 반드시 구별해야 한다.
  - *가시성(visibility)* 이라고도 불리는 **스코프**는 프로그램의 현재 실행 중인 부분, 즉 *실행 컨텍스트(execution context)* 에서 현재 보이고 접근할 수 있는 식별자들을 말한다.
  - 반면 존재한다는 말은 그 식별자가 메모리가 할당된(예약된) 무언가를 가리키고 있다는 뜻이다.
  - 무언가가 더는 존재하지 않는다고 해도 자바스크립트는 메모리를 바로 회수하지는 않는다. 그것을 계속 유지할 필요가 없다고 표시해 두면, 주기적으로 일어나는 *가비지 콜렉션* 프로세스에서 메모리를 회수한다.

### 2. 정적 스코프
```javascript
function f1() {
  console.log('one')
}
function f2() {
  console.log('two')
}
f2()
f1()
f2()
```
- 정적으로 보면 이 프로그램은 단순히 위에서 아래로 읽어내리는 문의 연속이다.
- 하지만 이 프로그램을 실행하면 실행 흐름은 읽는 순서와 다르다.
  - `f1`이 `f2`보다 먼저 정의됐지만, `f2`의 함수 바디가 실행된 다음 `f1`으로, 다시 `f1`로 넘어간다.
- 자바스크립트의 스코프는 *정적*이다.
  - 소스 코드만 봐도 변수가 스코프에 있는지 판단할 수 있다는 뜻이다.
  - 다만, 소스 코드만 봐도 즉시 스코프를 *분명히* 알 수 있다는 뜻은 아니다.
- 정적 스코프는 어떤 변수가 함수 스코프 안에 있는지 함수를 *정의할 때* 알 수 있다는 뜻이다. *호출할 때* 알 수 있는 것은 아니다.
```javascript
const x = 3

function f() {
  console.log(x)
  console.log(y)
}

{ // 새 스코프
  const y = 5
  f()
}
```
- 변수 `x`는 함수 `f`를 정의할 때 존재하지만, `y`는 그렇지 않다.
- 다른 스코프에서 `y`를 선언하고 그 스코프에서 `f`를 호출하더라도, `f`를 호출하면 `x`는 그 바디 안의 스코프에 있지만 `y`는 그렇지 않다.
- 함수 `f`는 자신이 **정의될 때** 접근할 수 있었던 식별자에는 여전히 접근할 수 있지만, 호출할 때 스코프에 있는 식별자에 접근할 수는 없다.
- 자바스크립트의 정적 스코프는 **전역 스코프(global scope)** 와 **블록 스코프(block scope)**, **함수 스코프(function scope)** 에 적용된다.

### 3. 전역 스코프
- 전역 스코프
  - 스코프는 계층적이며 트리의 맨 아래에는 바탕이 되는 무언가가 있어야 한다.
  - 즉, 프로그램을 시작할 때 암시적으로 주어지는 스코프가 필요하다.
  - 이 스코프를 *전역 스코프*라고 한다.
- 전역 스코프의 특징
  - 자바스크립트 프로그램을 시작할 때, 즉 어떤 함수도 호출하지 않았을 때 실행 흐름은 전역 스코프에 있다.
  - 전역 스코프에서 선언한 것은 무엇이든 프로그램의 모든 스코프에서 볼 수 있다.
  - 전역 스코프에서 선언된 것들을 *전역 변수*라고 한다.
  - 전역 스코프에 *의존*하는 것은 피해야 한다.

```javascript
let name = 'doy'  // 전역
let age = 25      // 전역

function greet() {
  console.log(`Hello, ${name}!`)
}
function getBirthYear() {
  return new Date().getFullYear() - age
}
```
- 이 방법의 문제는 함수가 호출하는 컨텍스트(스코프)에 대단히 의존적이라는 것이다.
- 어떤 함수든, 프로그램 어디에서든 상관없이 `name` 값을 의도적으로든, 실수로든 바꿀 수 있다.
- 또한 `name`과 `age`는 흔한 이름이므로 다른 곳에서 다른 이유로 사용할 가능성도 크다.
- `greet`와 `getBirthYear`는 전역 변수에 의존하므로, 프로그램의 다른 부분에서 `name`과 `age`를 정확히 사용한다고 가정하고 있는 것이다.
- 이보다는 사용자 정보를 단일 객체에 보관하는 방법이 더 낫다.
```javascript
let user = {
  name = 'doy',
  age = 25,
}

function greet() {
  console.log(`Hello, ${user.name}!`)
}
function getBirthYear() {
  return new Date().getFullYear() - user.age
}
```
- 이 예제에서는 `name`과 `age`를 없애고 대신 `user`를 써서 전역 스코프의 식별자 숫자를 겨우 하나 줄였을 뿐이지만, 사용자에 관한 정보를 10가지나 100가지 보관한다고 상상해 보자.
- 하지만 함수 `greet`와 `getBirthYear`는 여전히 전역 `user`에 의존하며, 이 객체는 어디서든 수정할 수 있다.
- 이 함수를 고쳐서 전역 스코프에 의존하지 않게 만들어보자.
```javascript
function greet(user) {
  console.log(`Hello, ${user.name}!`)
}
function getBirthYear(user) {
  return new Date().getFullYear() - user.age
}
```
- 이제 이 함수들은 모든 스코프에서 호출할 수 있고, 명시적으로 `user`를 전달받는다.
- 프로그램이 이렇게 단순하다면, 전역 변수를 쓰더라도 문제가 생길 소지는 전혀 없다. 하지만 프로그램이 수천, 수만 행이 되어 모든 스코프를 기억하고 관리할 수 없게 된다면 **전역 스코프에 의존하지 않는 것이 정말 중요해진다.**

### 4. 블록 스코프
- `let`과 `const`는 식별자를 *블록 스코프*에서 선언한다.
- 블록 스코프는 그 블록의 스코프에서만 보이는 식별자를 의미한다.
```javascript
console.log('before block')
{
  console.log('inside block')
  const x = 3
  console.log(x)  // 3
}
console.log(`outside block; x=${x}`)  // ReferenceError: x는 정의되지 않음
```
- 위 예제는 *독립 블록*을 사용했다.
  - 블록은 보통 `if`나 `for` 같은 제어문의 일부분으로 쓰이지만, 블록 그 자체로도 유효한 문법이다.
- `x`는 블록 안에서 정의됐고, 블록을 나가는 즉시 `x`도 스코프 밖으로 사라지므로 정의되지 않은  것으로 간주한다.

### 5. 변수 숨기기
- 다른 스코프에 있으면서 이름이 같은 변수나 상수는 혼란을 초래할 때가 많다.
```javascript
{
  // block 1
  const x = 'blue'
  console.log(x)      // 'blue'
}
console.log(typeof x) // 'undefiend'; x는 스코프 밖에 있다.
{
  // block 2
  const x = 3
  console.log(x)      // '3'
}
console.log(typeof x) // 'undefiend'; x는 스코프 밖에 있다.
```

```javascript
{
  // 외부 블록
  let x = 'blue'
  console.log(x)      // 'blue'
  {
    // 내부 블록
    let x = 3
    console.log(x)      // '3'
  }
  console.log(x)      // 'blue'
}
console.log(typeof x) // 'undefiend'; x는 스코프에 있지 않다.
```
- 이 예제는 *변수 숨김*을 잘 보여준다.
- 내부 블롤의 `x`는 외부 블록에서 정의한 `x`와는 이름만 같을 뿐 다른 변수이므로 외부 스코프의 `x`를 숨기는 효과가 있다.
- 실행 흐름이 내부 블록에 들어가 새 변수 `x`를 정의하는 순간, 두 변수가 모두 스코프 안에 있다.
- 변수의 이름이 같으므로 외부 스코프에 있는 변수에 접근할 방법이 없다.

```javascript
{
  // 외부 블록
  let x = { color: 'blue' }
  let y = x               // y과 x는 같은 객체를 가리킴
  let z = 3
  {
    // 내부 블록
    let x = 5             // 바깥의 x는 가려짐
    console.log(x)        // 5
    console.log(y.color)  // 'blue'; y가 가리키는, 외부 스코프의 x가 가리키는 객체는 스코프 안에 있음
    y.color = 'red'
    console.log(z)        // 3; z는 숨겨지지 않음
  }
  console.log(x.color)    // 'red'; 객체는 내부 스코프에서 수정됨
  console.log(y.color)    // 'red'; x과 y는 같은 객체를 가리킴
  console.log(z)          // 3
}
```
- 이전 스코프를 떠나지 않아도 새 스코프에 진입할 수 있다.
- 스코프의 계층적인 성격 때문에 어떤 변수가 스코프에 있는지 확인하는 *스코프 체인*이란 개념이 생겼다.
- 현재 스코프 체인에 있는 모든 변수는 스코프에 있는 것이며, 숨겨지지 않았다면 접근할 수 있다.

### 6. 함수, 클로저, 정적 스코프
- 클로저
  - 함수가 특정 스코프에 접근할 수 있도록 의도적으로 그 스코프에서 정의하는 경우가 많다. 이런 것을 보통 *클로저*라고 부른다.
  - 스코프를 함수 주변으로 좁히는 것이라고 생각해도 된다.
```javascript
let globalFunc              // 정의되지 않은 전역 함수
{
  let blockVar = 'a'        // 블록 스코프에 있는 변수
  globalFunc = function() {
    console.log(blockVar)
  }
}
globalFunc()                // 'a'
```
- `globalFunc`는 블록 안에서 값을 할당 받았다. 이 블록 스코프와 그 부모인 전역 스코프가 클로저를 형성한다. `globalFunc`를 어디서 호출하든, 이 함수는 클로저에 들어있는 식별자에 접근할 수 있다.
- `globalFunc`을 호출하면, 이 함수는 *스코프에서 빠져나왔음에도 불구하고* `blockVar`에 접근할 수 있다.
- 일반적으로 스코프에서 빠져나가면 해당 스코프에서 선언한 변수는 메모리에서 제거해도 안전하다. 하지만 여기서는 스코프 안에서 함수를 정의했고, 해당 함수는 스코프 밖에서도 참조할 수 있으므로 자바스크립트는 **스코프를 계속 유지**한다.
- 즉, 스코프 안에서 함수를 정의하면 해당 스코프는 더 오래 유지된다. 또한 일반적으로는 접근할 수 없는 것에 접근할 수 있는 효과도 있다.
```javascript
let f                       // 정의되지 않은 함수
{
  let o = { note: 'Safe' }
  f = function() {
    return o
  }
}
let oRef = f()
oRef.note = 'Not so safe after all!'
```
- 일반적으로는 스코프 바깥쪽에 있는 것들에는 접근할 수 없다.
- 함수를 정의해 클로저를 만들면 접근할 수 없었던 것들에 접근할 방법이 생긴다.

**Reference**
> - [MDN web docs - 클로저](https://developer.mozilla.org/ko/docs/Web/JavaScript/Guide/Closures)


### 7. 즉시 호출하는 함수 표현식
- 함수 표현식을 사용하면 *즉시 호출하는 함수 표현식(IIFE)* 이란 것을 만들 수 있다.
  - IIFE는 함수를 선언하고 즉시 실행한다.
  - IIFE는 다음과 같은 형태를 취한다.
    ```javascript
    (function() {
      // IIFE 바디
    })()
    ```
  - 함수 표현식으로 익명 함수를 만들고 그 함수를 즉시 호출한다.
  - IIFE의 장점은 내부에 있는 것들이 모두 자신만의 스코프를 가지지만, IIFE 자체는 함수이므로 그 스코프 밖으러 무언가를 내보낼 수 있다는 것이다.
    ```javascript
    const message = (function() {
      const secret = 'I am a secret!'
      return `The secret is ${secret.length} characters long.`
    })()
    console.log(message)
    ```
  - 변수 `secret`은 IIFE의 스코프 안에서 안전하게 보호되며 외부에서 접근할 수 없다.
  - IIFE는 함수이므로 무엇이든 반환할 수 있다.
  - ES6에서 블록 스코프 변수를 도입하면서 IFEE가 필요한 경우가 줄어들긴 했지만 여전히 매우 널리 쓰인다. 클로저를 만들고 클로저에서 무언가 반환받을 때에는 유용하게 쓸 수 있다.

**Reference**
> - [MDN web docs - IIFE](https://developer.mozilla.org/ko/docs/Glossary/IIFE)

### 8. 함수 스코프와 호이스팅
- ES6에서 `let`을 도입하기 전에는 `var`를 써서 변수를 선언했고, 이렇게 선언된 변수들은 함수 스코프라 불리는 스코프를 가졌다.
- `let`으로 변수를 선언하면, 그 변수는 선언하기 전에는 존재하지 않는다.
- `var`로 선언한 변수는 *현재 스코프 안이라면 어디서든* 사용할 수 있으며, 심지어 선언하기도 전에 사용할 수 있다.
  ```javascript
  let var1
  let var2 = undefined
  var1          // undefined
  var2          // undefined
  undefinedVar  // ReferrenceError: undefinedVar는 정의되지 않음
  ```
- `let`을 쓰면, 변수를 선언하기 전 사용하려 할 때 에러가 일어난다.
  ```javascript
  x             // ReferrenceError: x 정의되지 않음
  let x = 3     // 에러가 일어나 도달할 수 없음
  ```
- 반면 `var`로 변수를 선언하면 선언하기 전에도 사용할 수 있다.
  ```javascript
  x             // undefined
  var x = 3
  x             // 3
  ```
- `var`로 선언한 변수는 끌어올린다는 뜻의 **호이스팅(hoisting)** 이라는 매커니즘을 따른다.
- 자바스크립트는 함수나 전역 스코프 전체를 살펴보고 `var`로 선언한 변수를 맨 위로 *끌어올린다*.
  - 여기서 중요한 것은 *선언*만 끌어올려진다는 것이며, *할당*은 끌어올려지지 않는 다는 것이다.
  ```javascript
  var x         // 선언이 끌어올려짐
  x             // undefined
  x = 3
  x             // 3
  ```
- `var`를 쓰면 혼란스럽고 쓸모없는 코드가 생길 수 있다. 그럼 왜 `var`와 호이스팅을 이해해야 할까?
  - ES6를 어디에서든 쓸 수 있으려면 아직 시간이 더 필요하므로 ES5로 트랜스컴파일을 해야한다. 따라서 아직은 `var`가 어떻게 동작하는지 이해하고 있어야 한다.
  - 함수 선언 역시 끌어올려진다.

**Reference**
> - [MDN web docs - Hoisting](https://developer.mozilla.org/ko/docs/Glossary/Hoisting)

### 9. 함수 호이스팅
- `var`로 선언된 변수와 마찬가지로, 함수 선언도 스코프 맨 위로 끌어올려진다.
- 따라서 함수를 선언하기 전에 호출할 수 있다.
  ```javascript
  f()                 // 'f'
  function f() {
    console.log('f')
  }
  ```
- 변수에 할당된 함수 표현식은 끌어올려지지 않는다. 이들은 변수의 스코프 규칙을 그대로 따른다.
  ```javascript
  f()                 // ReferrenceError: f는 정의되지 않음
  let f = function() {
    console.log('f')
  }
  ```

**Reference**
> - [MDN web docs - 함수](https://developer.mozilla.org/ko/docs/Web/JavaScript/Guide/%ED%95%A8%EC%88%98#%ED%95%A8%EC%88%98_%ED%98%B8%EC%B6%9C)

### 10. 사각지대
- *사각지대*란, `let`으로 선언하는 변수는 선언하기 전까지 존재하지 않는다는 직관적 개념을 잘 나타내는 표현이다.
- 스코프 안에서 변수의 사각지대는 변수가 선언되기 전의 코드이다.
- `typeof` 연산자는 변수가 선언됐는지 알아볼 때 널리 쓰이고, 존재를 확인하는 안전한 방법으로 알려져 있다.
- 즉, `let` 키워드가 도입되고 변수의 사각지대가 생기기 전에는 다음과 같은 코드는 항상 안전하며 에러가 발생하지도 않았다.
  ```javascript
  if(typeof x === 'undefined') {
    console.log('x does not exit or is undefined')
  } else {
    // x를 사용해도 안전한 코드
  }
  ```
- 하지만 이 코드를 `let`으로 변수 선언하면 안전하지 않다. 다음 코드에서는 에러가 발생한다.
  ```javascript
  if(typeof x === 'undefined') {
    console.log('x does not exit or is undefined')
  } else {
    // x를 사용해도 안전한 코드
  }
  let x = 5
  ```
- ES6에서는 `typeof` 연산자로 변수가 정의됐는지 확인할 필요가 거의 없으므로 `typeof`가 문제를 일으킬 소지도 거의 없다.

**Reference**
> - [MDN web docs - let](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Statements/let#%EC%9E%84%EC%8B%9C%EC%A0%81%EC%9D%B8_%EC%82%AC%EA%B0%81_%EC%A7%80%EC%97%AD%EA%B3%BC_%EC%98%A4%EB%A5%98)

### 11. 스트릭트 모드
- ES5 문법에서는 *암시적 전역 변수*라는 것이 생길 수 있었다.
  - 암시적 전역 변수는 여러 가지 에러를 일으켰다.
    - `var`로 변수를 선언하는 것을 잊으면 자바스크립트는 전역 변수를 참조하려 한다고 간주하고, 그런 전역 변수가 존재하지 않으면 스스로 만들었다.
  - 이런 이유로 자바스크립트에서는 *스트릭트 모드(strict mode)* 를 도입했다.
- 스트릭트 모드에서는 암시적 전역 변수를 허용하지 않는다.
- 스트릭트 모드를 사용하려면 문자열 `'use strict'` 하나만으로 이루어진 행을 코드 맨 앞에 쓰면 된다.
- 전역 스코프에서 `'use strict'`를 사용하면 스크립트 전체가 스트릭트 모드로 실행되고, 함수 안에서 `'use strict'`를 사용하면 해당 함수만 스트릭트 모드로 실행된다.
- 전역 스코프에 스트릭트 모드를 적용하면 스크립트 전체의 동작 방식이 바뀌므로 주의해야 한다.
  - 최신 웹사이트는 대부분 다양한 스크립트를 불러와서 사용하므로 전역 스코프에서 스트릭트 모드를 사용하면 불러온 스크립트 전체에 스트릭트 모드가 강제된다.
  - 일반적으로는 전역 스코프에서 스트릭트 모드를 사용하지 않는 편이 좋다.
  ```javascript
  (function() {
    'use strict'

    // 코드를 전부 이 안에 작성한다.
    // 이 코드는 스트릭트 모드로 작동하지만,
    // 이 코드와 함께 동작하는 다른 스크립트는
    // 스트릭트 모드에 영향받지 않는다.

  })()
  ```

**Reference**
> - [MDN web docs - Strict mode](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/strict_mode)
