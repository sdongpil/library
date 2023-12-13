# library
도서관리 시스템 

## 사용 기술
- Java 17, Spring Framwork, Spring Boot, MYSQL, H2DB, JPA, Gradle 


## DB ERD
외래키 미사용 설계 DB
<img width="602" alt="erd" src="https://github.com/MuinMusic/MuinMusic/assets/112970256/003d5f71-9f6f-4e90-9977-4f439977258b">


### POST /api/members  : 회원가입
- **Request**
  ```json
  {
  "member":{
    "memberId": "sdongpil",
    "password": "1234",
    "name": "손동필",
    "age": 30,
    "email": "pildong94@naver.com",
    "phoneNumber": "010-2222-2222"
    }
  }

- **Response**
  ```json
  { 
     "member":{
     "name": "손동필",
     "age": 30,
     "email": "pildong94@naver.com",
     "phoneNumber": "010-2222-2222"
    }
  }
    
    "code": 201,
    "message": "Created" 

<br>

### POST /api/books: 도서 등록
- **Request**
  ```json
  {
    "book": {
      "name": "토비의 스프링",
      "author": "이일민",
      "description": ""
    }
  }

- **Response**
  ```json
  {
    "book": {
      "name": "토비의 스프링",
      "author": "이일민",
      "description": ""
    }
  }

    "code": 201,
    "message": "Created" 
  
<br>

### POST /api/books/{id} : 도서 수정
- **Request**
  ```json
  {
    "book": {
      "name": "토비의 스프링",
      "author": "이일민",
      "description": ""
    }
  }

- **Response**
  ```json
  {
    "book": {
      "name": "토비의 스프링",
      "author": "이일민",
      "description": ""
    }
  }
  
    "code": 200,
    "message": "Success" 
  
<br>

### POST /api/books/{booId}/rent : 도서 대출
- **Request**
  ```json
   PathVariable: Long bookId, 
   RequestParam: String memberId

- **Response**
  ```json
  { 
    "code": 201,
    "message": "Created" 
  }
<br>

### POST /api/books/{booId}/return : 도서 반납
- **Request**
  ```json
   PathVariable: Long bookId, 
   RequestParam: String memberId

- **Response**
  ```json
  { 
    "code": 200,
    "message": "Success" 
  }
<br>

### GET /api/books/{memberId} : 도서 대출이력 확인
- **Request**
  ```json
   PathVariable: String memberId

- **Response**
  ```json
  {
      "bookTitle": "토비의 스프링",
      "memberId": "이일민",
      "rentalDate": "2023-12-13",
      "returnDate": ""
    
  }

    "code": 200,
    "message": "Success" 
  
<br>




