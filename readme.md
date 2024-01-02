## 게시판 만들기

### 사용된 기술

- Spring Boot
- Spring MVC
- Spring JDBC
- MYSQL - SQL
- tymeleaf 템플릿 엔진

### 아키텍처
```dtd
 브라우저 --- 요청 ---> Contorller ---> Service ---> DAO ---> DB
 <--- 응답 --- 템플릿 < ---
```

### 게시판 만드는 순서
1. Controller와 템플릿
2. Service - 비지니스 로직을 처리 (하나의 트랜잭션)
3. Service - 비지니스로직을 처리하기위해 데이터를 CRUD 하기위해 DAO를 사용

