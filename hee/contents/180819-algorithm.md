## 알고리즘 공부

### 트리, 이진 탐색
[관련 내용 참고](/hee/contents/180729-algorithm.md)

### 그래프, 이분 그래프
**그래프**

    * 그래프란
        * 단순히 노드(N, node)와 그 노드를 연결하는 간선(E, edge)을 하나로 모아 놓은 자료 구조
        * 즉, 연결되어 있는 객체 간의 관계를 표현할 수 있는 자료 구조이다.

    * 그래프 관련 용어
        * 정점(vertex): 위치라는 개념. (node 라고도 부름)
        * 간선(edge): 위치 간의 관계. 즉, 노드를 연결하는 선 (link, branch 라고도 부름)
        * 인접 정점(adjacent vertex): 간선에 의 해 직접 연결된 정점
        * 정점의 차수(degree): 무방향 그래프에서 하나의 정점에 인접한 정점의 수
          * 무방향 그래프에 존재하는 정점의 모든 차수의 합 = 그래프의 간선 수의 2배
        * 진입 차수(in-degree): 방향 그래프에서 외부에서 오는 간선의 수 (내차수 라고도 부름)
        * 진출 차수(out-degree): 방향 그래픙에서 외부로 향하는 간선의 수 (외차수 라고도 부름)
          * 방향 그래프에 있는 정점의 진입 차수 또는 진출 차수의 합 = 방향 그래프의 간선의 수(내차수 + 외차수)
        * 경로 길이(path length): 경로를 구성하는 데 사용된 간선의 수
        * 단순 경로(simple path): 경로 중에서 반복되는 정점이 없는 경우
        * 사이클(cycle): 단순 경로의 시작 정점과 종료 정점이 동일한 경우

    * 그래프 특징
        * 네트워크 형 구조
            * 지도
            * 지하철 노선도의 최단 경로
            * 전기 회로의 소자들
            * 도로(교차점과 일방 통행길)
            * 선수 과목 등
        * 그래프는 여러 개의 고립된 부분 그래프(Isolated Subgraphs)로 구성될 수 있다.
        * 탐색 방법
            1. 깊이 우선 탐색(DFS, Depth-First Search)
            2. 너비 우선 탐색(BFS, Breadth-First Search)

    * 그래프 구현 방법
        1. 인접 리스트(Adjacency List)
            * 가장 '일반적인 방법'
            * 모든 정점(혹은 노드)을 인접 리스트에 저장한다. 즉, 각각의 정점에 인접한 정점들을 리스트로 표시한 것이다.
            * 배열(혹은 해시테이블)과 배열의 각 인덱스마다 존재하는 또 다른 리스트(배열, 동적 가변 크기 배열(ArrayList), 연결리스트(LinkedList) 등)를 이용해서 인접 리스트를 표현
            * 사용하는 경우
                * 그래프 내에 적은 숫자의 간선만을 가지는 '희소 그래프(Sparse Graph)' 의 경우
                * 어떤 노드에 '인접한 노드'들을 찾는 경우
                * 그래프에 존재하는 '모든 간선의 수'를 찾는 경우: O(N+E)
        2. 인접 행렬(Adjacency Matrix)
            * 인접 행렬은 NxN 불린 행렬(Boolean Matrix)
            * matrix[i][j]가 true라면 i -> j로의 간선이 있다는 뜻이다.
            * 사용하는 경우
                * 그래프에 간선이 많이 존재하는 '밀집 그래프(Dense Graph)' 의 경우
                * 두 정점을 연결하는 '간선의 존재 여부'를 판단하는 경우 : O(1) ((M[i][j]이므로)
                * '정점의 차수'를 구하는 경우: O(N)

**이분 그래프**

    * 이분 그래프란
        * 노드가 두 개의 집합으로 나뉜 그래프이다.
        * 이분 그래프의 간선은 같은 집합 내에서 존재할 수 없고, 항상 다른 집합 사이에서만 존재할 수 있다.
        * 어떤 그래프가 이분 그래프인지 아닌지 확인하는 알고리즘이 있다.
        * 이분 그래프와 두 개의 색깔로 색칠할 수 있는 그래프는 동일하다.

**Reference**
> - [https://gmlwjd9405.github.io/2018/08/13/data-structure-graph.html](https://gmlwjd9405.github.io/2018/08/13/data-structure-graph.html)


### DFS, BFS
**BFS**
* BFS(Breadth First Search)란
    * 너비 우선 탐색
    * 루트 노드(혹은 다른 임의의 노드)에서 시작해서 인접한 노드를 먼저 탐색하는 방법

* BFS 특징
    * 선입선출(FIFO) 원칙
    * 너비 우선 탐색(BFS)이 깊이 우선 탐색(DFS)보다 좀 더 복잡하다.

* BFS 사용 예시
    * 두 노드 사이의 최단 '경로 탐색'
    * 혹은 두 노드 사이의 임의의 '경로 탐색'
    * 트리의 지름 구하기

* BFS 구현 방법
    * 선입선출(FIFO) 원칙으로 탐색하기 위해 큐(Queue)를 이용
        * 탐색한 정점 순으로 인접 정점을 찾는다.
        * 즉, 방문한 정점의 위치를 기억하기 위해 큐(Queue)를 이용

* BFS 알고리즘 과정
    1. 시작 노드를 큐(Queue)에 배정한다.
    2. 큐(Queue)가 빌 떄까지 아래의 과정을 반복한다.
    3. 큐(Queue)의 가장 앞에 있는 노드를 pop
    4. 현재 노드에 인접한 모든 노드들 중 아직 방문하지 않은 노드들을 큐(Queue)에 push

**DFS**
* DFS(Depth First Search)란
    * 깊이 우선 탐색
    * 루트 노드(혹은 다른 임의의 노드)에서 시작해서 다음 분기(branch)로 넘어가기 전에 해당 분기를 완벽하게 탐색하는 방법

* DFS 특징        
    * 자기 자신을 호출하는 '순환 알고리즘의 형태'를 가지고 있다.
    * 깊이 우선 탐색(DFS)이 너비 우선 탐색(BFS)보다 좀 더 간단하다.
    * 단순 검색 속도 자체는 너비 우선 탐색(BFS)에 비해서 느리다.

* DFS 사용 예시
    * '모든 노드를 방문'하고자 하는 경우
    * 트리의 지름 구하기

* DFS 구현 방법
    1. 순환 호출 이용
    2. 명시적인 스택 사용
        * 명시적인 스택을 사용하여 방문한 정점들을 스택에 저장하였다가 다시 꺼내어 작업한다.

* DFS 알고리즘 과정
    1. a 노드(시작 노드)를 방문한다.
      * 방문한 노드는 방문했다고 표시한다.
    2. a와 인접한 노드들을 차례로 순회한다.
      * a와 인접한 노드가 없다면 종료한다.
    3. a와 이웃한 노드 b를 방문했다면, a와 인접한 또 다른 노드를 방문하기 전에 b의 이웃 노드들을 전부 방문해야 한다.
      * b를 시작 정점으로 DFS를 다시 시작하여 b의 이웃 노드들을 방문한다.
    4. b의 분기를 전부 완벽하게 탐색했다면 다시 a에 인접한 정점들 중에서 아직 방문이 안 된 정점을 찾는다.
      * 즉, b의 분기를 전부 완벽하게 탐색한 뒤에야 a의 다른 이웃 노드를 방문할 수 있다는 뜻이다.
      * 아직 방문이 안 된 정점이 없으면 종료한다.
      * 있으면 다시 그 정점을 시작 정점으로 DFS를 시작한다.

**Reference**
> - [https://gmlwjd9405.github.io/2018/08/15/algorithm-bfs.html](https://gmlwjd9405.github.io/2018/08/15/algorithm-bfs.html)
> - [https://gmlwjd9405.github.io/2018/08/14/algorithm-dfs.html](https://gmlwjd9405.github.io/2018/08/14/algorithm-dfs.html)


### 사이클, 플러드 필
* 사이클
  * 단순 경로의 시작 정점과 종료 정점이 동일한 경우를 말한다.

* 플러드 필
  * 다차원 배열의 어떤 칸과 연결된 영역을 찾는 알고리즘을 말한다.
    * 즉, 어떤 위치와 연결된 모든 위치를 찾는 알고리즘이다.
  * Ex)
    * 그림 프로그램에서 연결된 비슷한 색을 가지는 영역에 "채우기" 도구에 사용.
    * 바둑이나 지뢰 찾기 게임에서 어떤 비어 있는 칸을 표시 할 지를 결정하는 경우.
  * 특징
    * 플러드 필 알고리즘은 시작 칸, 목표 색, 대체 색의 세 개의 인자를 받는다.
    * 이 알고리즘은 배열에 있는 시작 칸에서 목표 색으로 연결된 모든 칸을 방문해서 대체 색으로 바꾼다.
    * 플러드 필 알고리즘을 구현하는 방법은 대부분 큐나 스택과 같은 자료구조를 사용한다.


<!-- **Reference**
> - []() -->


### :house: [Hee Home](https://github.com/WeareSoft/WWL/tree/master/hee)
