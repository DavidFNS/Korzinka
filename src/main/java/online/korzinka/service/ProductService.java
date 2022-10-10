package online.korzinka.service;

import online.korzinka.dto.ProductDto;
import online.korzinka.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ProductService {
    ResponseDto<String> addProduct(ProductDto dto);

    ResponseDto<ProductDto> findById(Integer id);

    ResponseDto<List<ProductDto>> getAllProducts();
    ResponseDto<Page<ProductDto>> getAllProductsByPage(Integer page, Integer size);

    ResponseDto<Page<ProductDto>> getProductsByParams(MultiValueMap<String, String> map);

    void export(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer id);
}
