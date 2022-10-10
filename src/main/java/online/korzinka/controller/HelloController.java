package online.korzinka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
@Schema(name = "№1. Hello")
public class HelloController {

    @Tag(name="External", description = "Returns list of products by page and size")
    @Operation(summary = "№1.1 Hello by name")
    @GetMapping(value = "/by-name")
    public String hello(@RequestParam(value = "ism", required = true) String name,
                        @RequestParam Integer age){
        if (name.equals("Sardor"))
            return "Hello, theSardor";

        return "Hello, " + name;
    }

    @PostMapping
    @Operation(summary = "№1.2 Post hello")
    public String helloPost(){
        hello("", 1);
        return "hello post";
    }
}
