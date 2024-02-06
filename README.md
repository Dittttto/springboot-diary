# 일정 관리 프로젝트

## 프로젝트 개요
Spring MVC를 활용하여 CRUD를 연습하기 위한 일정 관리 프로젝트

## 기능 정의

### 사용자 기능

- 회원 가입할 수 있다.
  - 아이디는 이메일을 사용하고, 중복된 이메일로는 가입하는 경우 `CustomException` 예외를 발생시킨다.
  - [제약조건]
      - 이메일 양식에 맞아야한다.
      - 비밀번호는 소문자, 대문자, 숫자를 포함한 8자 이상 15자 이하의 문자열이어야 한다.
- 로그인 및 로그아웃을 할 수 있다.
- 회원 정보를 조회할 수 있다.
  - 본인의 정보만 조회할 수 있다.
- 회원 정보를 수정할 수 있다.
  - 본인의 정보만 수정할 수 있다.
- 회원 탈퇴를 할 수 있다.

### 일정 기능
- 전체 일정을 조회할 수 있다.
  - 조회되는 일정에는 댓글이 포함되어 있다.
- 특정 일정을 조회할 수 있다.
- 일정을 작성할 수 있다.
  - 필수 항목은 `null`이 될 수 없으며, 만약 `null` 값이 들어오면 `CustomException` 예외를 발생시킨다.
- 일정을 수정할 수 있다.
  - 단, 작성자가 아닌 경우에는 수정할 수 없으며, `CustomException` 예외를 발생시킨다.
  - 수정하기 전에 비밀번호를 검사한다.
    - 만약, 비밀번호가 틀리다면 `CustomException` 예외를 발생시킨다.
- 일정을 삭제할 수 있다.
  - 단, 작성자가 아닌 경우에는 수정할 수 없으며, `CustomException` 예외를 발생시킨다.
  - 삭제 정책은 소프트 딜리트에 따른다.
  - 수정하기 전에 비밀번호를 검사한다.
    - 만약, 비밀번호가 틀리다면 `CustomException` 예외를 발생시킨다.
- 일정 담당자별로 전체 일정을 조회할 수 있다.

### 댓글 기능
- 댓글을 조회할 수 있다.
  - 댓글은 시간 순서로 정렬되어 조회된다.
- 댓글을 작성할 수 있다.
- 대댓글을 작성할 수 있다.
- 댓글을 수정할 수 있다.
  - 단, 작성자가 아닌 경우에는 수정할 수 없으며, `CustomException` 예외를 발생시킨다.
- 댓글을 삭제할 수 있다.
  - 단, 작성자가 아닌 경우에는 수정할 수 없으며, `CustomException` 예외를 발생시킨다.

## 기술 스택

- JDK 17
- SpringBoot 3.2.1
- Spring MVC
- Lombok
- Jpa
- MySQL
- JWT
- Springdoc-openapi

## ERD
![Screenshot 2024-02-06 at 5 16 47 PM](https://github.com/Dittttto/springboot-diary/assets/82052272/6cf83bfc-8ece-404f-a528-2eb6464aed4e)


## Usecase Diagram

<img src="https://github.com/Dittttto/springboot-diary/assets/82052272/665fa720-9715-4770-a65d-40f08381477f" width=400>

## API 명세서
- [Postman API documentation](https://shorturl.at/aeXY5)
- [Postman API collection json](/document/scheduleManager.postman_collection.json)

## 현재 작업 중
- 불필요한 DTO 클래스 삭제
- 단위 테스트 작성 중
  - [개선중] 불필요한 의존 관계로 인해서 단위 테스트 작성이 어려움


## Next Step
- 현재는 하나의 일정에 한 명의 담당자를 지정할 수 있지만, 복수의 맴버를 지정할 수 있도록 변경 예정
