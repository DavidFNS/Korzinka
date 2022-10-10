package online.korzinka.service;

import online.korzinka.dto.ProductDto;
import online.korzinka.dto.ResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestService {
    public void getProductById(Integer id){
        RestTemplate restTemplate = new RestTemplate();
        while(true){
            ResponseEntity<ResponseDto> response = restTemplate.exchange("http://192.168.6.36:8080/api/product/" + id,
                    HttpMethod.GET,
                    null, ResponseDto.class);
        }
//        return response;
    }

    public ResponseEntity<ResponseDto<List<ProductDto>>> getAllProduct(Integer page, Integer size){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseDto<List<ProductDto>>> responseEntity =
                restTemplate.exchange(String.format("http://192.168.6.61:8888/product?page=%d&size=%d",page, size),
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseDto<List<ProductDto>>>() {});
        return responseEntity;
    }

    public ResponseEntity<ResponseDto> addNewProduct(ProductDto productDto){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity("http://192.168.6.61:8888/product", productDto, ResponseDto.class);
    }

}
