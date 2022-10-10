package online.korzinka.service.mapper;

import online.korzinka.dto.ProductDto;
import online.korzinka.entity.Product;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);
}
