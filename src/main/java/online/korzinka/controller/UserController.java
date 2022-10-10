package online.korzinka.controller;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import online.korzinka.dto.JWTResponseDto;
import online.korzinka.dto.LoginDto;
import online.korzinka.dto.ResponseDto;
import online.korzinka.dto.UserInfoDto;
import online.korzinka.service.impl.UserDetailServiceImpl;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Schema(name = "№1. UserAPI", description = "User API controllerClass")
public class UserController {
    private final UserDetailServiceImpl userDetailService;
    public UserController(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }
    @Operation(summary = "№1.1 Post new User")
    @PostMapping
    public ResponseDto addNewUser(@RequestBody @Valid UserInfoDto userInfoDto){
        return userDetailService.addUser(userInfoDto);
    }

    @Operation(summary = "№1.2 Logging in and getting JWT")
    @PostMapping("/login")
    public ResponseDto<JWTResponseDto> login(@RequestBody LoginDto loginDto){
        return userDetailService.login(loginDto);
    }

    @GetMapping
    public String sayHello(){
        return "Hello";
    }

//    @PostMapping(value = "/login", produces = "application/xml")
//    public void login(@RequestBody LoginDto loginDto, HttpServletResponse response) throws IOException {
//        ResponseDto<JWTResponseDto> jwtResponseDto =  userDetailService.login(loginDto);
//
//        ServletOutputStream outputStream = response.getOutputStream();
//        XmlMapper xmlMapper = new XmlMapper();
//        ObjectWriter writer = xmlMapper.writerFor(new TypeReference<ResponseDto<JWTResponseDto>>() {});
//        writer.writeValue(outputStream, jwtResponseDto);
//        outputStream.close();
//    }

    @GetMapping("/checkToken")
    @Hidden
    public ResponseDto<UserInfoDto> checkUserToken(@RequestParam String token){
        return userDetailService.checkToken(token);
    }
}