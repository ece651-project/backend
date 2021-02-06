package com.project.ece651.webapp.bootstrap;

import com.project.ece651.webapp.services.UserService;
import com.project.ece651.webapp.shared.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Component
//@Profile("default")
//public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private final UserRepository userRepository;
//
//    public UserBootstrap(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        userRepository.saveAll(getInitialUsers());
//        logger.debug("Loading bootstrap user data");
//    }
//
//    private List<UserEntity> getInitialUsers() {
//        List<UserEntity> users = new ArrayList<>(2);
//
//        UserEntity user1 = new UserEntity();
//        // id is auto incremented by DB
////        user1.setId(1L);
//        user1.setUserId("1");
//        user1.setUserName("Li Lei");
//        user1.setEmail("lilei@gmail.com");
//        user1.setEncryptedPassword("$2a$10$arNGWGeos6wQa3Oqrx2kK.jCLli8HTyqEwOQUHawo1INXgdi/eDE2");
//        users.add(user1);
//
//        UserEntity user2 = new UserEntity();
////        user2.setId(2L);
//        user2.setUserId("2");
//        user2.setUserName("Han Meimei");
//        user2.setEmail("hanmeimei@gmail.com");
//        user2.setEncryptedPassword("fafvwqc15bgtfvf");
//        users.add(user2);
//
//        return users;
//    }
//}

@Component
@Profile("default")
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    public UserBootstrap(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        generateInitialUsers();
        logger.debug("Loading bootstrap user data");
    }

    private void generateInitialUsers() {
        UserDto user1 = new UserDto();
        user1.setNickname("Li Lei");
        user1.setEmail("lilei@gmail.com");
        user1.setPassword("leileili");
        user1.setPhoneNum("1231231231231");
        userService.addUser(user1);

        UserDto user2 = new UserDto();
        user2.setNickname("Han Meimei");
        user2.setEmail("hanmeimei@gmail.com");
        user2.setPassword("meimeihan");
        user2.setPhoneNum("4564564564564");
        userService.addUser(user2);
    }
}
