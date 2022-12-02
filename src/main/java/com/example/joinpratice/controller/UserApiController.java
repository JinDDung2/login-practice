package com.example.joinpratice.controller;

import com.example.joinpratice.domain.dto.UserJoinReqDto;
import com.example.joinpratice.domain.dto.UserJoinResDto;
import com.example.joinpratice.domain.dto.UserLoginResDto;
import com.example.joinpratice.service.UserLoginReqDto;
import com.example.joinpratice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    public Response join(@RequestBody UserJoinReqDto req) {
        UserJoinResDto user = userService.join(req);
        return Response.success(new UserJoinResDto(user.getUsername(), user.getEmail()));
    }

    @PostMapping("/login")
    public Response login(@RequestBody UserLoginReqDto req) {
        String token = userService.login(req.getUsername(), req.getPassword());
        return Response.success(new UserLoginResDto(token));
    }

}
