package online.korzinka.mapper;

import online.korzinka.dto.ProductTypeDto;
import online.korzinka.entity.Product;
import online.korzinka.entity.ProductTypes;

import java.util.List;

public class ProductTypeMapper {
    public static ProductTypes toEntity(ProductTypeDto productType){
        if (productType == null){
            return null;
        }
        return ProductTypes.builder()
                .id(productType.getId())
                .name(productType.getName())
                .unitId(productType.getUnitId())
                .barcode(productType.getBarcode())
                .products(productType.getProducts())
                .build();
    }
    public static ProductTypeDto toDto(ProductTypes productType){
        if (productType == null){
            return null;
        }

        List<Product> list = productType.getProducts().stream()
                .map(p ->
                        ProductsMapper.toEntityWithoutType(
                                ProductsMapper.toDtoWithoutType(p)
                        )
                )
                .toList();
        return ProductTypeDto.builder()
                .id(productType.getId())
                .name(productType.getName())
                .unitId(productType.getUnitId())
                .barcode(productType.getBarcode())
                .products(list)
                .build();
    }

    public static ProductTypes toEntityWithoutList(ProductTypeDto productType){
        if (productType == null){
            return null;
        }
        return ProductTypes.builder()
                .id(productType.getId())
                .name(productType.getName())
                .unitId(productType.getUnitId())
                .barcode(productType.getBarcode())
                .build();
    }
    public static ProductTypeDto toDtoWithoutList(ProductTypes productType){
        if (productType == null){
            return null;
        }
        return ProductTypeDto.builder()
                .id(productType.getId())
                .name(productType.getName())
                .unitId(productType.getUnitId())
                .barcode(productType.getBarcode())
                .build();
    }
}
