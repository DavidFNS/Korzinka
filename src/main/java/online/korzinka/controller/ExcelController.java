package online.korzinka.controller;

import lombok.extern.slf4j.Slf4j;
import online.korzinka.dto.ResponseDto;
import online.korzinka.service.impl.ExcelServiceImpl;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {
    private final ExcelServiceImpl excelService;

    public ExcelController(ExcelServiceImpl excelService) {
        this.excelService = excelService;
    }

    @PostMapping()
    public void getExcelFile(@ModelAttribute MultipartFile multipartFile){
        if (multipartFile == null){
            log.error("File is null");
        }

        excelService.getExcelFile(multipartFile);
        log.info("Successfully!");
    }

    @GetMapping
    public ResponseDto getAllProductsByParams(@RequestParam MultiValueMap<String, String> map){
        return excelService.writeToExcelFile(map);
    }
}
