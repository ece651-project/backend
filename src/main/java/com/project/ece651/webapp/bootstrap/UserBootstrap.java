package com.project.ece651.webapp.bootstrap;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.repositories.UserRepository;
import com.project.ece651.webapp.services.UserService;
import com.project.ece651.webapp.shared.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/* marked with @Profile to limit when it is loaded
 * https://docs.spring.io/spring-boot/docs/1.2.0.M1/reference/html/boot-features-profiles.html
 */
@Component
@Profile("dev")
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final UserRepository userRepository;

    public UserBootstrap(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        generateInitialUsers();
        generateTestBench();
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

    private void generateTestBench() {
        // Add users
        UserDto user1 = new UserDto();
        user1.setNickname("Li Lei");
        user1.setEmail("lilei@gmail.com");
        user1.setPassword("leileili");
        user1.setPhoneNum("1231231231231");
        String uid1 = userService.addUser(user1).getUid();

        UserDto user2 = new UserDto();
        user2.setNickname("Han Meimei");
        user2.setEmail("hanmeimei@gmail.com");
        user2.setPassword("meimeihan");
        user2.setPhoneNum("4564564564564");
        String uid2 = userService.addUser(user2).getUid();

        // Add apartments
        UserEntity landlord1 = userRepository.findByUid(uid1);
        ApartmentEntity apartment1 = new ApartmentEntity();
        apartment1.setLandlord(landlord1);
        apartment1.setType(null);
        apartment1.setVacancy(1);
        apartment1.setAddress("Empty address 1");
        apartment1.setUploadTime(null);
        apartment1.setStartMonth(null);
        apartment1.setTerm(1);
        apartment1.setDescription("Empty description 1");
        apartment1.setPrice(11111);
        landlord1.addOwnedApartments(apartment1);
        userRepository.save(landlord1);

        UserEntity landlord2 = userRepository.findByUid(uid2);
        ApartmentEntity apartment2 = new ApartmentEntity();
        apartment2.setLandlord(landlord2);
        apartment2.setType(null);
        apartment2.setVacancy(2);
        apartment2.setAddress("Empty address 2");
        apartment2.setUploadTime(null);
        apartment2.setStartMonth(null);
        apartment2.setTerm(2);
        apartment2.setDescription("Empty description 2");
        apartment2.setPrice(22222);
        landlord2.addOwnedApartments(apartment2);
        userRepository.save(landlord2);

        System.out.println("");
        System.out.println("uid 1: " + uid1);
        System.out.println("uid 2: " + uid2);
        System.out.println("");
    }
}
