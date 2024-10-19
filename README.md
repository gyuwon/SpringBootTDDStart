### API 목록

#### 판매자 회원 가입

요청
- 메서드: POST
- 경로: /seller/signUp
- 본문
  ```
  CreateSellerCommand {
    email: string,
    username: string,
    password: string
  }
  ```

응답
- 204 No Content

정책
- 사용자이름은 3자 이상의 영문자, 숫자, 하이픈, 밑줄 문자로 구성되어야 한다
- 비밀번호는 8자 이상의 문자로 구성되어야 한다

테스트
- [x] 올바르게 요청하면 204 No Content 상태코드를 반환한다
- [ ] email 속성이 지정되지 않으면 400 Bad Request 상태코드를 반환한다
- [ ] email 속성이 올바른 형식을 따르지 않으면 400 Bad Request 상태코드를 반환한다
- [ ] username 속성이 지정되지 않으면 400 Bad Request 상태코드를 반환한다
- [ ] username 속성이 올바른 형식을 따르지 않으면 400 Bad Request 상태코드를 반환한다
- [ ] password 속성이 지정되지 않으면 400 Bad Request 상태코드를 반환한다
- [ ] password 속성이 올바른 형식을 따르지 않으면 400 Bad Request 상태코드를 반환한다
- [ ] email 속성에 이미 존재하는 이메일 주소가 지정되면 400 Bad Request 상태코드를 반환한다
- [ ] username 속성에 이미 존재하는 사용자이름이 지정되면 400 Bad Request 상태코드를 반환한다
- [ ] 비밀번호를 올바르게 암호화한다
