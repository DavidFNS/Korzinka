package online.korzinka.service.mapper;

import online.korzinka.dto.ProductTypeDto;
import online.korzinka.entity.ProductTypes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTypesMapper {

    ProductTypes toEntity(ProductTypeDto productTypeDto);
    ProductTypeDto toDto(ProductTypes productTypes);
}
