# 일정 관리 프로젝트

## 프로젝트 개요
Spring MVC를 활용하여 CRUD를 연습하기 위한 일정 관리 프로젝트

## 기능 정의

- 전체 할 일을 조회할 수 있다.
- 특정 한 일을 조회할 수 있다.
- 할 일을 작성할 수 있다.
  - 필수 항목은 `null`이 될 수 없으며, 만약 `null` 값이 들어오면 `CustomException` 예외를 발생시킨다.
- 할 일을 수정할 수 있다.
  - 수정하기 전에 비밀번호를 검사한다.
    - 만약, 비밀번호가 틀리다면 `CustomException` 예외를 발생시킨다.
- 할 일을 삭제할 수 있다.
  - 수정하기 전에 비밀번호를 검사한다.
    - 만약, 비밀번호가 틀리다면 `CustomException` 예외를 발생시킨다.

## 기술 스택

- JDK 17
- SpringBoot 3.2.1
- Spring MVC
- Lombok
- Jpa
- MySQL

## ERD

![Screenshot 2024-01-19 at 4 53 14 PM](https://github.com/Dittttto/springboot-diary/assets/82052272/d51c420f-8f7f-4b26-a9d4-7254daa9b9f4)

## Usecase Diagram

![Screenshot 2024-01-19 at 4 52 51 PM](https://github.com/Dittttto/springboot-diary/assets/82052272/73ca57b0-3321-4cf9-9368-fe801a348a10)


## [API 명세서](/document/api.pdf)
