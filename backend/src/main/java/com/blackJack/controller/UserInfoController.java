package com.blackJack.controller;

import com.blackJack.dbo.User;
import com.blackJack.dbo.UserInfo;
import com.blackJack.service.UserInfoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/userInfo")
@Api(tags = "userInfo")
@AllArgsConstructor
public class UserInfoController {
    private  final UserInfoService userInfoService;

    @PostMapping("/add")
    public User addUserInfo(final Principal principal, final UserInfo userInfo){
        return userInfoService.addUserInfo(principal,userInfo);
    }
}
