package online.korzinka.service;

import online.korzinka.dto.ProductTypeDto;
import online.korzinka.dto.ResponseDto;
import java.util.List;

public interface ProductTypesService {
    ResponseDto<String> addProduct(ProductTypeDto dto);

    ResponseDto<List<ProductTypeDto>> getAll();

    ResponseDto<ProductTypeDto> getOne(Integer id);
}
