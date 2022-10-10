package online.korzinka.controller;
import lombok.RequiredArgsConstructor;
import online.korzinka.dto.ProductDto;
import online.korzinka.dto.ResponseDto;
import online.korzinka.service.impl.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    @GetMapping
//    @PreAuthorize("hasAnyRole('admin', 'super admin')")
    @PreAuthorize("hasAnyAuthority('read')")
    public ResponseDto<List<ProductDto>> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('read')")
    public ResponseDto<Page<ProductDto>> getAllProductPages(@RequestParam Integer page,
                                                            @RequestParam Integer size){
        return productService.getAllProductsByPage(page,size);
    }

    @PostMapping
    public ResponseDto addProduct(@RequestBody @Valid ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<ProductDto> findById(@PathVariable Integer id){
        return productService.findById(id);
    }

    @GetMapping("/byParams")
    public ResponseDto<Page<ProductDto>> getAllBooksByParams(@RequestParam
                                                                  MultiValueMap<String, String> map){
        return productService.getProductsByParams(map);
    }

    @GetMapping("/export-product")
    public void exportProduct(HttpServletResponse response, HttpServletRequest request, @RequestParam Integer id){
         productService.export(request, response, id);
    }
}
