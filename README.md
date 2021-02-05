[![CircleCI](https://circleci.com/gh/ece651-project/backend.svg?style=svg)](https://circleci.com/gh/ece651-project/backend)

# backend



## 1 Usage

application URL - http://localhost:8080

### h2 in-memory database

http://localhost:8080/h2-console
JDBC url - jdbc:h2:mem:testdb
username and password is default

### APIs

#### 1) User

##### create user:

http://localhost:8080/user/add_user

##### get user:

http://localhost:8080/user/get_user/{uid}
uid can be found in h2 DB

##### update user:

http://localhost:8080/user/update_user

##### delete user:

http://localhost:8080/user/delete_user/{uid}

##### login:

http://localhost:8080/user/login

#### 2) Apartment

## 2 Implementation

1) UserService extends UserDetailsService for authentication (password encryption...)
	If you create a new user, then you can see the effect in h2 (only encrypted password).
2) There is something wrong with login and authentication, so I annotated related code. And code in form-login, form-login-try also have problems.




## 3 Configuration

#### Centralized Config Classes

under config package

##### 1) Jackson config: 

for JSON mapper

##### 2) CORS config:

reference: https://spring.io/guides/gs/rest-service-cors/



#### Project Configuration

https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config

Config data files are considered in the following order:

1. [Application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-files) packaged inside your jar (`application.properties` and YAML variants).
2. [Profile-specific application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-files-profile-specific) packaged inside your jar (`application-{profile}.properties` and YAML variants).
3. [Application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-files) outside of your packaged jar (`application.properties` and YAML variants).
4. [Profile-specific application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-files-profile-specific) outside of your packaged jar (`application-{profile}.properties` and YAML variants).