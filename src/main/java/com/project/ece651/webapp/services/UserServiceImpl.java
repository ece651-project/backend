package com.project.ece651.webapp.services;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.repositories.UserRepository;
import com.project.ece651.webapp.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    // private final Environment environment;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserDto addUser(UserDto userDto) {

        userDto.setUid(UUID.randomUUID().toString());
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserDto findByUid(String userId) {
        UserEntity userEntity = userRepository.findByUid(userId);
        if (userEntity == null) {
            return null;
        }
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserDto findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            return null;
        }
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userEntity, UserDto.class);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
                .collect(Collectors.toList());
    }

    // TODO: add methods
    public void updateUser(UserDto updatedUser) {
        // Obtain the original user in the database
        UserEntity originalUser = userRepository.findByUid(updatedUser.getUid());

        /* Update the information of the user
         * Information used is the same as add_user, which are:
         *  - email
         *  - nickname
         *  - phoneNum
         *  - password
         */
        originalUser.setEmail(updatedUser.getEmail());
        originalUser.setNickname(updatedUser.getNickname());
        originalUser.setPhoneNum(updatedUser.getPhoneNum());
        originalUser.setEncryptedPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));

        // Save the updated data
        userRepository.save(originalUser);
    }

    public void deleteUser(String uid) {
        UserEntity userToDelete = userRepository.findByUid(uid);
        userRepository.delete(userToDelete);
    }

    public void addFav(String uid, ApartmentEntity apartment) {
        // Logic is similar to updateUser
        // Obtain the original user in the database
        UserEntity originalUser = userRepository.findByUid(uid);

        // Add an apartment
        originalUser.addFavoriteApartments(apartment);

        // Save the updated data
        userRepository.save(originalUser);
    }

    public void delFav(String uid, ApartmentEntity apartment) {
        // Logic is similar to updateUser
        // Obtain the original user in the database
        UserEntity originalUser = userRepository.findByUid(uid);

        // Add an apartment
        originalUser.delFavoriteApartments(apartment);

        // Save the updated data
        userRepository.save(originalUser);
    }
}
