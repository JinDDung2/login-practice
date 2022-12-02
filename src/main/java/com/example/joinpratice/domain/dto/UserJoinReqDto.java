package com.example.joinpratice.domain.dto;

import com.example.joinpratice.domain.User;
import lombok.*;


@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinReqDto {

    private String username;
    private String password;
    private String email;

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .build();
    }

}
