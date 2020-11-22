package com.blackJack.dbo;


import com.blackJack.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Document(collection = "USER")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity
{

    @Field(name = "USER_ROLE")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Role> roles;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    @NotNull
    @JsonIgnore
    @Field("PASSWORD")
    private String password;

    @Field("ACTIVE")
    private boolean active;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @DBRef
    @JsonManagedReference
    private UserInfo userInfo;
}

