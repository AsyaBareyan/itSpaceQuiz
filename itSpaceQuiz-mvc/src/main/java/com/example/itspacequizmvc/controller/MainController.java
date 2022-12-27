package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizcommon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserRepository userRepository;

    @GetMapping("/")
    public String main() {

        return "main";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, ModelMap map) {
        String email = user.getEmail();
        String password = user.getPassword();
        Optional<User> byEmail = userRepository.findByEmail(email);
        map.addAttribute("email", email);
        map.addAttribute("password", password);
        if (byEmail.get().getUserType() == UserType.TEACHER) {
            return "teacher";
        }

        return "user";
    }


}
