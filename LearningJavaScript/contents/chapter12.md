## Chapter 12. 이터레이터와 제너레이터
- ES6에서는 매우 중요한 새로운 개념 **이터레이터**와 **제너레이터**를 도입했다.
    - 제너레이터는 이터레이터에 의존하는 개념이니 이터레이터부터 시작한다.
- 이터레이터는 '지금 어디 있는지' 파악할 수 있도록 돕는다는 면에서 일종의 책갈피와 비슷한 개념이다.
    - 배열은 *이터러블* 객체의 좋은 예이다.
    - 책에 여러 페이지가 있는 것처럼 배열에는 여러 요소가 들어 있으므로, 책에 책갈피를 끼울 수 있듯 배열에는 이터레이터를 사용할 수 있다.
    - `book`이란 배열이 있고, 이 배열의 각 요소는 책의 한페이지를 나타내는 문자열이라고 하자.
        ```javascript
        const book = [
            'Twinkle, twinkle, little bat!',
            'How I wonder what you are at!',
            'Up above the world you fly,',
            'Like a tea tray in the sky.',
            'Twinkle, twinkle, little bat!',
            'How I wonder what you are at!',
        ]
        ``` 
    - 이제 `book` 배열에 `values` 메서드를 서서 이터레이터를 만들 수 있다.
        ```javascript
        const it = book.values()
        ``` 
    - 이터레이터는 책갈피지만, 이 책에만 사용할 수 있다. 그리고 아직은 책갈피를 꽂을 수 없다. 읽지 않았으니까.
    - '읽기 시작'하려면 이터레이터의 `next` 메서드를 호출한다. 이 메서드가 반환하는 개체에는 `value` 프로퍼티(지금 보이는 페이지)와 `done` 프로퍼티(마지막 페이지를 읽으면 true로 바뀌는)가 있다.
        ```javascript
        it.next() // { value: 'Twinkle, twinkle, little bat!', done: false }
        it.next() // { value: 'How I wonder what you are at!', done: false }
        it.next() // { value: 'Up above the world you fly,', done: false }
        it.next() // { value: 'Like a tea tray in the sky.', done: false }
        it.next() // { value: 'Twinkle, twinkle, little bat!', done: false }
        it.next() // { value: 'How I wonder what you are at!', done: false }
        it.next() // { value: undefined, done: true }
        it.next() // { value: undefined, done: true }
        it.next() // { value: undefined, done: true }
        ```
    - `next`에서 책의 마지막 페이지를 반환했다 해서 끝난 것은 아니다. 책의 마지막 페이지를 읽었다면 다 읽은 거지만, 이터레이터는 책을 읽는 것보다 훨씬 다양한 상황에 쓰일 수 있고 끝나는 시점을 간단히 결정할 수는 없다.
    - 더 진행할 것이 없으면 `value`는 `undefined`가 되지만, `next`는 계속 호출할 수 있다. 일단 이터레이터가 끝까지 진행하면 뒤로 돌아가서 다른 데이터를 제공할 수는 없다.
- 배열의 요소를 나열하는 것이 목적이라면 `for` 루프나 `for...of` 루프를 쓸 수 있다. 이터레이터와 `while` 루프를 사용해서 `for...of` 루프를 흉내 내 보자.
    ```javascript
    const it = book.values()
    let current = it.next()
    while(!current.done) {
        console.log(current.value)
        current = it.next()
    }
    ``` 
- 이터레이터는 모두 독립적이다. 즉, 새 이터레이터를 만들 때마다 처음에서 시작한다. 그리고 각각 다른 요소를 가리키는 이터레이터 여러 개를 동시에 사용할 수도 있다.
    ```javascript
    const it1 = book.values()
    const it2 = book.values()

    // it1으로 두 페이지를 읽는다.
    it1.next() // { value: 'Twinkle, twinkle, little bat!', done: false }
    it1.next() // { value: 'How I wonder what you are at!', done: false }

    // it2으로 한 페이지를 읽는다.
    it2.next() // { value: 'Twinkle, twinkle, little bat!', done: false }

    // it1으로 한 페이지를 더 읽는다.
    it1.next() // { value: 'Up above the world you fly,', done: false }
    ``` 
    - 이 예제는 두 이터레이터가 서로 독립적이며 같은 배열에서 따로따로 움직일 수 있음을 보여준다.

### 1. 이터레이션 프로토콜
- 이터레이터는 그 자체로 크게 쓸모가 있다기보다는, 더 쓸모 있는 동작이 가능해지도록 한다는데 의미가 있다.
- **이터레이션 프로토콜**은 모든 객체를 이터러블 객체로 바꿀 수 있다.
    - 메시지에 타임스탬프를 붙이는 로그 클래스가 필요하다고 생각해보자.
    - 내적으로 타임스탬프가 붙은 메시지는 배열에 저장한다.
        ```javascript
        class Log {
            constructor() {
                this.message = []
            }
            add(message) {
                this.message.push({ message, timestamp: Date.now() })
            }
        }
        ``` 
    - 로그를 기록한 항목을 순회하고 싶다면 어떻게 해야 할까?
        - `log.message`에 접근할 수는 있지만, `log`를 배열을 조작하듯 할 수 있다면 더 좋을 것이다.
        - 이터레이션 프로토콜을 사용하면 가능하다.
        - 이터레이션 프로토콜은 클래스에 심볼 메서드 `Symbol.iterator`가 있고 이 메서드가 이터레이터처럼 동작하는 객체, 즉 `value`와 `done` 프로퍼티가 있는 객체를 반환하는 `next` 메서드를 가진 객체를 반환한다면 그 클래스의 인스턴스는 이터러블 객체라는 뜻이다.
            ```javascript
            class Log {
                constructor() {
                    this.message = []
                }
                add(message) {
                    this.message.push({ message, timestamp: Date.now() })
                }
                [Symbol.iterator]() {
                    return this.message.values()
                }
            }
            ``` 
        - 이제 `Log` 인스턴스를 배열처럼 순회할 수 있다.
            ```javascript
            const log = new Log()
            log.add('first day at sea')
            log.add('spotted whale')
            log.add('spotted another vessel')

            // 로그를 배열처럼 순회한다.
            for(let entry of log) {
                console.log(`${entry.message} @ ${entry.timestamp}`)
            }
            ``` 
        - 이 예제에서는 `message` 배열에서 이터레이터를 가져와 이터레이터 프로토콜을 구현했지만, 다음과 같이 직접 이터레이터를 만들 수도 있다.
            ```javascript
            class Log {
                // ...

                [Symbol.iterator]() {
                    let i = 0
                    const message = this.messages
                    return {
                        next() {
                            if(i >= messages.length)
                                return { value: undefined, done: true }
                            return { value: messages[i++], done: false}
                        }
                    }
                }
            }
            ``` 
    - 이터레이터는 무한한 데이터에도 사용할 수 있다.
        - 단순한 예제로, 피보나치 수열을 만들어 보자.
        - 피보나치 수열은 무한히 계속되고, 프로그램에서는 몇 번째 숫자까지 계산해야 할지 알 수 없으므로 이터레이터를 사용하기에 알맞다.
        - 이 예제와 이전 예제의 차이는 이 예제의 이터레이터가 `done`에서 절대 `true`를 반환하지 않는다는 것뿐이다.
            ```javascript
            class FibonacciSequence {
                [Symbol.iterator]() {
                    let a = 0, b = 1
                    return {
                        next() {
                            let rval = { value: b, done: false}
                            b += a
                            a = rval.value
                            return rval
                        }
                    }
                }
            }
            ``` 
        - `for...of` 루프로 `FibonacciSequence` 인스턴스를 계산하면 무한 루프에 빠진다. 무한르프에 빠지지 않도록, 10회 계산한 뒤 `break` 문으로 빠져나오자.
            ```javascript
            const fib = new FibonacciSequence()
            let i = 0
            for(let n of fib) {
                console.log(n)
                if(++i > 9) break
            }
            ``` 

### 2. 제너레이터
- **제너레이터**란 이터레이터를 사용해 자신의 실행을 제어하는 함수이다.
    - 일반적인 함수는 매개변수를 받고 값을 반환하지만, 호출자는 매개변수 외에는 함수의 실행을 제어할 방법이 전혀 없다.
    - 함수를 호출하면 그 함수가 종료될 때까지 제어권을 완전히 넘기는 것이다.
    - 하지만 제너레이터에서는 그렇지 않다.
- 제너레이터는 두 가지 새로운 개념을 도입했다.
    - 함수의 실행을 개별적 단계로 나눔으로써 함수의 실행을 제어한다는 것이다.
    - 실행중인 함수와 통신한다는 것이다.
- 제너레이터는 두 가지 예외를 제외하면 일반적인 함수와 같다.
    - 제너레이터는 언제든 호출자에게 제어권을 넘길 수 있다.
    - 제너레이터는 호출한 즉시 실행되지는 않는다. 대신 이터레이터를 반환하고, 이터레이터의 `next` 메서드를 호출함에 따라 실행된다.
- 제너레이터를 만들 때는 `function` 키워드 뒤에 애스터리스크(*)를 붙인다.
    - 제너레이터에서는 `return` 외에 `yield` 키워드를 쓸 수 있다.
    - 무지개 색깔을 반환하는 단순한 제너레이터 예제를 하나 만들어 보자.
        ```javascript
        function* rainbow() {
            yield 'red'
            yield 'orange'
            yield 'yellow'
            yield 'green'
            yield 'blue'
            yield 'indigo'
            yield 'violet'
        }
        ``` 
    - 이제 이 제너레이터를 어떻게 호출하는지 알아보자. 제너레이터를 호출하면 이터레이터를 얻는다. 함수를 호출한 다음 이터레이터를 써서 단계별로 진행한다.
        ```javascript
        const it = rainbow()
        it.next() // { value: 'red', done: false }
        it.next() // { value: 'orange', done: false }
        it.next() // { value: 'yellow', done: false }
        it.next() // { value: 'green', done: false }
        it.next() // { value: 'blue', done: false }
        it.next() // { value: 'indigo', done: false }
        it.next() // { value: 'violet', done: false }
        it.next() // { value: 'red', done: false }
        ``` 
