package ru.tiutikova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tiutikova.dto.ResultDto;
import ru.tiutikova.dto.auth.AuthDto;
import ru.tiutikova.service.UserService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController  {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResultDto login(@RequestBody AuthDto dto) {
        return userService.login(dto);
    }

}
