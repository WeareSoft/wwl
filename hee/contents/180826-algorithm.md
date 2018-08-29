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
* MST

* Prim MST 알고리즘

* Kruskal MST 알고리즘

**Reference**
> - []()

### 최단 경로(Shortest Path)
* 최단 경로(Shortest Path)

* 벨만포드(Bellman-Ford) 알고리즘

**Reference**
> - []()


### :house: [Hee Home](https://github.com/T-WWL/WWL/tree/master/hee)
