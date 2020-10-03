# [item 31] 한정적 와일드카드를 사용해 API 유연성을 높이라

### 사전 조사

```java
    public static void main(String[] args) {
		List<Apple> apples = new ArrayList<>();
		apples.add(new Apple());

		List<? extends Fruit> extendsBasket = apples; // Fruit의 하위 타입의 Iterable

		for (Fruit fruit : extendsBasket) { // Compile Pass
			System.out.println(fruit);
		}

		extendsBasket.add(new Apple()); //Compile time error
		extendsBasket.add(new Fruit()); //Compile time error

		/*
		왜 extends Fruit은 add가 안될까?
		if we think about it; the <? extends Fruit> wildcard tells the compiler that we’re dealing with a subtype of the type Fruit, but we cannot know which fruit as there may be multiple subtypes.
		Since there’s no way to tell, and we need to guarantee type safety (invariance), you won’t be allowed to put anything inside such a structure.

		이상한 점.
		Java에서는 다중상속이 안되는 걸로 아는데 multiple subtype이 있을까?
		 */

		List<? super Apple> superBasket = apples;

		superBasket.add(new Apple());      //Successful
		superBasket.add(new AsianApple()); //Successful
		superBasket.add(new Fruit());      //Compile time error

		for (Apple apple : superBasket) { //Compile time error
			System.out.println(apple);
		}

		/*
		Reason is that basket is a reference to a List of something that is a supertype of Apple.
		Again, we cannot know which supertype it is, but we know that Apple and any of its subtypes (which are subtype of Fruit) can be added to be without problem (you can always add a subtype in collection of supertype).
		So, now we can add any type of Apple inside basket.
		What about getting data out of such a type?
		It turns out that you the only thing you can get out of it will be Object instances: since we cannot know which supertype it is, the compiler can only guarantee that it will be a reference to an Object, since Object is the supertype of any Java type.
		 */
	}
```
#### 결론

## Reference
- <https://howtodoinjava.com/java/generics/java-generics-what-is-pecs-producer-extends-consumer-super/>


### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

