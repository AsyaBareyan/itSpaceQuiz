package com.example.itspacequizmvc.security;



import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        User user=userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<User> byEmail = userRepository.findByEmail(username);
        if (byEmail.isEmpty()){
            throw new UsernameNotFoundException("username"+username+" not found");
        }
        return new CurrentUser(byEmail.get());
    }
}
