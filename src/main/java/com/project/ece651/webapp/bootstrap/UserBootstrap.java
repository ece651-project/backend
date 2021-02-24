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

// Please annotate this class after using it for the first time if spring.jpa.hibernate.ddl-auto=update.
// Otherwise, fail to insert duplicate entries.
@Component
// @Profile("default")
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
