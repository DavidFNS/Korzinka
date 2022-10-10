package online.korzinka.service.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import online.korzinka.dto.ProductDto;
import online.korzinka.dto.ResponseDto;
import online.korzinka.entity.Product;
import online.korzinka.entity.ProductTypes;
import online.korzinka.mapper.ProductsMapper;
import online.korzinka.repository.ProductRepository;
import online.korzinka.repository.impl.ExcelRepositoryImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
@Slf4j
@Data
public class ExcelServiceImpl {
    private static Sheet sheet;

    public static void setSheet(Sheet sheet) {
        ExcelServiceImpl.sheet = sheet;
    }

    private final ProductRepository repository;
    private final ExcelRepositoryImpl excelRepository;

    public ExcelServiceImpl(ProductRepository repository, ExcelRepositoryImpl excelRepository) {
        this.repository = repository;
        this.excelRepository = excelRepository;
    }

    public static void main(String[] args) throws IOException {
        List<ProductDto> list = read();

        ExcelServiceImpl.writeFromStaticToFile();
    }

    public static List<ProductDto> read() throws IOException {
        File file = new File("C:/Users/User/Downloads/Excel.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

//        Sheet sheet = workbook.getSheetAt(0);
        Sheet sheet1 = workbook.getSheet("Лист1");
        List<ProductDto> productDtoList = new ArrayList<>();
        boolean bool = true;

        for (Row row : sheet1){
            if (bool){
                System.out.println("Row Names: ");
                System.out.println(row.getCell(0).getStringCellValue());
                System.out.println(row.getCell(1).getStringCellValue());
                System.out.println(row.getCell(2).getStringCellValue());
                System.out.println(row.getCell(3).getStringCellValue());
                bool = false;
                continue;
            }

            ProductDto product = new ProductDto();
            product.setId((int)row.getCell(0).getNumericCellValue());
            product.setName(row.getCell(1).getStringCellValue());
            product.setPrice(row.getCell(2).getNumericCellValue());
            product.setAmount((int)row.getCell(3).getNumericCellValue());
            productDtoList.add(product);
            System.out.println(product);
            System.out.println("-----------");
        }

        return productDtoList;
    }

    public static void writeFromStaticToFile() throws IOException {
        File file = new File(ExcelServiceImpl.class.getClassLoader().getResource("templates/Excel.xlsx").getPath());
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);

        Sheet sheet = workbook.getSheet("my sheet");
        Row row = sheet.createRow(sheet.getLastRowNum()+1);
        row.createCell(0).setCellValue(130);
        row.createCell(1).setCellValue("handalak");
        row.createCell(2).setCellValue(2500);
        row.createCell(3).setCellValue(150);
        row.createCell(4).setCellValue(7);

        File file1 = new File("src/main/resources/templates/excel3.xlsx");
        file1.createNewFile();

        FileOutputStream outputStream = new FileOutputStream(file1);
        workbook.write(outputStream);
    }

    public ResponseDto writeToExcelFile(MultiValueMap<String, String> map) {
        if (!map.containsKey("page") || !map.containsKey("size")){
            return ResponseDto.builder().code(-2).success(false).message("Page or size is null").build();
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(map.getFirst("page")),
                Integer.parseInt(map.getFirst("size")));

        Page<ProductDto> productList = excelRepository.getAllProductsByParams(pageable, map).map(ProductsMapper::toDto);
        XSSFWorkbook workbook;

        try {
            workbook = new XSSFWorkbook(getClass().getClassLoader().getResource("static/Excel.xlsx").getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Sheet sheet = workbook.createSheet();
        Sheet sheet1 = workbook.createSheet();

        sheet1.createRow(0);

        for (ProductDto product: productList){
            Row row = sheet.createRow(sheet.getLastRowNum()+1);

            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getPrice());
            row.createCell(3).setCellValue(product.getAmount());
            row.createCell(4).setCellValue(product.getType().getId());
        }

        File file = new File("src/main/resources/templates/Excel.xlsx");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            return ResponseDto.builder().success(false).message("FileNotFound").build();
        }
        try {
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseDto.builder().success(false).message("IOExeption while writing to excelFile").build();
        }

        return ResponseDto.builder().code(200).success(true).message("OK").build();
    }

    public XSSFWorkbook getExcelFile(MultipartFile multipartFile){
        XSSFWorkbook xssfWorkbook;
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            insertIntoTable(xssfWorkbook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return xssfWorkbook;
    }

    public void insertIntoTable(XSSFWorkbook xssfWorkbook){
        Sheet sheet = xssfWorkbook.getSheetAt(0);
        boolean bool = true;

        for (Row row: sheet){
            if (bool){
                bool = false;
                continue;
            }
            Product product = new Product();
            product.setId((int)row.getCell(0).getNumericCellValue());
            product.setName(row.getCell(1).getStringCellValue());
            product.setPrice(row.getCell(2).getNumericCellValue());
            product.setAmount((int)row.getCell(3).getNumericCellValue());
            ProductTypes type = new ProductTypes();
            type.setId((int)row.getCell(4).getNumericCellValue());
            product.setType(type);
            repository.save(product);
        }

        System.out.println("Excel file successfully updated!");
    }

    public void exportProducts(Stream<ProductDto> productDtoList, HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File(getClass().getClassLoader().getResource("templates/Products.xlsx").getPath());
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));

        SXSSFWorkbook workbook = new SXSSFWorkbook(wb, 1000);
        sheet = workbook.getSheetAt(0);
        AtomicInteger integer = new AtomicInteger(1);

        AtomicInteger atomicInteger = new AtomicInteger(2);
        productDtoList.forEach(productDto ->  {

            if (sheet.getLastRowNum() > 1_000_000){
                setSheet(workbook.createSheet());
                atomicInteger.set(0);

                Row row1 = sheet.createRow(atomicInteger.getAndIncrement());

                row1.createCell(0).setCellValue("№");
                row1.createCell(1).setCellValue("Nomi");
                row1.createCell(2).setCellValue("Turi");
                row1.createCell(3).setCellValue("Narxi");
                row1.createCell(4).setCellValue("Miqdori");
            }

            Row row = sheet.createRow(atomicInteger.getAndIncrement());
            row.createCell(0).setCellValue(integer.getAndIncrement());
            row.createCell(1).setCellValue(productDto.getName());
            row.createCell(2).setCellValue(productDto.getType().getName());
            row.createCell(3).setCellValue(productDto.getPrice());
            row.createCell(4).setCellValue(productDto.getAmount());
        });

        response.setContentType("application/vnd.openxmlformats-officedocument.spreedsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"products1.xlsx\"");

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
