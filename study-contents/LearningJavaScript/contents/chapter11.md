## Chapter 11. 예외와 예외처리
- 견고하고 품질 좋은 소프트웨어를 만드는 첫 번째 단계는 에러가 일어날 수 있다는 사실을 받아들이는 것이다.
- 두 번째 단계를 에러를 예상하고 타당한 방법으로 처리하는 것이다.
- **예외 처리**는 에러를 컨트롤 하는 매커니즘이다.
    - *에러* 처리라고 하지 않고 *예외* 처리라고 하는 이유는 예상치 못한 상황에 대처하는 방식이 때문이다.
- 예상한 에러와 예상치 못한 에러(예외)를 구분하는 기준은 불명확하고 상황에 따라 크게 달라진다.
    - 예상할 수 있는 에러의 에를 들자면, 누군가 폼에 잘못된 이메일 주소를 입력하는 경우를 들 수 있다. 사람들은 **항상** 오타를 낸다.
    - 예상치 못한 에러라면 디스크에 남은 공간이 없어진다거나 믿을 만한 서비스가 갑자기 정지된다거나 하는 경우가 있을 것이다.

### 1. Error 객체
- 자바스크립트에는 내장된 `Error` 객체가 있고 이 객체는 에러 처리에 간편하게 사용할 수 있다. `Error` 인스턴스를 만들면서 에러 메시지를 지정할 수 있다.
    ```javascript
    const err = new Error('invalid email')
    ```
- `Error` 인스턴스를 만드는 것만으로는 아무 일도 일어나지 않는다. 이 인스턴스는 에러와 통신하는 수단이다.

**Reference**
> - [MDN web docs - Error](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Error)


### 2. try/catch와 예외 처리
- 예외 처리는 `try...catch` 문을 사용한다. 뭔가를 시도하고, 예외가 있으면 그것을 캐치한다는 뜻이 잘 드러난다.
- 예를 들어 부주의한 프로그래머가 `email`에 문자열이 아닌 어떤 것을 할당하려는 예상치 못한 에러에 대처하려면 `try...catch` 문으로 코드 전체를 감쌀 수 있다.
    ```javascript
    const email = null

    try {
        const validatedEmail = validateEmail(email)
        if(validatedEmail instanceof Error) {
            console.error(`Error: ${validatedEmail.message}`)
        } else {
            console.log(`Valid email: ${validatedEmail}`)
        }
    } catch(err) {
        console.error(`Error: ${err.message}`)
    }
    ```
- 에러를 캐치했으므로 프로그램은 멈추지 않는다. 에러를 기록하고 계속 진행할 수 있다.
- 실행 흐름은 에러가 일어나는 즉시 `catch` 블록으로 이동한다. 즉, `validateEmail`을 호출한 다음에 있는 `if` 문은 실행되지 않는다.
- `try` 블록 안에 에러가 일어나는 문에서 실행 흐름을 `catch` 블록으로 넘긴다. 에러가 일어나지 않으면 `catch` 블록은 실행되지 않는다.

**Reference**
> - [MDN web docs - try...catch](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Statements/try...catch)


### 3. 에러 일으키기
- 이전 예제에서는 `try...catch` 문을 써서, 문자열이 아닌 것에서 `match` 메서드를 호출하려 할 때 자바스크립트가 일으킨 에러를 캐치했다.
- 자바스크립트가 에러를 일으키기만 기다릴 필요 없이 직접 에러를 일으켜서 예외 처리 작업을 시작할 수도 있다.
- 예외 처리 기능이 있는 다른 언어와는 달리, 자바스크립트는 에러를 일으킬 때 꼭 객체만이 아니라 숫자나 문자열 등 어떤 값이든 `catch` 절에 넘길 수 있다.
- 은행 애플리케이션에 사용할 현금 인출 기능을 만든다고 생각해보자.
    - 계좌의 잔고가 요청받은 금액보다 적다면 예외를 일으켜야 할 것이다.
    ```javascript
    function billPay(amount, payee, accout) {
        if(amount > account.balance)
            throw new Error('insufficient funds')
        account.transfer(payee, amount)
    }
    ```
    - `throw`를 호출하면 현재 함수는 즉시 실행을 멈춘다.
    - 따라서 위 예제에서는 `account.transfer`이 호출되지 않으므로 잔고가 부족한데도 현금을 찾아가는 사고는 발생하지 않는다.

**Reference**
> - [MDN web docs - throw](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Statements/throw)


### 4. 예외 처리와 호출 스택
- 프로그램이 함수를 호출하고, 그 함수는 다른 함수를 호출하고, 호출된 함수는 또 다른 함수를 호출하는 일이 반복된다.
    - 자바스크립트 인터프리터는 이런 과정을 모두 추적하고 있어야 한다.
    - 이렇게 완료되지 않은 함수가 쌓이는 것을 **호출 스택**이라 부른다.
- 에러는 캐치될 떄까지 호출 스택을 따라 올라간다.
- 에러는 호출 스택 어디에서든 캐치할 수 있다. 어디에서 이 에러를 캐치하지 않으면 자바스크립트 인터프리터는 프로그램을 멈춘다.
    - 이런 것을 *처리하지 않은 예외, 캐치하지 않은 예외*라고 부르며 프로그램이 충돌하는 원인이 된다.
    - 에러가 일어날 수 있는 곳은 정말 다양하므로 가능한 에러를 모두 캐치하기는 정말 어렵다.
- 에러를 캐치하면 호출 스택에서 문제 해결에 유용한 정보를 얻을 수 있다.
    - 예를 들어 함수 a가 함수 b를 호출하고 b가 호출한 c에서 에러가 일어났다면, 호출 스택은 c에서 일어난 에러를 보고하는 데 그치지 않고 b가 c를 호출했으며 b는 a에서 호출했따는 것도 함께 알려준다.
    - 프로그램 여기저기에서 함수 c를 호출할 수 있으므로 이런 정보는 디버그에 유용하게 쓸 수 있다.
- 대부분의 자바스크립트 환경에서 `Error` 인스턴스에는 스택을 문자열로 표현한 `stack` 프로퍼티가 있다.
    - 이 기능은 자바스크립트 표준은 아니지만 대부분의 환경에서 지원한다.
    ```javascript
    function a() {
        console.log('a: calling b')
        b()
        console.log('a: done')
    }
    function b() {
        console.log('b: calling c')
        c()
        console.log('b: done')
    }
    function c() {
        console.log('c: throwing error')
        throw new Error('c error')
        console.log('c: done')
    }
    function d() {
        console.log('d: calling c')
        c()
        console.log('d: done')
    }

    try {
        a()
    } catch(err) {
        console.log(err.stack)
    }

    try {
        d()
    } catch(err) {
        console.log(err.stack)
    }
    ```
    - 이 예제를 실행하면 콘솔에 다음과 같은 결과가 출력된다.
    ```javascript
    a: calling b
    b: calling c
    c: throwing error
    c@debugger eval code:13:1
    b@debugger eval code:8:4
    a@debugger eval code:3:4
    @debugger eval code:23:4

    d: calling c
    c: throwing error
    c@debugger eval code:13:1
    d@debugger eval code:18:4
    @debugger eval code:29:4
    ```
    - @ 기호가 있는 행은 스택 추적이며 '가장 깊은' 함수(c)에서 시작하고 함수가 남지 않았을 때 (브라우저 자체) 끝난다.

**Reference**
> - [MDN web docs - 호출 스택](https://developer.mozilla.org/ko/docs/Glossary/Call_stack)
> - [MDN web docs - Error.prototype.stack](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Error/stack)


### 5. try...catch...finally
- `try` 블록의 코드가 HTTP 연결이나 파일 같은 일종의 '자원'을 처리할 때가 있다.
    - 프로그램에서 이 자원을 계속 가지고 있을 수는 없으므로 에러가 있든 없든 어느 시점에서는 이 자원을 해제해야 한다.
- `try` 블록에는 문을 원하는 만큼 쓸 수 있고, 그중 어디서든 에러가 일어나서 자원을 해제할 기회가 아예 사라질 수도 있으므로 `try` 블록에서 자원을 해제하는 건 안전하지 않다.
    - 에러가 일어나지 않으면 실행되지 않는 `catch` 블록 역시 안전하지 않다.
    - 이러한 상황에서 `finally` 블록이 필요하다. `finally` 블록은 에러가 일어나든, 일어나지 않든 반드시 호출된다.
    ```javascript
    try {
        console.log('this line is executed...')
        throw new Error('whoops')
        console.log('this line is not...')
    } catch(err) {
        console.log('there was an error...')
    } finally {
        console.log('...always executed')
        console.log('perform cleanup here')
    }
    ```
    - 이 예제를 실행한 후 `throw` 문을 주석 처리한 후 다시 실행하면 `finally` 블록은 어느 쪽에서든 실행되는 걸 볼 수 있다.
    
**Reference**
> - [MDN web docs - try...catch](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Statements/try...catch)