package online.korzinka.dto;

import lombok.*;
import online.korzinka.entity.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductTypeDto {

    private Integer id;
    @NotNull(message = "type null ga teng bo'masin!")
    @NotBlank
    private String name;
    private String barcode;
    private List<Product> products;
    private Integer unitId;
}
