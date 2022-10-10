package online.korzinka.controller;

import lombok.RequiredArgsConstructor;
import online.korzinka.dto.ProductTypeDto;
import online.korzinka.dto.ResponseDto;
import online.korzinka.service.impl.ProductTypesServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product-type")
@RequiredArgsConstructor
public class ProductTypesController {

    private final ProductTypesServiceImpl productTypesService;

    @PostMapping
    public @ResponseBody
    ResponseDto<String> add(@RequestBody @Valid ProductTypeDto productTypeDto){
        return productTypesService.addProduct(productTypeDto);
    }

    @GetMapping
    public @ResponseBody
    ResponseDto<List<ProductTypeDto>> getAll(){
        return productTypesService.getAll();
    }

    @GetMapping("/check")
    public @ResponseBody
    String lazy(){
        return productTypesService.getWithLazy();
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseDto<ProductTypeDto> getOne(@PathVariable Integer id){
        return productTypesService.getOne(id);
    }
}
