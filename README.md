도로망 댕글링 객체 검출 (RoadNetwork-dangling-arc)
==================================================
개요
----
이 프로젝트는 도로망 지도에서 상호 연결이 되어있지 않은 댕글링 객체를 검출하는 방법에 대한 연구이다. 댕글링 객체를 검출하기 위해 객체를 탐색하는 방법 세가지를 비교 분석 하였으며, 객체 사이의 연결에서 허용오차범위를 설정함에 따라 댕글링 객체를 더 정확하게 추출하는 방법을 모색하였다. 이 연구는 지도제작자나 관리기관에 댕글링 객체 검출 방법을 제공함으로써 GIS지도의 정확성을 높이는 것에 도울을 줄 것으로 기대된다.


<br>
<br>
<br>


# 댕글링 링크란?

<div>
<img width="300" alt="캡처1" src="https://user-images.githubusercontent.com/60821744/92328772-0786df80-f09e-11ea-8542-70aa51b88216.PNG">
</div>

<br>
도로망도에서 각 도로를 '링크'로 객체화 했을 때, 다른 링크와의 연결이 없어 경로찾기에 쓰이지 못한는 링크를 댕글링 링크라고 한다.   

<br><br><br>

# ESRI ShapeFile
본 프로젝트에서 사용되는 데이터는 ShapeFile형식이다. **ShapeFile이란, ESRI사의 GIS프로그램에 사용되는 지리현상에 대한 기하학적 위치와 속성 정보를 저장, 제공해주는 데이터 포맷이다.**
크게 Main file, Index file, dBase table로 구성이 되어있으며,
<br>
1. Main file은 피처의 지오메트리(형상)를 저장하는 기본 파일이다.가장 메인이 되며 도형type과 형상 data를 갖고있다고 보면된다.   
2. Index file은 피처의 기하학의 색인을 저장한다.   
3. dBase  table은 피처의 속성 정보를 저장하는 dBASE 테이블이다.   

각각의 파일은 (.shp) (.shx) (.dbf)의 파일 형식을 갖는다.<br>

Main file에 저장 되어있는 형상에는 점(point), 선,(polyline) 다각형(polygon)등이 있다. 이 프로젝트에서 쓰이는 데이터 타입은 Polyline이다. Polylined이 갖는 기본적인 속성은 아래와 같다.

```java
PolyLine
{
  Double[4]  Box
  Integer  NumParts
  Integer  NumPoints
  Integer[NumParts]  Parts
  Point[NumPoints]  Points
}
```
위의 속성 중 Box는 '최소경계상자(Bounding Box)'이다.<br>
Bounding Box 설명 : https://en.wikipedia.org/wiki/Minimum_bounding_box <br>
바운딩 박스는 향후 객체 탐색에 용이하게 쓰인다.

<img width="500" alt="댕글링_바운딩" src="https://user-images.githubusercontent.com/60821744/93160563-88ec0b00-f74b-11ea-8af7-d8fe57d80672.PNG">

<br><br><br>

# 클래스 구조

<img width="400" alt="댕글링poly" src="https://user-images.githubusercontent.com/60821744/93160560-8689b100-f74b-11ea-8445-e446237993e0.PNG">

```java
PolyLine
{
  int id //식별번호
  Node startnode //시작노드
  Node endnode //끝노드
  BBox box //바운딩박스
  PartList partlist //포함하고있는 Part리스트
  Polyline next //리스트상에서 다음 Polyline
}

Part
{
  int id //식별번호
  Node startnode //시작노드
  Node endnode //끝노드
  PointList pointlist //포함하고있는 Point리스트
  Part next //리스트상에서 다음 Part
}

Point
{
  double x //x좌표
  double y //y좌표
  Point next //리스트상에서 다음 Point
}

Node
{
  int id //식별번호
  double x //x좌표
  double y //y좌표
  int partID //속해있는 Part의 식별번호
  int PolylineID //속해있는 Polyline의 식별번호
}

PolylineList
{
  Polyline head
  Polyline tail
  int size
}

PartList
{
  Part head
  Part tail
  int size
}

PointList
{
  Point head
  Point tail
  int size
}

BBox
{
  double minX
  double minY
  double maxX
  double maxY
}
```

<br><br><br>

# 댕글링 객체 추출 방법

<img width="700" alt="댕글링_degree" src="https://user-images.githubusercontent.com/60821744/93171894-dde84b00-f764-11ea-886b-372a4572d82c.PNG">

1. Node 클래스에 'degree'라는 정수형 변수를 추가한다. degree는 Node에 연결된 링크의 갯수이며 초기값은 1이다.  
2. 두 Polyline의 Node좌표 값을 비교하여 좌표가 일치하면 연결되었다고 판단한다. 따라서 degree를 높여준다.   
3. 모든 Polyline에 대하여 2번을 진행한다. StartNode와 EndNode의 degree가 여전히 1인 Polyline를 댕글링 객체로 판단한다.   

<br>

## 탐색방법

### 1) 전체탐색
모든 Polyline을 서로 비교한다.    
(탐색시간 N^2)


### 2) Bounding Box 정렬
1. Polyline리스트를 Bounding Box 기준으로 정렬한다.
2. Bounding Box가 겹치는 Polyline끼리 비교한다.   

(탐색시간 N^2/2)


### 3) Quad Tree 구현 
1. 지도 전체의 Bounding Box를 재귀적으로 4등분한다.   
2. 쿼드트리를 생성한다. 지도 전체의 Bounding Box를 루트노드로 설정하고 재귀적으로 4등분한 Bounding Box를 자식노드로 설정한다.   
3. 쿼드트리를 사용하여 Bounding Box가 겹치는 Polyline끼리 비교한다. 

<img width="400" alt="댕글링_박스" src="https://user-images.githubusercontent.com/60821744/93175674-f8252780-f76a-11ea-9de0-79e409a1f687.PNG">
<img width="400" alt="댕글링_쿼드트리" src="https://user-images.githubusercontent.com/60821744/93175676-f9565480-f76a-11ea-986b-8525e3cf95b7.PNG">   

(탐색시간 NlogN)





