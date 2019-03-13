## Chapter 9. 객체와 객체지향 프로그래밍
- 배열과 마찬가지로 자바스크립트 객체 역시 *컨테이너*지만, 크게 보면 다음 두 가지 측면에서 배열과 다르다.
    - 배열은 값을 가지며 각 값에는 숫자형 인덱스가 있다. 객체는 프로퍼티를 가지며 각 프로퍼티에는 문자열이나 심볼 인덱스가 있다.
    - 배열에는 순서가 있다. 반면 객체에는 순서가 보장되지 않는다.
- *프로퍼티*는 *키*와 *값*으로 구성된다. 객체의 진짜 특징은 키를 통해 프로퍼티에 접근할 수 있다는 점이다.
### 1. 프로퍼티 나열
- 일반적으로 어떤 컨테이너의 콘텐츠를 리스트로 *나열*한다고 하면, 보통 배열을 생각하지 객체를 생각하는 사람은 별로 없다. 하지만 객체도 분명 컨테이너이고 프로퍼티 나열을 지원한다.
- 프로퍼티 나열에서 기억해야 할 것은 *순서가 보장되지 않는다*는 점이다.
#### 1. `for...in`
- 객체 프로퍼티를 나열할 때 `for...in`을 주로 사용한다.
- 문자열 프로퍼티가 몇 개 있고 심볼 프로퍼티가 하나 있는 객체가 있다고 하자.
    ```javascript
    const SYM = Symbol()

    const o = { a: 1, b: 2, c: 3, [SYM]: 4 }

    for(let prop in o) {
        if (!o.hasOwnProperty(prop)) continue
        console.log(`${prop}: ${o[prop]}`)
    }
    ```
- `for...in` 루프에는 키가 심볼인 프로퍼티는 포함되지 않는다.

**Reference**
> - [MDN web docs - for...in](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Statements/for...in)


#### 2. `Object.keys`
- `Object.keys`는 객체에서 나열 가능한 문자열 프로퍼티를 배열로 반환한다.
    ```javascript
    const SYM = Symbol()

    const o = { a: 1, b: 2, c: 3, [SYM]: 4 }

    Object.keys(o).forEach(prop => console.log(`${prop}: ${o[prop]}`))
    ```
- 객체의 프로퍼티 키를 배열로 가져와야 할 때는 `Object.keys`가 편리하다.
- 예를 들어 객체에서 x로 시작하는 프로퍼티를 모두 가져온다면 다음과 같이 할 수 있다.
    ```javascript
    const o = { apple: 1, xochitl: 2, balloon: 3, guitar: 4, xylophone: 5, }

    Object.keys(o)
        .filter(prop => prop.match(/^x/))
        .forEach(prop => console.log(`${prop}: ${o[prop]}`))
    ```

**Reference**
> - [MDN web docs - Object.keys()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/keys)


### 2. 객체지향 프로그래밍
- OOP의 기본 아이디어는 단순하고 직관적이다.
- *객체*는 데이터와 기능을 논리적으로 묶어 놓은 것이다.
- OOP는 우리가 사물을 이해하는 자연스러운 방식을 반영하도록 설계됐다.
    - 만약 자동차가 객체라면 그 데이터에는 제조사, 모델, 도어 숫자, 차량번호 등이 있을 것이다.
- 또한, OOP는 사물에 관해 추상적으로(어떤 자동차), 구체적으로(특정 자동차) 생각할 수 있게 한다.
- *클래스*는 *어떤* 자동차처럼 추상적이고 범용적인 것이다.
- *인스턴스*는 *특정* 자동차처럼 구체적이고 한정적인 것이다.
- 기능은 *메서드*라고 부른다.
- 클래스에 속하지만 특정 인스턴스에 묶이지 않는 기능을 *클래스 메서드*라고 한다.
    - 예를 들어 '시동을 거는' 기능은 클래스 메서드라 할 수 있다.
- 인스턴스를 처음 만들 때는 *생성자*가 실행된다. 생성자는 객체 인스턴스를 초기화한다.
- OOP는 클래스를 계층적으로 분류하는 수단도 제공한다.
    - 예를 들어 자동차보다 더 범용적인 *운송 수단* 클래스가 있다고 하자.
    - 운송 수단 클래스에는 자동차와 마찬가지로 급유나 충전 없이 이동할 수 있는 거리인 *범위* 프로퍼티가 있겠지만, 자동차와 달리 바퀴는 없을 수도 있다.
        - 예를 들어 보트는 바퀴가 없는 운송 수단이다.
    - 이때 운송 수단을 자동차의 *슈퍼클래스*라 부르고, 자동차를 운송 수단의 *서브클래스*라 부른다.
    - 운송 수단 클래스에는 자동차, 보트, 비행기, 오토바이, 자전거 등 여러 가지 서브클래스가 있을 수 있다.
    - 서브클래스 역시 서브클래스를 가질 수 있다.
        - 예를 들어 보트 서브클래스에는 요트, 카누, 예인선, 모터보트 등의 서브클래스가 있을 수 있다.

**Reference**
> - [MDN web docs - 객체지향 자바스크립트 개요](https://developer.mozilla.org/ko/docs/Web/JavaScript/Introduction_to_Object-Oriented_JavaScript)


#### 1. 클래스와 인스턴스 생성
```javascript
class Car {
    constructor() {
    }
}
```
- 위의 코드는 새 클래스 `Car`를 만든다. 아직 인스턴스(특정 자동차)는 만들어지지 않았지만 언제든 만들 수 있다. 인스턴스를 만들 때는 `new` 키워드를 사용한다.
    ```javascript
    const car1 = new Car()
    const car2 = new Car()
    ```
- 제조사와 모델 데이터, 변속 기능을 추가해보자.
    ```javascript
    class Car {
        constructor(make, model) {
            this.make = make
            this.model = model
            this.userGears = ['P', 'N', 'R', 'D']
            this.userGear = this.userGeares[0]
        }
        shift(gear) {
            if(this.userGears,indexOf(gear) < 0)
                throw new Error(`Invalid gear: ${gear}`)
            this.userGear = gear
        }
    }
    ```
    - 여기서 `this` 키워드는 의도한 목적, 즉 메서드를 호출한 인스턴스를 가리키는 목적으로 쓰였다.
    - 클래스를 만들 때 사용한 `this` 키워드는 나중에 만들 인스턴스의 플레이스홀더이다.
    - 메서드를 호출하는 시점에서 `this`가 무엇인지 알 수 있게 된다.
    - 이 생성자를 실행하면 인스턴스를 만들면서 자동차의 제조사와 모델을 지정할 수 있고, 몇 가지 기본값도 있다.
        - `userGears`는 사용할 수 있는 기어 목록이고 `gear`는 현재 ㅐ기어이며 사용할 수 있는 첫 번째 기어로 초기화 된다.
    - 생성자 외에 `shift` 메서드는 기어 변속에 사용된다.
- 이 클래스를 실제로 사용해보자.
    ```javascript
    const car1 = new Car('Tesla', 'Model S')
    const car2 = new Car('Mazda', '3i')
    car1.shift('D')
    car2.shift('R')
    ```
    - 이 예제에서 `car1.shift('D')`를 호출하면 `this`는 `car1`에 묶인다.
    - 마찬가지로 `car2.shift('R')`를 호출하면 `this`는 `car2`에 묶인다.
    - 다음과 같이 `car1`이 주행 중이고(D) `car2`가 후진 중임을(R) 확인할 수 있다.
    ```javascript
    car1.userGear   // 'D'
    car2.userGear   // 'R'
    ```
- `Car` 클래스에 `shift` 메서드를 사용하면 잘못된 기어를 선택하는 실수를 방지할 수 있을 것처럼 보인다.
    - 하지만 완벽하게 보호되는 건 아니다.
    - 직접 `car1.userGear = 'X'`라고 설정한다면 막을 수 없다.
    - 대부분의 객체지향 언어에서는 메서드와 프로퍼티에 어느 수준까지 접근할 수 있는지 대단히 세밀하게 설정할 수 있는 매커니즘을 제공해 위와 같은 실수를 막을 수 있게 한다.
    - 하지만 자바스크립트에는 그런 매커니즘이 없고, 이는 언어의 문제로 자주 비판을 받는다.
- `Car` 클래스를 다음과 같이 수정하면 실수로 기어 프로퍼티를 고치지 않도록 어느 정도 막을 수 있다.
    ```javascript
    class Car {
        constructor(make, model) {
            this.make = make
            this.model = model
            this._userGears = ['P', 'N', 'R', 'D']
            this._userGear = this._userGeares[0]
        }

        get userGear() { return this._userGear }
        set userGear(value) {
            if(this._userGears.indexOf(value) < 0)
                throw new Error(`Invalid gear: ${value}`)
            this._userGear = value
        }

        shift(gear) { this.userGear = gear }
    }
    ```
    - 이 예제에서는 외부에서 접근하면 안 되는 프로퍼티 이름 앞에 밑줄을 붙이는, 소위 '가짜 접근 제한'을 사용했다.
        - 진정한 제한이라기보다는 "아, 밑줄이 붙은 프로퍼티에 접근하려고 하네? 이건 실수로군." 하면서 빨리 찾을 수 있도록 하는 방편이라고 봐야 한다.
- 프로퍼티를 완벽하게 보호하려고 한다면 스코프를 이용해 보호하는 `WeakMap` 인스턴스를 사용할 수 있다.
    - `Car` 클래스를 다음과 같이 고치면 기어 프로퍼티를 완벽하게 보호할 수 있다.
    ```javascript
    const Car = (function() {

        const carProps = new WeakMap()

        class Car {
            constructor(make, model) {
                this.make = make
                this.model = model
                this._userGears = ['P', 'N', 'R', 'D']
                carProps.set(this, { userGear: this._userGears[0] })
            }

            get userGear() { return carProps.get(this).userGear }
            set userGear(value) {
                if(this._userGears.indexOf(value) < 0)
                    throw new Error(`Invalid gear: ${value}`)
                carProps.get(this).userGear = value
            }

            shift(gear) { this.userGear = gear }
        }

        return Car
    })()
    ```
    - 여기서는 즉시 호출하는 함수 표현식을 써서 `WeakMap`을 클로저로 감싸고 바깥에서 접근할 수 없게 했다.
    - `WeakMap`은 클래스 외부에서 접근하면 안 되는 프로퍼티를 안전하게 저장한다.
    - 프로피터 이름에 심볼을 쓰는 방법도 있다.
        - 이렇게 해도 어느 정도는 보호할 수 있지만, 클래스에 들어 있는 심볼 프로퍼티 역시 접근이 불가능한 것은 아니므로 한계가 있다.

**Reference**
> - [MDN web docs - WeakMap](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/WeakMap)


#### 2. 클래스는 함수다.
- ES6에서 `class` 키워드를 도입하기 전까지, 클래스를 만든다는 것은 곧 클래스 생성자로 사용할 함수를 만든다는 의미였다.
- `class` 문법이 훨씬 더 직관적이고 단순하긴 하지만, 사실 `class`는 단축 문법일 뿐이며 자바스크립트의 클래스 자체가 바뀐 것은 아니다.
- 클래스는 사실 함수일 뿐이다. ES5에서는 `Car` 클래스를 다음과 같이 만들었을 것이다.
    ```javascript
    function Car(make, model) {
        this.make = make
        this.model = model
        this._userGears = ['P', 'N', 'R', 'D']
        this._userGear = this.userGears[0]
    }
    ```
- ES6에서도 똑같이 할 수 있다. 결과는 완전히 동일하다.
    ```javascript
    class Es6Car {}
    function Es5Car {}
    > typeof Es6Car     // 'function'
    > typeof Es5Car     // 'function'
    ```
- ES6에서 클래스가 바뀐 것은 아니다. 단지 간편한 새 문법이 생겼을 뿐이다.

**Reference**
> - [MDN web docs - Classes](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Classes)

#### 3. 프로토타입
- 클래스의 인스턴스에서 사용할 수 있는 메서드를 *프로토타입* 메서드라고 말한다.
    - 예를 들어 `Car`의 인스턴스에서 사용할 수 있는 `shift` 메서드는 프로토타입 메서드이다.
- 프로토타입 메서드는 `Car.prototype.shift`처럼 표기할 때가 많다.
    - 최근에는 프로토타입 메서드는 `#`으로 표시하는 표기법이 널리 쓰인다.
    - 예를 들어 `Car.prototype.shift`를 `Car#shift`로 쓰는 것이다.
- 모든 함수에는 `prototype`이라는 특별한 프로퍼티가 있다. 일반적인 함수에서는 프로토타입을 사용할 일이 없지만, **객체 생성자로 동작하는 함수에서는 프로토타입이 대단히 중요하다.**
    - 함수의 `prototype` 프로퍼티가 중요해지는 시점은 `new` 키워드로 새 인스턴스를 만들었을 때이다.
    - `new` 키워드로 만든 새 객체는 생성자의 `prototype` 프로퍼티에 접근할 수 있다.
    - 객체 인스턴스는 생성자의 `prototype` 프로퍼티를 `__proto__`프로퍼티에 저장한다.
- 프로토타입에서 중요한 것은 *동적 디스패치*라는 매커니즘이다.
    - 여기서 디스패치는 메서드 호출과 같은 의미이다.
    - 객체의 프로퍼티나 메서드에 접근하려 할 때 그런 프로퍼티나 메서드가 존재하지 않으면 자바스크립트는 *객체의 프로토타입에서* 해당 프로퍼티나 메서드를 찾는다.
    - 클래스의 인스턴스는 모두 같은 프로토타입을 공유하므로 프로토타입에서 프로퍼티나 메서드가 있다는 해당 클래스의 인스턴스는 모두 그 프로퍼티나 메서드에 접근할 수 있다.
- 인스턴스에서 메서드나 프로퍼티를 정의하면 프로토타입에 있는 것을 가리는 효과가 있다.
    - 자바스크립트는 먼저 인스턴스를 체크하고 거기에 없으면 프로토타입을 체크하기 때문이다.
    ```javascript
    const car1 = new Car()
    const car2 = new Car()
    car1.shift === Car.prototype.shift  // true
    car1.shift('D')
    car1.shift('d')                     // error
    car1.userGear                       // 'D'
    car1.shift === car2.shirt           // true

    car1.shift = function(gear) { this.userGear = gear.toUpperCase() }
    car1.shift === Car.prototype.shift  // false
    car1.shift === car2.shift           // false
    car1.shift('d')
    car1.userGear                       // 'D'
    ```
    - 이 예제는 자바스크립트에서 동적 디스패치를 어떻게 구현하는지 잘 보여준다.
    - `car1` 객체에는 `shift` 메서드가 없지만, `car1.shift('D')`를 호출하면 자바스크립트는 `car1`의 프로토타입에서 그런 메서드를 검색한다.
    - `car1`에 `shift` 메서드를 추가하면 `car1`과 프로토타입에 같은 이름의 메서드가 존재하게 된다.
        - 이제 `car1.shift('d')`를 호출하면 `car1`의 메서드가 호출되고 프로토타입의 메서드는 호출되지 않는다.

**Reference**
> - [MDN web docs - Object.prototype](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/prototype)


#### 4. 정적 메서드
- 메서드에는 인스턴스 메서드 외에도 *정적 메서드(클래스 메서드)* 가 있다.
- 이 메서드는 특정 인스턴스에 적용되지 않는다.
- 정적 메서드에서 `this`는 인스턴스가 아니라 클래스 자체에 묶인다.
    - 하지만 일반적으로 정적 메서드에는 `this` 대신 클래스 이름을 사용하는 것이 좋은 습관이다.
- 정적 메서드는 클래스에 관련되지만 인스턴스와는 관련이 없는 범용적인 작업에 사용된다.
    - 예제로 자동차 식별 번호(VIN)을 붙이는 메서드를 생각해보자.
    - 개별 자동차가 자신만의 VIN을 생성한다는 것은 불가능하다.
    - VIN을 할당한다는 것은 자동차 전체를 대상으로 하는 추상적인 개념이므로 정적 메서드로 사용하는 게 어울리다.
    - 예를 들어 두 자동차의 제조사와 모델이 모두 같으면 `ture`를 반환하는 `areSimilar` 메서드, 두 자동차의 VIN이 같으면 `ture`를 반환하는 `areSame` 메서드를 만들어보자.
    ```javascript
    class Car {
        static getNextVin() {
            return Car.nextVin++
        }
        constructor(make, model) {
            this.make = make
            this.model = model
            this.vin = Car.getNextVin()
        }
        static areSimilar(car1, car2) {
            return car1.make===car2.make && car1.model===car2.model
        }
        static areSame(car1, car2) {
            return car1.vin===car2.vin
        }
    }
    Car.nextVin = 0

    const car1 = new Car('Tesla', 'S')
    const car2 = new Car('Mazda', '3')
    const car3 = new Car('Mazda', '3')

    car1.vin    // 0
    car2.vin    // 1
    car3.vin    // 2

    Car.areSimilar(car1, car2)  // false
    Car.areSimilar(car2, car3)  // true
    Car.areSame(car2, car3)     // false
    Car.areSame(car2, car2)     // true
    ```
**Reference**
> - [MDN web docs - static](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Classes/static)


#### 5. 상속
- 클래스의 인스턴스는 클래스의 기능을 모두 상속한다.
- 상속은 한 단계로 끝나지 않는다.
    - 객체의 프로토타입에서 메서드를 찾지 못하면 자바스크립트는 프로토타입의 프로토타입을 검색한다.
    - *프로토타입 체인*은 이런 식으로 만들어진다.
    - 자바스크립트는 조건에 맞는 프로토타입을 찾을 때까지 프로토타입 체인을 계속 거슬러 올라간다.
    - 조건에 맞는 프로토타입을 찾지 못하면 에러를 일으킨다.
- 클래스의 계층 구초를 만들 때 프로토타입 체인을 염두에 두면 효율적인 구조를 만들 수 있다.
    - 즉, 프로토타입 체인에서 가장 적절한 위치에 메서드를 정의하는 것이다.
    - 자동차는 운송 수단의 일종이다.
    - 예를 들어 자동차에는 `deployAirbags`이란 메서드가 있을 수 있다.
    - 이 메서드를 운송 수단 클래스에 정의할 수 있겠지만, 에어백이 달린 보트는 없다.
    - 반면 운송 수단은 대부분 승객을 태울 수 있으므로, `addPassenger` 메서드는 운송 수단 클래스에 적당하다.
    ```javascript
    class Vehicle {
        constructor() {
            this.passengers = []
            console.log('Vehicle created')
        }
        addPassenger(p) {
            this.passengers.push(p)
        }
    }

    class Car extends Vehicle {
        constructor() {
            super()
            console.log('Car created')
        }
        deployAirbags() {
            console.log('BWOOSH!')
        }
    }
    ```
    - `extends` 키워드는 `Car`를 `Vehicle`의 서브클래스로 만든다.
    - `super()`는 슈퍼클래스의 생성자를 호출하는 함수이다.
        - 서브클래스에서는 이 함수를 반드시 호출해야한다.
    ```javascript
    const v = new Vehicle()
    v.addPassenger('Frank')
    v.addPassenger('Judy')
    v.passengers        // ['Frank', 'Judy']
    const c = new Car()
    c.addPassenger('Alice')
    c.addPassenger('Camaeron')
    c.passengers        // ['Alice', 'Cameron']
    v.deployAirbags()   // error
    c.deployAirbags()   // 'BWOOSH!'
    ```
    - `c`에서는 `deployAirbags`를 호출할 수 있지만, `v`에서는 불가능하다.
    - `Car` 클래스의 인스턴스는 `Vehicle` 클래스의 모든 메서드에 접근할 수 있지만, 반대는 불가능하다.

**Reference**
> - [MDN web docs - extends](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Classes/extends)
> - [MDN web docs - super](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Operators/super)

#### 6. 다형성
- *다형성*이란 객체지향 언어에서 여러 슈퍼클래스의 멤버인 인스턴스를 가리키는 말이다.
- 대부분 객체지향 언어에서 다형성은 특별한 경우에 속한다.
- 자바스크립트는 느슨한 타입을 사용하고 어디서든 객체를 쓸 수 있으므로, 어떤 면에서는 자바스크립트의 객체는 모두 다형성을 갖고 있다고 할 수 있다.
- 자바스크립트에는 객체가 클래스의 인스턴스인지 확인하는 `instanceof` 연산자가 있다.
    - 이 연산자를 속일 수도 있지만, `prototype`과 `__proto__` 프로퍼티에 손대지 않았다면 정확한 결과를 기대할 수 있다.
```javascript
class Motorcycle extends Vehicle {}
const c = new Car()
const m = new Motorcycle()
c instanceof Car        // true
c instanceof Vehicle    // true
m instanceof Car        // false
m instanceof Motorcycle // true
m instanceof Vehicle    // true
```

**Reference**
> - [MDN web docs - instanceof](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Operators/instanceof)


#### 7. 객체 프로퍼티 나열 다시 보기
- 객체 `obj`와 프로퍼티 `x`에서, `obj.hasOwnProperty(x)`는 `obj`에 프로퍼티 `x`가 있다면 `ture`를 반환하며, 프로퍼티 `x`가 `obj`에 정의되지 않았거나 프로토타입 체인에만 정의되었다면 `false`를 반환한다.
- ES6 클래스를 설계 의도대로 사용한다면 데이터 프로퍼티는 항상 프로토타입 체인이 아니라 인스턴스에 정의해야 한다.
- 하지만 프로퍼티를 프로토타입에 정의하지 못하도록 강제하는 장치는 없으므로 확실히 확인하려면 항상 `hasOwnProperty`를 사용하는 편이 좋다.
```javascript
class Super {
    constructor() {
        this.name = 'Super'
        this.isSuper = true
    }
}

// 유효하지만, 권장하지는 않는다.
Super.prototype.sneaky = 'not recommended!'

class Sub extends Super {
    constructor() {
        super()
        this.name = 'Sub'
        this.isSub = true
    }
}

const obj = new Sub()

for(let p in obj) {
    console.log(`${p}: ${obj[p]}` + 
        (obj.hasOwnProperty(p) ? '' : ' (inherited)'))
}
```
- 이 프로그램을 실행한 결과는 다음과 같다.
    ```javascript
    name: Sub
    isSuper: true
    isSub: ture
    sneaky: not recommended! (inherited)
    ``` 
- `name`, `isSuper`, `isSub` 프로퍼티는 모두 프로토타입 체인이 아니라 인스턴스에 정의됐다.
    - 슈퍼클래스 생성자에서 선언한 프로퍼티는 서브클래스 인스턴스에도 정의된다.
- 반면 `sneaky` 프로퍼티는 슈퍼클래스의 프로토타입에 직접 정의했다.
- `Object.keys`를 사용하면 프로토타입 체인에 정의된 프로퍼티를 나열하는 문제를 피할 수 있다.

**Reference**
> - [MDN web docs - Object.prototype.hasOwnProperty()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/hasOwnProperty)


#### 8. 문자열 표현
- 모든 객체는 `Object`를 상속하므로 `Object`의 메서드는 기본적으로 모든 객체에서 사용할 수 있다.
- 객체의 기본적인 문자열 표현을 제공하는 `toString`도 그런 메서드 중 하나이다.
- `toString` 메서드에서 객체에 관한 중요한 정보를 제공한다면 디버깅에도 유용하고, 객체를 한 눈에 파악할 수 있다.
- `Car` 클래스의 `toString` 메서드가 제조사, 모델, VIN을 반환하도록 고쳐보자.
    ```javascript
    class Car {
        toString() {
            return `${this.make} ${this.model}: ${this.vin}`
        }
    }
    ``` 
- 이제 `Car`의 인스턴스에서 `toString`을 호출하면 객체 식별에 필요한 정보를 얻을 수 있다.

**Reference**
> - [MDN web docs - Object.prototype.toString()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/toString)


### 3. 다중 상속, 믹스인, 인터페이스
- 일부 객체지향 언어에서는 **다중 상속**이란 기능을 지원한다.
    - 이 기능은 클래스가 슈퍼클래스 두 개를 가지는 기능이며, 슈퍼클래스의 슈퍼클래스가 존재하는 일반적인 상속과는 다르다.
- 다중 상속에는 충돌의 위험이 있다.
    - 예를 들어 어떤 클래스에 두 개의 슈퍼 클래스가 있고, 두 슈퍼클래스에 모두 `greet` 메서드가 있다면 서브클래스는 어느 쪽의 메서드를 상속해야 할까?
    - 다중 상속을 지원하지 않는 언어가 많은 이유는 이런 문제를 피하기 위해서이다.
- 하지만 현실 세계를 생각해 보면 다중 상속을 적용할 수 있는 문제가 많다.
    - 예를 들어 자동차는 운송 수단인 동시에 '보험을 들 수 있는' 대상이다.
- 다중 상속을 지원하지 않는 언어 중에는 **인터페이스** 개념을 도입해서 이런 상황에 대처하는 언어가 많다.
    - `Car` 클래스의 슈퍼클래스는 `Vehicle` 하나뿐이지만, `Insurable`, `Container` 등 여러 인터페이스를 가질 수 있다.
- 자바스크립트는 프로토타입 체인에서 여러 부모를 검색하지는 않으므로 단일 상속 언어라고 해야 하지만, 어떤 면에서는 다중 상속이나 인터페이스보다 더 나은 방법을 제공한다.
- 자바스크립트가 다중 상속이 필요한 문제에 대한 해답으로 내놓은 개념이 **믹스인**이다.
    - 믹스인이란 기능을 필요한 만큼 섞어 놓은 것이다.
    - 자바스크립트는 느슨한 타입을 사용하고 관대한 언어이므로 그 어떤 기능이라도 언제든, 어떤 객체에든 추가할 수 있다.
- ex) 자동차에 적용할 수 있는 보험 가입 믹스인을 만들기, 보험 가입 믹스인 외에도 `InsurancePolicy` 클래스 만들기
    ```javascript
    class InsurancePolicy {}
    function makeInsurable(o) {
        o.addInsurancePolicy = function(p) { this.insurancePolicy = p }
        o.getInsurancePolicy = function() { return this.insurancePolicy }
        o.isInsured = function() { return !!this.insurancePolicy }
    }
    ``` 
    - 이제 어떤 객체든 보험에 가입할 수 있다. 그럼 `Car`로 돌아와서 무엇을 보험에 가입해야 할까? 가장 먼저 드는 생각은 이런 것이다.
        ```javascript
        makeInsurable(Car)
        ``` 
    - 하지만 그렇게 되지 않는다.
        ```javascript
        const car1 = new Car()
        car1.addInsurancePolicy(new InsurancePolicy())  // error
        ``` 
    - 자동차를 추상화한 개념을 보험에 가입할 수는 없다. 보험에 가입하는 것은 개별 자동차이다.
        ```javascript
        const car1 = new Car()
        makeInsurable(car1)
        car1.addInsurancePolicy(new InsurancePolicy())  // works
        ``` 
    - 이 방법은 동작하지만, 모든 자동차에서 `makeInsurable`을 호출해야 한다.
    - `Car` 생성자에 추가할 수도 있겠지만, 그렇게 하면 이 기능을 모든 자동차에 복사해야 한다.
        ```javascript
        makeInsurable(Car.prototype)
        const car1 = new Car()
        car1.addInsurancePolicy(new InsurancePolicy())  // works
        ```
    - 이제 보험 관련 메서드들은 모두 `Car` 클래스에 정의된 것처럼 동작한다.
        - 자바스크립트의 관점에서는 *실제로* 그렇다.
    - 개발자의 관점에서는 중요한 두 클래스를 관리하기 쉽게 만들었다.
        - 자동차 회사에서 `Car` 클래스의 개발과 관리를 담당하고, 보험 회사에서 `InsurancePolicy` 클래스와 `makeInsurable` 믹스인은 괄리하게 된다.
        - 두 회사의 업무가 충돌할 가능성을 완전히 없앤 건 아니지만, 모두가 거대한 `Car` 클래스에 달라붙어 일하는 것보단 낫다.

**Reference**
> - [MDN web docs - Mix-ins](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Classes#Mix-ins)