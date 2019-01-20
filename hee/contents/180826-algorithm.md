## 알고리즘 공부


### 방향성이 있는 비순환 그래프(DAG, Directed Acyclic Graph)
* DAG이란
  * 방향성이 있는 비순환 그래프를 말한다.
  * 즉, 방향성이 있고 사이클이 존재하지 않는 그래프이다.

### 위상 정렬(Topological Sort)
* 위상 정렬이란
  * 어떤 일을 하는 순서를 찾는 알고리즘이다.
  * 즉, 방향 그래프에 존재하는 각 정점들의 선행 순서를 위배하지 않으면서 모든 정점을 나열하는 것

* 위상 정렬의 특징
  * 하나의 방향 그래프에는 여러 위상 정렬이 가능하다.
  * 위상 정렬의 과정에서 선택되는 정점의 순서를 위상 순서(Topological Order)라 한다.
  * 위상 정렬의 과정에서 그래프에 남아 있는 정점 중에 진입 차수가 0인 정점이 없다면,
    위상 정렬 알고리즘은 중단되고 이러한 그래프로 표현된 문제는 실행이 불가능한 문제가 된다.

* 위상 정렬 해결 방법
  1. 진입 차수가 0인 정점(즉, 들어오는 간선의 수가 0)을 선택
    * 진입 차수가 0인 정점이 여러 개 존재할 경우 어느 정점을 선택해도 무방하다.
  2. 선택된 정점과 여기에 부속된 모든 간선을 삭제
  3. 위의 과정을 반복해서 모든 정점이 선택, 삭제되면 알고리즘 종료

* 위상 정렬 예시
  * 각각의 작업이 완료되어야만 끝나는 프로젝트
  * 선수 과목

**Reference**
> - [https://gmlwjd9405.github.io/2018/08/27/algorithm-topological-sort.html](https://gmlwjd9405.github.io/2018/08/27/algorithm-topological-sort.html)

### 최소 비용 신장 트리(MST, Minimum Spanning Tree)
* Spanning Tree란
  * 그래프 내의 모든 정점을 포함하는 트리
  * **Spanning Tree = 신장 트리 = 스패닝 트리**
  * Spanning Tree는 그래프의 **최소 연결 부분 그래프** 이다.
    * 최소 연결 = 간선의 수가 가장 적다.
    * n개의 정점을 가지는 그래프의 최소 간선의 수는 (n-1)개이고, (n-1)개의 간선으로 연결되어 있으면 필연적으로 트리 형태가 되고 이것이 바로 Spanning Tree가 된다.
  * 즉, 그래프에서 일부 간선을 선택해서 만든 트리

* MST란
  * Spanning Tree 중에서 사용된 간선들의 가중치 합이 최소인 트리
  * **MST = Minimum Spanning Tree = 최소 신장 트리**
  * 각 간선의 가중치가 동일하지 않을 때 단순히 가장 적은 간선을 사용한다고 해서 최소  비용이 얻어지는 것은 아니다.
  * MST는 간선에 가중치를 고려하여 최소 비용의 Spanning Tree를 선택하는 것을 말한다.
  * 즉, 네트워크(가중치를 간선에 할당한 그래프)에 있는 모든 정점들을 가장 적은 수의 간선과 비용으로 연결하는 것이다.

* Prim MST 알고리즘
  *

* Kruskal MST 알고리즘
  *

**Reference**
> - [https://gmlwjd9405.github.io/2018/08/28/algorithm-mst.html](https://gmlwjd9405.github.io/2018/08/28/algorithm-mst.html)
> - [https://gmlwjd9405.github.io/2018/08/29/algorithm-kruskal-mst.html](https://gmlwjd9405.github.io/2018/08/29/algorithm-kruskal-mst.htm])
> - [https://gmlwjd9405.github.io/2018/08/30/algorithm-prim-mst.html](https://gmlwjd9405.github.io/2018/08/30/algorithm-prim-mst.html)


### 최단 경로(Shortest Path)
* 최단 경로(Shortest Path)

* 벨만포드(Bellman-Ford) 알고리즘

**Reference**
> - []()


### :house: [Hee Home](https://github.com/WeareSoft/WWL/tree/master/hee)
