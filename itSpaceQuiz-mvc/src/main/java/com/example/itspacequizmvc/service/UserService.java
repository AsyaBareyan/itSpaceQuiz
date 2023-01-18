package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizcommon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setUserType(UserType.STUDENT);

        return userRepository.save(user);

    }
//    public boolean checkIfStudentIsNull(int id) {
//        Optional<User> student = userRepository.findById(id);
//        if (student.isPresent()) {
//            return true;
//        }
//        return false;
//    }

    public User findById(int id) {
        return userRepository.getById(id);
    }

//    public Optional<User> findByEmailAddress(String email) {
//        return userRepository.findByEmail(email);
//    }
}
