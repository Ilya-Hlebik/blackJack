package com.blackJack.service;


import java.security.Principal;

import com.blackJack.dbo.User;
import com.blackJack.dbo.UserInfo;
import com.blackJack.dto.UserInfoDto;
import com.blackJack.repository.UserInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserInfoService extends AbstractService<UserInfo, UserInfoRepository>
{
    private final UserService userService;


    public UserInfoService(final UserInfoRepository repository, final ModelMapper modelMapper,
            final UserService userService)
    {
        super(repository, modelMapper);
        this.userService = userService;
    }


    @Transactional
    public User saveUserInfo(final Principal principal, final UserInfoDto userInfoDto)
    {
        final User user = userService.findMe(principal);
        final UserInfo userInfo = new UserInfo();
        modelMapper.map(userInfoDto, userInfo);
        userInfo.setUser(user);
        final UserInfo save = repository.save(userInfo);
        user.setUserInfo(save);
        return userService.save(user);
    }


    public UserInfo save(final UserInfo userInfo)
    {
        return repository.save(userInfo);
    }


    @Transactional
    public UserInfo updateUserInfo(final Principal principal, final UserInfoDto userInfoDto)
    {
        final User user = userService.findMe(principal);
        final UserInfo userInfo = user.getUserInfo();
        modelMapper.map(userInfoDto, userInfo);
        userInfo.setUser(user);
        save(userInfo);
        return userInfo;
    }


    @Transactional
    public double depositSum(final Principal principal, final Double depositSum)
    {
        final UserInfo userInfo = userService.findMe(principal).getUserInfo();
        final double finalMoneyAmount = userInfo.getDepositSum() + depositSum;
        userInfo.setDepositSum(finalMoneyAmount);
        save(userInfo);
        return finalMoneyAmount;
    }
}
