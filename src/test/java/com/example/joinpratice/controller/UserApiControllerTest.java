package com.example.joinpratice.controller;

import com.example.joinpratice.domain.dto.UserJoinReqDto;
import com.example.joinpratice.domain.dto.UserJoinResDto;
import com.example.joinpratice.exception.ErrorCode;
import com.example.joinpratice.exception.UserException;
import com.example.joinpratice.service.UserLoginReqDto;
import com.example.joinpratice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserApiController.class)
class UserApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    UserJoinReqDto userJoinReqDto = UserJoinReqDto.builder()
            .username("sojin")
            .password("pateko")
            .email("sojin7432@naver.com")
            .build();

    @Test
    @WithMockUser
    void 회원가입_성공() throws Exception {

        when(userService.join(any())).thenReturn(mock(UserJoinResDto.class));

        mockMvc.perform(post("/api/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinReqDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void 회원가입_실패() throws Exception {

        when(userService.join(any())).thenThrow(new UserException(ErrorCode.DUPLICATED_USERNAME, ""));

        mockMvc.perform(post("/api/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinReqDto)))
                .andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    @WithMockUser
    void 로그인_성공() throws Exception {
        // given
        final String username = "Heo1";
        final String password = "Heo1";
        UserLoginReqDto userLoginReqDto = new UserLoginReqDto(username, password);
        // when
        when(userService.login(any(), any())).thenReturn("token");
        // then
        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginReqDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void 로그인_실패_아이디_다름() throws Exception {
        // given
        final String username = "Heo100";
        final String password = "Heo1";
        UserLoginReqDto userLoginReqDto = new UserLoginReqDto(username, password);
        // when
        when(userService.login(any(), any()))
                .thenThrow(new UserException(ErrorCode.USERNAME_NOT_FOUND, ""));
        // then
        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginReqDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void 로그인_실패_패스워드_다름() throws Exception {
        // given
        final String username = "Heo1";
        final String password = "Heo100";
        UserLoginReqDto userLoginReqDto = new UserLoginReqDto(username, password);
        // when
        when(userService.login(any(), any()))
                .thenThrow(new UserException(ErrorCode.INVALID_PASSWORD, ""));
        // then
        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginReqDto)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}