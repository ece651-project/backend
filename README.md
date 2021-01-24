[![CircleCI](https://circleci.com/gh/ece651-project/backend.svg?style=svg)](https://circleci.com/gh/ece651-project/backend)

# backend



## 1 Usage

application URL - http://localhost:8080



##### h2 in-memory database

http://localhost:8080/h2-console
JDBC url - jdbc:h2:mem:testdb
username and password is default

##### Create a new user:

http://localhost:8080/user/add_user

##### Show a user:

http://localhost:8080/user/{userId}
userId can be found in h2 DB



## 2 Implementation

1) UserService extends UserDetailsService for authentication (password encryption...)
	If you create a new user, then you can see the effect in h2 (only encrypted password).
2) There is something wrong with login and authentication, so I annotated related code. And code in form-login, form-login-try also have problems.
3) For the frontend (with Thymeleaf): somehow the application could not load bootstrap resources, so the page has no style. But preloading html files will include style. 