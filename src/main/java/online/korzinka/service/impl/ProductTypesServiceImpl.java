package online.korzinka.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.korzinka.dto.ProductTypeDto;
import online.korzinka.dto.ResponseDto;
import online.korzinka.entity.ProductTypes;
import online.korzinka.mapper.ProductTypeMapper;
import online.korzinka.repository.ProductTypesRepository;
import online.korzinka.repository.impl.ProductTypeRepositoryImpl;
import online.korzinka.service.ProductTypesService;
import online.korzinka.service.mapper.ProductTypesMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductTypesServiceImpl implements ProductTypesService {

    private final ProductTypesRepository repository;
    private final ProductTypesMapper mapper;
    private final ProductTypeRepositoryImpl repositoryImpl;


    @Override
    public ResponseDto<String> addProduct(ProductTypeDto dto) {
        try {
            repository.save(mapper.toEntity(dto));
            return ResponseDto.<String>builder().success(true).message("Muvaffaqiyatli saqlandi").build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder().success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<List<ProductTypeDto>> getAll() {
//        List<ProductTypes> productTypes = repository.findAll();
        List<ProductTypes> productTypes = repositoryImpl.findAllTypesUsingNativeQuery();

        List<ProductTypeDto> productTypeDto = productTypes.stream()
                .map(ProductTypeMapper::toDto)
                .collect(Collectors.toList());

        return ResponseDto.<List<ProductTypeDto>>builder().message("OK").success(true).data(productTypeDto).build();
    }

    public String getWithLazy(){
        List<ProductTypes> productTypes = repository.findAll();
        System.out.println(productTypes.get(0).getName());

        return "ok";
    }

    @Override
    public ResponseDto<ProductTypeDto> getOne(Integer id) {
        if (repository.existsById(id)){
//            ProductTypes productType = repository.findById(id).get();
            ProductTypes productType = repositoryImpl.findByIdUsingHQL(id).get(0);
            return ResponseDto.<ProductTypeDto>builder()
                    .message("OK")
                    .success(true)
                    .data(ProductTypeMapper.toDto(productType))
                    .build();
        }
        return ResponseDto.<ProductTypeDto>builder().message("NOT FOUND").data(null).success(false).build();
    }
}
