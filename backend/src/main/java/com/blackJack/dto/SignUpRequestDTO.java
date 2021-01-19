package com.blackJack.dto;


import java.util.List;

import com.blackJack.enumeration.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDTO {
    private List<Role> roles;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String streetAddress;
    private boolean active;
}
