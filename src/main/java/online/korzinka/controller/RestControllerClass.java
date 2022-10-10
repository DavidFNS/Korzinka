package online.korzinka.controller;

import online.korzinka.dto.ProductDto;
import online.korzinka.dto.ResponseDto;
import online.korzinka.service.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/remote")
public class RestControllerClass {
    private final RestService restService;

    public RestControllerClass(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getProductByIdFromOtherService(@PathVariable Integer id){
        restService.getProductById(id);
        return null;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ProductDto>>> getAllProducts(@RequestParam Integer page,
                                                                        @RequestParam Integer size){
        return restService.getAllProduct(page, size);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addNewProduct(@RequestBody ProductDto productDto){
        return restService.addNewProduct(productDto);
    }
}
