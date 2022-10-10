package online.korzinka.controller;

import online.korzinka.dto.ResponseDto;
import online.korzinka.dto.ValidatorDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@ControllerAdvice
@RestController
public class ExceptionsHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseDto methodArgumentException(MethodArgumentNotValidException me){
        List<ValidatorDto> validatorDtoList = me.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    return ValidatorDto.builder()
                            .field(fieldError.getField())
                            .message(fieldError.getDefaultMessage())
                            .build();
                })
                .toList();

        return ResponseDto.builder()
                .code(200)
                .success(true)
                .message("Successfully failed!")
                .validatorDtoList(validatorDtoList)
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    public NullPointerException nullPointerException(NullPointerException exception) {
        return new NullPointerException("Sizda bunaqa xato bor -->" + exception.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseDto unauthorized(){
        return ResponseDto.builder()
                .code(-3)
                .success(false)
                .message("Error while authorization")
                .build();
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseDto usernameNotFound(UsernameNotFoundException exception){
        return ResponseDto.builder()
                .code(-2)
                .message(exception.getMessage())
                .success(false)
                .build();
    }
}
