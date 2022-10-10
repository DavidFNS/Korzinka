package online.korzinka.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidatorDto {
    private final String field;
    private final String message;
}
