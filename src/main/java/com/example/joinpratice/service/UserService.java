package com.example.joinpratice.service;

import com.example.joinpratice.domain.User;
import com.example.joinpratice.domain.dto.UserJoinReqDto;
import com.example.joinpratice.domain.dto.UserJoinResDto;
import com.example.joinpratice.exception.ErrorCode;
import com.example.joinpratice.exception.UserException;
import com.example.joinpratice.repository.UserRepository;
import com.example.joinpratice.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("{jwt.token.secret}")
    private String secretKey;
    private long expireTimes = 1000 * 60 * 60;

    public UserJoinResDto join(UserJoinReqDto request) {
        // 중복확인
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        optionalUser.ifPresent((user) -> {
            throw new UserException(ErrorCode.DUPLICATED_USERNAME, "useranme: " + request.getUsername() + "이 중복입니다");
        });

        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();
        User savedUser = userRepository.save(user);
        return UserJoinResDto.builder()
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    throw new UserException(ErrorCode.USERNAME_NOT_FOUND, username + "이 없습니다.");
                });
        log.info("id={}, pwd={}", user.getUsername(), user.getPassword());
        if (!encoder.matches(password, user.getPassword())) {
            throw new UserException(ErrorCode.INVALID_PASSWORD, "password가 다릅니다.");
        }

        return JwtTokenUtil.createToken(username, secretKey, expireTimes);
    }

}
