package com.blackJack.service;

import com.blackJack.dbo.User;
import com.blackJack.dbo.UserInfo;
import com.blackJack.repository.AbstractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
public class UserInfoService extends AbstractService<UserInfo> {
    private final UserService userService;

    public UserInfoService(final AbstractRepository<UserInfo> repository, final ModelMapper modelMapper, final UserService userService) {
        super(repository, modelMapper);
        this.userService = userService;
    }

    @Transactional
    public User addUserInfo(final Principal principal, final UserInfo userInfo) {
        final User user = userService.findMe(principal);
        userInfo.setUser(user);
        final UserInfo save = repository.save(userInfo);
        user.setUserInfo(save);
        return userService.save(user);
    }
}
