package ru.tiutikova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tiutikova.dto.ResultDto;
import ru.tiutikova.dto.UserDto;
import ru.tiutikova.service.UserService;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "getProfile", method = RequestMethod.POST)
    public UserDto getProfile() {
        return userService.getProfile();
    }

    @RequestMapping(value = "saveProfile", method = RequestMethod.POST)
    public ResultDto saveProfile(@RequestBody UserDto dto) {
        return userService.saveProfile(dto);
    }

}
