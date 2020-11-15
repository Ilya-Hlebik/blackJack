package com.blackJack.dbo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "USER_INFO")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends AbstractEntity {
    @Indexed(name = "NAME")
    private String name;

    @Field(name = "PHONE")
    private String phone;

    @Field(name = "EMAIL")
    private String email;

    @Field(name = "CITY")
    private String city;

    @Field(name = "STREET_ADDRESS")
    private String streetAddress;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @DBRef
    private User user;
}
