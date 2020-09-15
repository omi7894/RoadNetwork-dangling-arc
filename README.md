도로망 댕글링 객체 검출 (RoadNetwork-dangling-arc)
==================================================
개요
----
이 프로젝트는 도로망 지도에서 상호 연결이 되어있지 않은 댕글링 객체를 검출하는 방법에 대한 연구이다. 댕글링 객체를 검출하기 위해 객체를 탐색하는 방법 세가지를 비교 분석 하였으며, 객체 사이의 연결에서 허용오차범위를 설정함에 따라 댕글링 객체를 더 정확하게 추출하는 방법을 모색하였다. 이 연구는 지도제작자나 관리기관에 댕글링 객체 검출 방법을 제공함으로써 GIS지도의 정확성을 높이는 것에 도울을 줄 것으로 기대된다.


<br>
<br>
<br>


프로젝트 설명
------------

### ●댕글링 링크란?

<div>
<img width="300" alt="캡처1" src="https://user-images.githubusercontent.com/60821744/92328772-0786df80-f09e-11ea-8542-70aa51b88216.PNG">
</div>

<br>
도로망도에서 각 도로를 '링크'로 객체화 했을 때, 다른 링크와의 연결이 없어 경로찾기에 쓰이지 못한는 링크를 댕글링 링크라고 한다.   

<br><br><br>

### ●ESRI ShapeFile
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

<img width="298" alt="댕글링poly" src="https://user-images.githubusercontent.com/60821744/93160560-8689b100-f74b-11ea-8445-e446237993e0.PNG">
<img width="320" alt="댕글링_바운딩" src="https://user-images.githubusercontent.com/60821744/93160563-88ec0b00-f74b-11ea-8af7-d8fe57d80672.PNG">


<br><br><br>

### ●클래스 구조
