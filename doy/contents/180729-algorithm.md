## 알고리즘 공부

### 트리
![tree](/doy/contents/images/tree.png)

    * 트리란
        * 비선형 자료구조로 계층적 관계를 표현한다.
            * 디렉터리 구조
            * 조직도
        * 노드(node)들과 노드들을 연결하는 간선(edge)들로 구성되어 있다.
        
    * 트리 용어
        * 루트 노드(root node): 부모가 없는 노드, 트리는 하나의 루트 노드만을 가진다.
        * 단말 노드(leaf node): 자식이 없는 노드
        * 내부(internal) 노드: 단말 노드가 아닌 노드
        * 긴선(edge): 노드를 연결하는 선 (link, branch 라고도 부름)
        * 형제(sibling): 같은 부모를 가지는 노드
        * 노드의 크기(size): 자신을 포함한 모든 자손 노드의 개수
        * 노드의 깊이(depth): 루트에서 어떤 노드에 도달하기 위해 거쳐야 하는 간선의 수
        * 노드의 레벨(level): 트리의 특정 깊이를 가지는 노드의 집합
        * 노드의 차수(degree): 하위 트리 개수 / 간선 수 (degree) = 각 노드가 지닌 가지의 수
        * 트리의 차수(degree of tree): 트리의 최대 차수
        * 트리의 높이(height): 루트 노드에서 가장 깊숙히 있는 노드의 깊이
        
    * 트리 특징
        * 노드가 N개인 트리는 항상 N-1개의 간선(edge)을 가진다.
        * 루트에서 어떤 노드로 가는 경로는 유일하다. 
        * 임의의 두 노드 간의 경로도 유일하다.
        
**Reference**
> - [https://jiwondh.github.io/2017/10/15/tree/](https://jiwondh.github.io/2017/10/15/tree/)
> - [http://monsieursongsong.tistory.com/6](http://monsieursongsong.tistory.com/6)

### 이진 탐색
    * 이진 탐색이란
        * 정렬된 자료를 반으로 나누어 탐색하는 방법이다.
        * 데이터가 오름차순 으로 정렬되어 있어야 한다. 
        * 시간복잡도: O(log N)
        
    * 이진 탐색 알고리즘 과정
        크기가 n인 리스트 data에서 target 이라는 특정 요소를 찾아낸다고 가정했을 때,
        1. low = 0, high = n-1 로 초기화한다.
        2. mid = (low + high) / 2 로 초기화한다.
        3-1. data[mid] 와 target이 같으면 탐색을 종료한다.
        3-2. data[mid] > targer 이면 high = mid-1 로 업데이트 한 후, 2번으로 돌아간다.
        3-3. data[mid] < targer 이면 low = mid+1 로 업데이트 한 후, 2번으로 돌아간다.
    
~~~java
public int binarySearch(int[] data, int target) {
    int low = 0;
    int high = data.length - 1;
    int mid;
    
    while (low <= high) {
        mid = (low + high) / 2;

        if (target == data[mid])
            return mid;
        
        if (target < data[mid])
            high = mid - 1;
        else
            low = mid + 1;
    }
    return -1;
}
~~~
**Reference**
> - [https://wayhome25.github.io/cs/2017/04/15/cs-16/](https://wayhome25.github.io/cs/2017/04/15/cs-16/)
> - [http://codepractice.tistory.com/101](http://codepractice.tistory.com/101)