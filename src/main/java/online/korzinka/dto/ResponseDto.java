package online.korzinka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto<T> {
    private int code;
    private boolean success;
    private String message;
    private T data;
    private List<ValidatorDto> validatorDtoList;
}
