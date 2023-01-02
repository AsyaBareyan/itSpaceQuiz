package com.example.itspacequizrest.service;

import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizcommon.repository.UserRepository;
import com.example.itspacequizrest.dto.SaveUserRequest;
import com.example.itspacequizrest.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto save(SaveUserRequest saveUserRequest) {
        User user = modelMapper.map(saveUserRequest, User.class);
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        user.setUserType(UserType.STUDENT);

        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);

    }


    public void save(User user) {
        userRepository.save(user);
    }

    public List<UserResponseDto> findAll() {
        List<UserResponseDto> result = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            result.add(modelMapper.map(user, UserResponseDto.class));

        }
        return result;

    }

    public ResponseEntity getById(int id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

            return ResponseEntity.ok(userResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity deleteById(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
