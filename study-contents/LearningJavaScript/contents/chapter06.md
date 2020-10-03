## Chapter 6. 함수
### 1. 반환 값
- 함수 호출은 표현식이고, 표현식은 값이 된다.
- 함수 호출의 값은 *반환 값*이다.
- 함수 바디 안에 `return` 키워드를 사용하면 *함수를 즉시 종료하고 값을 반환한다.*
```javascript
function getGreeting() {
    return "Hello, World!"
}
```
```javascript
getGreeting() // Hello, World!
```
- `return`을 명시적으로 호출하지 않으면 반환 값은 `undefined`가 된다.
- 함수는 어떤 타입의 값이라도 반환할 수 있다.

### 2. 호출과 참조
- 자바스크립트에서는 함수도 객체이다. 따라서 다른 객체와 마찬가지로 넘기거나 할당할 수 있다.
- 함수 *호출*과 *참조*의 차이를 이해하는 것이 중요하다.
    - 함수 식별자 뒤에 괄호를 쓰면 자바스크립트는 **함수를 호출**하려 한다고 이해하고, 함수 바디를 실행한다. 함수를 호출한 표현식은 반환 값이 된다.
    - 괄호를 쓰지 않으면 다른 값과 마찬가지로 **함수를 참조**하는 것이며, 그 함수는 실행되지 않는다.
    ```javascript
    getGreeting()   // Hello, World! (함수 호출)
    getGreeting     // function getGreeting() (함수 참조)
    ```
- 함수를 호출하지 않고 다른 값과 마찬가지로 참조하기만 할 수 있는 특징은 자바스크립트를 매우 유연한 언어로 만든다. 예를 들어 **함수를 변수에 할당**하면 다른 이름으로 함수를 호출 할 수 있다.
    ```javascript
    const f = getGreeting
    f() // Hello, World!
    ```
- **함수를 객체 프로퍼티에 할당**할 수도 있다.
    ```javascript
    const o = {}
    o.f = getGreeting
    o.f() // Hello, World!
    ```
- **함수를 객체 배열 요소로 할당**할 수도 있다.
    ```javascript
    const arr = [1, 2, 3]
    arr[1] = getGreeting
    arr[1]() // Hello, World!
    ```
- 값 뒤에 괄호를 붙이면 자바스크립트는 그 값을 함수로 간주하고 호출한다.
> - 함수가 아닌 값 뒤에 괄호를 붙이면 에러가 난다.
> - 예를 들어 `"whoops"()`는 `TypeError: "whoops" is not a function` 에러를 일으킨다.

### 3. 함수와 매개변수
- 함수를 호출하면서 정보를 전달할 때는 함수 *매개변수(argument, parameter)* 를 이용한다.
```javascript
function avg(a,b) {
    return (a + b)/2
}
```
- 이 함수 선언에서 `a`와 `b`를 *정해진 매개변수(formal argument)* 라고 한다.
- 함수가 호출되면 정해진 매개변수는 값을 받아 *실제 매개변수(actual argument)* 가 된다.
```javascript
avg(5, 10)
```
- 이 예제에서 정해진 매개변수 `a`와 `b`는 각각 값 5와 10을 받아 실제 매개변수가 된다. 실제 매개변수는 변수와 매우 비슷하지만, 함수 바디안에서만 존재한다.
```javascript
const a = 5, b = 10
avg(a, b)
```
- 첫 행의 변수 `a`, `b`는 함수 `avg`의 매개변수인 `a`, `b`와 같은 이름이지만, 엄연히 다른 변수이다.
- 함수를 호출하면 함수 매개변수는 변수 자체가 아니라 그 **값을 전달받는다.**
```javascript
function f(x) {
    console.log(`f 내부: x=${x}`)
    x = 5
    console.log(`f 내부: x=${x} (할당 후)`)
}

let x = 3
console.log(`f를 호출하기 전: x=${x}`)
f(x)
console.log(`f를 호출한 다음: x=${x}`)

/*  실행 결과
    f를 호출하기 전: x=3
    f 내부: x=3
    f 내부: x=5 (할당 후)
    f를 호출한 다음: x=3
*/
```
- 여기서 중요한 것은 함수 안에서 `x`에 값을 할당하더라도 함수 바깥의 변수 `x`에는 아무 영향도 없다는 것이다. 이름은 같지만, 둘은 다른 개체이다.
- 하지만 함수 안에서 객체 자체를 변경하면, 그 객체는 함수 바깥에서도 바뀐 점이 반영된다.
```javascript
function f(o) {
    o.message = `f 안에서 수정함 (이전 값: '${o.message}')`
}
let o = {
    message: '초기 값'
}
console.log(`f를 호출하기 전: o.message='${o.message}'`)
f(o)
console.log(`f를 호출한 다음: o.message='${o.message}'`)

/*  실행 결과
    f를 호출하기 전: o.message='초기 값'
    f를 호출한 다음: o.message='f 안에서 수정함 (이전 값: '초기 값')'
*/
```
- 이 에제를 보면 함수 `f`안에서 객체 `o`를 수정했고, 이렇게 바꾼 내용은 함수 바깥에서도 `o`에 그대로 반영되어 있음을 알 수 있다.
- 이것이 원시 값과 객체의 핵심적인 차이이다.
    - 원시 값은 불변이므로 수정할 수 없다.
    - 원시 값을 담은 변수는 수정할 수 있지만(다른 값으로 바꿀 수 있지만) 원시 *값* 자체는 바뀌지 않는다.
    - 반면 객체는 바뀔 수 있다.
- 함수 안의 `o`와 함수 바깥의 `o`는 서로 다른 개체이다. 하지만 그 둘은 같은 *객체를 카리키고 있다.*

#### 1. 매개변수가 함수를 결정하는가?
- 여러 언어에서 함수의 시그니처에는 매개변수가 포함된다.
    - 예를 들어 C 언어에서 매개변수 없는 함수 `f()`는 매개변수가 하나인 함수 `f(x)`와 다르고, `f(x)`는 매개변수가 두 개인 함수 `f(x, y)`와 다르다.
- 자바스크립트는 함수 `f`가 있다면 호출할 때 매개변수를 한 개 전달하든 열 개 전달하는 같은 함수를 호출한다.
- 다시 말해, 어떤 함수를 호출하든 그 함수에서 정해진 매개변수 숫자와 관계없이 몇 개의 매개변수를 전달해도 된다.
- 정해진 매개변수에 값을 제공하지 않으면 암시적으로 `undefined`가 할당된다.
    ```javascript
    function f(x) {
        return `in f: x=${x}`
    }
    f() // 'in f: x=undefined'
    ```

#### 2. 매개변수 해체
- 매개변수는 해체할 수 있다.
- 객체를 변수로 해체하는 예제를 보자.
    ```javascript
    function getSentence({ subject, verb, object }) {
        return `${subject} ${verb} ${object}`
    }

    const o = {
        subject: 'I',
        verb: 'love',
        object: 'JavaScript'
    }

    getSentence(o) // 'I love JavaScript'
    ```
- 프로퍼티 이름은 반드시 유효한 식별자여야 하고, 들어오는 객체에 해당하는 프로퍼티가 없는 변수느 `undefiend`를 할당받는다.
- 배열 역시 해체할 수 있다.
    ```javascript
    function getSentence([ subject, verb, object ]) {
        return `${subject} ${verb} ${object}`
    }

    const arr = [ 'I', 'love', 'JavaScript' ]
    getSentence(arr) // 'I love JavaScript'
    ```
- 확산 연산자(`...`)를 써서 남는 매개변수를 이용할 수 있다.
    ```javascript
    function addPrefix(prefix, ...words) {
        const prefixedWords = []
        for(let i=0; i<words.length; i++) {
            prefixedWords[i] = prefix + words[i]
        }
        return prefixedWords
    }

    addPrefix('con', 'verse', 'vex') // ['converse', 'convex']
    ```
- 함수를 선언할 때 확산 연산자는 반드시 *마지막 매개변수*여야 한다.
    - 확산 연산자 뒤에 다른 매개변수가 있으면 자바스크립트는 전달된 값 중 어디까지를 확산 매개변수에 할당해야 하는지 판단할 수 없어 에러를 일으킨다.

#### 3. 매개변수 기본값
- ES6에서는 매개변수에 기본값을 지정하는 기능이 추가됐다.
- 일반적으로 매개변수에 값을 제공하지 않으면 `undefined`가 값으로 할당된다.
    ```javascript
    function f(a, b = 'default', c = 3) {
        return `${a} - ${b} - ${c}`
    }

    f(5, 6, 7)  // '5 - 6 - 7'
    f(5, 6)     // '5 - 6 - 3'
    f(5)        // '5 - default - 3'
    f()         // 'undefined - default - 3' 
    ```

**Reference**
> - [MDN web docs - 기본 매개변수](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Functions/Default_parameters)

### 4. 객체의 프로퍼티인 함수
- 객체의 프로피티인 함수를 *메서드*라고 불러 일반적인 함수와 구별한다.
    ```javascript
    const o = {
        name: 'Wallace',                    // 원시 값 프로퍼티
        bark: function() { return 'Woof!' } // 함수 프로퍼티(메서드)
    }
    ```
- ES6에서는 간편하게 메서드를 추가할 수 있는 문법이 새로 생겼다.
     ```javascript
    const o = {
        name: 'Wallace',            // 원시 값 프로퍼티
        bark() { return 'Woof!' }   // 함수 프로퍼티(메서드)
    }
    ```

### 5. this 키워드
- 함수 바디 안에는 특별한 읽기 전용 값인 `this`가 있다.
- 일반적으로 `this`는 객체의 프로퍼티인 함수에서 의미가 있다. 메서드를 호출하면 `this`는 호출한 메서드를 소유하는 객체가 된다.
```javascript
const o = {
    name: 'doy',
    speak() { return `My name is ${this.name}!` },
}
```
- `o.speak()`를 호출하면 `this`는 `o`에 묶인다.
- `this`는 함수를 어떻게 선언했느냐가 아니라 어떻게 호출했느냐에 따라 달라진다.
- 즉, `this`가 `o`에 묶인 이유는 `speak`가 `o`의 프로퍼티여서가 아니라, `o`에서 `speak`를 호출했기 때문이다.
```javascript
const speak = o.speak
speak === o.speak   // true, 두 변수는 같은 함수를 가리킨다.
speak()             // 'My name is undefined!'
```
- 함수를 이렇게 호출하면 자바스크립트는 이 함수가 어디에 속하는지 알 수 없으므로 `this`는 `undefined`에 묶인다.
- 중첩된 함수 안에서 `this`를 사용하려다 보면 혼란스러울 때가 많다.
```javascript
const o = {
    name: 'doy',
    greetBackwards: function() {
        function getReverseName() {
            let nameBackwards = ''
            for(let i=this.name.length-1; i>=0; i--) {
                nameBackwards += this.name[i]
            }
            return nameBackwards
        }
        return `${getReverseName()} si eman ym ,olleH`
    },
}
o.greetBackwards()
```
- 위 예제는 이름을 거꾸로 쓰고자 중첩된 함수 `getReverseName`을 사용했다.
- 하지만 `getReverseName`은 의도한 대로 동작하지 않는다.
    - `o.greetBackwards()`를 호출하는 시점에서 자바스크립트는 `this`를 의도한 대로 `o`에 연결하지만, `greetBackwards` 안에서 `getReverseName`을 호출하면 `this`는 `o`가 아닌 다른 것에 묶인다.
    - 이런 문제를 해결하기 위해 널리 사용하는 방법은 다른 변수에 `this`를 할당하는 것이다.
```javascript
const o = {
    name: 'doy',
    greetBackwards: function() {
        const self = this
        function getReverseName() {
            let nameBackwards = ''
            for(let i=this.name.length-1; i>=0; i--) {
                nameBackwards += self.name[i]
            }
            return nameBackwards
        }
        return `${getReverseName()} si eman ym ,olleH`
    },
}
o.greetBackwards()
```
- 이 방법은 널리 쓰이는 방법이며, `this`를 `self`나 `that`에 할당하는 코드를 많이 보게 될 것이다.
- 화살표 함수를 써도 이 문제를 해결할 수 있다.

**Reference**
> - [MDN web docs - this](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Operators/this)

### 6. 함수 표현식과 익명 함수
- 함수를 선언하면 함수에 바디와 식별자가 모두 주어진다.
- 자바스크립트는 *익명 함수(anonymous function)* 를 지원한다.
    - 익명 함수에서는 함수에 식별자가 주어지지 않는다.
- 식별자가 없다면, 도대체 어떻게 호출해야 할까?
    - 답은 함수 표현식에 있다.
    - 표현식이 값이 되고, 함수 역시 값이 된다.
    - 함수 포현식은 함수를 선언하는 한 가지 방법일 뿐이며, 그 함수가 익머여이 될 수도 있을 뿐이다.
    - 함수 표현식은 식별자에 할당할 수도 있고 즉시 호출할 수도 있다.
- 함수 표현식은 함수 이름을 생략할 수 있다는 점을 제외하면 함수 선언과 문법적으로 완전히 같다.
    ```javascript
    const f = function() {
        // ...
    }
    ```
    - 식별자 `f`가 이 함수를 가리킨다.
    - 일반적인 함수 선언과 마찬가지로 `f()`로 이 함수를 호출할 수 있다.
    - 차이점은 먼저 함수 표현식으로 익명 함수를 만들고 그 함수를 변수에 할당했다는 것이다.
- 익명 함수는 다른 함수의 메서드나 매개변수로 넘길 수도 있고, 객체의 함수 프로퍼티가 될 수도 있다.

**Reference**
> - [MDN web docs - 함수 표현식](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Operators/function)

### 7. 화살표 표기법
- ES6에 새로 추가된 화살표 표기법은 간단히 말해 `function`이라는 단어와 중괄호 숫자를 줄이려고 고안된 단축 문법이다.
- 화살표 함수에는 세 가지 단축 문법이 있다.
    - `function`을 생략해도 된다.
    - 함수에 매개변수가 단 하나 뿐이라면 괄호(())도 생략할 수 있다.
    - 함수 바디가 표현식 하나라면 중괄호와 `return`문도 생략할 수 있다.
- 화살표 함수는 항상 익명이다.
- 화살표 함수도 변수에 할당할 수는 있지만, `function` 키워드처럼 이름 붙은 함수를 만들 수는 없다.
```javascript
const f1 = function() { return "hello!" }
// 또는
const f1 = () => "hello!"

const f2 = function(name) { return `Hello, ${name}!` }
// 또는
const f2 = name => `Hello, ${name}!`

const f3 = function(a, b) { return a + b }
// 또는
const f3 = (a,b) => a + b
```
- 이 예제는 다분히 인위적이다. 이름 붙은 함수가 필요하다면 그냥 일반적인 함수 선언을 사용하면 된다.
- 화살표 함수는 익명함수를 만들어 다른 곳에 전달하려 할 때 가장 유용하다.
- 화살표 함수에는 일반적인 함수와 중요한 차이가 있다.
    - `this`가 다른 변수와 마찬가지로, 정적으로 묶인다.
    - 위에 만들었던 `greetBackwards` 예제를 화살표 함수를 사용하면 내부 함수 안에서 `this`를 사용할 수 있다.
    ```javascript
    const o = {
        name: 'doy',
        greetBackwards: function() {
            const getReverseName = () => {
                let nameBackwards = ''
                for(let i=this.name.length-1; i>=0; i--) {
                    nameBackwards += this.name[i]
                }
                return nameBackwards
            }
            return `${getReverseName()} si eman ym ,olleH`
        },
    }
    o.greetBackwards()
    ```
    - 또다른 차이점은 화살표 함수는 객체 생성자로 사용할 수 없고, arguments 변수도 사용할 수 없다.
    - 하지만 ES6에서 확산 연산자가 생겨 arguments 변수는 필요 없다.

**Reference**
> - [MDN web docs - 화살표 함수](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Functions/%EC%95%A0%EB%A1%9C%EC%9A%B0_%ED%8E%91%EC%85%98)

### 8. call과 apply, bind
- 자바스크립트에서는 함수를 어디서, 어떻게 호출했느냐와 관계없이 `this`가 무엇인지 지정할 수 있다.
#### 1. call
- `call` 메서드는 모든 함수에서 사용할 수 있으며, `this`를 특정 값으로 지정할 수 있다.
    ```javascript
    const doy = { name: 'Doy' }
    const hee = { name: 'Hee' }

    // 이 함수는 어떤 객체에도 연결되지 않았지만 this를 사용한다.
    function greet() {
        return `Hello, I'm ${this.name}!`
    }

    greet()                 // 'Hello, I'm undefined!'
    greet.call(doy)         // 'Hello, I'm Doy'
    greet.call(hee)         // 'Hello, I'm Hee!'
    ```
- 함수를 호출하면서 `call`을 사용하고 `this`로 사용할 객체를 넘기면 해당 함수가 주어진 객체의 메서드인 것처럼 사용할 수 있다.
- `call`의 첫 번째 매개변수는 `this`로 사용할 값이고, 매개변수가 더 있으면 그 매개변수는 호출하는 함수로 전달된다.
    ```javascript
    function update(birthYear, occupation) {
        this.birthYear = birthYear
        this.occupation = occupation
    }

    update.call(doy, 1995, 'student')
    // doy는 이제 { name: 'doy', birthYear: 1995,
    //             occupation: 'student' } 이다.
    update.call(hee, 1994, 'student')
    // hee는 이제 { name: 'hee', birthYear: 1994,
    //             occupation: 'student' } 이다.
    }
    ```

#### 2. apply
- `apply`는 함수 매개변수를 처리하는 방법을 제외하면 `call`과 완전히 같다.
- `call`은 일반적인 함수와 마찬가지로 매개변수를 직접 받지만, `apply`는 매개변수를 배열로 받는다.
    ```javascript
    update.apply(doy, [1955, 'actor'])
    // doy는 이제 { name: 'doy', birthYear: 1955,
    //             occupation: 'actor' } 이다.
    update.apply(hee, [1944, 'writer'])
    // hee는 이제 { name: 'hee', birthYear: 1944,
    //             occupation: 'writer' } 이다.
    }
    ```
- `apply`는 배열 요소를 함수 매개변수로 사용해야 할 때 유용하다.

#### 3. bind
- `bind`를 사용하면 함수의 `this` 값을 영구적으로 바꿀 수 있다.
- `update` 메서드를 이리저리 옮기면서도 호출할 때 `this` 값은 항상 `doy`가 되게끔, `call`이나 `apply`, 다른 `bind`와 함께 호출하더라도 `this` 값이 `doy`가 되도록 하려면 `bind`를 사용한다.
    ```javascript
    const updateDoy = update.bind(doy)

    updateDoy(1996, 'actor')
    // doy는 이제 { name: 'doy', birthYear: 1996,
    //             occupation: 'actor' } 이다.
    updateDoy.call(hee, 1997, 'Queen')
    // doy는 이제 { name: 'doy', birthYear: 1997,
    //             occupation: 'Queen' } 이다.
    // hee는 변하지 않았다.
    ```
- `bind`는 함수의 동작을 영구적으로 바꾸므로 찾기 어려운 버그의 원인이 될 수 있다.
    - `bind`를 사용한 함수는 `call`이나 `apply`, 다른 `bind`와 함께 사용할 수 없다.
    - `bind`는 매우 유용하지만, 함수의 `this`가 어디에 묶이는지 정확히 파악하고 사용해야 한다.
- `bind`에 매개변수를 넘기면 항상 그 매개변수를 받으면서 호출되는 새 함수를 만드는 효과가 있다.
    - 예를 들어 `doy`가 태어난 해를 항상 1995로 고정하지만, 직업은 자유롭게 바꿀 수 있는 업데이트 함수를 만들고 싶다면 다음과 같이 하면 된다.
    ```javascript
    const updateDoy1995 = update.bind(doy, 1995)
    updateDoy1995('singer, songwriter')
    ```

**Reference**
> - [MDN web docs - call](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Function/apply)
> - [MDN web docs - apply](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Function/call)
> - [MDN web docs - bind](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Function/bind)
