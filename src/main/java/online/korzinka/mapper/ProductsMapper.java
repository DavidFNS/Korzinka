package online.korzinka.mapper;

import online.korzinka.dto.ProductDto;
import online.korzinka.entity.Product;

public class ProductsMapper {
    public static Product toEntity(ProductDto productDto){
        if (productDto == null){
            return null;
        }
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .amount(productDto.getAmount())
                .type(productDto.getType())
                .build();
    }

    public static ProductDto toDto(Product product){
        if (product == null){
            return null;
        }
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .amount(product.getAmount())
                .type(
                        ProductTypeMapper.toEntityWithoutList(
                                ProductTypeMapper.toDtoWithoutList(product.getType())
                        )
                )
                .build();
    }
    public static Product toEntityWithoutType(ProductDto productDto){
        if (productDto == null){
            return null;
        }
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .amount(productDto.getAmount())
                .build();
    }
    public static ProductDto toDtoWithoutType(Product product){
        if (product == null){
            return null;
        }
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .amount(product.getAmount())
                .build();
    }

}
