package online.korzinka.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.korzinka.entity.ProductTypes;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Schema(name = "")
public class ProductDto {
    SecurityContextHolder securityContextHolder;
    SecurityContext securityContext;

    private Integer id;
    @NotBlank(message = "имя продукта не должно быть пустым!")
    @NotNull(message = "имя продукта не должно быть равен нулю")
    private String name;
    private ProductTypes type;

    private Integer amount;
    @Min(value = 1000, message = "Product must be higher or equal 1000")
    @Max(value = 200000, message = "Product must be lower or equal 200000")
    private Double price;
    public String toString(){
        return String.format("Id: %d\n" +
                            "Name_product: %s\n" +
                            "Price: %.2f\n" +
                            "Amount: %d", getId(), getName(), getPrice(), getAmount());
    }
}
