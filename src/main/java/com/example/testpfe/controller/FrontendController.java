package com.example.testpfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/frontend")
public class FrontendController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/compile")
    public String compileCodeFromFrontend(@RequestBody String code) {
        String compilerApiUrl = "http://localhost:8082/api/compiler/compile";
        return restTemplate.postForObject(compilerApiUrl, code, String.class);
    }
}
