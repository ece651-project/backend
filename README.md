[![CircleCI](https://circleci.com/gh/ece651-project/backend.svg?style=svg)](https://circleci.com/gh/ece651-project/backend)

# backend

## 1 Introduction

A house rental web app that allows users to publish, get, and filter rental information and add favorites.



## 2 RESTful APIs

**1) User**

| Operation                               | HTTP verb | URL                          | upload data                                                  | respond data                                                 |
| --------------------------------------- | --------- | ---------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| create user                             | POST      | /user/add_user               | { email:string,	 	nickname:string,	phoneNum:string, Password:string, avatar: string (base64) } | { uid:string, 		success:bool, 		msg:string }     |
| get user                                | GET       | /user/get_user/{uid}         | /                                                            | { uid:string, 		email:string,				 nickname:string, 		phoneNum:string, 	avatar: string (base64) } |
| update user                             | PUT       | /user/update_user            | { uid: string, 		email:string,				 nickname:string, 	phoneNum:string, 	Password:string 		avatar: string (base64) } | {success:bool; msg:string}                                   |
| delete user                             | DELETE    | /user/delete_user/{uid}      | /                                                            | {success:bool; msg:string}                                   |
| login                                   | POST      | /user/login                  | {email:string; 		password:string}                      | {uid:string, success:bool; msg:string}                       |
| list all apartments belong to this user | GET       | /user/get_apt/{uid}          | /                                                            | JsonArray of { 		landlordId:id, 		type:int/string? 		address:String 		uploadTime:String, 	startMonth:String, 	endMonth:String, 	description:String, 	images:base64,		price:int } |
| get favour                              | GET       | /user/get_fav/{uid}          | /                                                            | JsonArray of  		 	 { aid: long }                    |
| add favour                              | POST      | /user/add_fav                | { uid: string 		 	aid: long }                       | {success:bool; msg:string}                                   |
| delete favour                           | DELETE    | /user/delete_fav/{uid}/{aid} | /                                                            | {success:bool; msg:string}                                   |



2) **Apartment**

| Operation           | HTTP verb | URL                         | upload data                                                  | respond data                                                 |
| ------------------- | --------- | --------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| get all apts        | GET       | /apt/get_all                | /                                                            | JsonArray of { 		aid:long, 		landlordId:id, 		type:String, 		vacancy:int, 		address:String 		uploadTime:String, 		startMonth:String, 		term:int,(months) 		description:String, 		images:base64, 	price:int,                     rating: int, 		Comments:[ 		 		 uid:string, 		 		 comment:string, 		 	rating:int]} |
| get an apt info     | GET       | /apt/get_apt/{aid}          | /                                                            | { aid:long, 		landlordId:id, 		type:String, 		vacancy:int, 		address:String 		uploadTime:String, 		startMonth:String, 		term:int,(months) 		description:String, 		images:base64, 	price:int,                     rating: int, 		Comments:[ 		 		 uid:string, 		 		 comment:string, 		 	rating:int]} |
| create apartment    | POST      | /apt/add_apt                | {landlordId:id, 	type:String, 		vacancy:int, 		address:String, 	startMonth:String, term:int,(months) description:String, images:base64, price:int} | {success:bool; msg:string}                                   |
| update an apartment | PUT       | /apt/update_apt/{aid}       | same as create apartment                                     | {success:bool; msg:string}                                   |
| delete an apartment | DELETE    | /apt/delete_apt/{uid}/{aid} | /                                                            | {success:bool; msg:string}                                   |




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