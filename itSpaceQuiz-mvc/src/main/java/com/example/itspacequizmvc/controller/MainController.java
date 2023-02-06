package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizmvc.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main() {

        return "main";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @PostMapping("/login")
    public String userLogin(@ModelAttribute CurrentUser currentUser, ModelMap map) {
        map.addAttribute("currentUser", currentUser);
        return "redirect:/successLogin";
    }


    @GetMapping("/successLogin")
    public String successLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/";
        }
        User user = currentUser.getUser();
        if (user.getUserType() == UserType.TEACHER) {
            return "teacher";
        } else {
            return "redirect:/user";
        }
    }
}
