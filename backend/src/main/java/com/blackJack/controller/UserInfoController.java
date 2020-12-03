package com.blackJack.controller;


import java.security.Principal;

import com.blackJack.dbo.User;
import com.blackJack.dbo.UserInfo;
import com.blackJack.dto.UserInfoDto;
import com.blackJack.service.UserInfoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userInfo")
@Api(tags = "userInfo")
@AllArgsConstructor
public class UserInfoController {
    private  final UserInfoService userInfoService;

    @PostMapping("/save")
    public User saveUserInfo(final Principal principal, final UserInfoDto userInfo){
        return userInfoService.saveUserInfo(principal,userInfo);
    }


    @PatchMapping("/update")
    public UserInfo updateUserInfo(final Principal principal,@RequestBody final UserInfoDto userInfo){
        return userInfoService.updateUserInfo(principal,userInfo);
    }
}
