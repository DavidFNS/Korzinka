package online.korzinka.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.korzinka.dto.ProductDto;
import online.korzinka.dto.ResponseDto;
import online.korzinka.entity.Product;
import online.korzinka.mapper.ProductsMapper;
import online.korzinka.repository.ProductRepository;
import online.korzinka.repository.impl.ProductRepositoryImpl;
import online.korzinka.service.ProductService;
import online.korzinka.service.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImpl productRepositoryImpl;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ExcelServiceImpl excelService;

    @Override
    public ResponseDto<String> addProduct(ProductDto dto) {
        try {
            productRepository.save(ProductsMapper.toEntity(dto));
            return ResponseDto.<String>builder().success(true).message("Muvaffaqiyatli saqlandi").build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder().success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<ProductDto> findById(Integer id){
        try {
            Optional<Product> productOptional = productRepository.findById(id);
            if (productOptional.isEmpty()){
                return ResponseDto.<ProductDto>builder().success(false).message("NOT FOUND").build();
            }
            Product product = productOptional.get();

            ProductDto productDto = ProductsMapper.toDto(product);

            return ResponseDto.<ProductDto>builder().success(true).message("OK").data(productDto).build();
        }catch (Exception e){
            return ResponseDto.<ProductDto>builder().success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<List<ProductDto>> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDtoList = productList.stream()
                .map(ProductsMapper::toDto)
                .toList();
        return ResponseDto.<List<ProductDto>>builder()
                .code(200)
                .success(true)
                .message("OK")
                .data(productDtoList)
                .build();
    }

    @Override

    public ResponseDto<Page<ProductDto>> getAllProductsByPage(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductDto> productDtoList = productRepository.findAll(pageRequest).map(ProductsMapper::toDto);
        return ResponseDto.<Page<ProductDto>>builder()
                .code(200)
                .success(true)
                .message("OK")
                .data(productDtoList)
                .build();
    }

    @Override
    public ResponseDto<Page<ProductDto>> getProductsByParams(MultiValueMap<String, String> map) {
        if(!map.containsKey("page") || !map.containsKey("size")){
            return ResponseDto.<Page<ProductDto>>builder()
                    .code(-2)
                    .success(false)
                    .message("Page or size is null!")
                    .build();
        }
        PageRequest page = PageRequest.of(Integer.parseInt(map.getFirst("page")),
                Integer.parseInt(map.getFirst("size")));
        Page<ProductDto> productDtoPage = productRepositoryImpl.findBooksByParams(page, map).map(ProductsMapper::toDto);

        return ResponseDto.<Page<ProductDto>>builder()
                .code(200)
                .success(true)
                .message("OK")
                .data(productDtoPage)
                .build();
    }

    @Transactional
    @Override
    public void export(HttpServletRequest request, HttpServletResponse response, Integer id) {
        Stream<ProductDto> productDtoList = productRepository.findAllByIdLessThan(id).map(ProductsMapper::toDto);

        try {
            excelService.exportProducts(productDtoList, request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
