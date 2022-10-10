package online.korzinka.service.modalMapper;

import lombok.RequiredArgsConstructor;
import online.korzinka.dto.ProductDto;
import online.korzinka.entity.Product;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class ProductMapperM {
    private final ModelMapper mapper;

    public Product toEntity(ProductDto productDto){
        return mapper.map(productDto, Product.class);
    }

    public ProductDto toDto(Product product){
        return mapper.map(product, ProductDto.class);
    }
}
