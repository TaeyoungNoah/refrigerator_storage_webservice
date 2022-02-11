# 냉장고 관리 서비스



### 요구사항

- 냉장고, 냉동실 구분 가능

- 냉장고에 넣는 식료품 관리

  - C :  저장할 식료품을 생성함

  - R : 저장한 식료품 목록을 냉장고와 냉동실에서 조회 가능

    > 이때 Category로 필터링, 유통기한 만료에 가까운 순 등 으로 정렬 + 월별 필터링정도? 필요는 없지만 공부목적

  - U : 저장한 식료품의 내용을 수정할 수 있음

  - D : 저장한 식료품을 삭제할 수 있음

- 식료품

  - 제품명

  - Category 선택 가능 (ex. 육류, 채소, 과일, 유제품, 기타)

  - 구매일자 

  - 유통기한 만료일

    > 명시되어있는 유통기한이 있으면 직접 입력
    >
    > 없다면 빈칸 가능

  - 제품의 상태 표시

    > 아래는 예시
    >
    > Status.FRESH : 아직 유통기한에 근접하지 않은 신선한 제품
    >
    > Status.IMMINENT : 유통기한까지 n일 남음
    >
    > Status.STALE : 유통기한이 지남
    >
    > Status.UNKNOWN : 유통기한 모름





## 구조적 설계

> 수정 가능성 있음

<img src="foodbasket_%E1%84%89%E1%85%A5%E1%86%AF%E1%84%80%E1%85%A8.png" alt="foodbasket_설계" style="zoom:50%;" />



### Domain 

- **Food**

  - (Long) id
  - (String) name
  - (int) quantity
  - (Enum) category
  - (Enum) location 
  - (Enum) status
  - (LocalDateTime) expirationDate

- **(Enum) Category**

  - MEAT : 육류
  - VEGE : 채소
  - FRUIT : 과일
  - MILK : 유제품
  - ETC : 기타

- **(Enum)** **Location**

  - REFRIGERATOR : 냉장실
  - FREEZER : 냉동실

- **(Enum) Status**

  - SAFE : 유통기한까지 여유가 있음
  - WARNING : 유통기한까지 1일 이하 남음
  - DANGER : 유통기한 지남
  - NONE : 유통기한 모름 or 사용자가 입력하지 않음

- **BaseTimeEntity**

  - (LocalDateTime) initDate

    > Entity가 생성될 때 자동으로 생성되도록 설계



### Repository

- **FoodRepository**



### Service

- **FoodService**



### Controller

- **HomeController**
- **FoodController**



## Mapping 설계

> 수정 가능성 있음



### HomeController

- `GetMapping` ( / ) 

  >  메인페이지

  

### FoodController

- `GetMapping` (/foods)

  > 냉장고와 냉동실로 따로 조회하여 각 영역에 맞게 ListUp

- `GetMapping` (/foods/{foodId})

  > 식료품 상세페이지

- `GetMapping` (/foods/save)

  > 식료품 추가 폼

- `PostMapping` (/foods/save)

  > 식료품 추가
  >
  > redirect:/

- `GetMapping` (/foods/{foodId}/edit)

  > 식료품 수정 폼 

- `PostMapping` (/foods/{foodId}/edit)

  > 식료품 수정
  >
  > redirect:/foods/{foodId}





## Table 설계

> 수정 가능성 있음

- FOOD

  - Id

    > @Id
    >
    > @GeneratedValue

  - name

    > @Column(length = 20, nullable = false)

  - quantity

    > @Column(nullable = false)

  - category

    > @Column(nullable = false)

  - location 

    > @Column(nullable = false)

  - status

    > @Column(nullable = false)

  - expirationDate

    > @Column(nullable = true)



## TODOLIST

- 도메인 구체화 ✅
- 요구사항에 맞게 Repository 구체화 ✅ (ListUp 제외 굵은 기본기능만)

- 요구사항에 맞게 Service 구체화

  - C 

    > Entity 생성 (DTO 이용)

  - R 

    > initDate 순서대로 ListUp
    > expirationDate 가 가까운 순으로 ListUp
    > 월별 필터링
    > status 로 필터링

  - U 

    > Entity 수정 (DTO 이용)

  - D 

    > Entity 삭제

- Service Test코드 작성

- Web 구현





