## Chapter 13. 함수와 추상적 사고
- 함수는 자신이 존재하는 컨텍스트에 따라 다양한 모습을 취한다. 우리가 가장 먼저 되짚어 볼, 가장 간단한 일면은 코드를 재사용하는 수단이라는 측면이다.

### 1. 서브루틴으로서의 함수
- 서브루틴은 아주 오래 된 개념이며 복잡한 코드를 간단하게 만드는 기초적인 수단이다.
- 서브루틴은 반복되는 작업의 일부를 떼어내서 이름을 붙이고, 언제든 그 이름을 부르기만 하면 실행한다.
> 서브루틴은 프로시저, 루틴, 서브프로그램, 매크로 등 다양한 이름으로 불린다. 모두 매우 단순하고 범용적인, *호출할 수 있는* 한 단위를 일컫는 말이다. 자바스크립트에서 서브루틴이라는 용어를 쓰지 않는다. 함수는 함수(또는 메서드)라고 부를 뿐이다. 여기서 서브루틴이라는 용어를 쓰는 이유는 함수의 가장 간단한 사용 형태를 강조학 위해서다.
- 서브루틴은 대게 어떤 알고리즘을 나타내는 형태이고, 알고리즘이란 주어진 작업을 수행하는 방법이다.
    - 프로그램안에서 오늘이 윤년인지 판단하는 알고리즘을 10번, 100번 실행해야 한다고 생각해보자.
    - 콘솔이 기록하는 메시지를 바꿔야 한다면? 이 코드를 쓰는 부분을 일일이 찾아다니며 바꿔야 한다.
    - 서브루틴은 바로 이런 문제를 해결한다. 자바스크립트에서는 함수가 해결한다.
    ```javascript
    function printLeapYearStatus() {
        const year = new Date().getFullYear()
        if(year % 4 !== 0) console.log(`${year} is NOT a leap year.`)
        else if(year % 100 !== 0) console.log(`${year} IS a leap year.`)
        else if(year % 400 !== 0) console.log(`${year} is NOT a leap year.`)
        else console.log(`${year} IS a leap year.`)
    }
    ```
    - 이제 **재사용할 수 있는** 서브루틴(함수)를 만들었다.
- 함수의 이름을 정하는 것은 아주 중요한 일이다. 함수의 이름은 다른 사람, 또는 나중에 이 코드를 다시 볼 사람을 위해 정하는 것이다.
    - 이상적인 경우는 함수가 하는 일을 이름만으로 완벽하게 나타내는 것이겠지만, 그렇게 하면 지나치게 번거로울 수 있다.
    - 예를 들어 '오늘이윤년인지계산해서콘솔에기록한다' 같은 이름을 쓴다면(즉, calculateCurrentLeapYearStatusAndPrintToConsole) 더 많은 정보를 얻을 수 있겠지만, 너무 길어서 의미가 없다.

### 2. 값을 반환하는 서브루틴으로서의 함수
- 이전 예제의 `printLeapYearStatus`는 재사용하기 편리하도록 어떤 동작을 하나로 묶었고, 그 이상은 아무 것도 하지 않았으니 서브루틴이라는 용어를 잘 반영했다고 할 수 있다.
- 하지만 이렇게 단순한 함수를 사용하는 일은 별로 없을 테고, 복잡하고 추상적인 문제에 도전하게 되면 더 줄어들 것이다. 한 단계 더 추상화해서 함수를 *값을 반환하는* 서브루틴으로 생각해 보자.
    - 프로그램이 커지면 콘솔에 기록하는 것은 곧 쓸모없어진다.
    - 파일에 저장하거나, 다른 계산에 사용해야 할 수도 있는데 지금 가진 서브루틴은 그렇게 활용할 수 없다.
    - 다행히 함수가 값을 반환하는 서브루틴이 되도록 고쳐 쓰기는 쉽다.
    ```javascript
    function isCurrentYearLeapYear() {
        const year = new Date().getFullYear()
        if(year % 4 !== 0) return false
        else if(year % 100 !== 0) return true
        else if(year % 400 !== 0) return false
        else return true
    }
    ```
    - 이제 새로 만든 함수의 반환값을 다음과 같이 활용할 수 있다.
    ```javascript
    const daysInMonth = [31, isCurrentYearLeapYear() ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    
    if(isCurrentYearLeapYear()) console.log('It is a leap year')
    ``` 
    - 불리언을 반환하거나, 불리언이 필요한 컨텍스트에서 사용하도록 만든 함수는 `is`로 시작하는 이름을 붙이는 게 일반적이다.

### 3. 함수로서의 함수
- 함수는 입력이 들어가면 결과가 나오는 일종의 관계로 생각할 수 있다.
- 프로그래머들은 이렇게 함수의 수학적인 정의에 충실한 함수를 *순수한 함수*라고 부른다.
- 그러면 순수한 함수는 우리가 여태까지 생각해 본 함수와 어떤 면에서 다를까?
  - 가장 중요한 차이는, 순수한 함수에서는 *입력이 같으면 결과도 반드시 같다*는 점이다.
    - `isCurrentYearLeapYear`는 언제 호출하느냐에 따라서 `true`를 반환하기도 하고 `false`를 반환하기도 하므로 순수한 함수라고 할 수 없다.
  - 순수한 함수에는 부수 효과가 없어야 한다.
    - 즉, 함수를 호출한다고 해서 프로그램의 상태가 바뀌어서는 안된다.
- ex) 윤년 문제를 순수한 함수로 고치기
  ```javascript
  function isLeapYear(year) {
      if(year % 4 !== 0) return false
      else if(year % 100 !== 0) return true
      else if(year % 400 !== 0) return false
      else return true
  }
  ```
  - 이 함수는 입력이 같으면 결과도 항상 같고, 다른 효과는 전혀 없으므로 순수한 함수라고 볼 수 있다.
- ex) 무지개의 일곱 가지 색깔 반환
  ```javascript
  const colors = ['red', 'orange', 'yellow', 'green', 'blue', 'indigo', 'violet']
  let colorIndex = -1
  function getNextRainbowColor() {
    if(++colorIndex >= colors.length) colorIndex = 0
    return colors[colorIndex]
  }
  ``` 
  - 이 함수는 순수한 함수의 두 가지 정의를 모두 어긴다.
    - 입력이 같아도(매개변수가 없다는 점이 같다) 결과가 항상 다르고, 변수 `colorIndex`를 바꾸는 부수 효과가 있다.
      - `colorIndex` 변수는 `getNextRainbowColor` 함수에 속하지 않는데도 함수를 호출하면 변수가 바뀐다.
  - 이 함수를 순수한 함수로 고쳐보자.
    - 먼저 외부 변수를 클로저로 감싸는 방법을 보자.
    ```javascript
    const getNextRainbowColor = (function() {
      const colors = ['red', 'orange', 'yellow', 'green', 'blue', 'indigo', 'violet']
      let colorIndex = -1
      return function() {
        if(++colorIndex >= colors.length) colorIndex = 0
        return colors[colorIndex]
      }
    })()
    ``` 
    - 이제 부수효과가 없어졌지만, 아직 입력이 같아도 결과가 다를 수 있으므로 순수한 함수라고 볼 수는 없다.
    - 여기서 이터레이터를 사용하는 것이 나은 방법이다.
    ```javascript
    function getRainbowIterator() {
      const colors = ['red', 'orange', 'yellow', 'green', 'blue', 'indigo', 'violet']
      let colorIndex = -1
      return function() {
        next() {
          if(++colorIndex >= colors.length) colorIndex = 0
          return { value: colors[colorIndex], done: false }
        }
      }
    }
    ``` 
    - 이제 `getRainbowIterator`는 순수한 함수이다. 이 함수는 항상 같은 것(이터레이터)를 반환하며 외부에 아무 영향도 주지 않는다.
      - 사용법이 바뀌긴 했지만, 훨씬 안전하다.
      ```javascript
      const rainbowIterator = getRainbowIterator()
      setInterval(function() {
        document.querySelector('.rainbow').style['background-color'] = rainbowIterator.next().value
      })
      ```
      - 결국 `next()` 메서드는 매번 값을 반환할 테니, 문제를 뒤로 미뤘을 뿐 아니냐고 생각할 수 있다.
        - 틀린 말은 아니지만, `next()`는 함수가 아니라 메서드라는 점에 주목할 필요가 있다.
        - 메서드는 자신이 속한 객체라는 컨텍스트 안에서 동작하므로 메서드의 동작은 그 객체에 의해 좌우된다.
        - 프로그램의 다른 부분에서 `getRainbowIterator`를 호출하더라도 독립적인 이터레이터가 생성되므로 다른 이터레이터를 간섭하지 않는다.

### 4. 그래서?
- 이제 함수의 세 가지 측면, 즉 서브루틴, 값을 반환하는 서브루틴, 순수한 함수라는 측면을 살펴봤으니, 이렇게 구분하는 것에 어떤 의미가 있는지 생각해 봐야 한다.
- 왜 함수를 사용할까?
  - 서브루틴이라는 관점에서 보면 반복을 없애는 것이다.
    - 서브루틴을 쓰면 자주 사용하는 동작을 하나로 묶을 수 있다는 매우 분명한 장점이 있다.
  - 순수한 함수는 조금 더 복잡한 문제이며, 왜? 라는 의문에 대한 답도 더 추상적인 형태이다.
    - "순수한 함수를 쓰면 코드를 테스트하기 쉽고, 이해하기 쉽고, 재사용하기도 더 쉬우니까" 라고 답할 수 있다.
- 함수가 상황에 따라 다른 값을 반환하거나 부작용이 있다면 그 함수는 **컨텍스트에 좌우되는 함수**이다.
  - 어떤 함수가 정말 유용하더라도 부수 효과가 있다면, 그리고 그 함수가 쓰이던 프로그램이 아닌 프로그램에서 사용하려 한다면 문제를 일으킬 수 있다.
- **항상 순수한 함수를 쓰는 습관을 들이는** 편이 좋다.
  - 가끔 함수에 부수 효과가 있더라도 그냥 쓰는 편이 훨씬 쉬울 때가 있다.
  - 그러지 말라는 건 아니지만 단지, 잠시 멈추고 순수한 함수를 사용하는 방법이 있는지 생각해 보자.

#### 1. 함수도 객체다
- 자바스크립트 함수는 `Function` 객체의 인스턴스이다.
  - 현실적으로 이 사실은 함수를 사용하는 데 아무 영향도 없다.
  - `typeof v`를 사용하면 `v`가 함수일 때 "function"이 반환된다는 사실을 알아둘 만하다.
  - `v`가 함수더라도 `v instanceof Object`는 여전히 `ture`를 반환하므로, 변수가 함수인지 아닌지 확인하고 싶다면 먼저 `typeof`를 써보는 편이 좋다.

### 5. IIFE와 비동기적 코드
- IIFE를 사용하는 사례 중 하나는 비동기적 코드가 정확히 동작할 수 있도록 새 변수를 새 스코프에 만드는 것이다.
- ex) 5초에서 시작하고 카운트다운이 끝나면 'go'를 표시하는 타이머 예제
  ```javascript
  var i
  for(i=5; i>=0; i--) {
    setTimeout(function() {
      console.log(i===0 ? 'go!' : i)
    }, (5-i)*1000)
  }
  ``` 
  - 여기서 `let` 대신 `var`를 쓴 이유는 IIFE가 중요하던 시점으로 돌아가서 왜 중요했는지 이해하기 위해서이다.
    - `5, 4, 3, 2, 1, go!`가 출력될 거라 예상했다면, 아쉽지만 틀렸다.
      - `-1`이 여섯 번 출력될 뿐이다.
    - `setTimeout`에 전달된 함수가 루프 안에서 실행되지 않고 루프가 종료된 뒤에 실행됐기 때문이다.
    - `let`을 사용해 블록 수준 스코프를 만들면 이 문제는 해결된다.
  - 블록 스코프 변수가 도입되기 전에는 이런 문제를 해결하기 위해 함수를 하나 더 썼다.
    - 함수를 하나 더 쓰면 스코프가 새로 만들어지고 각 단계에서 `i`의 값이 클로저에 *캡처*된다.
    ```javascript
    function loopBody(i) {
      setTimeout(function() {
        console.log(i===0 ? 'go!' : i)
      }, (5-i)*1000)
    }
    var i
    for(i=5; i>=0; i--) {
      loopBody(i)
    }
    ``` 
    - 루프의 각 단계에서 `loopBody`가 호출된다.
    - 자바스크립트는 매개변수를 값으로 넘기므로 루프의 각 단계에서 함수에 전달되는 것은 변수 `i`가 아니라 `i`의 *값* 이다.
    - 중요한 것은 스코프 일곱 개가 만들어졌고 변수도 일곱 개 만들어졌다는 것이다.(하나는 외부 스코프, 나머지 여섯 개는 `loopBody`를 호출할 때마다)
  - 하지만 루프에 한 번 쓰고 말 함수에 일일이 이름을 붙이는 건 성가신 일이다.
    - 익명 함수를 만들어 즉시 호출하는 IIFE를 사용하는게 더 낫다.
    ```javascript
    var i
    for(i=5; i>=0; i--) {
      (function(i) {
        setTimeout(function() {
          console.log(i===0 ? 'go!' : i)
        }, (5-i)*1000)
      })(i)
    }
    ``` 
  - 블록 스코프 변수를 사용하면 스코프 하나 때문에 함수를 새로 만드는 번거로운 일을 하지 않아도 된다.
    ```javascript
    for(let i=5; i>=0; i--) {
      setTimeout(function() {
        console.log(i===0 ? 'go!' : i)
      }, (5-i)*1000)
    }
    ``` 
    - `let` 키워드를 이런 식으로 사용하면 자바스크립트는 루프의 단계마다 변수 `i`의 복사본을 새로 만든다.
    - 따라서 `setTimeout`에 전달한 함수가 실행될 때는 독립 스코프에서 변수를 받는다.

### 6. 변수로서의 함수
- 함수는 **능동적인 것**이므로, 우리가 보통 수동적이라고 생각하는 데이터와 연결이 잘 되지 않을 수 있다.
- 물론 함수는 **호출되었을 때**는 능동적이지만, 호출하기 전에는 다른 변수와 마찬가지로 수동적이다.
- 함수는 다음과 같은 일을 할 수 있다.
  - 함수를 가리키는 변수를 만들어 별명을 정할 수 있다.
  - 배열에 함수를 넣을 수 있다. 물론 다른 타입 데이터와 섞일 수 있다.
  - 함수를 객체의 프로퍼티로 사용할 수 있다.
  - 함수에 함수를 전달할 수 있다.
  - 함수가 함수를 반환할 수 있다.
  - 함수를 매개변수로 받는 함수를 반환하는 것도 물론 가능하다.
#### 1. 배열 안의 함수
- 배열 안에 함수를 쓰는 패턴은 그리 오래되지 않았지만 점점 늘어나고 있고, 특정 상황에서는 대단히 유용하다.
- 자주 하는 일을 한 셋으로 묶는 *파이프라인*이 좋은 예이다.
- 배열을 사용하면 작업 단계를 언제든 쉽게 바꿀 수 있다는 장점이 있다.
  - 어떤 작업을 빼야 한다면 배열에서 제거하기만 하면 되고, 추가할 작업이 있다면 배열에 추가하기만 하면 된다.
- ex) 그래픽 변형
  ```javascript
  const sin = Math.sin
  const cos = Math.cos
  const theta = Math.PI/4
  const zoom = 2
  const offset = [1, -3]

  const pipeline = [
    function rotate(p) {
      return {
        x: p.x * cos(theta) - p.y * sin(theta),
        y: p.x * sin(theta) + p.y * cos(theta),
      }
    },
    function scale(p) {
      return { x: p.x * zoom, y: p.y * zoom }
    },
    function translate(p) {
      return { x: p.x + offset[0], y: p.y + offset[1] }
    },
  ]

  const p = { x: 1, y: 1 }
  let p2 = p
  for(let i=0; i<pipeline.length; i++) {
    p2 = pipeline[i](p2)
  }
  // p2는 이제 p1을 좌표 원점 기준으로 45도 회전하고
  // 원점에서 2 단위만큼 떨어뜨린 후
  // 1단위 오른쪽, 3단위 아래쪽으로 움직인 점이다.
  ``` 
  - 파이프라인의 각 함수를 호출할 때 사용한 문법을 보면, `pipeline[i]`는 파이프라인의 `i`번째 요소에 접근하고, 그 요소는 함수로 평가된다.
  - 그러면 괄호를 써서 함수를 호출한다. 각 함수에 점을 전달하고, 반환값을 다시 그 점에 할당한다. 이런식으로 파이프라인의 각 단계가 모두 적용된다.
- 일정한 순서에 따라 함수를 실행해야 한다면 파이프라인을 써서 효윺적으로 일할 수 있다.

#### 2. 함수에 함수 전달
- 함수에 함수를 전달하는 다른 용도는 비동기적 프로그래밍이다.
  - 이런 용도로 전달하는 함수를 보통 *콜백*이라 부른다.
  - 콜백 함수는 자신을 감싼 함수가 실행을 마쳤을 때 호출된다.
- 함수에 함수를 전달하는 대표적인 사례가 콜백이긴 하지만, 그게 전부는 아니다.
  - 함수는 동작이고, 함수를 받는 함수는 그 동작을 활용할 수 있다.
- ex) 배열에 들어있는 숫자의 제곱을 합해서 반환하는 함수
  - `sunOfSquares`를 만들어도 되지만, 세제곱을 합해서 반환하는 함수도 필요하다면? 이런 상황에서 함수에 함수를 전달하는 발상이 필요하다.
  ```javascript
  function sum(arr, f) {
    if(typeof f != 'function') f = x => x
    return arr.reduce((a, x) => a += f(x), 0)
  }
  sum([1, 2, 3])
  sum([1, 2, 3], x => x*x)
  sum([1, 2, 3], Math.pow(x, 3))
  ``` 
  - 임의의 함수를 `sum`에 전달하면 원하는 일을 거의 모두 할 수 있다.
  - `sum`을 함수를 넘기지 않고 호출해야 할 때는 에러를 방지하기 위해 함수가 아닌 것은 모두 'null 함수', 즉 아무 일도 하지 않는 것으로 바꾼다.
  - 배열의 모든 요소에서 `null` 함수를 호출하는 것보다는 요소를 그냥 더하는 함수를 한 번 호출하는 등 더 효윺적인 방법이 있겠지만, 이런식으로 '안전한' 함수를 만들 수 있다는 걸 충분히 이해했을 것이다.

#### 3. 함수를 반환하는 함수
- 함수를 반환하는 함수는 아마 함수의 가장 난해한 사용법이겠지만, 그만큼 유용하기도 하다.
- ex) `sum`함수 고치기
  - 이 함수는 각 요소를 더하기 전에 해당 요소를 바꾸는 함수를 받는다.
  - 원한다면 `sumOfSquares` 함수를 만들 수도 있다고 했는데, 그런 함수가 정말 필요하다고 해보자.
  - *배열과 함수*를 받는 함수로는 만족스러운 결과를 얻을 수 없고, 배열 *하나만* 받아서 제곱의 합을 반환하는 함수가 필요하다.
  - 먼저, 이미 만들어 둔 `sum` 함수를 활용하는 방법이 있다.
    ```javascript
    function sumOfSquares(arr) {
      return sum(arr, x => x*x)
    }
    ``` 
    - 필요한 것이 함수라면 가장 간단한 해결책이 될 수 있다.
  - 하지만 제곱근의 합, 세제곱의 합 등 이런 패턴이 반복된다면 필요한 함수를 반환하는 함수를 만들어 문제를 해결할 수 있다.
    ```javascript
    function newSummer(f) {
      return arr => sum(arr, f)
    }
    ``` 
    - 새 함수 `newSummer`가 반환하는 함수는 단 하나의 매개변수만 받으면서도, 우리가 원하는 중간 함수를 마음대로 쓸 수 있다.
    ```javascript
    const sumOfSquares = newSummer(x => x*x)
    const sumOfCubes = newSummer(x => Math.pow(x, 3))
    sumOfSquares([1, 2, 3]) // 14
    sumOfCubes([1, 2, 3])   // 36
    ``` 
    > 이 예제처럼 매개변수 여러 개를 받는 함수를 매개변수 하나만 받는 함수로 바꾸는 것을 *커링(currying)* 이라 부른다.
  - 함수를 반환하는 함수의 예제를 더 보고 싶다면 자바스크립트의 웹 개발 프레임워크로 널리 쓰이는 익스프레스나 Koa 같은 미들웨어 패키지를 살펴봐라. 미들웨어는 대게 함수를 반환하는 함수 형태로 만들어진다.

### 7. 재귀
- **재귀** 역시 널리 쓰이며 함수를 활용하는 중요한 패턴이다.
- 재귀란 자기 자신을 호출하는 함수이다.
- 같은 일을 반복하면서 그 대상이 점차 줄어드는 상황에서 유용하게 활용할 수 있다.
- ex) 건초 더미에서 바늘을 찾는 예제
  > 1. 건초 더미에서 바늘이 보이면 3단계로 이동한다.
  > 2. 건초 더미에서 건초를 하나 덜어낸다. 1단계로 이동한다.
  > 3. 찾았다!
  - 바늘을 찾을 때까지 건초 더미에서 건초를 하나씩 제외하는 소거법이며, 이것이 재귀이다.
  ```javascript
  function findNeedle(haystack) {
    if(haystack.length === 0) return 'no haystack here!'
    if(haystack.shift() === 'needle') return 'found it!'
    return findNeedle(haystack)
  }

  findNeedle(['hay', 'hay', 'hay', 'deedle', 'hay', 'hay'])
  ``` 
  - 이 재귀 함수에서 눈여겨볼 점은 모든 가능성을 전부 고려한다는 것이다.
    - 가능한 경우의 수는 `haystack`이 비어있거나, 배열의 첫 번째 요소가 바늘이거나, 배열의 첫 번째 요소가 바늘이 아닌 경우이다.
    - 마지막은 배열의 어딘가에 바늘이 들어있을 테니 `Array.prototype.shift`로 배열의 첫 번째 요소를 제거하고 함수를 반복한다.
  - 재귀 함수에는 *종료 조건*이 있어야 한다.
    - 종료 조건이 없다면 자바스크립트 인터프리터에서 스택이 너무 깊다고 판단할 때까지 재귀 호출을 계속하다가 프로그램이 멈춘다.
    - `findNeedle`함수에는 두 가지 종료 조건이 있다.
      - 바늘을 찾거나, 배열이 비어 있으면 재귀 호출을 멈춘다.
      - 호출할 때마다 배열의 길이가 줄어들므로 언젠가는 두 조건 중 하나를 만족하게 된다.
- ex) 숫자의 계승을 찾는 예제
  - > 4! = 4 x 3 x 2 x 1 = 24
  ```javascript
  function fact(n) {
    if(n === 1) return 1
    return n * fact(n-1)
  }
  ``` 
  - 이 함수의 종료 조건은 `n === 1`이고, 재귀 호출할 때마다 숫자 `n`은 1씩 줄어들다가 결국 1이 된다.
  - 이 함수에 0이나 음수를 넘겨서 호출하면 물론 에러가 생기지만, 그런 상황을 막을 조건문을 넣는건 쉽다.

