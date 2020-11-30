package com.blackJack.controller;

import com.blackJack.dbo.User;
import com.blackJack.dto.ForgotPasswordRequestDto;
import com.blackJack.dto.PasswordUpdateRequestDto;
import com.blackJack.dto.SignInRequestDTO;
import com.blackJack.dto.SignUpRequestDTO;
import com.blackJack.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signin")
    public User login(@ApiParam("UserName and Password") @RequestBody final SignInRequestDTO signInRequestDTO,
                      final HttpServletResponse res) {
        return userService.signIn(signInRequestDTO, res);
    }

    @PostMapping("/signup")
    public User signUp(@ApiParam("Signup User") @Valid @RequestBody final SignUpRequestDTO user) {
        return userService.signUp(user, true);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User create(@ApiParam("Create User manually by admin") @RequestBody final SignUpRequestDTO user) {
        return userService.signUp(user, false);
    }

    @PutMapping("/update_pass")
    public User updatePassword(@ApiParam("Password") @Valid @RequestBody final PasswordUpdateRequestDto user) {
        return userService.updatePassword(user);
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@ApiParam("Username") @PathVariable final String username) {
        userService.delete(username);
        return username;
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User search(@ApiParam("Username") @PathVariable final String username) {
        return userService.search(username);
    }

    @GetMapping(value = "/me")
    public User findMe(final Principal principal) {
        return userService.findMe(principal);
    }

    @GetMapping(value = "/logout")
    public void logOut(final HttpServletRequest req) {
        userService.logOut(req);
    }

    @PutMapping(value = "/update_pass/without_previous")
    public ResponseEntity<User> updatePassword(@ApiParam(
        "Update Password") @Valid @RequestBody final ForgotPasswordRequestDto forgotPasswordRequestDto) {
        return userService.updatePassword(forgotPasswordRequestDto);
    }
}
