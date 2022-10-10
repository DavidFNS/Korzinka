package online.korzinka.service.impl;
import lombok.RequiredArgsConstructor;
import online.korzinka.DateUtil;
import online.korzinka.dto.JWTResponseDto;
import online.korzinka.dto.LoginDto;
import online.korzinka.dto.ResponseDto;
import online.korzinka.dto.UserInfoDto;
import online.korzinka.entity.User;
import online.korzinka.repository.UserRepository;
import online.korzinka.security.JwtService;
import online.korzinka.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public static Map<Integer, UserInfoDto> usersMap = new HashMap<>();
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsername(username).get();

        UserInfoDto userInfoDto = userMapper.toDto(user);
        return userInfoDto;
    }

    public ResponseDto addUser(UserInfoDto userInfoDto){
        User user = userMapper.toEntity(userInfoDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return ResponseDto.builder()
                .code(200)
                .success(true)
                .message("Successfully saved")
                .build();
    }

    public ResponseDto<JWTResponseDto> login(LoginDto loginDto){
        User user = userRepository.findFirstByUsername(loginDto.getUsername()).orElseThrow(
                () ->
                        new UsernameNotFoundException(String.format("User with username %s not found",
                                loginDto.getUsername()))
        );

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Password is incorrect!");
        }

        try{
            usersMap.put(user.getId(), userMapper.toDto(user));

            String token = jwtService.generateToken(String.valueOf(user.getId()));

            return ResponseDto.<JWTResponseDto>builder()
                    .code(200)
                    .success(true)
                    .message("OK")
                    .data(new JWTResponseDto(token, DateUtil.OneDay(), null))
                    .build();

        }catch (Exception e){
            return ResponseDto.<JWTResponseDto>builder()
                    .code(-12)
                    .message(e.getMessage())
                    .build();
        }
    }

    public ResponseDto<UserInfoDto> checkToken(String token){
        Integer subject = Integer.valueOf(String.valueOf(jwtService.getClaim(token, "sub")));

        return ResponseDto.<UserInfoDto>builder()
                .code(200)
                .success(true)
                .message("OK")
                .data(usersMap.get(subject))
                .build();
    }
}