## Chapter 14. 비동기적 프로그래밍
- 사용자의 행동은 전적으로 비동기적이다.
    - 사용자가 언제 클릭할지, 터치할지, 또는 타이핑할지 전혀 알 수 없다.
    - 하지만 비동기적 실행이 사용자 입력 하나 때문에 필요한 건 아니다.
    - 사실 *자바스크립트 본성 때문에* 비동기적 프로그래밍이 필요하다.
- 자바스크립트 애플리케이션은 **단일 스레드**에서 동작한다.
    - 즉, 자바스크립트는 한 번에 한 가지 일만 할 수 있다.
    - 자바스크립트가 싱글 스레드라는 얘기를 듣고 할 수 있는 일이 제한된다고 느낄지도 모르지만, 사실 멀티스레드에서 겪어야 하는 문제를 신경 쓰지 않아도 된다는 장점도 있다.
    - 물론 부드럽게 동작하는 소프트웨어를 만들기 위해서는 사용자의 입력뿐만 아니라 여러 문제를 비동기적 관점에서 생각해야 한다.
- 자바스크립트는 매우 일찍부터 비동기적 실행 매커니즘이 존재했지만, 자바스크립트의 인기가 높아지고 자바사크립트로 만드는 소프트웨어가 점점 복잡해짐에 따라 비동기적 프로그래밍에 필요한 장치들이 추가됐다.
    - 처음에는 콜백이 있었고, 프라미스가 뒤를 이었으며 마지막은 제너레이터다.
    - 제너레이터 자체는 비동기적 프로그래밍을 전혀 지원하지 않는다.
    - 제너레이터를 비동기적으로 사용하려면 프라미스나 특수한 콜백과 함께 사용해야 한다.
    - 프라미스 역시 콜백에 의존한다.
    - 콜백은 제너레이터나 프라미스 외에도 이벤트 처리 등에 유용하게 쓸 수 있다.
- 사용자 입력 외에, 비동기적 테크닉을 사용해야 하는 경우는 크게 세 가지이다.
    - Ajax 호출을 비롯한 네트워크 요청
    - 파일을 읽고 쓰는 등의 파일시스템 작업
    - 의도적으로 시간 지연을 사용하는 기능(알람 등)

### 1. 비유
- 콜백
    - 어떤 음식점은 내가 줄을 서서 기다리지 않도록, 내 전화번호를 받아서 자리가 나면 전화를 해준다.
    - 자리가 나면 **내가 알 수 있도록 하는 수단을 내가 음식점 주인에게 넘겨준다.**
    - 음식점은 다른 손님을 대접하면 되고, 나는 다른 일을 하면 된다.
    - 어느 쪽도 서로를 기다리지 않는다.
- 프라미스
    - 다른 음식점은 자리가 났을 때 진동하는 호출기를 나에게 넘겨준다.
    - 자리가 나면 **내가 알 수 있도록 하는 수단을 음식점에서 나에게 넘겨준다.**

### 2. 콜백
- 콜백은 자바스크립트에서 가장 오래된 비동기적 메커니즘이다.
- 콜백은 간단히 말해 나중에 호출할 함수이다.
    - 콜백 함수 자체에는 특별한 것이 전혀 없다. 일반적인 자바스크립트 함수일 뿐이다.
- 콜백 함수는 일반적으로 다른 함수에게 넘기거나 객체의 프로퍼티로 사용한다.
- 항상 그런건 아니지만, 콜백은 보통 익명 함수로 사용한다.
- ex) `setTimeout` 을 사용하는 에제
    - `setTimeout`은 콜백의 실행을 지정된 밀리초만큼 지연하는 내장 함수이다.
        ```javascript
        console.log('Before timeout: ' + new Date())
        function f() {
            console.log('After timeout: ' + new Date())
        }
        setTimeout(f, 60*1000)
        console.log('I happen after setTimeout!')
        console.log('Me too!')
        ``` 
    - 초보자들은 우리가 **작성하는** 코드와 실제 **실행되는** 코드의 순서가 다르다는 사실에 종종 당황하곤 한다.
    - 우리는 컴퓨터가 우리가 작성한 코드를 정확히 그 순서대로 실행할 거라고 기대한다.
        - 기대대로 된다면 좋겠지만, 그것은 비동기적이지 않다.
    - 비동기적 실행의 가장 큰 목적, 가장 중요한 점은 **어떤 것도 차단하지 않는다**는 것이다.
    - 자바스크립트는 싱글 스레드를 사용하므로, 우리가 컴퓨터에 60초 동안 대기한 후 코드를 실행하라고 지시한다면, 그리고 그 실행이 동기적으로 이루어진다면 프로그램이 멈추고, 사용자 입력을 받아들이지 않고, 화면도 업데이트 하지 않을 것이다.
        - 비동기적 테크닉은 이런 식으로 멈추는 일을 막아준다.
    - 이름 붙은 함수를 써야 하는 타당한 이유가 없다면, 일반적으로는 다음과 같이 익명 함수를 사용한다.
        ```javascript
        setTimeout(function() {
            console.log('After timeout: ' + new Date())
        }, 60*1000)
        ``` 

#### 1. `setInterval`과 `clearInterval`
- `setTimeout`은 콜백 함수를 한 번만 실행하고 멈추지만, `setInterval`은 콜백을 정해진 주기마다 호출하며 `clearInterval`을 사용할 때까지 멈추지 않는다.
- ex) `setInterval` 을 사용하는 에제
    - 다음 코드는 분이 넘어가거나 10회째가 될 때까지 5초마다 콜백은 실행한다.
        ```javascript
        const start = new Date()
        let i=0
        const intervalId = setInterval(function() {
            let now = new Date()
            if(now.getMinutes() !== start.getMinutes() || ++i > 10)
                return clearInterval(intervalId)
            console.log(`${i}: ${now}`)
         }, 5*1000)
        ```
    - 이 예제를 보면 `setInterval`이 ID를 반환한다는 사실을 알 수 있다.
    - 이 ID를 써서 실행을 멈출 수 있다.
    - `clearInterval`은 `setInterval`이 반환하는 ID를 받아 타입아웃을 멈춘다.
> `setTimeout`, `setInterval`, `clearInterval`은 모두 전역 객체(브라우저에서는 window, 노드에서는 global)에 정의되어 있다.

#### 2. 스코프와 비동기적 실행
- 비동기적 실행에서 혼란스럽고 에러도 자주 일어나는 부분은 스코프와 클로저가 비동기적 실행에 영향을 미치는 부분이다.
- 함수를 호출하면 항상 클로저가 만들어진다.
    - 매개변수를 포함해 함수 안에서 만든 변수는 모두 무언가가 자신에 접근할 수 있는 한 계속 존재한다.
- ex) 5초 카운트다운 에제
    ```javascript
    function countdown() {
        let i
        console.log('countdown:')
        for(i=5; i>=0; i--) {
            setTimeout(function() {
                console.log(i===0 ? 'GO!' : i)
            }, (5-i)*1000)
        }
    }
    countdown()
    ```
    - 코드를 보면 5에서부터 카운트다운 할 것처럼 보이지만, 결과는 -1이 여섯 번 반복될 뿐이고, 'GO!'는 나타나지 않는다.
    - 이 문제는 변수를 `for` 루프 밖에서 선언했으므로 일어난다.
        - `for` 루프가 실행을 마치고 `i`의 값이 -1이 된 *다음에서야* 콜백이 샐행되기 시작한다. 문제는, 콜백이 실행될 때 `i`의 값은 이미 -1이란 것이다.
    - 스코프와 비동기적 실행이 어떻게 연관되는지 이해하는 것이 중요하다.
        - `countdown`을 호출하면 변수 `i`가 들어있는 클로저가 만들어진다.
        - `for` 루프 안에서 만드는 콜백은 모두 `i`에 접근할 수 있고, 그들이 접근하는 `i`는 똑같은 `i`이다.
    - `for` 루프 안에서 `i`를 두 가지 방법으로 사용했다.
        - `i`를 써서 타임아웃을 계산하는 부분은 예상대로 동작한다.
            - 이 계산이 예상대로 동작한 것은 동기적으로 실행됐기 때문이다.
        - 사실 `setTimeout`을 호출하는 것 역시 동기적이다.
            - `setTimeout`을 동기적으로 호출해야만 콜백을 언제 호출할지 계산할 수 있다.
            - 비동기적인 부분은 `setTimeout`에 전달된 함수이다.
    - 이 문제는 즉시 호출하는 함수 표현식(IIFE)로 해결할 수 있고, 좀 더 간단하게 `i`를 `for` 루프 선언부에서 선언하는 방식으로도 해결할 수 있다.
        ```javascript
        function countdown() {
            console.log('countdown:')
            for(let i=5; i>=0; i--) {
                setTimeout(function() {
                    console.log(i===0 ? 'GO!' : i)
                }, (5-i)*1000)
            }
        }
        countdown()
        ```
        - 여기서 주의할 부분은 콜백이 어느 스코프에서 선언됐느냐이다.
            - 콜백은 자신을 선언한 스코프(클로저)에 있는 것에 접근할 수 있다. 따라서 `i`의 값은 콜백이 실제 실행되는 순간마다 다를 수 있다.
            - 이 원칙은 콜백뿐만 아니라 *모든 비동기적 테크닉*에 적용된다.

#### 3. 오류 우선 콜백
- 노드가 점점 인기를 얻어가던 시기에 *오류 우선 콜백*이라는 패턴이 생겼다.
- 콜백을 사용하면 예외 처리가 어려워지므로, 콜백과 관련된 에러를 처리할 방법의 표준이 필요했다.
    - 이에 따라 나타난 패턴이 콜백의 첫 번째 매개변수에 에러 객체를 쓰자는 것이었다.
    - 에러가 `null`이거나 `undefined`이면 에러가 없는 것이다.
- 오류 우선 콜백을 다룰 때 가장 먼저 생각할 것은 에러 매개변수를 체크하고 그에 맞게 대응하는 것이다.
- ex) 노드에서 파일 읽기
    ```javascript
    const fs = require('fs')

    const fname = 'may_of_may_not_exist.txt'
    fs.readFile(fname, function(err, data) {
        if(err) return console.error(`error reading file ${fname} : ${err.message}`)
        console.log(`${fname} contents: ${data}`)
    }) 
    ```
    - 콜백에서 가장 먼저 하는 일은 `err`이 참 같은 값인지 확인하는 것이다.
        - `err`이 참 같은 값이라면 파일을 읽는데 문제가 있다는 뜻이므로 콘솔에 오류를 보고하고 *즉시 빠져나온다.*
    - 에러 객체를 체크해야 한다는 사실을 기억하고, 아마 로그를 남기기도 하겠지만, 빠져나와야 한다는 사실을 잊는 사람이 많다.
        - 콜백은 사용하는 함수는 대게 콜백이 성공적이라고 가정하고 만들어진다.
        - 그런데 콜백이 실패했으니, 빠져나가지 않으면 오류를 예약하는 것이나 다름없다.
- 프라미스를 사용하지 않으면 오류 우선 콜백은 노드 개발의 표준이나 다름없다.
    - 콜백을 사용하는 인터페이스를 만들 때는 오류 우선 콜백을 사용하길 강력히 권한다.

#### 4. 콜백 헬
- 콜백을 사용해 비동기적으로 실행할 수 있긴 하지만, 현실적인 단점이 있다.
    - 한 번에 여러가지를 기다려야 한다면 콜백을 관리하기가 상당히 어려워진다.
- ex) 노트에서 세 가지 파일의 콘텐츠를 읽고, 60초가 지난 다음 이들을 겹합해 네 번째 파일에 기록하기
    ```javascript
    const fs = require('fs')

    fs.readFile('a.txt', function(err, dataA) {
        if(err) console.error(err)
        fs.readFile('b.txt', function(err, dataB) {
            if(err) console.error(err)
            fs.readFile('c.txt', function(err, dataC) {
                if(err) console.error(err)
                setTimeout(function() {
                    fs.writeFile('d.txt', dataA+dataB+dataC, function(err) {
                        if(err) console.error(err)
                    })
                }, 60*1000)
            })
        })
    }) 
    ```
    - 이런 코드를 콜백 헬이라 부른다.
        - 중괄호로 둘러싸여 끝없이 중첩된 삼각형의 코드 블록들은 마치 버뮤다 삼각지대처럼 보일 지경이다.
    - 더 심각한 문제는 에러 처리이다.
        - 이 예제에서는 에러를 기록하기만 했지만, 예외를 일으키려 했다면 더더욱 심각한 문제일 것이다.
- ex) 파일 읽기
    ```javascript
    const fs = require('fs')
    function readSketchyFile() {
        try {
            fs.readFile('does_not_exitst.txt', function(err, data) {
                if(err) throw err
            })
        } catch(err) {
            console.log('warning')
        }
    }
    readSketchyFile()
    ```
    - 이 코드는 얼핏 타당해 보이고, 예외 처리도 수행하는 방어적인 코드처럼 보인다.
    - 하지만 예상되는 에러가 문제를 일으키지 않도록 대비했는데도 프로그램은 멈춘다.
        - 예외 처리가 의도대로 동작하지 않는 이유는 `try...catch` 블록은 같은 함수 안에서만 동작하기 때문이다.
        - `try...catch` 블록은 `readSketchyFile` 함수 안에 있지만, 정작 예외는 `fs.readFile`이 콜백으로 호출하는 익명 함수 안에서 일어났다.
    - 또한, 콜백이 우연히 두 번 호출되거나, 아예 호출되지 않는 경우를 방지하는 안전장치도 없다.
        - 콜백이 정확히 한 번만 호출될 것을 가정하고 코드를 작성한다면, 애석하지만 자바스크립트는 그런 걸 보장하지는 않는다.
    - 이런 문제가 해결할 수 없는 문제는 아니다.
        - 하지만 비동기적 코드가 늘어나면 늘어날수록 버그가 없고 관리하기 쉬운 코드를 작성하기는 매우 어려워진다.
        - 그래서 프라미스가 등장했다.

**Reference**
> - [MDN web docs - WindowTimers.setTimeout()](https://developer.mozilla.org/ko/docs/Web/API/WindowTimers/setTimeout)
> - [MDN web docs - WindowOrWorkerGlobalScope.setInterval()](https://developer.mozilla.org/en-US/docs/Web/API/WindowOrWorkerGlobalScope/setInterval)
> - [MDN web docs - WindowOrWorkerGlobalScope.clearInterval()](https://developer.mozilla.org/en-US/docs/Web/API/WindowOrWorkerGlobalScope/clearInterval)

### 3. 프라미스
- 프라미스는 콜백의 단점을 해결하려는 시도 속에서 만들어졌다.
- 프라미스가 콜백을 대체하는 것은 아니다.
    - 사실 프라미스에서도 콜백을 사용한다.
    - 프라미스는 콜백을 예측 가능한 패턴으로 사용할 수 있게 하며, 프라미스 없이 콜백만 사용했을 때 나타날 수 있는 엉뚱한 현상이나 찾기 힘든 버그를 상당수 해결한다.
- 프라미스 기반 비동기적 함수를 호출하면 그 함수는 `Promise` 인스턴스를 반환한다.
    - 프라미스는 **성공**하거나, **실패**하거나 단 두 가지뿐이다.
    - 프라미스는 성공 혹은 실패 둘 중 **하나만** 일어난다고 확신할 수 있다.
    - 성공한 프라미스가 나중에 실패하는 일 같은 건 절대 없다.
    - 또한 성공이든 실패단 *단 한 번만* 일어난다.
    - 프라미스가 성공하거나 실패하면 그 프라미스를 **결정됐다**고 한다.
- 프라미스는 객체이므로 어디든 전달할 수 있다는 점도 콜백에 비해 간편한 장점이다.
    - 비동기적 처리를 여기서 하지 않고 다른 함수에서 처리하게 하고 싶다면 프라미스를 넘기기만 하면 된다.

#### 1. 프라미스 만들기
- 프라미스는 `resolve`(성공)와 `reject`(실패) 콜백이 있는 함수로 새 `Promise` 인스턴스를 만들기만 하면 된다.
- ex) 5초 카운트다운
    - 매개변수를 받게 만들어서 5초 카운트다운에 메이지 않게 하고, 카운트다운이 끝나면 프라미스를 반환
    ```javascript
    function countdown(seconds) {
        return new Promise(function(resolve, reject) {
            for(let i=seconds; i>=0; i--) {
            setTimeout(function() {
                if(i>0) console.log(i + '...')
                else resolve(console.log('GO!'))
            }, (seconds-i)*1000)
        })
    }
    ```
    - 이대로라면 별로 좋은 함수는 아니다.
        - 너무 장황한 데다가, 콘솔을 아예 스지 않기를 원할 수도 있다.
    - 하지만 프라미스를 어떻게 만드는지는 잘 드러나 있다.
        - `resolve`와 `reject`는 함수이다.
        - 여러번 호출해도 첫 번째로 호출한 것만 의미가 있다.
        - 프라미스는 성공 또는 실패를 나타낼뿐이다.

#### 2. 프라미스 사용
- `countdown` 함수를 어떻게 사용하는지 알아보자.
    - 프라미스는 무시해버리고 `countdown(5)` 처럼 호출해도 된다.
    - 카운트다운은 여전히 동작하고, 무슨 말인지 알기 어려운 프라미스는 신경쓰지 않아도 된다.
    - 하지만 프라미스의 장점을 이용하고 싶다면 어떻게 해야 할까?
    - ex) 반환된 프라미스를 사용하는 예제
        ```javascript
        countdown(5).then(
            function() {
                console.log('countdown completed successfully.')
            },
            function(err) {
                console.log('countdown experienced an error: ' + err.message)
            }
        )
        ```
        - 이 예제는 반환된 프라미스를 변수에 할당하지 않고 `then` 핸들러를 바로 호출했다.
            - `then` 핸들러는 성공 콜백과 에러 콜백을 받는다.
            - 경우의 수는 단 두 가지이다. 성공 콜백이 실행되거나, 에러 콜백이 실행되거나.
            - 프라미스는 `catch` 핸들러도 지원하므로 핸들러를 둘로 나눠서 써도 된다.
            ```javascript
            const p = countdown(5)
            p.then(function() {
                console.log('countdown completed successfully.')
            })
            p.catch(function(err) {
                console.log('countdown experienced an error: ' + err.message)
            })
            ```
    - ex) `countdown` 함수에서 13을 만나면 에러를 내도록 수정
        ```javascript
        function countdown(seconds) {
            return new Promise(function(resolve, reject) {
                for(let i=seconds; i>=0; i--) {
                setTimeout(function() {
                    if(i===13) return reject(new Error('OMG'))
                    if(i>0) console.log(i + '...')
                    else resolve(console.log('GO!'))
                }, (seconds-i)*1000)
            })
        }
        ```
        - 13보다 작은 숫자를 사용하면 에러 없이 카운트다운이 실행되지만 13이상의 숫자를 사용하면 13에서 에러가 일어난다.
            - 하지만 콘솔에는 12부터 다시 카운트를 기록한다.
            - `reject`나 `resolve`가 함수를 멈추지는 않는다. 그저 프라미스의 상태를 관리할 뿐이다.
        - 일반적으로 함수가 성공이든 실패든 결정됐다면 멈춰야 하는데 이 예제는 실패한 후에도 계속 진행한다.
            - 필요한 것은 카운트다운을 컨틀로 할 수 있는 기능이다.
        - 프라미스는 비동기적 작업이 성공 또는 실패하도록 확정하는, 매우 안전하고 잘 정의된 메커니즘을 제공하지는 현재는 진행 상황을 전혀 알려주지 않는다.
            - 즉 프라미스는 완료되거나 파기될 뿐, '50% 진행됐다'라는 개념은 아예 없다.
            - [Q](https://github.com/kriskowal/q) 같은 프라미스 라이브러리에는 진행 상황을 보고하는 기능이 있고, 나중에 언젠가는 이런 기능이 자바스크립트 프라미스에 도입될 수 도 있다.

#### 3. 이벤트
- 이벤트가 일어나면 이벤트 발생을 담당하는 개체에서 이벤트가 일어났음을 알린다.
- 필요한 이벤트는 모두 주시할 수 있다.
    - 어떻게 이벤트를 주시할까? 물론 콜백을 통해서이다.
- 이벤트 시스템을 직접 만드는 것도 별로 어려운 일은 아니지만, 노드에는 이미 이벤트를 지원하는 모듈 `EventEmitter`가 내장돼 있다.
- ex) `EventEmitter` 모듈을 써서 `countdown` 함수 개선
    - `EventEmitter`는 원래 클래스와 함께 사용하도록 설계되었으므로 먼저 `countdown` 함수를 `Countdown` 클래스로 바꿔 보자.
    ```javascript
    const EventEmitter = require('events').EventEmitter

    class Countdown extends EventEmitter {
        constructor(seconds, superstitious) {
            super()
            this.seconds = seconds
            this.superstitious = !!superstitious
        }
        go() {
            const countdown = this
            return nuew Promise(function(resolve, reject) {
                for(let i=countdown.seconds; i>=0; i--) {
                    setTimeout(function() {
                        if(countdown.superstitious && i===13)
                            return reject(new Error('OMG'))
                        countdown.emit('tick', i)
                        if(i===0) resolve()
                    }, (countdown.seconds-i)*1000)
                }
            })
        }
    }
    ```
    - `EventEmitter`를 상속하는 클래스는 이벤트를 발생시킬 수 있다.
    - 실제로 카운트다운을 시작하고 프라미스를 반환하는 부분은 `go` 메서드이다.
        - `go` 메서드 안에서 가장 먼저 한 일은 `countdown`에 `this`를 할당한 것이다.
        - 카운트다운이 얼마나 남았는지 알기 위해서는 `this` 값을 알아야 하고, 13인지 아닌지 열시 *콜백 안에서* 알아야 한다.
        - `this`는 특별한 변수이고 콜백 안에서는 값이 달라진다.
        - 따라서 `this`의 현재 값을 다른 변수에 저장해야 프라미스 안에서 쓸 수 있다.
    - 가장 중요한 부분은 `countdown.emit('tick', 1)`이다.
        - 이 부분에서 `tick` 이벤트를 발생시키고, 필요하다면 프로그램의 다른 부분에서 이 이벤트를 주시할 수 있다.
    - 개선한 카운트다운은 다음과 같이 사용할 수 있다.
        ```javascript
        const c = new Countdown(5)

        c.on('tick', function(i) {
            if(i>0) console.log(i + '...'))
        }
        c.go()
            .then(function() {
                console.log('GO!')
            })
            .catch(function(err) {
                console.error(err.message)
            })
        ```
        - `EventEmitter`의 `on` 메서드가 이벤트를 주시하는 부분이다.
        - 이 예제에서는 `tick` 이벤트 전체에 콜백을 등록했다.
            - `tick`이 0이 아니면 출력한 다음 카운트다운을 시작하는 `go`를 호출한다.
            - 카운트다운이 끝나면 GO!를 출력한다.
        - 이제 카운트다운을 어떻게 활용할지 마음대로 바꿀 수 있고, 카운트다운이 끝났을 때 완료되는 프라미스도 생겼다.
        - 하지만 `Countdown` 인스턴스가 13에 도달했을 때 프라미스를 파기했는데도 카운트다운이 계속 진행되는 문제가 있다.
        ```javascript
        const c = new Countdown(15, true)
            .on('tick', function(i) {
                if(i>0) console.log(i + '...')
            })

        c.go()
            .then(function() {
                console.log('GO!')
            })
            .catch(function(err) {
                console.error(err.message)
            })
        ```
        - 여전히 모든 카운트가 출력되며 0이 될 때까지 진행한다.
        - 이 문제를 해결하기 조금 어려운건 타임아웃이 이미 모두 만들어졌기 때문이다.
        - 이 문제를 해결하려면 더 진행할 수 없다는 사실을 아는 즉시 대기중인 타임아웃을 모두 취소하면 된다.
        ```javascript
        const EventEmitter = require('events').EventEmitter

        class Countdown extends EventEmitter {
            constructor(seconds, superstitious) {
                super()
                this.seconds = seconds
                this.superstitious = !!superstitious
            }
            go() {
                const countdown = this
                const timeoutIds = []
                return nuew Promise(function(resolve, reject) {
                    for(let i=countdown.seconds; i>=0; i--) {
                        timeoutIds.push(setTimeout(function() {
                            if(countdown.superstitious && i===13) {
                                timeoutIds.forEach(clearTimeout)
                                return reject(new Error('OMG'))
                            }
                            countdown.emit('tick', i)
                            if(i===0) resolve()
                        }, (countdown.seconds-i)*1000))
                    }
                })
            }
        }
        ```

#### 4. 프라미스 체인
- 프라미스에는 *체인*으로 연결할 수 있다는 장점이 있다.
    - 즉, 프라미스가 완료되면 다른 프라미스를 반환하는 함수를 즉시 호출할 수 있다.
 - ex) `launch` 함수를 만들어 카운트 다운이 끝나면 실행
    ```javascript
    function launch() {
        return new Promise(function(resolve, reject) {
            console.log('Lift off!')
            setTimeout(function() {
                resolve('In orbit!')
            }, 2*1000)
        })
    }
    ```
    - 이 함수를 카운트다운에 쉽게 묶을 수 있다.
    ```javascript
    const c = new Countdown(5)
        .on('tick', i => console.log(i + '...'))
    c.go()
        .then(launch)
        .then(function(msg) {
            console.log(msg)
        })
        .catch(function(err) {
            console.error('error')
        })
    ```
    - 프라미스 체인을 사용하면 모든 단계에서 에러를 캐치할 필요는 없다.
        - 체인 어디에서든 에러가 생기면 체인 전체가 멈추고 `catch` 핸들러가 동작한다.

#### 5. 결정되지 않는 프라미스 방지하기
- 프라미스는 비동기적 코드를 단순화하고 콜백이 두 번 이상 실행되는 문제를 방지한다.
- 하지만 `resolve`나 `reject`를 호출하는 걸 잊어서 프라미스가 결정되지 않는 문제까지 자동으로 해결하지는 못한다.
    - 에러가 일어나지 않으므로 이런 실수는 찾기 매우 어렵다.
    - 복잡한 시스템에서 결정되지 않은 프라미스는 그냥 잊혀질 수 있다.
- 결정되지 않는 프라미스를 방지하는 한 가지 방법은 프라미스에 타임아웃을 거는 것이다.
    - 충분한 시간이 지났는데도 프라미스가 결정되지 않으면 자동으로 실패하게 만들 수 있다.
    - 물론 얼마나 기다려야 '충분히' 기다렸는지는 스스로 판단해야 한다.
- ex) `launch` 함수에 에러 조건 넣기
    ```javascript
    function launch() {
        return new Promise(function(resolve, reject) {
            if(Math.random() < 0.5) return
            console.log('Lift off!')
            setTimeout(function() {
                resolve('In orbit!')
            }, 2*1000)
        })
    }
    ```
    - 이 코드는 정말 무책임하다. `rejcet`를 호출하지 않는데다가, 심지어 콘솔에 기록하지도 않는다.
    - 다음과 같이 프라미스에 타임아웃을 거는 함수를 만들 수 있다.
    ```javascript
    function addTimeout(fn, timeout) {
        if(timeout === undefined) timeout = 1000
        return function(...args) {
            return new Promise(function(resolve, reject) {
                const tid = setTimeout(reject, timeout, new Error('promise timed out'))
                fn(...args)
                    .then(function(...args) {
                        clearTimeout(tid)
                        resolve(...args)
                    })
                    .catch(function(...args) {
                        clearTimeout(tid)
                        reject(...args)
                    })
            })
        }
    }
    ```
    - 프라미스에 타임아웃을 걸기 위해서는 함수를 반환하는 함수가 필요한데, 쉬운 문제는 아니다.
    - 이 함수를 완벽히 이해하는 건 상당한 고급 사용자에게나 가능한 일이니 당장 이해하지 못해도 괜찮다.
    - 하지만 이 함수를 사용하는 건 아주 쉽다.
        - 프라미스를 반환하는 어떤 함수에든 타임아웃을 걸 수 있다.
    ```javascript
    c.go()
        .then(addTimeout(launch, 11*1000))
        .then(function(msg) {
            console.log(msg)
        })
        .catch(function(err) {
            console.error('error')
        })
    ```
    - 이제 `launch` 함수에 문제가 있더라도 프라미스 체인은 항상 결정된다.

**Reference**
> - [MDN web docs - Promise](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise)

### 4. 제너레이터
- 제너레이터는 함수와 호출자 사이의 양방향 통신을 가능하게 한다.
- 제너레이터는 원래 동기적인 성격을 가졌지만, 프라미스와 결합하면 비동기 코드를 효율적으로 관리할 수 있다.
- 비동기 코드에서 가장 어려운 부분은 무엇일까?
    - 동기적인 코드에 비해 만들기가 어렵다는 점이다.
    - 어려운 문제를 해결해야 할 때 우리는 대게 동기적으로 생각한다.
    - 1단게, 2단계, 3단계 하는식으로 말이다.
    - 하지만 이렇게 하면 성능 문제가 있어, 비동기 코드는 성능 문제를 해결하기 위해 등장했다.
    - 제너레이터를 사용하면 비동기적 코드를 사용한 성능 개선을 일정 부분 가능하게 한다.

<!-- generator run example -->

#### 1. 1보 전진과 2보 후퇴?
- *'그냥 세 개의 파일을 동시에 읽으면 더 효율적이지 않나?'* 라는 질문이 있을 수 있다.
    - 이 질문에 대한 답은 문제에 따라, 자바스크립트 엔진에 따라, 운영체제에 따라, 파일시스템에 따라 크게 다를 수 있다.
    - 하지만 복잡한 부분은 잠시 미뤄두고, 세 파일을 읽는 **순서는 상관이 없다는 것**, 그리고 설령 세 파일을 동시에 읽었다 한들 과연 효율적일지는 의문스럽다는 점을 상기하자.
- `Promise`에는 `all` 메서드가 있다.
    - 이 메서드는 배열로 받은 프라미스가 모두 완료될 때 완료되며, 가능하다면 비동기적 코드를 동시에 실행한다.
    - `theFutureIsNow` 함수가 `Promise.all`을 사용하도록 수정하기만 하면 된다.
        ```javascript
        function* theFutureIsNow() {
            const data = yield Promis.all([
                nfcall(fs.readFile, 'a.txt')
                nfcall(fs.readFile, 'b.txt')
                nfcall(fs.readFile, 'c.txt')
            ])
            yield ptimeout(60*1000)
            yield nfcall(fs.writeFile, 'd.txt', data[0]+data[1]+data[2])
        }
        ``` 
    - 이 섹션에서 가장 중요한 것은 `Promise.all`이 아니라 **프로그램에서 어떤 부분을 동시에 실행할 수 있고 어떤 부분은 동시에 실행할 수 없는지를 판단하는 것**이어야 한다.
    - 세 파일을 읽은 다음에 60초를 기다리고 **그다음에** 병합 결과를 파일에 저장하는 것이 중요하다면 그 답은 이미 예제에 들어있다.
    - 반면 세 파일을 읽는 것과 무관하게 *60초 이상이 흐른 다음* 네 번째 파일에 결과를 저장하는 것이 중요하다면 타임아웃을 `Promise.all`로 옮기는 편이 좋을 것이다.

#### 2. 제너레이터 실행기를 직접 만들지 말 것
- 더 좋은 제너레이트들이 만들어져 있는데 처음부터 많은 과정을 반복할 필요는 없다.
- [co](https://github.com/tj/co)는 기능이 풍부하고 단단하게 잘 만들어진 제너레이터 실행기이다.
- 웹 사이트를 만들고 있다면 [Koa 미들웨어](http://koajs.com/)를 한 번 살펴보길 권한다.
- Koa는 co와 함께 사용하도록 설계된 미들웨어이다.
    - Koa에서는 우리가 `theFutureIsNow`에서 했던 것처럼 `yield`를 응용해 웹 핸들러를 만들 수 있다.

#### 3. 제너레이터 실행기와 예외 처리
- 제너레이터 실행기를 쓰면 `try/catch`를 써서 예외 처리를 할 수 있다는 것도 중요한 장점이다.
    - 콜백이나 프라미스를 사용하면 예외 처리가 쉽지 않다.
    - 콜백에서 일으킨 예외는 그 콜백 밖에서 캐치할 수 없다.
    - 제너레이터 실행기는 비동기적으로 실행하면서도 동기적인 동작 방식을 유지하므로 `try/catch` 문과 함께 쓸 수 있다.
- ex) `theFutureIsNow` 함수에 예외 핸들러 추가하기
    ```javascript
    function* theFutureIsNow() {
        let data
        try {
            data = yield Promis.all([
                nfcall(fs.readFile, 'a.txt')
                nfcall(fs.readFile, 'b.txt')
                nfcall(fs.readFile, 'c.txt')
            ])
        } catch(err) {
            console.err(err.message)
            throw err
        }
        yield ptimeout(60*1000)
        try {
            yield nfcall(fs.writeFile, 'd.txt', data[0]+data[1]+data[2])
        } catch(err) {
            console.error(err.message)
            throw err
        }
    }
    ``` 
    - `try/catch`는 예외 처리에 널리 사용되고 다들 잘 이해하는 구조이니, 아직 동기적인 처리가 더 익숙하다면 예외처리에 `try/catch`를 사용하는 것도 좋다.

**Reference**
> - [MDN web docs - Generator](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Generator)